package com.ibraheemrodrigues.epicplanes.entity.basicplane;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.CompositeEntityModel;

import java.util.Arrays;
import java.util.function.Function;

public class BasicPlaneModel extends CompositeEntityModel<BasicPlane> {

    private static BoatEntityModel fakeModel = new BoatEntityModel();

    private final ImmutableList<ModelPart> parts;


    public BasicPlaneModel() {
        super();

        ImmutableList<ModelPart> boatHull = fakeModel.getParts().subList(0, 5);

        ImmutableList.Builder<ModelPart> builder = ImmutableList.builder();
        builder.addAll(boatHull);
        this.parts = builder.build();
    }

    @Override
    public void setAngles(BasicPlane entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {

    }

    @Override
    public Iterable<ModelPart> getParts() {
        return this.parts;
    }
}
