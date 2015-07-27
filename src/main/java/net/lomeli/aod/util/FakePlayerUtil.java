package net.lomeli.aod.util;

import java.util.regex.Pattern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraftforge.common.util.FakePlayer;

public class FakePlayerUtil {
    private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");

    public static boolean isFakePlayer(EntityPlayer player) {
        return player != null ? !(player instanceof EntityPlayerMP) || (player instanceof FakePlayer) || FAKE_PLAYER_PATTERN.matcher(player.getCommandSenderName()).matches() : false;
    }
}
