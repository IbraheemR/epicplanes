package com.ibraheemrodrigues.epicplanes.entity.blimp;

import com.ibraheemrodrigues.epicplanes.Util;
import com.ibraheemrodrigues.epicplanes.item.PlaneItems;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BlimpEntity extends BoatEntity {

    public static final EntityType<BlimpEntity> BLIMP = Registry.register(Registry.ENTITY_TYPE, Util.getID("blimp"),
            FabricEntityTypeBuilder.create(EntityCategory.MISC, BlimpEntity::new).size(EntityDimensions.fixed(1.5F, 4F))
                    .trackable(80, 3).build());

    public BlimpEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public static final void init() {
    }

    public static final void clientInit() {
        EntityRendererRegistry.INSTANCE.register(BLIMP, (dispatcher, context) -> new BlimpEntityRenderer(dispatcher));
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

        return PlaneItems.BLIMP_ITEM;
    }

    @Override
    public void tick() {
        float pitch = getBlimpPitch();
        Vec3d vel = this.getVelocity();

        if (MathHelper.abs(pitch) > 5 && hasPassengers()) {
            if (vel.y < 10) {
                this.addVelocity(0, pitch * -0.0001F, 0);
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
