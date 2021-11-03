package com.github.klainstom.microstom.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.SimpleCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShutdownCommand extends SimpleCommand {
    public ShutdownCommand() {
        super("end", "shutdown", "stop");
    }

    @Override
    public boolean process(@NotNull CommandSender sender, @NotNull String command, @NotNull String[] args) {
        MinecraftServer.stopCleanly();
        return true;
    }

    @Override
    public boolean hasAccess(@NotNull CommandSender sender, @Nullable String commandString) {
        // TODO: 03.11.21 enable execution for players in settings
        return sender.isConsole();
    }
}
