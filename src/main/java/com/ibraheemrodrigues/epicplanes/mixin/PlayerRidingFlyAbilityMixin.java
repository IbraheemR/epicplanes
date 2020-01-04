package com.ibraheemrodrigues.epicplanes.mixin;

import com.ibraheemrodrigues.epicplanes.entity.PlaneEntities;
import com.ibraheemrodrigues.epicplanes.entity.basicplane.BasicPlane;
import com.ibraheemrodrigues.epicplanes.entity.blimp.BlimpEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class PlayerRidingFlyAbilityMixin {

    @Shadow
    private ServerPlayerEntity player;

    @Shadow
    private Entity topmostRiddenEntity;

    @Shadow
    private int vehicleFloatingTicks;

    @Shadow
    private boolean ridingEntity;

    // Prevents player form being kicked if flying in a flying entity
    @Inject(at = @At("HEAD"), method = "tick")
    private void startRiding(CallbackInfo info) {
        Entity rootVehicle = player.getRootVehicle();

        if (rootVehicle instanceof BasicPlane || rootVehicle instanceof BlimpEntity) {
            vehicleFloatingTicks = 0;
        }
    }
}
