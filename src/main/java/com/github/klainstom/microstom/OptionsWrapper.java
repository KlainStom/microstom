package com.github.klainstom.microstom;

import java.io.IOException;

public class OptionsWrapper {
    public static void main(String[] args) throws IOException {
        Settings.read();
        if (Settings.getTps() != null)
            System.setProperty("minestom.tps", Settings.getTps());
        if (Settings.getChunkViewDistance() != null)
            System.setProperty("minestom.chunk-view-distance", Settings.getChunkViewDistance());
        if (Settings.getEntityViewDistance() != null)
            System.setProperty("minestom.entity-view-distance", Settings.getEntityViewDistance());
        if ("true".equalsIgnoreCase(Settings.getTerminalDisabled()))
            System.setProperty("minestom.terminal.disabled", "");
        // TODO: 21.01.22 maybe add command line arguments to args
        Server.main(args);
    }
}
