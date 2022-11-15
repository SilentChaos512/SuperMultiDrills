package net.silentchaos512.supermultidrills;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.silentchaos512.supermultidrills.client.ColorHandlers;
import net.silentchaos512.supermultidrills.data.SmdDataGenerators;
import net.silentchaos512.supermultidrills.init.Registration;

import javax.annotation.Nullable;

class SideProxy implements IProxy {
    @Nullable private static MinecraftServer server;

    SideProxy() {
        Registration.register();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(SmdDataGenerators::onGatherData);
        MinecraftForge.EVENT_BUS.addListener(SideProxy::serverStopping);

//        Config.init();
//        Network.init();

//        ModLootStuff.init();
    }

    private static void serverStopping(ServerStoppingEvent event) {
        server = null;
    }

    @Nullable
    @Override
    public Player getClientPlayer() {
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
        }

        @Nullable
        @Override
        public Player getClientPlayer() {
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
