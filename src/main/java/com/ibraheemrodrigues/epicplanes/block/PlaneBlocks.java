package com.ibraheemrodrigues.epicplanes.block;

import com.ibraheemrodrigues.epicplanes.Util;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class PlaneBlocks {
    public static final Block BALLOON_BLOCK = new FloatingBlock(FabricBlockSettings.of(Material.WOOL).build());

    public static void init() {
        Registry.register(Registry.BLOCK, Util.getID("balloon"), BALLOON_BLOCK);
        Registry.register(Registry.ITEM, Util.getID("balloon"),
                new BlockItem(BALLOON_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
    }
}