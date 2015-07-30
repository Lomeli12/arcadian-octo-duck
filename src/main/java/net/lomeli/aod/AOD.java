package net.lomeli.aod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.lomeli.aod.core.config.ModConfig;
import net.lomeli.aod.core.handler.ModEventHandler;
import net.lomeli.aod.core.handler.IMCHandler;
import net.lomeli.aod.util.LogUtil;

@Mod(modid = AOD.MOD_ID, name = AOD.NAME, version = AOD.VERSION, acceptedMinecraftVersions = AOD.MC_VERSION, guiFactory = AOD.FACTORY)
public class AOD {
    public static final String MOD_ID = "aod";
    public static final String NAME = "Arcadian Octo Duck";
    public static final int MAJOR = 1, MINOR = 0, REV = 1;
    public static final String VERSION = MAJOR + "." + MINOR + "." + REV;
    public static final String MC_VERSION = "1.7.10";
    public static final String FACTORY = "net.lomeli.aod.core.config.AODGuiConfigFactory";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogUtil.logInfo("Pre-Initialization");
        ModConfig.config = new Configuration(event.getSuggestedConfigurationFile());
        ModConfig.loadConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LogUtil.logInfo("Initialization");
        ModEventHandler modEventHandler = new ModEventHandler();
        MinecraftForge.EVENT_BUS.register(modEventHandler);
        FMLCommonHandler.instance().bus().register(modEventHandler);
    }

    @Mod.EventHandler
    public void imcMessage(FMLInterModComms.IMCEvent event) {
        LogUtil.logInfo("Handling IMC Messages");
        IMCHandler.processIMCMessages(event.getMessages());
    }
}
