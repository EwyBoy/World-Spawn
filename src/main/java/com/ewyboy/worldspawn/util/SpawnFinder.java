package com.ewyboy.worldspawn.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;

public class SpawnFinder {

    public static BlockPos locateBiome(ServerLevel level, Biome biome, BlockPos pos) {
        BlockPos blockpos = new BlockPos(pos);
        return level.getLevel().findNearestBiome(biome, blockpos, 12800, 16);
    }

}
