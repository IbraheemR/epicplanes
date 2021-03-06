package com.ibraheemrodrigues.epicplanes.mixin;

// From https://github.com/liachmodded/kayak/blob/master/src/main/java/com/github/liachmodded/kayak/mixin/client/ClientPlayNetworkHandlerMixin.java

import com.ibraheemrodrigues.epicplanes.Util;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

  @Shadow
  private ClientWorld world;

  @Inject(method = "onEntitySpawn", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;getEntityTypeId()Lnet/minecraft/entity/EntityType;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
  public void handleEntitySpawnPacket(EntitySpawnS2CPacket packet, CallbackInfo ci, double x, double y, double z,
      EntityType<?> type) {
    Identifier id = Registry.ENTITY_TYPE.getId(type);
    if (id.getNamespace().equals(Util.ID)) {
      Entity entity = type.create(world);
      if (entity == null) {
        Util.LOGGER.warn("Invalid entity type factory for {}!", id);
        ci.cancel();
        return;
      }

      entity.updatePosition(x, y, z);

      int networkId = packet.getId();
      entity.updateTrackedPosition(x, y, z);
      entity.pitch = (float) (packet.getPitch() * 360) / 256.0F;
      entity.yaw = (float) (packet.getYaw() * 360) / 256.0F;
      entity.setEntityId(networkId);
      entity.setUuid(packet.getUuid());
      this.world.addEntity(networkId, entity);
      ci.cancel();
    }
  }
}