package com.ibraheemrodrigues.epicplanes.entity.blimp;

import com.ibraheemrodrigues.epicplanes.block.PlaneBlocks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
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

    protected final BoatEntityModel model = new BoatEntityModel();

    public BlimpEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
        this.field_4673 = 0.8F;
    }

    @Override
    public Identifier getTexture(BlimpEntity BlimpEntity) {
        return new Identifier("minecraft:textures/entity/boat/oak.png");
    }

    @Override
    public void render(BlimpEntity blimpEntity, float yaw, float tickDelta, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light) {

        matrices.push();
        matrices.translate(0.0D, 0.375D, 0.0D);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - yaw));

        float wobbleTime = (float) blimpEntity.getDamageWobbleTicks() - tickDelta;
        float wobbleAmount = blimpEntity.getDamageWobbleStrength() - tickDelta;
        if (wobbleAmount < 0.0F) {
            wobbleAmount = 0.0F;
        }

        if (wobbleTime > 0.0F) {
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(wobbleTime) * wobbleTime
                    * wobbleAmount / 10.0F * (float) blimpEntity.getDamageWobbleSide()));
        }

        float float_5 = blimpEntity.interpolateBubbleWobble(tickDelta);
        if (!MathHelper.approximatelyEquals(float_5, 0.0F)) {
            matrices.multiply(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F),
                    blimpEntity.interpolateBubbleWobble(tickDelta), true));
        }

        // Start Ropes
        for (int i = 0; i < 4; i++) {
            int side = i < 2 ? -1 : 1;
            int frontback = i % 2 == 0 ? -1 : 1;

            ModelPart rope = new ModelPart(16, 16, 0, 0);

            rope.addCuboid(9f * side - ((side + 1) / 2), 2f, 0, 1F, 40.0F, 1F, 0.0F);
            rope.pitch = frontback * 0.314f;
            rope.roll = side * -0.314f * 0.5f;

            VertexConsumer ropeVertexConsumer = vertexConsumers
                    .getBuffer(this.model.getLayer(new Identifier("minecraft:textures/block/oak_log.png")));

            rope.render(matrices, ropeVertexConsumer, light, OverlayTexture.DEFAULT_UV, (Sprite) null);
        }

        // End Ropes

        matrices.scale(-1.0F, -1.0F, 1.0F);

        // Start Balloon

        matrices.push();
        matrices.scale(2, 2, 2);
        matrices.translate(-0.5D, -2, 0.5D);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));

        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(
                PlaneBlocks.BALLOON_BLOCK.getDefaultState(), matrices, vertexConsumers, light,
                OverlayTexture.DEFAULT_UV);
        matrices.pop();

        // End Balloon

        // Align with player pitch
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(blimpEntity.getBlimpPitch() * 2));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));

        VertexConsumer lightVertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(getTexture(blimpEntity)));
        this.model.render(matrices, lightVertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F);

        VertexConsumer waterVertexConsumer = vertexConsumers.getBuffer(RenderLayer.getWaterMask());
        this.model.getBottom().render(matrices, waterVertexConsumer, light, OverlayTexture.DEFAULT_UV, (Sprite) null);

        matrices.pop();
        super.render(blimpEntity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

}