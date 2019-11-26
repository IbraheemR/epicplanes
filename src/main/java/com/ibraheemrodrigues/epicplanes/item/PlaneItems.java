package com.ibraheemrodrigues.epicplanes.item;

import com.ibraheemrodrigues.epicplanes.Util;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class PlaneItems {
    private static final Item DUMMY_ICON_ITEM = new Item(new Item.Settings().maxCount(100));

    public static final ItemGroup PLANE_GROUP = FabricItemGroupBuilder.build(Util.getID("main"),
            () -> new ItemStack(DUMMY_ICON_ITEM));

    public static final Item BLIMP_ITEM = new BlimpItem(new Item.Settings().group(PLANE_GROUP).maxCount(1));

    public static final void init() {
        Registry.register(Registry.ITEM, Util.getID("dummy_icon_item"), DUMMY_ICON_ITEM);
        Registry.register(Registry.ITEM, Util.getID("blimp"), BLIMP_ITEM);
    }
}