package com.ewyboy.worldspawn.event;

import com.ewyboy.worldspawn.util.ModLogger;
import com.ewyboy.worldspawn.util.WorldUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber()
public class SpawnEvent {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onWorldLoad(WorldEvent.CreateSpawnPosition event) {
        Level level = WorldUtility.getWorldIfInstanceOfAndNotRemote(event.getWorld());
        ModLogger.info("Finding spawn..");

        if (level == null) {
            return;
        }

        event.setCanceled(true);
        ServerLevel serverLevel = (ServerLevel) level;
        serverLevel.setDefaultSpawnPos(getSafeSpawn(serverLevel), 0.0f);
    }

    public static BlockPos getSafeSpawn(ServerLevel level) {
        Random random = new Random(level.getSeed());
        double d0 = -1.0D;
        int xPos = 0;
        int yPos = 420;
        int zPos = 0;
        int xPosCopy = xPos;
        int yPosCopy = yPos;
        int zPosCopy = zPos;
        int l1 = 0;
        int i2 = random.nextInt(4);
        BlockPos.MutableBlockPos blockPosMutable = new BlockPos.MutableBlockPos();

        for (int j2 = xPos - 16; j2 <= xPos + 16; ++j2)
        {
            double d1 = (double) j2 + 0.5D - xPos;

            for (int l2 = zPos - 16; l2 <= zPos + 16; ++l2)
            {
                double d2 = (double) l2 + 0.5D - zPos;

                label276:
                for (int j3 = level.getMaxBuildHeight() - 1; j3 >= 0; --j3)
                {
                    if (level.isEmptyBlock(blockPosMutable.set(j2, j3, l2)))
                    {
                        while (j3 > 0 && level.isEmptyBlock(blockPosMutable.set(j2, j3 - 1, l2)))
                        {
                            --j3;
                        }

                        for (int k3 = i2; k3 < i2 + 4; ++k3)
                        {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;
                            if (k3 % 4 >= 2)
                            {
                                l3 = -l3;
                                i4 = -i4;
                            }

                            for (int j4 = 0; j4 < 3; ++j4)
                            {
                                for (int k4 = 0; k4 < 4; ++k4)
                                {
                                    for (int l4 = -1; l4 < 4; ++l4)
                                    {
                                        int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
                                        int j5 = j3 + l4;
                                        int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
                                        blockPosMutable.set(i5, j5, k5);
                                        if (l4 < 0 && !level.getBlockState(blockPosMutable).getMaterial().isSolid() || l4 >= 0 && !level.isEmptyBlock(blockPosMutable))
                                        {
                                            continue label276;
                                        }
                                    }
                                }
                            }

                            double d5 = (double) j3 + 0.5D - yPos;
                            double d7 = d1 * d1 + d5 * d5 + d2 * d2;
                            if (d0 < 0.0D || d7 < d0)
                            {
                                d0 = d7;
                                xPosCopy = j2;
                                yPosCopy = j3;
                                zPosCopy = l2;
                                l1 = k3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D)
        {
            for (int l5 = xPos - 16; l5 <= xPos + 16; ++l5)
            {
                double d3 = (double) l5 + 0.5D - xPos;

                for (int j6 = zPos - 16; j6 <= zPos + 16; ++j6)
                {
                    double d4 = (double) j6 + 0.5D - zPos;

                    label214:
                    for (int i7 = level.getMaxBuildHeight() - 1; i7 >= 0; --i7)
                    {
                        if (level.isEmptyBlock(blockPosMutable.set(l5, i7, j6)))
                        {
                            while (i7 > 0 && level.isEmptyBlock(blockPosMutable.set(l5, i7 - 1, j6)))
                            {
                                --i7;
                            }

                            for (int l7 = i2; l7 < i2 + 2; ++l7)
                            {
                                int l8 = l7 % 2;
                                int k9 = 1 - l8;

                                for (int i10 = 0; i10 < 4; ++i10)
                                {
                                    for (int k10 = -1; k10 < 4; ++k10)
                                    {
                                        int i11 = l5 + (i10 - 1) * l8;
                                        int j11 = i7 + k10;
                                        int k11 = j6 + (i10 - 1) * k9;
                                        blockPosMutable.set(i11, j11, k11);
                                        if (k10 < 0 && !level.getBlockState(blockPosMutable).getMaterial().isSolid() || k10 >= 0 && !level.isEmptyBlock(blockPosMutable))
                                        {
                                            continue label214;
                                        }
                                    }
                                }

                                double d6 = (double) i7 + 0.5D - yPos;
                                double d8 = d3 * d3 + d6 * d6 + d4 * d4;
                                if (d0 < 0.0D || d8 < d0)
                                {
                                    d0 = d8;
                                    xPosCopy = l5;
                                    yPosCopy = i7;
                                    zPosCopy = j6;
                                    l1 = l7 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i6 = xPosCopy;
        int k2 = yPosCopy;
        int k6 = zPosCopy;
        int l6 = l1 % 2;
        int i3 = 1 - l6;
        if (l1 % 4 >= 2)
        {
            l6 = -l6;
            i3 = -i3;
        }

        if (d0 < 0.0D)
        {
            yPosCopy = Mth.clamp(yPosCopy, 70, level.getMaxBuildHeight() - 10);
            k2 = yPosCopy;

            for (int j7 = -1; j7 <= 1; ++j7)
            {
                for (int i8 = 1; i8 < 3; ++i8)
                {
                    for (int i9 = -1; i9 < 3; ++i9)
                    {
                        int l9 = i6 + (i8 - 1) * l6 + j7 * i3;
                        int j10 = k2 + i9;
                        int l10 = k6 + (i8 - 1) * i3 - j7 * l6;
                        boolean flag = i9 < 0;
                        blockPosMutable.set(l9, j10, l10);
                        level.setBlock(blockPosMutable, flag ? Blocks.STONE.defaultBlockState() : Blocks.AIR.defaultBlockState(), 0);
                    }
                }
            }
        }

        for(int k7 = -1; k7 < 3; ++k7) {
            for(int j8 = -1; j8 < 4; ++j8) {
                if (k7 == -1 || k7 == 2 || j8 == -1 || j8 == 3) {
                    blockPosMutable.set(i6 + k7 * l6, k2 + j8, k6 + k7 * i3);
                }
            }
        }

        for(int k8 = 0; k8 < 2; ++k8) {
            for(int j9 = 0; j9 < 3; ++j9) {
                blockPosMutable.set(i6 + k8 * l6, k2 + j9 - 2, k6 + k8 * i3);
            }
        }

        return blockPosMutable;
    }

}
