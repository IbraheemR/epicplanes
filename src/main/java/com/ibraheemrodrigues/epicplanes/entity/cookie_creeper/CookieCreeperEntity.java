package com.ibraheemrodrigues.epicplanes.entity.cookie_creeper;

import com.ibraheemrodrigues.epicplanes.Util;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
// import com.ibraheemrodrigues.epicplanes.Util;

public class CookieCreeperEntity extends CreeperEntity {
    public CookieCreeperEntity(EntityType<? extends CreeperEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    public static final EntityType<CookieCreeperEntity> COOKIE_CREEPER = Registry.register(Registry.ENTITY_TYPE,
            Util.getID("cookie_creeper"),
            FabricEntityTypeBuilder.create(EntityCategory.MONSTER, CookieCreeperEntity::new)
                    .size(EntityDimensions.fixed(1, 2)).build());

    public static final void init() {
        //
    }

    public static final void clientInit() {
        EntityRendererRegistry.INSTANCE.register(COOKIE_CREEPER,
                (entityRenderDispatcher, context) -> new CookieCreeperRenderer(entityRenderDispatcher));
    }
}