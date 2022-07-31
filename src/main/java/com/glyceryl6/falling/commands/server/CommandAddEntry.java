package com.glyceryl6.falling.commands.server;

import com.glyceryl6.falling.json.JsonHandler;
import com.glyceryl6.falling.json.objects.BlockListEntry;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;

import java.util.Objects;

public class CommandAddEntry {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("add").requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("block", BlockStateArgument.block())
                .executes((commandSource) -> addEntry(commandSource.getSource(),
                BlockStateArgument.getBlock(commandSource, "block"))));
    }

    private static int addEntry(CommandSourceStack source, BlockInput blockInput) {
        Block block = blockInput.getState().getBlock();
        String entryName = Objects.requireNonNull(block.getRegistryName()).toString();
        String blockName = block.getName().getString();
        BlockListEntry blockListEntry = new BlockListEntry(entryName);
        if (!(block instanceof FallingBlock)) {
            if (!block.defaultBlockState().isAir()) {
                if (JsonHandler.addEntry(blockListEntry)) {
                    source.sendSuccess(new TranslatableComponent("falling_blocks.info.add.success", blockName), true);
                } else {
                    source.sendSuccess(new TranslatableComponent("falling_blocks.info.add.fail"), true);
                }
            } else {
                source.sendSuccess(new TranslatableComponent("falling_blocks.info.add.air"), true);
            }
        } else {
            source.sendSuccess(new TranslatableComponent("falling_blocks.info.add.falling"), true);
        }

        return 0;
    }

}