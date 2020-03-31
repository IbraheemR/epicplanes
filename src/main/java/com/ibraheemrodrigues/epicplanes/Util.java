package com.ibraheemrodrigues.epicplanes;

import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
    public static final String ID = "epicplanes";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final Identifier getID(String name) {
        return new Identifier(ID, name);
    }
}