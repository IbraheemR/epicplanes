package com.ibraheemrodrigues.epicplanes;

import com.ibraheemrodrigues.epicplanes.block.PlaneBlocks;
import com.ibraheemrodrigues.epicplanes.client.PlaneKeybinds;
import com.ibraheemrodrigues.epicplanes.entity.PlaneEntitiesClient;
import com.ibraheemrodrigues.epicplanes.network.PlanePackets;
import net.fabricmc.api.ClientModInitializer;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PlaneBlocks.clientInit();
        PlaneEntitiesClient.clientInit();
        PlaneKeybinds.clientInit();
        PlanePackets.clientInit();
    }
}