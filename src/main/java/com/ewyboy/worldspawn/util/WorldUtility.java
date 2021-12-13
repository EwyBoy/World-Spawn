package com.ewyboy.worldspawn.util;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

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

}
