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
            candidates.addAll(MinecraftServer.getCommandManager().getDispatcher().getCommands().stream()
                    .map(c -> new Candidate(c.getName())).toList());
        } else {
            Command command = MinecraftServer.getCommandManager().getCommand(line.words().get(line.wordIndex()-1));
            if (command == null) return;
            candidates.addAll(command.getSubcommands().stream().map(c -> new Candidate(c.getName())).toList());
        }
    }
}
