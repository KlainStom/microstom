package com.github.klainstom.microstom;

import com.github.klainstom.microstom.commands.Commands;
import com.github.klainstom.microstom.terminal.MicrostomTerminal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.velocity.VelocityProxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class Server {
    private static final String START_SCRIPT_FILENAME = "start.sh";

    public static void main(String[] args) throws IOException {
        Info.printVersionLines();
        if (args.length > 0 && args[0].equalsIgnoreCase("-v")) System.exit(0);

        File startScriptFile = new File(START_SCRIPT_FILENAME);
        if (startScriptFile.isDirectory()) MinecraftServer.LOGGER.warn("Can't create startup script!");
        if (!startScriptFile.isFile()) {
            MinecraftServer.LOGGER.info("Create startup script.");
            Files.copy(
                    Objects.requireNonNull(Server.class.getClassLoader().getResourceAsStream(START_SCRIPT_FILENAME)),
                    startScriptFile.toPath());
            Runtime.getRuntime().exec("chmod u+x start.sh");
            MinecraftServer.LOGGER.info("Use './start.sh' to start the server.");
            System.exit(0);
        }

        // Actually start server
        MinecraftServer server = MinecraftServer.init();

        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            if (MinecraftServer.getInstanceManager().getInstances().isEmpty())
                event.getPlayer().kick(Component.text("There is no instance available!", NamedTextColor.RED));
        });

        MinecraftServer.getCommandManager().register(Commands.SHUTDOWN);
        MinecraftServer.getCommandManager().register(Commands.RESTART);

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
        Info.printSettingsLines();

        if (!Settings.isTerminalDisabled() && Settings.isMicrostomTerminal()) {
            MicrostomTerminal.start();
            new Thread(() -> {
                while (MinecraftServer.isStarted()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                MicrostomTerminal.stop();
            }).start();
        }
    }
}