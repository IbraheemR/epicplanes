package com.ibraheemrodrigues.epicplanes.item;

import com.ibraheemrodrigues.epicplanes.Util;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class PlaneItems {

    // Main Items
    public static final Item ANTENNA = new Item(new Item.Settings());

    public static final Item OAK_BLIMP = new BlimpItem(BoatEntity.Type.OAK, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP).maxCount(1));
    public static final Item SPRUCE_BLIMP = new BlimpItem(BoatEntity.Type.SPRUCE, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP).maxCount(1));
    public static final Item BIRCH_BLIMP = new BlimpItem(BoatEntity.Type.BIRCH, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP).maxCount(1));
    public static final Item JUNGLE_BLIMP = new BlimpItem(BoatEntity.Type.JUNGLE, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP).maxCount(1));
    public static final Item ACACIA_BLIMP = new BlimpItem(BoatEntity.Type.ACACIA, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP).maxCount(1));
    public static final Item DARK_OAK_BLIMP = new BlimpItem(BoatEntity.Type.DARK_OAK, new Item.Settings().group(PlaneItemGroup.MAIN_GROUP).maxCount(1));


    public static final Item LOCALIZER_RECEIVER = new LocalizerReceiver(
            new Item.Settings().group(PlaneItemGroup.MAIN_GROUP).maxCount(1));


    public static void init() {
        PlaneItemGroup.init();

        Registry.register(Registry.ITEM, Util.getID("antenna"), ANTENNA);

        Registry.register(Registry.ITEM, Util.getID("oak_blimp"), OAK_BLIMP);
        Registry.register(Registry.ITEM, Util.getID("spruce_blimp"), SPRUCE_BLIMP);
        Registry.register(Registry.ITEM, Util.getID("birch_blimp"), BIRCH_BLIMP);
        Registry.register(Registry.ITEM, Util.getID("jungle_blimp"), JUNGLE_BLIMP);
        Registry.register(Registry.ITEM, Util.getID("acacia_blimp"), ACACIA_BLIMP);
        Registry.register(Registry.ITEM, Util.getID("dark_oak_blimp"), DARK_OAK_BLIMP);

        Registry.register(Registry.ITEM, Util.getID("localizer_receiver"), LOCALIZER_RECEIVER);
    }
}