package com.github.klainstom.microstom.logging;

import org.tinylog.core.LogEntry;
import org.tinylog.core.LogEntryValue;
import org.tinylog.writers.Writer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;

public class MicrostomFileWriter implements Writer {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final PrintStream logPrintStream;

    public MicrostomFileWriter(Map<String, String> properties) throws IOException {
        File logFile = new File(properties.get("file"));
        if (!logFile.exists()) logFile.createNewFile();
        this.logPrintStream = new PrintStream(logFile);
    }

    @Override
    public Collection<LogEntryValue> getRequiredLogEntryValues() {
        return EnumSet.of(LogEntryValue.LEVEL, LogEntryValue.DATE, LogEntryValue.MESSAGE);
    }

    @Override
    public void write(LogEntry logEntry) {
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
