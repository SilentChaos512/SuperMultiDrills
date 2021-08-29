package net.silentchaos512.supermultidrills;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nullable;

public interface IProxy {
    @Nullable
    Player getClientPlayer();

    @Nullable
    MinecraftServer getServer();
}
