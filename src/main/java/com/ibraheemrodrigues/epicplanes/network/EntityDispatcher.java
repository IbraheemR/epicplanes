package com.ibraheemrodrigues.epicplanes.network;

import com.ibraheemrodrigues.epicplanes.entity.basicplane.BasicPlane;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.PacketByteBuf;

import java.util.Optional;

public class EntityDispatcher {
    public static void spawnPlaneFrom(final PacketContext ctx, final PacketByteBuf bytes) {
        PlanePackets.readFrom(bytes, PlaneSpawnPacket::new).ifPresent(packet -> {
                System.out.println("Spawned1");
                ctx.getTaskQueue().execute(() -> {
                    final ClientWorld world = MinecraftClient.getInstance().world;
                    System.out.println("Spawned 2");
                    Optional.ofNullable(packet.getEntityTypeId().create(world)).ifPresent(entity -> {
                        entity.setPosition(packet.getX(), packet.getY(), packet.getZ());
                        entity.updateTrackedPosition(packet.getX(), packet.getY(), packet.getZ());
                        entity.setVelocity(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityz());
                        entity.pitch = (float) (packet.getPitch() * 360) / 256.0F;
                        entity.yaw = (float) (packet.getYaw() * 360) / 256.0F;
                        entity.setEntityId(packet.getId());
                        entity.setUuid(packet.getUuid());
                        if (entity instanceof BasicPlane) {
                            ((BasicPlane) entity).setEnginePower(packet.getEnginePower());
                        }
                        world.addEntity(packet.getId(), entity);
                    });
                });
        });

    }
    public static void spawnEntityFrom(final PacketContext ctx, final PacketByteBuf bytes) {
        PlanePackets.readFrom(bytes, EntitySpawnS2CPacket::new).ifPresent(packet -> {
                System.out.println("Spawned1");
                ctx.getTaskQueue().execute(() -> {
                    final ClientWorld world = MinecraftClient.getInstance().world;
                    System.out.println("Spawned 2");
                    Optional.ofNullable(packet.getEntityTypeId().create(world)).ifPresent(entity -> {
                        entity.setPosition(packet.getX(), packet.getY(), packet.getZ());
                        entity.updateTrackedPosition(packet.getX(), packet.getY(), packet.getZ());
                        entity.setVelocity(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityz());
                        entity.pitch = (float) (packet.getPitch() * 360) / 256.0F;
                        entity.yaw = (float) (packet.getYaw() * 360) / 256.0F;
                        entity.setEntityId(packet.getId());
                        entity.setUuid(packet.getUuid());
                        world.addEntity(packet.getId(), entity);
                    });
                });
        });

    }
}
