package com.ibraheemrodrigues.epicplanes;

import net.minecraft.util.Identifier;

public class Util {
    public static final String ID = "epicplanes";

    public static final Identifier getID(String name) {
        return new Identifier(ID, name);
    }
}