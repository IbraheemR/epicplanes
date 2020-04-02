package com.ibraheemrodrigues.epicplanes.mixin;

import com.ibraheemrodrigues.epicplanes.block.SteelScaffoldingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityScaffoldClimbableMixin extends Entity {

    public LivingEntityScaffoldClimbableMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract BlockState getBlockState();

    @Shadow
    public abstract boolean isClimbing();

    @Shadow
    public abstract boolean isHoldingOntoLadder();

    @Inject( method ="isClimbing", at = @At("HEAD"), cancellable = true)
    private void isClimbing(CallbackInfoReturnable<Boolean> info) {
        if (!isSpectator() && getBlockState().getBlock() instanceof SteelScaffoldingBlock) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "applyClimbingSpeed", at=@At("HEAD"), cancellable = true)
    private void applyClimbingSpeedh(Vec3d motion, CallbackInfoReturnable<Vec3d> info) {
        if (isClimbing() && motion.y < 0 && getBlockState().getBlock() instanceof SteelScaffoldingBlock && isHoldingOntoLadder() && (Object) this instanceof PlayerEntity) {
            this.fallDistance = 0;
            double x = MathHelper.clamp(motion.z, -0.15000000596046448D, 0.15000000596046448D);
            double z = MathHelper.clamp(motion.z, -0.15000000596046448D, 0.15000000596046448D);
            double y = Math.max(motion.y, -0.15000000596046448D);

            info.setReturnValue(new Vec3d(x, y, z));
        }
    }
}
