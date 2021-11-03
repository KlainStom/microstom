package com.github.klainstom.microstom.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.SimpleCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RestartCommand extends SimpleCommand {
    public RestartCommand() {
        super("restart");
    }

    @Override
    public boolean process(@NotNull CommandSender sender, @NotNull String command, @NotNull String[] args) {
        // TODO: 03.11.21 write file to signal a restart instead of using unreliable exit codes
        MinecraftServer.stopCleanly();
        System.exit(99);
        return true;
    }

    @Override
    public boolean hasAccess(@NotNull CommandSender sender, @Nullable String commandString) {
        // TODO: 03.11.21 enable execution for players in settings
        return sender.isConsole();
    }
}
