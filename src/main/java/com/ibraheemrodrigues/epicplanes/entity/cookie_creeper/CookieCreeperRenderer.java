package com.ibraheemrodrigues.epicplanes.entity.cookie_creeper;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CookieCreeperRenderer
        extends MobEntityRenderer<CookieCreeperEntity, CreeperEntityModel<CookieCreeperEntity>> {

    public CookieCreeperRenderer(EntityRenderDispatcher entityRenderDispatcher_1) {
        super(entityRenderDispatcher_1, new CreeperEntityModel<>(), 1);
    }

    @Override
    public Identifier getTexture(CookieCreeperEntity cookieCreeperEntity) {
        return new Identifier("epicplanes:textures/entity/cookie_creeper.png");
    }

    @Override
    protected float getWhiteOverlayProgress(CookieCreeperEntity entity, float tickDelta) {
        float g = entity.getClientFuseTime(tickDelta);
        return (int)(g * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(g, 0.5F, 1.0F);
    }
}