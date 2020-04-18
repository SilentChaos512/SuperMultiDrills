package net.silentchaos512.supermultidrills;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.silentchaos512.supermultidrills.client.ColorHandlers;
import net.silentchaos512.supermultidrills.init.ModItems;
import net.silentchaos512.supermultidrills.init.ModRecipeStuff;
import net.silentchaos512.supermultidrills.part.BatteryPart;
import net.silentchaos512.supermultidrills.part.ChassisPart;
import net.silentchaos512.supermultidrills.part.MotorPart;

import javax.annotation.Nullable;

class SideProxy implements IProxy {
    @Nullable private static MinecraftServer server;

    SideProxy() {
        // Just need to class-load part types to register them
        SuperMultiDrills.LOGGER.info("Register part type: {}", BatteryPart.TYPE);
        SuperMultiDrills.LOGGER.info("Register part type: {}", ChassisPart.TYPE);
        SuperMultiDrills.LOGGER.info("Register part type: {}", MotorPart.TYPE);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::imcEnqueue);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::imcProcess);

//        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModBlocks::registerAll);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModContainers::registerAll);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModEntities::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ModItems::registerAll);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModTileEntities::registerAll);

        MinecraftForge.EVENT_BUS.addListener(SideProxy::serverAboutToStart);
        MinecraftForge.EVENT_BUS.addListener(SideProxy::serverStarted);
        MinecraftForge.EVENT_BUS.addListener(SideProxy::serverStarting);
        MinecraftForge.EVENT_BUS.addListener(SideProxy::serverStopping);

//        Config.init();
//        Network.init();

//        ModLootStuff.init();
        ModRecipeStuff.init();
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
    }

    private static void imcEnqueue(InterModEnqueueEvent event) {}

    private static void imcProcess(InterModProcessEvent event) {}

    private static void serverAboutToStart(FMLServerAboutToStartEvent event) {
    }

    private static void serverStarting(FMLServerStartingEvent event) {
    }

    private static void serverStarted(FMLServerStartedEvent event) {
    }

    private static void serverStopping(FMLServerStoppingEvent event) {
        server = null;
    }

    @Nullable
    @Override
    public PlayerEntity getClientPlayer() {
        return null;
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return server;
    }

    static class Client extends SideProxy {
        Client() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Client::clientSetup);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::onItemColors);
        }

        private static void clientSetup(FMLClientSetupEvent event) {
//            ModEntities.registerRenderers(event);
//            ModTileEntities.registerRenderers(event);
//            ModContainers.registerScreens(event);
        }

        @Nullable
        @Override
        public PlayerEntity getClientPlayer() {
            return Minecraft.getInstance().player;
        }
    }

    static class Server extends SideProxy {
        Server() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
        }

        private void serverSetup(FMLDedicatedServerSetupEvent event) {}
    }
}
