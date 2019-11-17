package com.ibraheemrodrigues.epicplanes;

import net.fabricmc.api.ModInitializer;

import com.ibraheemrodrigues.epicplanes.blimp.BlimpEntity;
import com.ibraheemrodrigues.epicplanes.cookie_creeper.CookieCreeperEntity;

public class Main implements ModInitializer {

	@Override
	public void onInitialize() {
		CookieCreeperEntity.init();
		BlimpEntity.init();
	}
}
