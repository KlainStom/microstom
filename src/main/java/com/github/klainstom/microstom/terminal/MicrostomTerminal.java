package com.github.klainstom.microstom.terminal;

import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class MicrostomTerminal {
    private static final String PROMPT = "> ";

    private static volatile Terminal terminal;
    private static volatile LineReader reader;
    private static volatile boolean running = false;

    @ApiStatus.Internal
    public static void start() {
        final Thread thread = new Thread(null, () -> {
            try {
                terminal = TerminalBuilder.terminal();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();
            running = true;

            while (running) {
                String command;
                try {
                    command = reader.readLine(PROMPT);
                    var commandManager = MinecraftServer.getCommandManager();
                    commandManager.execute(commandManager.getConsoleSender(), command);
                } catch (UserInterruptException ignore) {
                } catch (EndOfFileException e) {
                    return;
                }
            }
        }, "Jline");
        thread.setDaemon(true);
        thread.start();
    }

    @ApiStatus.Internal
    public static void stop() {
        running = false;
        if (terminal != null) {
            try {
                terminal.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void print(String line) {
        reader.printAbove(line);
    }

    public static boolean isRunning() { return running; }
}
