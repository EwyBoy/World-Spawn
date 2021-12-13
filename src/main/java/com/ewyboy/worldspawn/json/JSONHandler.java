package com.ewyboy.worldspawn.json;

import com.ewyboy.worldspawn.WorldSpawn;
import com.ewyboy.worldspawn.json.objects.Spawn;
import com.ewyboy.worldspawn.json.objects.SpawnConfig;
import com.ewyboy.worldspawn.util.ModLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONHandler {

    private static final Gson gson = new Gson();
    public static final File JSON_FILE = new File(FMLPaths.CONFIGDIR.get() + "/worldspawn/spawn.json");

    public static SpawnConfig spawnConfig = new SpawnConfig(new Spawn(400, 450));

    public static void setup() {
        createDirectory();
        if(!JSON_FILE.exists()) {
            ModLogger.info("Creating World Spawn JSON file");
            writeJson(JSON_FILE);
        }
        ModLogger.info("Reading World Spawn config JSON file");
        readJson(JSON_FILE);
    }

    public static void reload() {
        writeJson(JSON_FILE);
        readJson(JSON_FILE);
    }

    public static void setEntry(Spawn entry) {
        spawnConfig.setSpawnEntry(entry);
        reload();
    }

    public static void writeJson(File jsonFile) {
        try(Writer writer = new FileWriter(jsonFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(spawnConfig, writer);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void readJson(File jsonFile) {
        try(Reader reader = new FileReader(jsonFile)) {
            spawnConfig = gson.fromJson(reader, SpawnConfig.class);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectory() {
        Path configPath = FMLPaths.CONFIGDIR.get();
        Path seedDropConfigPath = Paths.get(configPath.toAbsolutePath().toString(), WorldSpawn.MOD_ID);
        try {
            ModLogger.info("Creating World Spawn config directory");
            Files.createDirectory(seedDropConfigPath);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            ModLogger.error("Failed to create World Spawn config directory", e);
        }
    }

}
