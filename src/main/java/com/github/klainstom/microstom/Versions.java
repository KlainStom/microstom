package com.github.klainstom.microstom;

import net.minestom.server.MinecraftServer;

import java.util.List;

public class Versions {
    public static final String VERSION = "$version";
    public static final String MINESTOM_VERSION = "$minestomVersion";

    public static List<String> getVersionLines() {
        return List.of(
                "====== VERSIONS ======",
                "Java: "+Runtime.version(),
                "$Name: "+ Versions.VERSION,
                "Minestom: "+Versions.MINESTOM_VERSION,
                "Protocol: "+MinecraftServer.PROTOCOL_VERSION,
                "======================"
        );
    }

    public static void printVersionLines() {
        for (String line : getVersionLines()) {
            MinecraftServer.LOGGER.info(line);
        }
    }
}
