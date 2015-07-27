package net.lomeli.aod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.lomeli.aod.core.ModConfig;
import net.lomeli.aod.core.handler.EventHandlerServer;
import net.lomeli.aod.core.handler.IMCHandler;

@Mod(modid = AOD.MOD_ID, name = AOD.NAME, version = AOD.VERSION, acceptedMinecraftVersions = AOD.MC_VERSION)
public class AOD {
    public static final String MOD_ID = "aod";
    public static final String NAME = "Arcadian Octo Duck";
    public static final int MAJOR = 1, MINOR = 0, REV = 0;
    public static final String VERSION = MAJOR + "." + MINOR + "." + REV;
    public static final String MC_VERSION = "1.7.10";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModConfig.config = new Configuration(event.getSuggestedConfigurationFile());
        ModConfig.loadConfig();

        EventHandlerServer eventHandlerServer = new EventHandlerServer();
        MinecraftForge.EVENT_BUS.register(eventHandlerServer);
        FMLCommonHandler.instance().bus().register(eventHandlerServer);
    }

    @Mod.EventHandler
    public void imcMessage(FMLInterModComms.IMCEvent event) {
        IMCHandler.processIMCMessages(event.getMessages());
    }
}
