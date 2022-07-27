package com.github.klainstom.microstom.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.ServerSender;
import net.minestom.server.command.builder.Command;

public class ShutdownCommand extends Command {
    public ShutdownCommand() {
        super("shutdown", "end", "stop");
        setCondition((sender, commandString) -> (sender instanceof ServerSender)
                || (sender instanceof ConsoleSender)
                || sender.hasPermission(Permissions.SHUTDOWN));
        addSyntax(((sender, context) -> MinecraftServer.stopCleanly()));
    }
}
