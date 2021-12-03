package com.ewyboy.worldspawn.commands.server;

import com.ewyboy.worldspawn.json.JSONHandler;
import com.ewyboy.worldspawn.json.objects.Spawn;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class CommandSet {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("set").requires((commandSource) -> commandSource.hasPermission(2))
            .then(Commands.argument("min", IntegerArgumentType.integer())
            .then(Commands.argument("max",IntegerArgumentType.integer())
            .executes((commandSource) -> editEntry(
                    commandSource.getSource(),
                    IntegerArgumentType.getInteger(commandSource, "min"),
                    IntegerArgumentType.getInteger(commandSource, "max")
            )))
        );
    }

    private static int editEntry(CommandSourceStack source, Integer min, Integer max) {
        Spawn spawn = new Spawn(min, max);
        JSONHandler.setEntry(spawn);
        source.sendSuccess(new TextComponent(
                ChatFormatting.GREEN + "SUCCESS:" +
                        ChatFormatting.WHITE + " Spawn changed to a random place between " +
                        ChatFormatting.GOLD + spawn.getMin_y() +
                        ChatFormatting.WHITE + " & " +
                        ChatFormatting.GOLD + spawn.getMax_y()
                ), true);
        return 0;
    }
}
