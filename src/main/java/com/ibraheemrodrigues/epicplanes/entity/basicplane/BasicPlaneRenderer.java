package com.ibraheemrodrigues.epicplanes.entity.basicplane;

import com.ibraheemrodrigues.epicplanes.client.PlaneKeybinds;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;


public class BasicPlaneRenderer extends EntityRenderer<BasicPlane> {
    protected final BasicPlaneModel model = new BasicPlaneModel();

    public BasicPlaneRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public Identifier getTexture(BasicPlane entity) {
        return new Identifier("textures/entity/boat/oak.png");
    }

    @Override
    public void render(BasicPlane plane, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {

        matrixStack.push();

        // Rotate to plane yaw
        matrixStack.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(plane.getYaw(tickDelta)));
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(plane.getPitch(tickDelta)));

        // Wobble on damage
        float h = (float)plane.getDamageWobbleTicks() - tickDelta;
        float j = plane.getDamageWobbleStrength() - tickDelta;
        if (j < 0.0F) {
            j = 0.0F;
        }

        if (h > 0.0F) {
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.sin(h) * h * j / 10.0F * (float)plane.getDamageWobbleSide()));
        }

        // Roll to simulate turning
        if (plane.hasPassengers()) {
            if (plane.getPrimaryPassenger() instanceof PlayerEntity) {
                if (!PlaneKeybinds.INSTANCE.freecamToggle) {
                    matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.wrapDegrees(getRiderYaw(plane, tickDelta) - plane.getYaw(tickDelta)) * 0.5F));
                }
            } else {
                matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.wrapDegrees(getRiderYaw(plane, tickDelta) - plane.getYaw(tickDelta)) * 0.5F));
            }
        }


        matrixStack.translate(0, 0.375, 0);

        matrixStack.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(90));

        matrixStack.push();
        // Rotate Model into pos
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180));

        VertexConsumer lightVertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(getTexture(plane)));
        this.model.render(matrixStack, lightVertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        super.render(plane, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);

        matrixStack.pop();

        this.renderInstruments(plane, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);

        matrixStack.pop();

    }

    private void renderInstruments(BasicPlane plane, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        matrixStack.push();

        // Engine Power
        matrixStack.translate(0.5, -0.15, 0.4);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45));

        float engineProportion = (plane.enginePower/plane.maxEnginePower);

        float red = MathHelper.clamp(3 * engineProportion - 1, 0, 1);
        float green = MathHelper.clamp(-3 * engineProportion + 3, 0, 1);

        WorldRenderer.drawBox(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.LINES), 0, 0.5, 0, 0.1, 0.5 * engineProportion, 0.1, 0.5f, 0.5f, 0.5f, 1);
        WorldRenderer.drawBox(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.LINES), 0, 0, 0, 0.1, 0.5 * engineProportion, 0.1, red, green, 0, 1);

        matrixStack.pop();
    }

    private float getRiderYaw(BasicPlane entity, float tickDelta) {
        if (entity.hasPassengers()) {
            Entity rider = entity.getPrimaryPassenger();

            return rider.prevYaw + (rider.yaw - rider.prevYaw) * tickDelta;
        }
        return 0;
    }
}
