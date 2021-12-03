package com.ewyboy.worldspawn;

import com.ewyboy.worldspawn.commands.CommandCenter;
import com.ewyboy.worldspawn.event.SpawnEvent;
import com.ewyboy.worldspawn.json.InfoHandler;
import com.ewyboy.worldspawn.json.JSONHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.ewyboy.worldspawn.WorldSpawn.MOD_ID;

@Mod(MOD_ID)
public class WorldSpawn {

    public static final String MOD_ID = "worldspawn";

    public WorldSpawn() {
        ignoreServerOnly();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this :: loadComplete);
        JSONHandler.setup();
        InfoHandler.setup();
        MinecraftForge.EVENT_BUS.addListener(this :: registerCommands);
    }

    //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
    private void ignoreServerOnly() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () ->
                new IExtensionPoint.DisplayTest(() -> "You Can Write Whatever The Fuck You Want Here", (YouCanWriteWhatEverTheFuckYouWantHere, ICreatedSlimeBlocks2YearsBeforeMojangDid) -> ICreatedSlimeBlocks2YearsBeforeMojangDid)
        );
    }

    public void registerCommands(RegisterCommandsEvent event) {
        new CommandCenter(event.getDispatcher());
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
        MinecraftForge.EVENT_BUS.register(new SpawnEvent());
    }


}
