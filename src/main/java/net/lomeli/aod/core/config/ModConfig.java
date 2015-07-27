package net.lomeli.aod.core.config;

import net.minecraft.util.StatCollector;

import net.minecraftforge.common.config.Configuration;

public class ModConfig {
    public static EnumDifficulty difficulty = EnumDifficulty.NORMAL;
    public static Configuration config;

    public static void loadConfig() {
        String cat = config.CATEGORY_GENERAL;
        int i = config.getInt("difficulty", cat, 1, 0, 3, StatCollector.translateToLocal("config.aod.difficulty"));
        difficulty = EnumDifficulty.getDifficulty(i);

        if (config.hasChanged())
            config.save();
    }
}
