package com.ibraheemrodrigues.epicplanes.item;

import com.ibraheemrodrigues.epicplanes.Util;
import com.ibraheemrodrigues.epicplanes.block.PlaneBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class PlaneItemGroup {
    private static final Item DUMMY_ICON_ITEM = new Item(new Item.Settings());

    public static  final ItemGroup MAIN_GROUP = FabricItemGroupBuilder.create(Util.getID("main")).icon(() -> new ItemStack(DUMMY_ICON_ITEM)).appendItems(itemStacks -> {
        // New Line
        itemStacks.add(new ItemStack(PlaneItems.OAK_BLIMP));
        itemStacks.add(new ItemStack(PlaneItems.SPRUCE_BLIMP));
        itemStacks.add(new ItemStack(PlaneItems.BIRCH_BLIMP));
        itemStacks.add(new ItemStack(PlaneItems.JUNGLE_BLIMP));
        itemStacks.add(new ItemStack(PlaneItems.ACACIA_BLIMP));
        itemStacks.add(new ItemStack(PlaneItems.DARK_OAK_BLIMP));
        itemStacks.add(ItemStack.EMPTY);
        itemStacks.add(ItemStack.EMPTY);
        itemStacks.add(new ItemStack(PlaneBlocks.BALLOON_BLOCK));

        // New Line
        itemStacks.add(new ItemStack(PlaneBlocks.ASPHALT_BLOCK));
        itemStacks.add(new ItemStack(PlaneBlocks.LANDING_LIGHT_WHITE));
        itemStacks.add(new ItemStack(PlaneBlocks.LANDING_LIGHT_YELLOW));
        itemStacks.add(new ItemStack(PlaneBlocks.LANDING_LIGHT_RED));
        itemStacks.add(new ItemStack(PlaneBlocks.LANDING_LIGHT_GREEN));
        itemStacks.add(new ItemStack(PlaneBlocks.LANDING_LIGHT_BLUE));
        itemStacks.add(ItemStack.EMPTY);
        itemStacks.add(new ItemStack(PlaneItems.LOCALIZER_RECEIVER));
        itemStacks.add(new ItemStack(PlaneBlocks.LOCALIZER_BLOCK));


        // New Line
        itemStacks.add(new ItemStack(PlaneBlocks.RUNWAY_MARKING_WHITE));
        itemStacks.add(new ItemStack(PlaneBlocks.RUNWAY_MARKING_THICK_WHITE));
        itemStacks.add(new ItemStack(PlaneBlocks.RUNWAY_MARKING_YELLOW));
        itemStacks.add(new ItemStack(PlaneBlocks.RUNWAY_MARKING_DASHED_YELLOW));
        itemStacks.add(ItemStack.EMPTY);
        itemStacks.add(ItemStack.EMPTY);
        itemStacks.add(ItemStack.EMPTY);
        itemStacks.add(ItemStack.EMPTY);
        itemStacks.add(ItemStack.EMPTY);

        // New Line
        itemStacks.add(new ItemStack(PlaneItems.ANTENNA));

    }).build();

    public static void init() {
        Registry.register(Registry.ITEM, Util.getID("dummy_icon_item"), DUMMY_ICON_ITEM);
    }
}
