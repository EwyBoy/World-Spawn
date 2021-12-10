package com.ewyboy.worldspawn.event;

import com.ewyboy.worldspawn.json.JSONHandler;
import com.ewyboy.worldspawn.util.ModLogger;
import com.ewyboy.worldspawn.util.WorldUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class SpawnEvent {

    private int getRandomArbitrary(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private void setInitialSpawn(ServerLevel level, ServerLevelData serverLevelData) {
        level.setDefaultSpawnPos(BlockPos.ZERO.atY(getRandomArbitrary(JSONHandler.spawnConfig.getSpawnEntry().getMin_y(), JSONHandler.spawnConfig.getSpawnEntry().getMax_y())), 0.0F);
        ModLogger.info("Spawn Point found at :: " + new BlockPos(serverLevelData.getXSpawn(), serverLevelData.getYSpawn(), serverLevelData.getZSpawn()).toShortString());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onWorldLoad(WorldEvent.CreateSpawnPosition event) {
        Level level = WorldUtility.getWorldIfInstanceOfAndNotRemote(event.getWorld());
        ModLogger.info("Finding spawn..");

        if (level == null) {
            return;
        }

        event.setCanceled(true);
        ServerLevel serverLevel = (ServerLevel) level;
        setInitialSpawn(serverLevel, (ServerLevelData) serverLevel.getServer().getWorldData());
    }

}
