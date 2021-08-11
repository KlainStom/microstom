package com.github.klainstom;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.velocity.VelocityProxy;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class MicroStom {
    public static void main(String[] args) {
        MinecraftServer.LOGGER.info("Microstom $version");

        Config config = null;
        try {
            Reader reader = new FileReader("micro.json");
            config = new Gson().fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
            MinecraftServer.LOGGER.error("Config could not be read.");
            System.exit(1);
        }

        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            if (MinecraftServer.getInstanceManager().getInstances().isEmpty())
                event.getPlayer().kick(Component.text("There is no instance available!", NamedTextColor.RED));
        });

        Command restart = new Command("restart");
        Command stop = new Command("stop", "end");

        restart.setDefaultExecutor((sender, context) -> {
            MinecraftServer.stopCleanly();
            System.exit(99);
        });
        stop.setDefaultExecutor((sender, context) -> MinecraftServer.stopCleanly());

        MinecraftServer.getCommandManager().register(restart);
        MinecraftServer.getCommandManager().register(stop);

        MinecraftServer server = MinecraftServer.init();

        switch (config.mode) {
            case OFFLINE:
                MinecraftServer.LOGGER.info("Running in offline mode.");
                break;
            case ONLINE:
                MinecraftServer.LOGGER.info("Running in online mode.");
                MojangAuth.init();
                break;
            case PROXY: if (config.velocitySecret.isBlank()) {
                MinecraftServer.LOGGER.info("Running behind BungeeCord proxy.");
                BungeeCordProxy.enable();
            } else {
                MinecraftServer.LOGGER.info("Running behind Velocity proxy.");
                VelocityProxy.enable(config.velocitySecret);
            }
        }

        server.start(config.host, config.port);
        MinecraftServer.LOGGER.info("Server listens on {}:{}", config.host, config.port);
    }

    private static class Config {
        private String host = "127.0.0.1";
        private int port = 25565;
        private Mode mode = Mode.PROXY;
        private String velocitySecret = "";

        private enum Mode { PROXY, ONLINE, OFFLINE }
    }
}
