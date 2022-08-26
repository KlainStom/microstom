package com.github.klainstom.microstom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Settings {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            //.serializeNulls()
            .create();
    private static final File settingsFile = new File("micro-settings.json");

    private static SettingsState currentSettings = null;

    public static void read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(settingsFile));
            currentSettings = gson.fromJson(reader, SettingsState.class);
        } catch (FileNotFoundException e) {
            currentSettings = new SettingsState();
            try {
                write();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void write() throws IOException {
        String json = gson.toJson(currentSettings);
        Writer writer = new FileWriter(settingsFile);
        writer.write(json);
        writer.close();
    }

    private static class SettingsState {
        private final String SERVER_IP;
        private final int SERVER_PORT;

        private final RunMode MODE;
        private final String VELOCITY_SECRET;

        // JVM arguments
        private final String TPS;
        private final String CHUNK_VIEW_DISTANCE;
        private final String ENTITY_VIEW_DISTANCE;
        private final boolean TERMINAL_DISABLED;

        private SettingsState() {
            this.SERVER_IP = "localhost";
            this.SERVER_PORT = 25565;

            this.MODE = RunMode.OFFLINE;
            this.VELOCITY_SECRET = "";

            this.TPS = null;
            this.CHUNK_VIEW_DISTANCE = null;
            this.ENTITY_VIEW_DISTANCE = null;
            this.TERMINAL_DISABLED = false;
        }

    }

    public enum RunMode {
        OFFLINE("offline"),
        ONLINE("online"),
        BUNGEECORD("BungeeCord"),
        VELOCITY("Velocity");

        private final String name;

        RunMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public static RunMode getMode() { return currentSettings.MODE; }

    public static String getServerIp() {
        return System.getProperty("server.ip", currentSettings.SERVER_IP);
    }
    public static int getServerPort() {
        int port = Integer.getInteger("server.port", currentSettings.SERVER_PORT);
        if (port < 1) return 25565;
        return port;
    }

    public static boolean hasVelocitySecret() {
        return !currentSettings.VELOCITY_SECRET.isBlank();
    }

    public static String getVelocitySecret() {
        return currentSettings.VELOCITY_SECRET;
    }

    public static String getTps() { return currentSettings.TPS; }
    public static String getChunkViewDistance() { return currentSettings.CHUNK_VIEW_DISTANCE; }
    public static String getEntityViewDistance() { return currentSettings.ENTITY_VIEW_DISTANCE; }
    public static boolean isTerminalDisabled() { return currentSettings.TERMINAL_DISABLED; }
}
