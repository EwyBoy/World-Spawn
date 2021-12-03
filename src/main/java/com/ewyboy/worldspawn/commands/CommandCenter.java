package com.ewyboy.worldspawn.commands;

import com.ewyboy.worldspawn.WorldSpawn;
import com.ewyboy.worldspawn.commands.server.CommandReloadJSON;
import com.ewyboy.worldspawn.commands.server.CommandSet;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public class CommandCenter {

    public CommandCenter(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            LiteralArgumentBuilder.<CommandSourceStack> literal(WorldSpawn.MOD_ID)
                .then(CommandReloadJSON.register())
                .then(CommandSet.register())
                .executes(ctx -> 0)
        );
    }
}
