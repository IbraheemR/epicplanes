package com.ibraheemrodrigues.epicplanes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Identifier;

public class Util {
    public static final String ID = "epicplanes";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final Identifier getID(String name) {
        return new Identifier(ID, name);
    }
}