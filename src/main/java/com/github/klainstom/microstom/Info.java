package com.github.klainstom.microstom;

import net.minestom.server.MinecraftServer;

public class Info {
    public static void printVersionLines() {
        MinecraftServer.LOGGER.info("====== MICROSTOM =====");
        MinecraftServer.LOGGER.info("Java: " + Runtime.version());
        MinecraftServer.LOGGER.info("$Name: " + Versions.VERSION);
        MinecraftServer.LOGGER.info("Minestom: " + Versions.MINESTOM_VERSION);
        MinecraftServer.LOGGER.info("Protocol: " + MinecraftServer.PROTOCOL_VERSION);
        MinecraftServer.LOGGER.info("======================");
    }

    public static void printSettingsLines() {
        MinecraftServer.LOGGER.info("====== SETTINGS ======");
        MinecraftServer.LOGGER.info("Mode: " + Settings.getMode());
        MinecraftServer.LOGGER.info("Address: " + Settings.getServerIp() + ":" + Settings.getServerPort());
        MinecraftServer.LOGGER.info("======================");
    }
}
