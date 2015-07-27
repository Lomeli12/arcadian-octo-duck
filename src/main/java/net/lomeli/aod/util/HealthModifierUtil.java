package net.lomeli.aod.util;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;

import net.lomeli.aod.AOD;
import net.lomeli.aod.core.config.ModConfig;

public class HealthModifierUtil {
    public static final UUID healthUUID = UUID.fromString("80987510-3419-11E5-A2CB-0800200C9A66");
    public static final String modifierTag = AOD.NAME + " health modifier";
    public static final String heartTag = AOD.MOD_ID + "_heart_count";

    /**
     * Only used when getting player's true health
     */
    public static void removeModifier(EntityPlayer player) {
        if (player == null) return;
        removeModifier(player.getEntityAttribute(SharedMonsterAttributes.maxHealth));
    }

    private static void removeModifier(IAttributeInstance attributeInstance) {
        if (attributeInstance == null) return;
        AttributeModifier modifier = attributeInstance.getModifier(healthUUID);
        if (modifier != null)
            attributeInstance.removeModifier(modifier);
    }

    public static void applyModifier(EntityPlayer player, int amount) {
        IAttributeInstance attributeInstance = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        double health = player.getMaxHealth() - ModConfig.difficulty.heartLoss(amount, player.getMaxHealth());
        if (amount != 0)
            attributeInstance.applyModifier(new AttributeModifier(healthUUID, modifierTag, health, 0));
        setHeartCount(player, amount);
    }

    public static void setHeartCount(EntityPlayer player, int amount) {
        if (player != null && !FakePlayerUtil.isFakePlayer(player))
            player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setInteger(heartTag, amount);
    }

    public static int getHeartCount(EntityPlayer player) {
        if (player != null && !FakePlayerUtil.isFakePlayer(player))
            return player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getInteger(heartTag);
        return 0;
    }
}
