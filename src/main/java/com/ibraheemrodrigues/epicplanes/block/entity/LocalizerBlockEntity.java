package com.ibraheemrodrigues.epicplanes.block.entity;

import com.ibraheemrodrigues.epicplanes.block.PlaneBlocks;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;

public class LocalizerBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

    private Direction heading;
    public static String HEADING_KEY = "heading";
    private int pitch;
    public static String PITCH_KEY = "pitch";

    public LocalizerBlockEntity() {
        super(PlaneBlocks.LOCALIZER_BLOCK_ENTITY);

        this.heading = Direction.NORTH;
        this.pitch = 3;

    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        if (tag.contains(HEADING_KEY)) {
            this.heading = Direction.byName(tag.getString(HEADING_KEY));
        } else {
            this.heading = Direction.NORTH;
        }
        if (tag.contains(PITCH_KEY)) {
            this.pitch = tag.getInt(PITCH_KEY);
        } else {
            this.pitch = 3;
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        tag.putString(HEADING_KEY, this.heading.asString());

        tag.putInt(PITCH_KEY, this.pitch);

        return tag;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return toTag(tag);
    }

    public void cycleBeamHeading() {
        this.heading = this.heading.rotateYClockwise();

        if (!world.isClient) {
            sync();
        }
    }

    public void cycleBeamPitch() {
        this.pitch++;
        if (this.pitch > 15) {
            this.pitch = 3;
        }

        if (!world.isClient) {
            sync();
        }
    }

    public Direction getHeading() { return this.heading != null ? this.heading : Direction.NORTH; }
    public int getPitch() {
        return this.pitch;
    }
}