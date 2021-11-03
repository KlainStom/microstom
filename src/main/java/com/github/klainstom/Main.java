package com.github.klainstom;

import net.minestom.server.Bootstrap;
import net.minestom.server.MinecraftServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        File settingsFile = new File("micro.json");
        if (settingsFile.isDirectory()) {
            MinecraftServer.LOGGER.error("Can't create settings file!");
            System.exit(1);
        }
        if (!settingsFile.isFile()) {
            MinecraftServer.LOGGER.info("Create settings file.");
            Files.copy(
                    Objects.requireNonNull(Main.class.getClassLoader().
                            getResourceAsStream(settingsFile.getName())),
                    settingsFile.toPath());

        }

        String filename = "start.sh";
        File file = new File(filename);
        if (file.isDirectory()) {
            MinecraftServer.LOGGER.warn("Can't create startup script!");
        }
        if (!file.isFile()) {
            MinecraftServer.LOGGER.info("Create startup script.");
            Files.copy(
                    Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(filename)),
                    file.toPath());
            Runtime.getRuntime().exec("chmod u+x start.sh");
            System.out.println("Use './start.sh' to start the server.");
            System.exit(0);
        }

        Bootstrap.bootstrap("com.github.klainstom.microstom.Server", args);
    }
}