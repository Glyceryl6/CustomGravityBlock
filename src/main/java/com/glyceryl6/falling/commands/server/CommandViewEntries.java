package com.glyceryl6.falling.commands.server;

import com.glyceryl6.falling.json.JsonHandler;
import com.glyceryl6.falling.json.objects.BlockListEntry;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class CommandViewEntries {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("view").requires((commandSource) -> commandSource.hasPermission(2))
                .executes((commandSource) -> viewEntries(commandSource.getSource()));
    }

    private static int viewEntries(CommandSourceStack source) {
        for (BlockListEntry entry : JsonHandler.blockListConfig.getBlockListConfig()) {
            ResourceLocation resourceLocation = new ResourceLocation(entry.getBlock());
            Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);
            if (block != null) {
                String blockName = block.getName().getString();
                source.sendSuccess(new TextComponent(ChatFormatting.AQUA + blockName), true);
            }
        }
        return 0;
    }


}
