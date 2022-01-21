package com.github.klainstom.microstom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minestom.server.utils.NetworkUtils;

import java.io.*;
import java.util.List;

public class Settings {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
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

    public static List<String> getSettingsLines() {
        return List.of(
                "====== SETTINGS ======",
                "Mode: " + getMode(),
                "Address: " + getServerIp() + ":" + getServerPort(),
                "======================"
        );
    }

    private static class SettingsState {
        private final String SERVER_IP;
        private final int SERVER_PORT;

        private final RunMode MODE;
        private final String VELOCITY_SECRET;

        private final boolean PLAYER_RESTART;
        private final boolean PLAYER_SHUTDOWN;

        // JVM arguments
        private final String TPS;
        private final String CHUNK_VIEW_DISTANCE;
        private final String ENTITY_VIEW_DISTANCE;
        private final String TERMINAL_DISABLED;

        private SettingsState() {
            this.SERVER_IP = "localhost";
            this.SERVER_PORT = 25565;

            this.MODE = RunMode.OFFLINE;
            this.VELOCITY_SECRET = "";

            this.PLAYER_RESTART = false;
            this.PLAYER_SHUTDOWN = false;

            this.TPS = null;
            this.CHUNK_VIEW_DISTANCE = null;
            this.ENTITY_VIEW_DISTANCE = null;
            this.TERMINAL_DISABLED = null;
        }

    }

    public enum RunMode {
        OFFLINE("offline"),
        ONLINE("online"),
        BUNGEECORD("behind BungeeCord"),
        VELOCITY("behind Velocity");

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
        if (port == 0) {
            try {
                port = NetworkUtils.getFreePort();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return port;
    }

    public static boolean hasVelocitySecret() {
        return !currentSettings.VELOCITY_SECRET.isBlank();
    }

    public static String getVelocitySecret() {
        return currentSettings.VELOCITY_SECRET;
    }

    public static boolean isAllowPlayerRestart() { return currentSettings.PLAYER_RESTART; }

    public static boolean isAllowPlayerShutdown() { return currentSettings.PLAYER_SHUTDOWN; }

    public static String getTps() { return currentSettings.TPS; }
    public static String getChunkViewDistance() { return currentSettings.CHUNK_VIEW_DISTANCE; }
    public static String getEntityViewDistance() { return currentSettings.ENTITY_VIEW_DISTANCE; }
    public static String getTerminalDisabled() { return currentSettings.TERMINAL_DISABLED; }
}
