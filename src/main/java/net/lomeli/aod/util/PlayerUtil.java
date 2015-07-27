package net.lomeli.aod.util;

import java.util.regex.Pattern;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraftforge.common.util.FakePlayer;

public class PlayerUtil {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");

    public static boolean isFakePlayer(EntityPlayer player) {
        return player != null ? !(player instanceof EntityPlayerMP) || (player instanceof FakePlayer) || FAKE_PLAYER_PATTERN.matcher(player.getCommandSenderName()).matches() : false;
    }

    public static float getHealthWithoutMod(EntityPlayer player) {
        if (player == null || isFakePlayer(player))
            return 20f;
        IAttributeInstance attributeInstance = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        AttributeModifier mod = attributeInstance.getModifier(HealthModifierUtil.healthUUID);
        return (float) (attributeInstance.getAttributeValue() - (mod == null ? 0d : mod.getAmount()));
    }
}
