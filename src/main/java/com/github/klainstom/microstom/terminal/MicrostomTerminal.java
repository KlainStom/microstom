package com.github.klainstom.microstom.terminal;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.CommandResult;
import org.jetbrains.annotations.ApiStatus;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultExpander;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class MicrostomTerminal {
    private static CommandManager COMMAND_MANAGER;
    private static final String PROMPT = "> ";

    private static volatile Thread terminalThread;

    private static volatile Terminal terminal;
    private static volatile LineReader lineReader;
    private static final Highlighter highlighter = new MicrostomHighlighter();
    private static final Completer completer = new MicrostomCompleter();
    private static final Expander expander = new DefaultExpander();
    private static final History history = new DefaultHistory();
    private static volatile boolean running = false;

    @ApiStatus.Internal
    public static boolean start() {
        MinecraftServer.LOGGER.info("Start Microstom terminal");
        if (COMMAND_MANAGER == null) COMMAND_MANAGER = MinecraftServer.getCommandManager();
        try {
            terminal = TerminalBuilder.terminal();
            lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .highlighter(highlighter)
                    .completer(completer)
                    .expander(expander)
                    .history(history)
                    .build();

            terminalThread = new Thread(() -> {
                while (running) {
                    String command;
                    try {
                        command = lineReader.readLine(PROMPT);
                        if (command.isBlank()) continue;
                        MinecraftServer.LOGGER.info("Console executes command: {}", command);
                        CommandResult result = COMMAND_MANAGER.execute(COMMAND_MANAGER.getConsoleSender(), command);
                        switch (result.getType()) {
                            case UNKNOWN -> print("Unknown command.");
                            case INVALID_SYNTAX -> print("Invalid syntax: " + result.getInput());
                            case CANCELLED -> print("Execution got cancelled.");
                            case SUCCESS -> print("Execution succeeded.");
                        }
                    } catch (UserInterruptException e) {
                        MinecraftServer.LOGGER.info("Terminal user interrupt. Shut down.", e);
                        MinecraftServer.stopCleanly();
                        System.exit(0);
                        break;
                    } catch (EndOfFileException e) {
                        MinecraftServer.LOGGER.info("Terminal EOF. Stop console.", e);
                        MicrostomTerminal.stop();
                    }
                }
                running = false;
            }, "MicrostomTerminal");
            running = true;
            terminalThread.setDaemon(true);
            terminalThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (running) {
            Thread stopThread  = new Thread(() -> {
                while (MinecraftServer.isStarted()) {
                    if (!isRunning()) return;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stop();
            }, "terminalExit");
            stopThread.setDaemon(true);
            stopThread.start();
        }

        return running;
    }

    @ApiStatus.Internal
    public static void stop() {
        running = false;
        MinecraftServer.LOGGER.info("Stop Microstom terminal");
        if (terminal != null) {
            try {
                terminalThread.interrupt();
                terminal.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void print(String line) {
        lineReader.printAbove(line);
    }

    public static boolean isRunning() { return running; }
}
