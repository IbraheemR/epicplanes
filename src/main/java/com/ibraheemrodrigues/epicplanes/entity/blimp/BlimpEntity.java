package com.ibraheemrodrigues.epicplanes.entity.blimp;

import com.ibraheemrodrigues.epicplanes.item.PlaneItems;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import net.minecraft.world.World;

public class BlimpEntity extends BoatEntity {

    public BlimpEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public float getBlimpPitch() {
        if (this.getPrimaryPassenger() != null) {
            float pitch = this.getPrimaryPassenger().pitch;
            pitch *= 0.2;
            return MathHelper.clamp(pitch, -20, 20);
        } else {
            return 0.0F;
        }
    }

    @Override
    public Item asItem() {
        switch(this.getBoatType()) {
            case OAK:
            default:
                return PlaneItems.OAK_BLIMP;
            case SPRUCE:
                return PlaneItems.SPRUCE_BLIMP;
            case BIRCH:
                return PlaneItems.BIRCH_BLIMP;
            case JUNGLE:
                return PlaneItems.JUNGLE_BLIMP;
            case ACACIA:
                return PlaneItems.ACACIA_BLIMP;
            case DARK_OAK:
                return PlaneItems.DARK_OAK_BLIMP;
        }
    }

    @Override
    public void tick() {

        float pitch = getBlimpPitch();
        Vec3d vel = this.getVelocity();

        if (MathHelper.abs(pitch) > 5 && hasPassengers()) {
            if (vel.y < 10) {
                this.addVelocity(0, MathHelper.clamp(pitch * -0.0003F, 90 * -0.0005F, 90 * 0.0005F), 0);
            }
        } else {
            this.setVelocity(vel.x, 0, vel.z);
        }

        super.tick();
    }

    @Override
    public boolean hasNoGravity() {
        return hasPassengers();
    }
}
