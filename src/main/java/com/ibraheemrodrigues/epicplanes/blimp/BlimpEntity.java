package com.ibraheemrodrigues.epicplanes.blimp;

import com.ibraheemrodrigues.epicplanes.Util;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BlimpEntity extends BoatEntity {

    public BlimpEntity(EntityType<? extends BoatEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    public static final EntityType<BlimpEntity> BLIMP = Registry.register(Registry.ENTITY_TYPE, Util.Id("blimp"),
            FabricEntityTypeBuilder.create(EntityCategory.MISC, BlimpEntity::new)
                    .size(EntityDimensions.fixed(1.375F, 0.5625F)).trackable(80, 3).build());

    public static final void init() {
    }

    public static final void clientInit() {
        EntityRendererRegistry.INSTANCE.register(BLIMP, (dispatcher, context) -> new BoatEntityRenderer(dispatcher));
    }
}