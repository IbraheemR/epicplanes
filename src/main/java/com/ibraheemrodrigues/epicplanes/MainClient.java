package com.ibraheemrodrigues.epicplanes;

import com.ibraheemrodrigues.epicplanes.cookie_creeper.CookieCreeperEntity;

import net.fabricmc.api.ClientModInitializer;

public class MainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CookieCreeperEntity.clientInit();
    }
}