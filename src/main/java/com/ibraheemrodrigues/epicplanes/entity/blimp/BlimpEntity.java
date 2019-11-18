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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BlimpEntity extends BoatEntity {

    public BlimpEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public static final EntityType<BlimpEntity> BLIMP = Registry.register(Registry.ENTITY_TYPE, Util.getID("blimp"),
            FabricEntityTypeBuilder.create(EntityCategory.MISC, BlimpEntity::new).size(EntityDimensions.fixed(1.5F, 4F))
                    .trackable(80, 3).build());

    public static final void init() {
    }

    public static final void clientInit() {
        EntityRendererRegistry.INSTANCE.register(BLIMP, (dispatcher, context) -> new BlimpEntityRenderer(dispatcher));
    }

    @Override
    public Item asItem() {
        return PlaneItems.BLIMP_ITEM;
    }

    @Override
    public boolean hasNoGravity() {
        return hasPassengers();
    }
}
