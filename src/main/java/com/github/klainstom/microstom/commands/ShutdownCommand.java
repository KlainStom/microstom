package com.github.klainstom.microstom.commands;

import com.github.klainstom.microstom.Settings;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.SimpleCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShutdownCommand extends SimpleCommand {
    public ShutdownCommand() {
        super("shutdown", "end", "stop");
    }

    @Override
    public boolean process(@NotNull CommandSender sender, @NotNull String command, @NotNull String[] args) {
        MinecraftServer.stopCleanly();
        return true;
    }

    @Override
    public boolean hasAccess(@NotNull CommandSender sender, @Nullable String commandString) {
        return (sender instanceof ConsoleSender) || Settings.isAllowPlayerShutdown();
    }
}
