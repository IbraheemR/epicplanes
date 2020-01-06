package com.ibraheemrodrigues.epicplanes.network;

import com.ibraheemrodrigues.epicplanes.Util;
import com.ibraheemrodrigues.epicplanes.entity.basicplane.BasicPlane;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.network.packet.CustomPayloadS2CPacket;
import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class PlanePackets {
    public static final Identifier S2C_PLANE_SPAWN_PACKET = Util.getID("s2c_generic_spawn");
    public static final Identifier S2C_GENERIC_SPAWN_PACKET = Util.getID("s2c_plane_spawn");

    public static final Identifier S2C_PLANE_DATA_PACKET = Util.getID("s2c_plane_data");
    public static final Identifier C2S_PLANE_DATA_PACKET = Util.getID("c2s_plane_data");


    public static void init() {


        ServerSidePacketRegistry.INSTANCE.register(C2S_PLANE_DATA_PACKET, (packetContext, attachedData) -> {
            int planeId = attachedData.readInt();
            float enginePower = attachedData.readFloat();

            packetContext.getTaskQueue().execute(() -> {
                BasicPlane plane = (BasicPlane) packetContext.getPlayer().world.getEntityById(planeId);

                if (plane != null) {
                    plane.setEnginePower(enginePower);

                    // Forward packet
                    Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(plane);

                    PacketByteBuf relayData = new PacketByteBuf(Unpooled.buffer());

                    relayData.writeInt(planeId);
                    relayData.writeFloat(enginePower);

                    watchingPlayers.forEach(player -> {
                        if (player != packetContext.getPlayer()) {
                            ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, S2C_PLANE_DATA_PACKET, relayData);
                        }
                    });
                }
            });
        });
    }

    public static void clientInit() {
        ClientSidePacketRegistry.INSTANCE.register(S2C_PLANE_SPAWN_PACKET, EntityDispatcher::spawnPlaneFrom);
        ClientSidePacketRegistry.INSTANCE.register(S2C_GENERIC_SPAWN_PACKET, EntityDispatcher::spawnEntityFrom);

        ClientSidePacketRegistry.INSTANCE.register(S2C_PLANE_DATA_PACKET, (packetContext, attachedData) -> {
            int planeId = attachedData.readInt();
            float enginePower = attachedData.readFloat();

            packetContext.getTaskQueue().execute(() -> {
                BasicPlane plane = (BasicPlane) packetContext.getPlayer().world.getEntityById(planeId);

                if (packetContext.getPlayer() == plane.getPrimaryPassenger()) {
                    plane.setEnginePower(enginePower);
                }
            });
        });
    }

    public static Packet<?> newSpawnPacket(final BasicPlane plane) {
        final PacketByteBuf bytes = new PacketByteBuf(Unpooled.buffer());
        try {
            new PlaneSpawnPacket(plane).write(bytes);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Failed to write bytes for " + plane, e);
        }
        return new CustomPayloadS2CPacket(S2C_PLANE_SPAWN_PACKET, bytes);
    }

    public static Packet<?> newSpawnPacket(final Entity entity) {
        final PacketByteBuf bytes = new PacketByteBuf(Unpooled.buffer());
        try {
            new EntitySpawnS2CPacket(entity).write(bytes);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Failed to write bytes for " + entity, e);
        }
        return new CustomPayloadS2CPacket(S2C_GENERIC_SPAWN_PACKET, bytes);
    }

    public static <T extends Packet<?>> Optional<T> readFrom(final PacketByteBuf bytes, final Supplier<T> packet) {
        final T deserializedPacket = packet.get();
        try {
            deserializedPacket.read(bytes);
        } catch (final IOException e) {
            if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                throw new IllegalStateException("Reading " + deserializedPacket + " from " + bytes, e);
            }
            return Optional.empty();
        }
        return Optional.of(deserializedPacket);
    }
}
