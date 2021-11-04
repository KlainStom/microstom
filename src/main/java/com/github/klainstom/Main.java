package com.github.klainstom;

import com.github.klainstom.microstom.Versions;
import net.minestom.server.Bootstrap;
import net.minestom.server.MinecraftServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equalsIgnoreCase("-v")) {
            MinecraftServer.LOGGER.info("==== VERSIONS ====");
            MinecraftServer.LOGGER.info("Java: "+Runtime.version());
            MinecraftServer.LOGGER.info("$Name: "+ Versions.VERSION);
            MinecraftServer.LOGGER.info("Minestom: "+Versions.MINESTOM_VERSION);
            MinecraftServer.LOGGER.info("==================");
            System.exit(0);
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