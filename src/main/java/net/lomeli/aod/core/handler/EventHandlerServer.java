package net.lomeli.aod.core.handler;

import com.google.common.collect.Lists;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldSettings;

import net.minecraftforge.event.entity.living.LivingDeathEvent;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import net.lomeli.aod.util.FakePlayerUtil;
import net.lomeli.aod.util.HealthModifierUtil;

public class EventHandlerServer {
    public static List<String> mobBlackList = Lists.newArrayList();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerDeath(LivingDeathEvent event) {
        if (!event.entityLiving.worldObj.isRemote && (event.entityLiving instanceof EntityPlayer && !FakePlayerUtil.isFakePlayer((EntityPlayer) event.entityLiving))) {
            EntityPlayerMP player = (EntityPlayerMP) event.entityLiving;
            Entity damageSource = getDamageSource(event.source);
            if (player.theItemInWorldManager.getGameType() == WorldSettings.GameType.CREATIVE)
                return;
            if (damageSource != null && damageSource instanceof IBossDisplayData && !mobBlackList.contains(damageSource.getClass()))
                HealthModifierUtil.setHeartCount(player, HealthModifierUtil.getHeartCount(player) + 1);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.player.worldObj.isRemote && !FakePlayerUtil.isFakePlayer(event.player)) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            int bossDeaths = HealthModifierUtil.getHeartCount(player);

        }
    }

    private static Entity getDamageSource(DamageSource source) {
        return source.isProjectile() ? source.getEntity() : source.getSourceOfDamage();
    }
}
