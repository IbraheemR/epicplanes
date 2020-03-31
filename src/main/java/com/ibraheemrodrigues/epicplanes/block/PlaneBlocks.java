package com.ibraheemrodrigues.epicplanes.block;

import com.ibraheemrodrigues.epicplanes.Util;
import com.ibraheemrodrigues.epicplanes.block.entity.LocalizerBlockEntity;
import com.ibraheemrodrigues.epicplanes.item.PlaneItemGroup;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class PlaneBlocks {
        public static final FloatingBlock BALLOON_BLOCK = new FloatingBlock(
                        FabricBlockSettings.of(Material.WOOL).build());

        // Runway Blocks
        public static final Block ASPHALT_BLOCK = new Block(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.BLACK).build());
        // TODO: this needs to be updated to change light level based on "lit" property. Waiting on FabricAPI
        public static final RedstoneLampBlock LANDING_LIGHT_WHITE = new RedstoneLampBlock(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.WHITE).lightLevel(15)
                                        .build());
        public static final RedstoneLampBlock LANDING_LIGHT_RED = new RedstoneLampBlock(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.RED).lightLevel(15).build());
        public static final RedstoneLampBlock LANDING_LIGHT_YELLOW = new RedstoneLampBlock(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.YELLOW).lightLevel(15)
                                        .build());
        public static final RedstoneLampBlock LANDING_LIGHT_BLUE = new RedstoneLampBlock(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.BLUE).lightLevel(15)
                                        .build());
        public static final RedstoneLampBlock LANDING_LIGHT_GREEN = new RedstoneLampBlock(
                        FabricBlockSettings.copy(Blocks.STONE).materialColor(MaterialColor.GREEN).lightLevel(15)
                                        .build());

        // Runway Markings
        public static final RunwayMarkingBlock RUNWAY_MARKING_WHITE = new RunwayMarkingBlock(
                        FabricBlockSettings.of(Material.STONE).noCollision().materialColor(MaterialColor.WHITE).build());
        public static final RunwayMarkingBlock RUNWAY_MARKING_THICK_WHITE = new RunwayMarkingBlock(
                        FabricBlockSettings.of(Material.STONE).noCollision().materialColor(MaterialColor.WHITE).build());

        public static final RunwayMarkingBlock RUNWAY_MARKING_YELLOW = new RunwayMarkingBlock(FabricBlockSettings
                        .of(Material.STONE).noCollision().materialColor(MaterialColor.YELLOW).build());
        public static final RunwayMarkingBlock RUNWAY_MARKING_DASHED_YELLOW = new RunwayMarkingBlock(FabricBlockSettings
                        .of(Material.STONE).noCollision().materialColor(MaterialColor.YELLOW).build());

        // Localizer
        public static final Block LOCALIZER_BLOCK = new LocalizerBlock(
                        FabricBlockSettings.copy(Blocks.PISTON).materialColor(MaterialColor.RED).build());
        public static BlockEntityType<LocalizerBlockEntity> LOCALIZER_BLOCK_ENTITY;

        public static void init() {
                Registry.register(Registry.BLOCK, Util.getID("balloon"), BALLOON_BLOCK);
                Registry.register(Registry.ITEM, Util.getID("balloon"),
                                new BlockItem(BALLOON_BLOCK, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));

                Registry.register(Registry.BLOCK, Util.getID("asphalt"), ASPHALT_BLOCK);
                Registry.register(Registry.ITEM, Util.getID("asphalt"),
                                new BlockItem(ASPHALT_BLOCK, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("white_landing_light"), LANDING_LIGHT_WHITE);
                Registry.register(Registry.ITEM, Util.getID("white_landing_light"),
                                new BlockItem(LANDING_LIGHT_WHITE, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("red_landing_light"), LANDING_LIGHT_RED);
                Registry.register(Registry.ITEM, Util.getID("red_landing_light"),
                                new BlockItem(LANDING_LIGHT_RED, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("yellow_landing_light"), LANDING_LIGHT_YELLOW);
                Registry.register(Registry.ITEM, Util.getID("yellow_landing_light"),
                                new BlockItem(LANDING_LIGHT_YELLOW, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("blue_landing_light"), LANDING_LIGHT_BLUE);
                Registry.register(Registry.ITEM, Util.getID("blue_landing_light"),
                                new BlockItem(LANDING_LIGHT_BLUE, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("green_landing_light"), LANDING_LIGHT_GREEN);
                Registry.register(Registry.ITEM, Util.getID("green_landing_light"),
                                new BlockItem(LANDING_LIGHT_GREEN, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));

                Registry.register(Registry.BLOCK, Util.getID("white_runway_marking"), RUNWAY_MARKING_WHITE);
                Registry.register(Registry.ITEM, Util.getID("white_runway_marking"),
                                new BlockItem(RUNWAY_MARKING_WHITE, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("thick_white_runway_marking"), RUNWAY_MARKING_THICK_WHITE);
                Registry.register(Registry.ITEM, Util.getID("thick_white_runway_marking"), new BlockItem(
                        RUNWAY_MARKING_THICK_WHITE, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));

                Registry.register(Registry.BLOCK, Util.getID("yellow_runway_marking"), RUNWAY_MARKING_YELLOW);
                Registry.register(Registry.ITEM, Util.getID("yellow_runway_marking"), new BlockItem(
                        RUNWAY_MARKING_YELLOW, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));
                Registry.register(Registry.BLOCK, Util.getID("dashed_yellow_runway_marking"),
                        RUNWAY_MARKING_DASHED_YELLOW);
                Registry.register(Registry.ITEM, Util.getID("dashed_yellow_runway_marking"), new BlockItem(
                        RUNWAY_MARKING_DASHED_YELLOW, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP)));

                Registry.register(Registry.BLOCK, Util.getID("localizer_block"), LOCALIZER_BLOCK);
                Registry.register(Registry.ITEM, Util.getID("localizer_block"), new BlockItem(LOCALIZER_BLOCK,
                                new Item.Settings().group(PlaneItemGroup.MAIN_GROUP).maxCount(1)));
                LOCALIZER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, Util.getID("localizer_block"),
                                BlockEntityType.Builder.create(LocalizerBlockEntity::new, LOCALIZER_BLOCK).build(null));
        }
}