package com.github.saneminestom;

import net.minestom.server.Bootstrap;
import net.minestom.server.MinecraftServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        final String[] LINUX_SCRIPT = {
                "start.sh",
                "java -Dhost=127.0.0.1 -Dport=25565 -jar micro.jar"
        };
        final String[] WINDOWS_SCRIPT = {
                "start.bat",
                "java -Dhost=127.0.0.1 -Dport=25565 -jar micro.jar"
        };

        // TODO: 04.08.21 create start.sh or start.bat depending on OS
        String[] script = LINUX_SCRIPT;

        File file = new File(script[0]).getAbsoluteFile();
        if (file.isDirectory()) {
            MinecraftServer.LOGGER.warn("Can't create startup script!.");
        }
        if (!file.isFile()) {
            MinecraftServer.LOGGER.info("Create startup script");
            FileWriter writer = new FileWriter(file);
            writer.write(script[1]);
            writer.flush();
            writer.close();
        }

        Bootstrap.bootstrap("com.github.saneminestom.MicroStom", args);
    }
}