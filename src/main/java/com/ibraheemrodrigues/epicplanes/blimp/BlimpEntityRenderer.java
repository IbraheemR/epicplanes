package com.ibraheemrodrigues.epicplanes.blimp;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class BlimpEntityRenderer extends EntityRenderer<BlimpEntity> {

    protected final BoatEntityModel model = createModel();

    public BlimpEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
        this.field_4673 = 0.8F;
    }

    private BoatEntityModel createModel() {
        return new BoatEntityModel();
    }

    @Override
    public Identifier getTexture(BlimpEntity BlimpEntity) {
        return new Identifier("minecraft:textures/entity/boat/oak.png");
    }

    @Override
    public void render(BlimpEntity blimpEntity, float yaw, float tickDelta, MatrixStack matricies,
            VertexConsumerProvider vertexConsumers, int light) {

        matricies.push();
        matricies.translate(0.0D, 0.375D, 0.0D);
        matricies.multiply(Vector3f.POSITIVE_Y.getRotationQuaternion(180.0F - yaw));
        float wobbleTime = (float) blimpEntity.getDamageWobbleTicks() - tickDelta;
        float wobbleAmount = blimpEntity.getDamageWobbleStrength() - tickDelta;
        if (wobbleAmount < 0.0F) {
            wobbleAmount = 0.0F;
        }

        if (wobbleTime > 0.0F) {
            matricies.multiply(Vector3f.POSITIVE_X.getRotationQuaternion(MathHelper.sin(wobbleTime) * wobbleTime
                    * wobbleAmount / 10.0F * (float) blimpEntity.getDamageWobbleSide()));
        }

        float float_5 = blimpEntity.interpolateBubbleWobble(tickDelta);
        if (!MathHelper.approximatelyEquals(float_5, 0.0F)) {
            matricies.multiply(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F),
                    blimpEntity.interpolateBubbleWobble(tickDelta), true));
        }

        matricies.scale(-1.0F, -1.0F, 1.0F);
        matricies.multiply(Vector3f.POSITIVE_Y.getRotationQuaternion(90.0F));

        this.model.method_22952(blimpEntity, tickDelta, 0.0F, -0.1F, 0.0F, 0.0F);

        VertexConsumer lightVertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(getTexture(blimpEntity)));
        this.model.render(matricies, lightVertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F);

        VertexConsumer waterVertexConsumer = vertexConsumers.getBuffer(RenderLayer.getWaterMask());
        this.model.getBottom().render(matricies, waterVertexConsumer, light, OverlayTexture.DEFAULT_UV, (Sprite) null);

        matricies.pop();
        super.render(blimpEntity, yaw, tickDelta, matricies, vertexConsumers, light);
    }

}