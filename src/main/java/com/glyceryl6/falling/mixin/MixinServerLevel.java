package com.glyceryl6.falling.mixin;

import com.glyceryl6.falling.json.JsonHandler;
import com.glyceryl6.falling.json.objects.BlockListEntry;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level {

    protected MixinServerLevel(WritableLevelData levelData, ResourceKey<Level> dimension, Holder<DimensionType> dimensionTypeRegistration, Supplier<ProfilerFiller> profiler, boolean isClientSide, boolean isDebug, long biomeZoomSeed) {
        super(levelData, dimension, dimensionTypeRegistration, profiler, isClientSide, isDebug, biomeZoomSeed);
    }

    @Inject(method = "tickBlock", at = @At("HEAD"))
    private void tickBlock(BlockPos pos, Block block, CallbackInfo ci) {
        BlockState state = this.getBlockState(pos);
        String regName = Objects.requireNonNull(block.getRegistryName()).toString();
        BlockListEntry entry = new BlockListEntry(regName);
        if (JsonHandler.containsEntry(entry)) {
            if (FallingBlock.isFree(this.getBlockState(pos.below())) && pos.getY() >= this.getMinBuildHeight()) {
                FallingBlockEntity.fall(this, pos, state);
            }
        }
    }

}