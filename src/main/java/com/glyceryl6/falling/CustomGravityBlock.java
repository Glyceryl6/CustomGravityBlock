package com.glyceryl6.falling;

import com.glyceryl6.falling.commands.CommandCenter;
import com.glyceryl6.falling.event.FallingBlockTick;
import com.glyceryl6.falling.json.JsonHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(CustomGravityBlock.MOD_ID)
public class CustomGravityBlock {

    public static final String MOD_ID = "falling_blocks";

    public CustomGravityBlock() {
        JsonHandler.setup();
        JsonHandler.removeNullBlock();
        MinecraftForge.EVENT_BUS.register(new FallingBlockTick());
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
    }

    public void registerCommands(RegisterCommandsEvent event) {
        new CommandCenter(event.getDispatcher());
    }

}