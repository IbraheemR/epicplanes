package com.ibraheemrodrigues.epicplanes;

import com.ibraheemrodrigues.epicplanes.entity.PlaneEntities;

import net.fabricmc.api.ClientModInitializer;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PlaneEntities.clientInit();
    }
}