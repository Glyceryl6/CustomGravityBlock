package com.glyceryl6.falling.commands;

import com.glyceryl6.falling.commands.server.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public class CommandCenter {

    public CommandCenter(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder
                .<CommandSourceStack> literal("falling")
                .then(CommandAddEntry.register())
                .then(CommandRemoveEntry.register())
                .then(CommandViewEntries.register())
                .then(CommandReloadJSON.register())
                .executes(ctx -> 0));
    }

}
