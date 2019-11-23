package com.ibraheemrodrigues.epicplanes;

import net.fabricmc.api.ModInitializer;

import com.ibraheemrodrigues.epicplanes.entity.PlaneEntities;
import com.ibraheemrodrigues.epicplanes.item.PlaneItems;
import com.ibraheemrodrigues.epicplanes.block.PlaneBlocks;

public class Main implements ModInitializer {

	@Override
	public void onInitialize() {
		PlaneItems.init();
		PlaneBlocks.init();
		PlaneEntities.init();
	}
}
