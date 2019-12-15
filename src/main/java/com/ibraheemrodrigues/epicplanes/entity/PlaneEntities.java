package com.ibraheemrodrigues.epicplanes.entity;

import com.ibraheemrodrigues.epicplanes.Util;
import com.ibraheemrodrigues.epicplanes.entity.blimp.BlimpEntity;
import com.ibraheemrodrigues.epicplanes.entity.blimp.BlimpEntityRenderer;
import com.ibraheemrodrigues.epicplanes.entity.cookie_creeper.CookieCreeperEntity;
import com.ibraheemrodrigues.epicplanes.entity.cookie_creeper.CookieCreeperRenderer;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class PlaneEntities {

    public static final EntityType<CookieCreeperEntity> COOKIE_CREEPER = Registry.register(Registry.ENTITY_TYPE,
            Util.getID("cookie_creeper"),
            FabricEntityTypeBuilder.create(EntityCategory.MONSTER, CookieCreeperEntity::new)
                    .size(EntityDimensions.fixed(1, 2)).build());

    public static final EntityType<BlimpEntity> BLIMP = Registry.register(Registry.ENTITY_TYPE, Util.getID("blimp"),
            FabricEntityTypeBuilder.create(EntityCategory.MISC, BlimpEntity::new)
                    .size(EntityDimensions.fixed(1.5F, 0.6F)).trackable(80, 3).build());

    public static  final void init() {

    }
}