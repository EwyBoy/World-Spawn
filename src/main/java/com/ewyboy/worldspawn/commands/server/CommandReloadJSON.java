package com.ewyboy.worldspawn.commands.server;

import com.ewyboy.worldspawn.json.JSONHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class CommandReloadJSON {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("reload").requires((commandSource) -> commandSource.hasPermission(2))
            .executes((commandSource) -> reload(
                commandSource.getSource()
            )
        );
    }

    private static int reload(CommandSourceStack source) {
        try {
            JSONHandler.readJson(JSONHandler.JSON_FILE);
            source.sendSuccess(new TextComponent(ChatFormatting.GREEN + "SUCCESS: " + ChatFormatting.WHITE + "Config reloaded"), true);
        } catch (Exception e) {
            e.printStackTrace();
            source.sendSuccess(new TextComponent(ChatFormatting.RED + "ERROR: " + ChatFormatting.WHITE + "Config failed to reload"), true);
        }
        return 0;
    }
}
