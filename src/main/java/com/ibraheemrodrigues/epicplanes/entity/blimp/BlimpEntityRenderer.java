package com.ibraheemrodrigues.epicplanes.entity.blimp;

import com.ibraheemrodrigues.epicplanes.block.PlaneBlocks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class BlimpEntityRenderer extends EntityRenderer<BlimpEntity> {
    protected final BoatEntityModel model = new BoatEntityModel();

    private final BoatEntityRenderer fakeRenderer;

    public BlimpEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);

        // Used to easily retrieve texture in getTexture
        this.fakeRenderer = new BoatEntityRenderer(dispatcher);
    }

    @Override
    public Identifier getTexture(BlimpEntity blimpEntity) {
        return fakeRenderer.getTexture(blimpEntity);
    }

    @Override
    public void render(BlimpEntity blimpEntity, float yaw, float tickDelta, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int light) {

        matrixStack.push();
        matrixStack.translate(0.0D, 0.375D, 0.0D);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - yaw));

        float float_5 = blimpEntity.interpolateBubbleWobble(tickDelta);
        if (!MathHelper.approximatelyEquals(float_5, 0.0F)) {
            matrixStack.multiply(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F),
                    blimpEntity.interpolateBubbleWobble(tickDelta), true));
        }

        // Start Ropes
        for (int i = 0; i < 4; i++) {
            int side = i < 2 ? -1 : 1;
            int frontBack = i % 2 == 0 ? -1 : 1;

            ModelPart rope = new ModelPart(16, 16, 0, 0);

            rope.addCuboid(9f * side - ((side + 1) / 2), 2f, 0, 1F, 40.0F, 1F, 0.0F);
            rope.pitch = frontBack * 0.314f;
            rope.roll = side * -0.314f * 0.5f;

            VertexConsumer ropeVertexConsumer = vertexConsumerProvider
                    .getBuffer(this.model.getLayer(new Identifier("minecraft:textures/block/oak_log.png")));

            rope.render(matrixStack, ropeVertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }

        // End Ropes

        matrixStack.scale(-1.0F, -1.0F, 1.0F);

        // Start Balloon
        matrixStack.push();
        matrixStack.scale(2, 2, 2);
        matrixStack.translate(-0.5D, -2, 0.5D);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
        if (!(blimpEntity.getPrimaryPassenger() == MinecraftClient.getInstance().player)) {

            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(
                    PlaneBlocks.BALLOON_BLOCK.getDefaultState(), matrixStack, vertexConsumerProvider, light,
                    OverlayTexture.DEFAULT_UV);
        } else {
            WorldRenderer.drawBox(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getLines()), 0, 0, 0, 1, 1, 1,
                    0.75F, 0.67F, 0.52F, 1);
        }
        matrixStack.pop();

        // End Balloon

        // Wobble
        float wobbleTime = (float) blimpEntity.getDamageWobbleTicks() - tickDelta;
        float wobbleAmount = blimpEntity.getDamageWobbleStrength() - tickDelta;
        if (wobbleAmount < 0.0F) {
            wobbleAmount = 0.0F;
        }

        if (wobbleTime > 0.0F) {
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(wobbleTime) * wobbleTime
                    * wobbleAmount / 10.0F * (float) blimpEntity.getDamageWobbleSide()));
        }

        // Rotate Paddles
        this.model.setAngles(blimpEntity, tickDelta, 0.0F, -0.1F, 0.0F, 0.0F);

        // Align with player pitch
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(blimpEntity.getBlimpPitch() * 2));
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));

        VertexConsumer lightVertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(getTexture(blimpEntity)));
        this.model.render(matrixStack, lightVertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        VertexConsumer waterVertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask());
        this.model.getBottom().render(matrixStack, waterVertexConsumer, light, OverlayTexture.DEFAULT_UV);

        matrixStack.pop();
        super.render(blimpEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

}