package com.ewyboy.worldspawn.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.NoiseSampler;

import java.util.List;

public class WorldUtility {

    public static String getWorldDimensionName(Level world) {
        return world.dimension().location().toString();
    }
    public static boolean isOverworld(Level world) {
        return getWorldDimensionName(world).toLowerCase().endsWith("overworld");
    }
    public static boolean isNether(Level world) {
        return getWorldDimensionName(world).toLowerCase().endsWith("nether");
    }
    public static boolean isEnd(Level world) {
        return getWorldDimensionName(world).toLowerCase().endsWith("end");
    }

    public static Level getWorldIfInstanceOfAndNotRemote(LevelAccessor levelAccessor) {
        if (levelAccessor.isClientSide()) {
            return null;
        }
        if (levelAccessor instanceof Level) {
            return ((Level)levelAccessor);
        }
        return null;
    }

    public interface Sampler {
        Climate.TargetPoint sample(int x, int y, int z);

        default BlockPos findSpawnPosition() {
            return BlockPos.ZERO;
        }
    }

    public static class SpawnPoint {

        private static int p_186990_;
        private static int p_186991_;
        public WorldUtility.SpawnPoint.Result result;

        public SpawnPoint(List<Climate.ParameterPoint> parameterPoints, NoiseSampler noiseSampler) {
            this.result = getSpawnPositionAndFitness(0, 0);
            this.radialSearch(2048.0F, 512.0F);
            this.radialSearch(512.0F, 32.0F);
        }

        public interface Sampler {
            default BlockPos findSpawnPosition() {
                return BlockPos.ZERO;
            }
        }

        private void radialSearch(float p_186985_, float p_186986_) {
            float f = 0.0F;
            float f1 = p_186986_;
            BlockPos blockpos = this.result.location();

            while(f1 <= p_186985_) {
                int x = blockpos.getX() + (int)(Math.sin(f) * (double)f1);
                int z = blockpos.getZ() + (int)(Math.cos(f) * (double)f1);
                WorldUtility.SpawnPoint.Result result = getSpawnPositionAndFitness(x, z);

                f += p_186986_ / f1;
                if ((double)f > (Math.PI * 2D)) {
                    f = 0.0F;
                    f1 += p_186986_;
                }
            }

        }

        private static WorldUtility.SpawnPoint.Result getSpawnPositionAndFitness(int x, int z) {
            SpawnPoint.p_186990_ = x;
            SpawnPoint.p_186991_ = z;
            return new WorldUtility.SpawnPoint.Result(new BlockPos(x, 0, z));
        }

        static record Result(BlockPos location) {}
    }

}
