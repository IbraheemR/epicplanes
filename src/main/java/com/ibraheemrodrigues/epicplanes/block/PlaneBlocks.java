package com.ibraheemrodrigues.epicplanes.block;

import com.ibraheemrodrigues.epicplanes.Util;
import com.ibraheemrodrigues.epicplanes.item.PlaneItems;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class PlaneBlocks {
        public static final FloatingBlock BALLOON_BLOCK = new FloatingBlock(
                        FabricBlockSettings.of(Material.WOOL).build());

        // Runway Blocks
        public static final Block ASPHALT_BLOCK = new Block(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.BLACK).build());

        public static final InvertedRedstoneLampBlock LANDING_LIGHT_WHITE = new InvertedRedstoneLampBlock(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.WHITE).lightLevel(15)
                                        .build());
        public static final InvertedRedstoneLampBlock LANDING_LIGHT_RED = new InvertedRedstoneLampBlock(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.RED).lightLevel(15).build());
        public static final InvertedRedstoneLampBlock LANDING_LIGHT_YELLOW = new InvertedRedstoneLampBlock(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.YELLOW).lightLevel(15)
                                        .build());
        public static final InvertedRedstoneLampBlock LANDING_LIGHT_BLUE = new InvertedRedstoneLampBlock(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.BLUE).lightLevel(15)
                                        .build());
        public static final InvertedRedstoneLampBlock LANDING_LIGHT_GREEN = new InvertedRedstoneLampBlock(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.GREEN).lightLevel(15)
                                        .build());

        // Runway Markings
        public static final RunwayMarkingBlock WHITE_RUNWAY_MARKING = new RunwayMarkingBlock(
                        FabricBlockSettings.of(Material.PART).noCollision().materialColor(MaterialColor.WHITE).build());
        public static final RunwayMarkingBlock THICK_WHITE_RUNWAY_MARKING = new RunwayMarkingBlock(
                        FabricBlockSettings.of(Material.PART).noCollision().materialColor(MaterialColor.WHITE).build());

        public static final RunwayMarkingBlock YELLOW_RUNWAY_MARKING = new RunwayMarkingBlock(FabricBlockSettings
                        .of(Material.PART).noCollision().materialColor(MaterialColor.YELLOW).build());
        public static final RunwayMarkingBlock DASHED_YELLOW_RUNWAY_MARKING = new RunwayMarkingBlock(FabricBlockSettings
                        .of(Material.PART).noCollision().materialColor(MaterialColor.YELLOW).build());

        public static void init() {
                Registry.register(Registry.BLOCK, Util.getID("balloon"), BALLOON_BLOCK);
                Registry.register(Registry.ITEM, Util.getID("balloon"),
                                new BlockItem(BALLOON_BLOCK, new Item.Settings().group(PlaneItems.PLANE_GROUP)));

                Registry.register(Registry.BLOCK, Util.getID("asphalt"), ASPHALT_BLOCK);
                Registry.register(Registry.ITEM, Util.getID("asphalt"),
                                new BlockItem(ASPHALT_BLOCK, new Item.Settings().group(PlaneItems.PLANE_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("white_landing_light"), LANDING_LIGHT_WHITE);
                Registry.register(Registry.ITEM, Util.getID("white_landing_light"),
                                new BlockItem(LANDING_LIGHT_WHITE, new Item.Settings().group(PlaneItems.PLANE_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("red_landing_light"), LANDING_LIGHT_RED);
                Registry.register(Registry.ITEM, Util.getID("red_landing_light"),
                                new BlockItem(LANDING_LIGHT_RED, new Item.Settings().group(PlaneItems.PLANE_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("yellow_landing_light"), LANDING_LIGHT_YELLOW);
                Registry.register(Registry.ITEM, Util.getID("yellow_landing_light"),
                                new BlockItem(LANDING_LIGHT_YELLOW, new Item.Settings().group(PlaneItems.PLANE_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("blue_landing_light"), LANDING_LIGHT_BLUE);
                Registry.register(Registry.ITEM, Util.getID("blue_landing_light"),
                                new BlockItem(LANDING_LIGHT_BLUE, new Item.Settings().group(PlaneItems.PLANE_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("green_landing_light"), LANDING_LIGHT_GREEN);
                Registry.register(Registry.ITEM, Util.getID("green_landing_light"),
                                new BlockItem(LANDING_LIGHT_GREEN, new Item.Settings().group(PlaneItems.PLANE_GROUP)));

                Registry.register(Registry.BLOCK, Util.getID("white_runway_marking"), WHITE_RUNWAY_MARKING);
                Registry.register(Registry.ITEM, Util.getID("white_runway_marking"),
                                new BlockItem(WHITE_RUNWAY_MARKING, new Item.Settings().group(PlaneItems.PLANE_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("thick_white_runway_marking"), THICK_WHITE_RUNWAY_MARKING);
                Registry.register(Registry.ITEM, Util.getID("thick_white_runway_marking"), new BlockItem(
                                THICK_WHITE_RUNWAY_MARKING, new Item.Settings().group(PlaneItems.PLANE_GROUP)));

                Registry.register(Registry.BLOCK, Util.getID("yellow_runway_marking"), YELLOW_RUNWAY_MARKING);
                Registry.register(Registry.ITEM, Util.getID("yellow_runway_marking"), new BlockItem(
                                YELLOW_RUNWAY_MARKING, new Item.Settings().group(PlaneItems.PLANE_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("dashed_yellow_runway_marking"),
                                DASHED_YELLOW_RUNWAY_MARKING);
                Registry.register(Registry.ITEM, Util.getID("dashed_yellow_runway_marking"), new BlockItem(
                                DASHED_YELLOW_RUNWAY_MARKING, new Item.Settings().group(PlaneItems.PLANE_GROUP)));
        }

        public static void clientInit() {
                BlockRenderLayerMap.INSTANCE.putBlock(WHITE_RUNWAY_MARKING, RenderLayer.getCutout());
                BlockRenderLayerMap.INSTANCE.putBlock(THICK_WHITE_RUNWAY_MARKING, RenderLayer.getCutout());
                BlockRenderLayerMap.INSTANCE.putBlock(YELLOW_RUNWAY_MARKING, RenderLayer.getCutout());
                BlockRenderLayerMap.INSTANCE.putBlock(DASHED_YELLOW_RUNWAY_MARKING, RenderLayer.getCutout());
        }
}