package net.lomeli.aod.util;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

import net.lomeli.aod.AOD;

public class LogUtil {

    public static void log(Level level, String message, Object... data) {
        FMLLog.log(AOD.MOD_ID, level, message, data);
    }

    public static void logInfo(String message, Object... data) {
        log(Level.INFO, message, data);
    }

    public static void logWarn(String message, Object... data) {
        log(Level.WARN, message, data);
    }

    public static void logError(String message, Object... data) {
        log(Level.ERROR, message, data);
    }
}
