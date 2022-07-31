package com.glyceryl6.falling.mixin;

import com.glyceryl6.falling.json.JsonHandler;
import com.glyceryl6.falling.json.objects.BlockListEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class MixinBlockBehaviour {

    @Inject(method = "updateShape", at = @At("HEAD"))
    public void updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        Block block = state.getBlock();
        if (block.getRegistryName() != null) {
            String regName = block.getRegistryName().toString();
            BlockListEntry entry = new BlockListEntry(regName);
            if (JsonHandler.containsEntry(entry)) {
                level.scheduleTick(currentPos, block, 2);
            }
        }
    }

}
