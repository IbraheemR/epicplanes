package com.ibraheemrodrigues.epicplanes.entity;

import com.ibraheemrodrigues.epicplanes.entity.blimp.BlimpEntity;
import com.ibraheemrodrigues.epicplanes.entity.cookie_creeper.CookieCreeperEntity;

public class PlaneEntities {
    public static final void init() {
        CookieCreeperEntity.init();
        BlimpEntity.init();
    }

    public static final void clientInit() {
        CookieCreeperEntity.clientInit();
        BlimpEntity.clientInit();
    }
}