package com.ibraheemrodrigues.epicplanes.block;

import com.ibraheemrodrigues.epicplanes.block.entity.LocalizerBlockEntityRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class PlaneBlocksClient {
    public static void clientInit() {
        BlockRenderLayerMap.INSTANCE.putBlock(PlaneBlocks.RUNWAY_MARKING_WHITE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlaneBlocks.RUNWAY_MARKING_THICK_WHITE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlaneBlocks.RUNWAY_MARKING_YELLOW, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlaneBlocks.RUNWAY_MARKING_DASHED_YELLOW, RenderLayer.getCutout());

        BlockEntityRendererRegistry.INSTANCE.register(PlaneBlocks.LOCALIZER_BLOCK_ENTITY,
                LocalizerBlockEntityRenderer::new);
    }
}
