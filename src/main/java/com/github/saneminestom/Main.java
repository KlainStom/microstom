package com.github.saneminestom;

import net.minestom.server.Bootstrap;
import net.minestom.server.MinecraftServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        // TODO: 04.08.21 create start.sh or start.bat depending on OS
        String os = "linux";
        String filename;
        switch (os) {
            case "linux": filename = "start.sh"; break;
            case "windows": filename = "start.bat"; break; // TODO: 06.08.21 add windows script
            default:
                throw new IllegalStateException("Unexpected value: " + os);
        }
        File file = new File(filename);
        if (file.isDirectory()) {
            MinecraftServer.LOGGER.warn("Can't create startup script!");
        }
        if (!file.isFile()) {
            MinecraftServer.LOGGER.info("Create startup script.");
            Files.copy(
                    Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(filename)),
                    file.toPath());
            switch (os) {
                case "linux": Runtime.getRuntime().exec("chmod u+x start.sh"); break;
            }
            System.out.println("Use './start.sh' or 'start.bat' to start the server.");
            System.exit(0);
        }

        Bootstrap.bootstrap("com.github.saneminestom.MicroStom", args);
    }
}