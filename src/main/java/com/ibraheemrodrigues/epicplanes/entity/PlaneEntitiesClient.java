package com.ibraheemrodrigues.epicplanes.entity;

import com.ibraheemrodrigues.epicplanes.entity.blimp.BlimpEntityRenderer;
import com.ibraheemrodrigues.epicplanes.entity.cookie_creeper.CookieCreeperRenderer;

public class PlaneEntitiesClient {
    public static final void clientInit() {
        net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE.register(PlaneEntities.COOKIE_CREEPER,
                (entityRenderDispatcher, context) -> new CookieCreeperRenderer(entityRenderDispatcher));

        net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE.register(PlaneEntities.BLIMP, (dispatcher, context) -> new BlimpEntityRenderer(dispatcher));
    }
}
