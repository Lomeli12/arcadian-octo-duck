package net.lomeli.aod.core.handler;

import java.util.List;

import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

import net.lomeli.aod.util.LogUtil;

public class IMCHandler {
    private static final String blackListKey = "boss_black_list";
    private static final String whiteListKey = "boss_white_list";

    public static void processIMCMessages(List<IMCMessage> imcMessages) {
        if (imcMessages != null && imcMessages.size() > 0) {
            for (IMCMessage message : imcMessages) {
                if (message != null && message.isStringMessage()) {
                    if (blackListKey.equalsIgnoreCase(message.key)) {
                        ModEventHandler.mobBlackList.add(message.getStringValue());
                        LogUtil.logInfo("%s has blacklisted %s", message.getSender(), message.getStringValue());
                    } else if (whiteListKey.equalsIgnoreCase(message.key)) {
                        ModEventHandler.mobWhiteList.add(message.getStringValue());
                        LogUtil.logInfo("%s has whitelisted %s", message.getSender(), message.getStringValue());
                    }
                }
            }
        }
    }
}
