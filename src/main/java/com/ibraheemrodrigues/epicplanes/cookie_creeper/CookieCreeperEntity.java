package com.ibraheemrodrigues.epicplanes.cookie_creeper;

import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
// import com.ibraheemrodrigues.epicplanes.Util;

public class CookieCreeperEntity extends CreeperEntity {
    public CookieCreeperEntity(EntityType<? extends CreeperEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    public static final EntityType<CookieCreeperEntity> COOKIE_CREEPER = Registry.register(Registry.ENTITY_TYPE,
            new Identifier("epicplanes", "cookie_creeper"),
            FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, CookieCreeperEntity::new)
                    .size(EntityDimensions.fixed(1, 2)).build());

    public static final void init() {
        //
    }

    public static final void clientInit() {
        EntityRendererRegistry.INSTANCE.register(CookieCreeperEntity.class,
                (entityRenderDispatcher, context) -> new CookieCreeperRenderer(entityRenderDispatcher));
    }

    public void tick() {
        super.tick();
        // System.out.println(this.getHealth());
    }

}