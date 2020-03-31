package com.ibraheemrodrigues.epicplanes.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneLampBlock;

public class InvertedRedstoneLampBlock extends RedstoneLampBlock {

    public InvertedRedstoneLampBlock(Settings settings) {
        super(settings);
    }

    // TODO: HACK should really redefine LIT property, so lit:true is on, but this
    // works for now
    @Override
    public int getLuminance(BlockState state) {
        return (Boolean) state.get(LIT) ? 0 : this.lightLevel;
    }
}
