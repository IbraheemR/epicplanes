package com.ibraheemrodrigues.epicplanes;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

class PlaneBlocks {
    public static final Block BALLOON_BLOCK = new Block(FabricBlockSettings.of(Material.WOOL).build());

    public static void init() {
        Registry.register(Registry.BLOCK, Util.Id("balloon"), BALLOON_BLOCK);
        Registry.register(Registry.ITEM, Util.Id("balloon"),
                new BlockItem(BALLOON_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
    }
}