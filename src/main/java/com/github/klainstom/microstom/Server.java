package com.github.klainstom.microstom;

import com.github.klainstom.microstom.commands.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.velocity.VelocityProxy;

public class Server {
    public static void main(String[] args) {
        Settings.read();

        MinecraftServer.LOGGER.info("==== VERSIONS ====");
        MinecraftServer.LOGGER.info("$Name: "+Versions.VERSION);
        MinecraftServer.LOGGER.info("Minestom: "+Versions.MINESTOM_VERSION);
        MinecraftServer.LOGGER.info("==================");

        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            if (MinecraftServer.getInstanceManager().getInstances().isEmpty())
                event.getPlayer().kick(Component.text("There is no instance available!", NamedTextColor.RED));
        });

        MinecraftServer.getCommandManager().register(Commands.SHUTDOWN);
        MinecraftServer.getCommandManager().register(Commands.RESTART);

        MinecraftServer server = MinecraftServer.init();

        switch (Settings.getMode()) {
            case OFFLINE:
                break;
            case ONLINE:
                MojangAuth.init();
                break;
            case BUNGEECORD:
                BungeeCordProxy.enable();
                break;
            case VELOCITY:
                if (!Settings.hasVelocitySecret())
                    throw new IllegalArgumentException("The velocity secret is mandatory.");
                VelocityProxy.enable(Settings.getVelocitySecret());
        }

        server.start(Settings.getServerIp(), Settings.getServerPort());
        MinecraftServer.LOGGER.info("==== SETTINGS ====");
        MinecraftServer.LOGGER.info("Mode: {}", Settings.getMode());
        MinecraftServer.LOGGER.info("Address: {}:{}", Settings.getServerIp(), Settings.getServerPort());
        MinecraftServer.LOGGER.info("==================");
    }
}
