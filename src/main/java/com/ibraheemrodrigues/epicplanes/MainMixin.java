package com.ibraheemrodrigues.epicplanes;

import net.minecraft.entity.mob.CreeperEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public class MainMixin {
    @Inject(at = @At("HEAD"), method = "explode()V")
    private void init(CallbackInfo info) {
        System.out.println("Boom");
    }
}