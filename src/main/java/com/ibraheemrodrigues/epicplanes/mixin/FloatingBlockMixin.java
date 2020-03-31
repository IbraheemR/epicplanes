package com.ibraheemrodrigues.epicplanes.mixin;

import com.ibraheemrodrigues.epicplanes.block.FloatingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public abstract class FloatingBlockMixin extends Entity {

    @Shadow
    private BlockState block;

    @Shadow
    private int timeFalling;

    public FloatingBlockMixin(EntityType<? extends Entity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tickHead(CallbackInfo info) {

        if (this.block.isAir()) {
            this.remove();
        } else {
            BlockPos blockPos = new BlockPos(this.getPos());

            if (!this.hasNoGravity()) {
                if (this.block.getBlock() instanceof FloatingBlock) {
                    this.addVelocity(0, 0.08F, 0);
                }
            }

            if (!FallingBlock.canFallThrough(this.getEntityWorld().getBlockState(blockPos.up(1)))) {
                this.getEntityWorld().setBlockState(blockPos, this.block);
                this.remove();
            }
        }
    }
}