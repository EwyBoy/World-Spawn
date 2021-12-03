package com.ewyboy.worldspawn.json.objects;

public class SpawnConfig {

    private Spawn spawn;

    public SpawnConfig(Spawn spawnEntry) {
        this.spawn = spawnEntry;
    }

    public Spawn getSpawnEntry() {
        return spawn;
    }

    public void setSpawnEntry(Spawn spawnEntry) {
        this.spawn = spawnEntry;
    }

    @Override
    public String toString() {
        return "SpawnConfig{" +
                "spawn=" + spawn +
                '}';
    }
}
