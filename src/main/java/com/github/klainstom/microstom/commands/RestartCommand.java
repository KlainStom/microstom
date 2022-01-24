package com.github.klainstom.microstom.commands;

import com.github.klainstom.microstom.Settings;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
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
            return false;
        }
        return true;
    }

    @Override
    public boolean hasAccess(@NotNull CommandSender sender, @Nullable String commandString) {
        return (sender instanceof ConsoleSender) || Settings.isAllowPlayerRestart();
    }
}
