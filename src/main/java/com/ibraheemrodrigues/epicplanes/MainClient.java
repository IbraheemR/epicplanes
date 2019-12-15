package com.ibraheemrodrigues.epicplanes;

import com.ibraheemrodrigues.epicplanes.block.PlaneBlocks;
import com.ibraheemrodrigues.epicplanes.entity.PlaneEntities;

import com.ibraheemrodrigues.epicplanes.entity.PlaneEntitiesClient;
import net.fabricmc.api.ClientModInitializer;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PlaneBlocks.clientInit();
        PlaneEntitiesClient.clientInit();
    }
}