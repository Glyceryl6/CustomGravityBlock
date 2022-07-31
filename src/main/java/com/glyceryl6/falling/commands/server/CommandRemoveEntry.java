package com.glyceryl6.falling.commands.server;

import com.glyceryl6.falling.json.JsonHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

public class CommandRemoveEntry {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("remove").requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("block", BlockStateArgument.block())
                .executes((commandSource) -> removeEntry(commandSource.getSource(),
                BlockStateArgument.getBlock(commandSource, "block"))));
    }

    private static int removeEntry(CommandSourceStack source, BlockInput blockInput) {
        Block block = blockInput.getState().getBlock();
        String blockName = block.getName().getString();
        String entryName = Objects.requireNonNull(block.getRegistryName()).toString();
        if (JsonHandler.removeEntry(entryName)) {
            source.sendSuccess(new TranslatableComponent("falling_blocks.info.remove.success", blockName), true);
        } else {
            source.sendSuccess(new TranslatableComponent("falling_blocks.info.remove.fail"), true);
        }

        return 0;
    }

}
