package com.github.klainstom.microstom.logging;

import org.tinylog.Level;
import org.tinylog.core.LogEntry;
import org.tinylog.core.LogEntryValue;
import org.tinylog.writers.Writer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;

public class MicrostomFileWriter implements Writer {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat NAME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    private final PrintStream logPrintStream;
    private final Level level;

    public MicrostomFileWriter(Map<String, String> properties) throws IOException {
        this.level = Level.valueOf(properties.getOrDefault("level", Level.DEBUG.name()).toUpperCase());
        File logFile = new File("logs/%s.log".formatted(NAME_DATE_FORMAT.format(new Date())));
        if (!logFile.exists()) logFile.createNewFile();
        this.logPrintStream = new PrintStream(logFile);
    }

    @Override
    public Collection<LogEntryValue> getRequiredLogEntryValues() {
        return EnumSet.of(LogEntryValue.LEVEL, LogEntryValue.DATE, LogEntryValue.MESSAGE);
    }

    @Override
    public void write(LogEntry logEntry) {
        if (logEntry.getLevel().compareTo(this.level) < 0) return;
        String dateString = DATE_FORMAT.format(logEntry.getTimestamp().toDate());
        this.logPrintStream.printf("[%s] %s > %s\n", dateString, logEntry.getLevel().name(), logEntry.getMessage());
    }

    @Override
    public void flush() {
        this.logPrintStream.flush();
    }

    @Override
    public void close() {
        this.logPrintStream.close();
    }
}
