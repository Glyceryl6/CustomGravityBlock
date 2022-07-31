package com.glyceryl6.falling.event;

import com.glyceryl6.falling.json.JsonHandler;
import com.glyceryl6.falling.json.objects.BlockListEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class FallingBlockTick {

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        Block block = event.getPlacedBlock().getBlock();
        BlockPos pos = event.getPos();
        if (block.getRegistryName() != null && event.getEntity() != null) {
            Level level = event.getEntity().level;
            String regName = block.getRegistryName().toString();
            BlockListEntry entry = new BlockListEntry(regName);
            if (JsonHandler.containsEntry(entry)) {
                level.scheduleTick(pos, block, 2);
            }
        }
    }

    @SubscribeEvent
    public void fallingBlockParticle(TickEvent.WorldTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        Player player = mc.player;
        Random random = new Random();
        if (level instanceof ClientLevel clientLevel && !mc.isPaused() && player != null) {
            int x1 = player.getBlockX() + clientLevel.random.nextInt(16) - clientLevel.random.nextInt(16);
            int y1 = player.getBlockY() + clientLevel.random.nextInt(16) - clientLevel.random.nextInt(16);
            int z1 = player.getBlockZ() + clientLevel.random.nextInt(16) - clientLevel.random.nextInt(16);
            int x2 = player.getBlockX() + clientLevel.random.nextInt(32) - clientLevel.random.nextInt(32);
            int y2 = player.getBlockY() + clientLevel.random.nextInt(32) - clientLevel.random.nextInt(32);
            int z2 = player.getBlockZ() + clientLevel.random.nextInt(32) - clientLevel.random.nextInt(32);
            BlockPos pos1 = new BlockPos(x1, y1, z1);
            BlockPos pos2 = new BlockPos(x2, y2, z2);
            BlockState state1 = clientLevel.getBlockState(pos1);
            BlockState state2 = clientLevel.getBlockState(pos2);
            for(int j = 0; j < 100; ++j) {
                this.animateTick(state1, clientLevel, pos1, random);
                this.animateTick(state2, clientLevel, pos2, random);
            }
        }
    }

    private void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        ParticleOptions particle = new BlockParticleOption(ParticleTypes.FALLING_DUST, state);
        if (state.getBlock().getRegistryName() != null) {
            if (random.nextInt(10) == 0) {
                String regName = state.getBlock().getRegistryName().toString();
                BlockListEntry entry = new BlockListEntry(regName);
                BlockState state1 = level.getBlockState(pos.below());
                if (JsonHandler.containsEntry(entry) && FallingBlock.isFree(state1) && pos.getY() >= level.getMinBuildHeight()) {
                    double d0 = (double)pos.getX() + random.nextDouble();
                    double d1 = (double)pos.getY() - 0.05D;
                    double d2 = (double)pos.getZ() + random.nextDouble();
                    level.addParticle(particle, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

}