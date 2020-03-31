package com.ibraheemrodrigues.epicplanes.mixin;

import com.ibraheemrodrigues.epicplanes.block.entity.LocalizerBlockEntity;
import com.ibraheemrodrigues.epicplanes.item.LocalizerReceiver;
import com.ibraheemrodrigues.epicplanes.item.PlaneItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class LocalizerWorldRendererMixin {

        @Shadow
        private BufferBuilderStorage bufferBuilders;

        @Shadow
        private MinecraftClient client;

        @Shadow
        private ClientWorld world;

        @Inject(at = @At("HEAD"), method = "render")
        private void render(MatrixStack matrixStack, float tickDelta, long limitTime, boolean renderBlockOutline,
                            Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager,
                            Matrix4f matrix4f, CallbackInfo info) {

                if (this.client.player.getStackInHand(Hand.MAIN_HAND).getItem() == PlaneItems.LOCALIZER_RECEIVER
                                || this.client.player.getStackInHand(Hand.OFF_HAND)
                                                .getItem() == PlaneItems.LOCALIZER_RECEIVER) {

                        ItemStack localizerItemStack = this.client.player.getStackInHand(Hand.MAIN_HAND)
                                        .getItem() == PlaneItems.LOCALIZER_RECEIVER
                                                        ? this.client.player.getStackInHand(Hand.MAIN_HAND)
                                                        : this.client.player.getStackInHand(Hand.OFF_HAND);

                        BlockPos localizerPos = null;
                        boolean shouldDisplay = false;

                        Direction localizerDirection = Direction.NORTH;
                        int pitch = 0;

                        CompoundTag nbt = localizerItemStack.getTag();
                        if (nbt != null) {
                                if (nbt.contains(LocalizerReceiver.POS_NBT_KEY)) {
                                        localizerPos = BlockPos.fromLong(nbt.getLong(LocalizerReceiver.POS_NBT_KEY));
                                }

                                if (nbt.contains(LocalizerReceiver.SHOULD_DISPLAY_NBT_KEY)) {
                                        shouldDisplay = nbt.getBoolean(LocalizerReceiver.SHOULD_DISPLAY_NBT_KEY);
                                }

                                if (nbt.contains(LocalizerBlockEntity.HEADING_KEY)) {
                                        localizerDirection = Direction.byName(nbt.getString(LocalizerBlockEntity.HEADING_KEY));
                                }

                                if (nbt.contains(LocalizerBlockEntity.PITCH_KEY)) {
                                        pitch = nbt.getInt(LocalizerBlockEntity.PITCH_KEY);
                                }
                        }

                        if (localizerPos != null && shouldDisplay) {
                                VertexConsumer vertexConsumer = bufferBuilders.getEntityVertexConsumers()
                                                .getBuffer(RenderLayer.getLines());

                                Vec3d cameraPos = camera.getPos();

                                int x = localizerPos.getX();
                                int y = localizerPos.getY();
                                int z = localizerPos.getZ();

                                double distToLocalizer = cameraPos.distanceTo(new Vec3d(x, y, z));

                                double innerBeamWidth = 0.5;
                                double innerBeamLength = Math.max(distToLocalizer, 20);

                                double outerBeamWidth = MathHelper.clamp(distToLocalizer, 1, 50);
                                double outerBeamLength = Math.min(distToLocalizer, 100);

                                matrixStack.push();

                                matrixStack.translate(-cameraPos.getX(), -cameraPos.getY(), -cameraPos.getZ());

                                matrixStack.translate(x, y, z);
                                matrixStack.translate(0.5, 1.5, 0.5);

                                // Move to player

                                // Rotate Pitch/Yaw
                                matrixStack.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(localizerDirection.asRotation() + 90));
                                matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(pitch));

                                // Bound box/Outer beam
                                matrixStack.push();
                                // Slide bound box near player
                                matrixStack.translate(distToLocalizer - outerBeamLength, 0, 0);

                                matrixStack.translate(0, -outerBeamWidth / 2, -outerBeamWidth / 2);
                                WorldRenderer.drawBox(matrixStack, vertexConsumer, 0, 0, 0, outerBeamLength * 2,
                                                outerBeamWidth, outerBeamWidth, 1, 0, 0, 1);

                                matrixStack.pop();

                                // Centre line/Inner beam
                                matrixStack.push();

                                matrixStack.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(
                                                this.client.player.getEntityWorld().getTime() + tickDelta));
                                matrixStack.translate(0, -innerBeamWidth / 2, -innerBeamWidth / 2);

                                // Blue/Away
                                WorldRenderer.drawBox(matrixStack, vertexConsumer, innerBeamLength + 5, 0, 0,
                                                2 * innerBeamLength, innerBeamWidth, innerBeamWidth, 0, 0, 1, 1);

                                // Green/Towards
                                WorldRenderer.drawBox(matrixStack, vertexConsumer, 0, 0, 0, innerBeamLength - 5,
                                                innerBeamWidth, innerBeamWidth, 0, 1, 0, 1);

                                matrixStack.pop();
                                matrixStack.pop();

                        }

                }

        }

}