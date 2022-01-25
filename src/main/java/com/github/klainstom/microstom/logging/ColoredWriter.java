package com.github.klainstom.microstom.logging;

import com.github.klainstom.microstom.Settings;
import com.github.klainstom.microstom.terminal.MicrostomTerminal;
import org.tinylog.Level;
import org.tinylog.core.LogEntry;
import org.tinylog.core.LogEntryValue;
import org.tinylog.writers.Writer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;

public class ColoredWriter implements Writer {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ColoredWriter(Map<String, String> properties) { }

    @Override
    public Collection<LogEntryValue> getRequiredLogEntryValues() {
        return EnumSet.of(LogEntryValue.LEVEL, LogEntryValue.DATE, LogEntryValue.MESSAGE);
    }

    @Override
    public void write(LogEntry logEntry) {
        String dateString = DATE_FORMAT.format(logEntry.getTimestamp().toDate());

        Level level = logEntry.getLevel();
        String levelString = switch (level) {
            case ERROR -> "\u001B[31m";
            case WARN -> "\u001B[33m";
            case INFO -> "\u001B[32m";
            case DEBUG -> "\u001B[36m";
            case TRACE -> "\u001B[37m";
            case OFF -> "";
        } + level + "\u001B[0m";

        if (MicrostomTerminal.isRunning()) {
            MicrostomTerminal.print(String.format(
                    "[%s] %s > %s", dateString, levelString, logEntry.getMessage()));
        } else if (Settings.isMicrostomTerminal())
            System.out.printf("[%s] %s > %s\n", dateString, levelString, logEntry.getMessage());
        else System.out.printf("\r[%s] %s > %s\n> ", dateString, levelString, logEntry.getMessage());
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }
}
