package com.ibraheemrodrigues.epicplanes.item;

import com.ibraheemrodrigues.epicplanes.Util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class PlaneItems {
    public static final Item BLIMP_ITEM = new BlimpItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));

    public static final void init() {
        Registry.register(Registry.ITEM, Util.getID("blimp"), BLIMP_ITEM);
    }
}