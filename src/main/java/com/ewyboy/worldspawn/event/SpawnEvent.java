package com.ewyboy.worldspawn.event;

import com.ewyboy.worldspawn.json.JSONHandler;
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

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static BlockPos getSafeSpawn(ServerLevel level) {

        Random random = new Random(level.getSeed());
        BlockPos.MutableBlockPos blockPosMutable = new BlockPos.MutableBlockPos();

        double f = -1.0D;

        int xPos = getRandomNumber(-32, 32);
        int yPos = getRandomNumber(JSONHandler.spawnConfig.getSpawnEntry().getMin_y(), JSONHandler.spawnConfig.getSpawnEntry().getMax_y());
        int zPos = getRandomNumber(-32, 32);

        ModLogger.info("Pos X :: " + xPos);
        ModLogger.info("Pos Y :: " + yPos);
        ModLogger.info("Pos Z :: " + zPos);

        int xPosCopy = xPos;
        int yPosCopy = yPos;
        int zPosCopy = zPos;

        int modifer = 0;
        int rand = random.nextInt(4);

        for (int x = xPos - 32; x <= xPos + 32; ++x)
        {
            double xx = (double) x + 0.5D - xPos;

            for (int z = zPos - 32; z <= zPos + 32; ++z)
            {
                double zz = (double) z + 0.5D - zPos;

                callBack:
                for (int y = level.getMaxBuildHeight() - 1; y >= 0; --y)
                {
                    if (level.isEmptyBlock(blockPosMutable.set(x, y, z))) {
                        while (y > 0 && level.isEmptyBlock(blockPosMutable.set(x, y - 1, z))) {
                            --y;
                        }

                        for (int k3 = rand; k3 < rand + 4; ++k3)
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
                                        int i5 = x + (k4 - 1) * l3 + j4 * i4;
                                        int j5 = y + l4;
                                        int k5 = z + (k4 - 1) * i4 - j4 * l3;
                                        blockPosMutable.set(i5, j5, k5);
                                        if (l4 < 0 && !level.getBlockState(blockPosMutable).getMaterial().isSolid() || l4 >= 0 && !level.isEmptyBlock(blockPosMutable))
                                        {
                                            continue callBack;
                                        }
                                    }
                                }
                            }

                            double d5 = (double) y + 0.5D - yPos;
                            double d7 = xx * xx + d5 * d5 + zz * zz;
                            if (f < 0.0D || d7 < f)
                            {
                                f = d7;
                                xPosCopy = x;
                                yPosCopy = y;
                                zPosCopy = z;
                                modifer = k3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (f < 0.0D)
        {
            for (int x = xPos - 16; x <= xPos + 16; ++x)
            {
                double xx = (double) x + 0.5D - xPos;

                for (int z = zPos - 16; z <= zPos + 16; ++z)
                {
                    double zz = (double) z + 0.5D - zPos;

                    callBack:
                    for (int y = level.getMaxBuildHeight() - 1; y >= 0; --y)
                    {
                        if (level.isEmptyBlock(blockPosMutable.set(x, y, z)))
                        {
                            while (y > 0 && level.isEmptyBlock(blockPosMutable.set(x, y - 1, z)))
                            {
                                --y;
                            }

                            for (int rando = rand; rando < rand + 2; ++rando)
                            {
                                int l8 = rando % 2;
                                int k9 = 1 - l8;

                                for (int i10 = 0; i10 < 4; ++i10)
                                {
                                    for (int k10 = -1; k10 < 4; ++k10)
                                    {
                                        int i11 = x + (i10 - 1) * l8;
                                        int j11 = y + k10;
                                        int k11 = z + (i10 - 1) * k9;
                                        blockPosMutable.set(i11, j11, k11);
                                        if (k10 < 0 && !level.getBlockState(blockPosMutable).getMaterial().isSolid() || k10 >= 0 && !level.isEmptyBlock(blockPosMutable))
                                        {
                                            continue callBack;
                                        }
                                    }
                                }

                                double d6 = (double) y + 0.5D - yPos;
                                double d8 = xx * xx + d6 * d6 + zz * zz;
                                if (f < 0.0D || d8 < f)
                                {
                                    f = d8;
                                    xPosCopy = x;
                                    yPosCopy = y;
                                    zPosCopy = z;
                                    modifer = rando % 2;
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
        int l6 = modifer % 2;
        int i3 = 1 - l6;
        if (modifer % 4 >= 2)
        {
            l6 = -l6;
            i3 = -i3;
        }

        if (f < 0.0D)
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
