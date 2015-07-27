package net.lomeli.aod.core.handler;

import com.google.common.collect.Lists;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

import net.minecraftforge.event.entity.living.LivingDeathEvent;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.aod.AOD;
import net.lomeli.aod.core.config.ModConfig;
import net.lomeli.aod.util.PlayerUtil;
import net.lomeli.aod.util.HealthModifierUtil;

public class ModEventHandler {
    public static List<String> mobBlackList = Lists.newArrayList();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerDeath(LivingDeathEvent event) {
        if (!event.entityLiving.worldObj.isRemote && (event.entityLiving instanceof EntityPlayer && !PlayerUtil.isFakePlayer((EntityPlayer) event.entityLiving))) {
            EntityPlayerMP player = (EntityPlayerMP) event.entityLiving;
            Entity damageSource = getDamageSource(event.source);
            // Removed temporarily for debugging
            //if (player.theItemInWorldManager.getGameType() == WorldSettings.GameType.CREATIVE)
            //    return;
            if (damageSource != null && damageSource instanceof IBossDisplayData && !mobBlackList.contains(damageSource.getClass())) {
                int deathCount = HealthModifierUtil.getDeathCount(player) + 1;
                HealthModifierUtil.setDeathCount(player, deathCount);
                float playerHealth = PlayerUtil.getHealthWithoutMod(player);
                if (playerHealth <= ModConfig.difficulty.heartLoss(deathCount, playerHealth)) {
                    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                    if (server != null) {
                        if (server.isSinglePlayer() && player.getCommandSenderName().equals(server.getServerOwner())) {
                            player.playerNetServerHandler.kickPlayerFromServer(StatCollector.translateToLocal("message.aod.reasonKick"));
                            server.logSevere("Player lost all hearts, time to die.");
                            server.deleteWorldAndStopServer();
                        } else {
                            UserListBansEntry userBanList = new UserListBansEntry(player.getGameProfile(), null, AOD.NAME, null, StatCollector.translateToLocal("message.aod.reason"));
                            server.getConfigurationManager().func_152608_h().func_152687_a(userBanList);
                            player.playerNetServerHandler.kickPlayerFromServer(StatCollector.translateToLocal("message.aod.reasonKick"));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.player.worldObj.isRemote && !PlayerUtil.isFakePlayer(event.player)) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            int deathCount = HealthModifierUtil.getDeathCount(player);
            float playerHealth = PlayerUtil.getHealthWithoutMod(player);
            if (playerHealth > ModConfig.difficulty.heartLoss(deathCount, playerHealth))
                HealthModifierUtil.applyModifier(player, deathCount);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void configChanged(ConfigChangedEvent event) {
        if (AOD.MOD_ID.equals(event.modID))
            ModConfig.loadConfig();
    }

    private Entity getDamageSource(DamageSource source) {
        return source.isProjectile() ? source.getEntity() : source.getSourceOfDamage();
    }
}
