package com.github.saneminestom;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerLoginEvent;

import java.util.Objects;

public class MicroStom {
    public static void main(String[] args) {
        String address = Objects.requireNonNull(System.getProperty("host"));
        int port = Integer.parseInt(System.getProperty("port"));

        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            if (MinecraftServer.getInstanceManager().getInstances().isEmpty())
                event.getPlayer().kick(Component.text("There is no instance available!", NamedTextColor.RED));
        });

        MinecraftServer.init().start(address, port);
        MinecraftServer.LOGGER.info("Server listens on {}:{}", address, port);
    }
}
