package com.github.klainstom.microstom;

import net.minestom.server.MinecraftServer;

import java.util.List;

public class Info {
    public static void printLines(List<String> lines) {
        for (String line : lines) {
            MinecraftServer.LOGGER.info(line);
        }
    }

    public static void printVersionLines() {
        printLines(Versions.getVersionLines());
    }

    public static void printSettingsLines() {
        printLines(Settings.getSettingsLines());
    }
}
