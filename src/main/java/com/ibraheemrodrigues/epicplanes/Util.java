package com.ibraheemrodrigues.epicplanes;

import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Identifier;

public class Util {
    public static final String ID = "epicplanes";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static Identifier getID(String name) {
        return new Identifier(ID, name);
    }

    public static Vec3d vec3dNoY(Vec3d vector) {
        return new Vec3d(vector.getX(), 0, vector.getZ());
    }
}
