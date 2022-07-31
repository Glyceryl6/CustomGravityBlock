package com.glyceryl6.falling.commands.server;

import com.glyceryl6.falling.json.JsonHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;

public class CommandReloadJSON {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("reload").requires((commandSource) -> commandSource.hasPermission(2))
                .executes((commandSource) -> reload(commandSource.getSource()));
    }

    private static int reload(CommandSourceStack source) {
        try {
            JsonHandler.readJson(JsonHandler.JSON_FILE);
            source.sendSuccess(new TranslatableComponent("falling_blocks.info.reload.success"), true);
        } catch (Exception e) {
            e.printStackTrace();
            source.sendSuccess(new TranslatableComponent("falling_blocks.info.reload.fail"), true);
        }
        return 0;
    }

}