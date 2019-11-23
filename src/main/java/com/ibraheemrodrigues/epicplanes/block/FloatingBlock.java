package com.ibraheemrodrigues.epicplanes.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class FloatingBlock extends FallingBlock {

    public FloatingBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public void scheduledTick(BlockState blockState_1, ServerWorld serverWorld_1, BlockPos blockPos_1,
            Random random_1) {
        if (canFallThrough(serverWorld_1.getBlockState(blockPos_1.up(1))) && blockPos_1.getY() >= 0) {
            FallingBlockEntity fallingBlockEntity_1 = new FallingBlockEntity(serverWorld_1,
                    (double) blockPos_1.getX() + 0.5D, (double) blockPos_1.getY(), (double) blockPos_1.getZ() + 0.5D,
                    serverWorld_1.getBlockState(blockPos_1));
            this.configureFallingBlockEntity(fallingBlockEntity_1);
            serverWorld_1.spawnEntity(fallingBlockEntity_1);
        }
    }

}