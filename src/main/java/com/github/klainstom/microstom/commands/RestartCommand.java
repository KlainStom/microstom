package com.github.klainstom.microstom.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.SimpleCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class RestartCommand extends SimpleCommand {
    public RestartCommand() {
        super("restart");
    }

    @Override
    public boolean process(@NotNull CommandSender sender, @NotNull String command, @NotNull String[] args) {
        MinecraftServer.stopCleanly();
        try {
            new ProcessBuilder("./start.sh").start();
        } catch (IOException e) {
            LOGGER.error("Could not restart server.", e);
        }
        return true;
    }

    @Override
    public boolean hasAccess(@NotNull CommandSender sender, @Nullable String commandString) {
        // TODO: 03.11.21 enable execution for players in settings
        return sender.isConsole();
    }
}
