package net.lomeli.aod.core.config;

import com.google.common.base.Strings;

import net.minecraft.util.StatCollector;

import net.minecraftforge.common.config.Configuration;

import net.lomeli.aod.core.handler.ModEventHandler;
import net.lomeli.aod.util.LogUtil;

public class ModConfig {
    public static EnumDifficulty difficulty = EnumDifficulty.NORMAL;
    public static boolean regainHeart = true;
    public static Configuration config;

    public static void loadConfig() {
        String cat = config.CATEGORY_GENERAL;
        int i = config.getInt("difficulty", cat, 0, 0, 2, StatCollector.translateToLocal("config.aod.difficulty"));
        difficulty = EnumDifficulty.getDifficulty(i);
        regainHeart = config.getBoolean("regainHeart", cat, true, StatCollector.translateToLocal("config.aod.regainHeart"));
        String blackList = config.getString("blackList", cat, "", StatCollector.translateToLocal("config.aod.blackList"));
        String whiteList = config.getString("whiteList", cat, "", StatCollector.translateToLocal("config.aod.whiteList"));

        if (!Strings.isNullOrEmpty(blackList)) {
            String[] entities = blackList.split(";");
            if (entities != null && entities.length > 0) {
                for (String clazz : entities) {
                    if (Strings.isNullOrEmpty(clazz))
                        continue;
                    LogUtil.logInfo("%s has been blacklisted via configs", clazz);
                    ModEventHandler.mobBlackList.add(clazz);
                }
            }
        }

        if (!Strings.isNullOrEmpty(whiteList)) {
            String[] entities = whiteList.split(";");
            if (entities != null && entities.length > 0) {
                for (String clazz : entities) {
                    if (Strings.isNullOrEmpty(clazz))
                        continue;
                    LogUtil.logInfo("%s has been whitelisted via configs", clazz);
                    ModEventHandler.mobWhiteList.add(clazz);
                }
            }
        }

        if (config.hasChanged())
            config.save();
    }
}
