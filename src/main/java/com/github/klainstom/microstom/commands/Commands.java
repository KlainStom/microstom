package com.github.klainstom.microstom.commands;

import net.minestom.server.command.builder.SimpleCommand;

public class Commands {
    public static final SimpleCommand SHUTDOWN = new ShutdownCommand();
    public static final SimpleCommand RESTART = new RestartCommand();
}
