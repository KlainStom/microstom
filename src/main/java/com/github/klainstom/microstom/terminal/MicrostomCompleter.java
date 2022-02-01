package com.github.klainstom.microstom.terminal;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MicrostomCompleter implements Completer {
    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        if (line.wordIndex() == 0) {
            Set<Command> commands = MinecraftServer.getCommandManager().getDispatcher().getCommands();
            for (Command command : commands) {
                candidates.addAll(Arrays.stream(command.getNames()).map(Candidate::new).toList());
            }
        } else {
            Command command = MinecraftServer.getCommandManager().getCommand(line.words().get(0));
            if (command == null) return;
            candidates.addAll(command.getSyntaxesStrings().stream().map(Candidate::new).toList());
        }
    }
}
