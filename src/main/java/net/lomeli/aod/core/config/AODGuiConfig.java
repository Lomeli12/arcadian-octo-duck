package net.lomeli.aod.core.config;

import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.config.GuiConfig;

import net.lomeli.aod.AOD;

public class AODGuiConfig extends GuiConfig {
    public AODGuiConfig(GuiScreen parent) {
        super(parent, new ConfigElement(ModConfig.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), AOD.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ModConfig.config.toString()));
    }
}
