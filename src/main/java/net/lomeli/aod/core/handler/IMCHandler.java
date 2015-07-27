package net.lomeli.aod.core.handler;

import java.util.List;

import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

import net.lomeli.aod.util.LogUtil;

public class IMCHandler {
    private static final String blackListKey = "boss_black_list";

    public static void processIMCMessages(List<IMCMessage> imcMessages) {
        if (imcMessages != null && imcMessages.size() > 0) {
            for (IMCMessage message : imcMessages) {
                if (message != null && message.isStringMessage() && blackListKey.equalsIgnoreCase(message.key)) {
                    ModEventHandler.mobBlackList.add(message.getStringValue());
                    LogUtil.logInfo("%s has blacklisted %s", message.getSender(), message.getStringValue());
                }
            }
        }
    }
}
