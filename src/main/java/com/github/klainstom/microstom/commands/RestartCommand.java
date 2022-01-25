package com.github.klainstom.microstom.commands;

import com.github.klainstom.microstom.Settings;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.ServerSender;
import net.minestom.server.command.builder.Command;

import java.io.IOException;

public class RestartCommand extends Command {
    public RestartCommand() {
        super("restart");
        setCondition(((sender, commandString) -> (sender instanceof ServerSender)
                || (sender instanceof ConsoleSender)
                || Settings.isAllowPlayerRestart()));
        setDefaultExecutor((sender, context) -> {
            try {
                new ProcessBuilder("./start.sh").start();
                MinecraftServer.stopCleanly();
            } catch (IOException e) {
                if (!(sender instanceof ConsoleSender)) sender.sendMessage("Could not restart server.");
                LOGGER.error("Could not restart server.", e);
            }
        });
    }
}
