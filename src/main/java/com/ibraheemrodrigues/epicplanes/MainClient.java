package com.ibraheemrodrigues.epicplanes;

import com.ibraheemrodrigues.epicplanes.entity.PlaneEntitiesClient;
import net.fabricmc.api.ClientModInitializer;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PlaneEntitiesClient.clientInit();
        PlaneEntitiesClient.clientInit();
    }
}