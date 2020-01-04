package com.ibraheemrodrigues.epicplanes.network;

import com.ibraheemrodrigues.epicplanes.entity.basicplane.BasicPlane;
import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.util.PacketByteBuf;

import java.io.IOException;

public class PlaneSpawnPacket extends EntitySpawnS2CPacket {
    private float enginePower;

    public PlaneSpawnPacket() {
        super();
    }

    public PlaneSpawnPacket(BasicPlane plane) {
        super(plane);
        this.enginePower = plane.getEnginePower();
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        super.write(buf);
        buf.writeFloat(enginePower);
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        super.read(buf);
        this.enginePower = buf.readFloat();
    }

    public float getEnginePower() {
        return enginePower;
    }
}
