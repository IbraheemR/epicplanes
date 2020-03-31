package com.ibraheemrodrigues.epicplanes.block.entity;

import com.ibraheemrodrigues.epicplanes.item.PlaneItems;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class LocalizerBlockEntityRenderer extends BlockEntityRenderer<LocalizerBlockEntity> {

    public LocalizerBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(LocalizerBlockEntity blockEntity, float partialTicks, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {

        matrixStack.push();

        matrixStack.translate(0.5, 1.1, 0.5);
        matrixStack.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(blockEntity.getHeading().asRotation()));

        // Metal Post
        MinecraftClient.getInstance().getItemRenderer().renderItem(new ItemStack(PlaneItems.ANTENNA), ModelTransformation.Mode.GROUND, light, overlay, matrixStack, vertexConsumerProvider);

        matrixStack.translate(0, 0.4, 0);

        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-blockEntity.getPitch()));

        // Beacon
        matrixStack.push();
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
        matrixStack.translate(0, -0.2, 0);

        MinecraftClient.getInstance().getItemRenderer().renderItem(new ItemStack(Blocks.BEACON), ModelTransformation.Mode.GROUND, light, overlay, matrixStack, vertexConsumerProvider);
        matrixStack.pop();

        // Radial Element
        matrixStack.push();

        matrixStack.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(blockEntity.getWorld().getTime() + partialTicks));
        matrixStack.translate(0, 0.1 , 0);

        MinecraftClient.getInstance().getItemRenderer().renderItem(new ItemStack(Items.REDSTONE_TORCH), ModelTransformation.Mode.GROUND, light, overlay, matrixStack, vertexConsumerProvider);

        matrixStack.translate(0, -0.2 , 0);

        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180));


        MinecraftClient.getInstance().getItemRenderer().renderItem(new ItemStack(Items.REDSTONE_TORCH), ModelTransformation.Mode.GROUND, light, overlay, matrixStack, vertexConsumerProvider);

        matrixStack.pop();

        matrixStack.pop();
    }
}