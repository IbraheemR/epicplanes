package com.ibraheemrodrigues.epicplanes.block;

import com.ibraheemrodrigues.epicplanes.block.entity.LocalizerBlockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockPlacementEnvironment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LocalizerBlock extends Block implements BlockEntityProvider {

    public static String CYCLE_BEAM_HEADING_LANG_KEY = "block.epicplanes.localizer_block.setHeading";
    public static String CYCLE_BEAM_PITCH_LANG_KEY = "block.epicplanes.localizer_block.setPitch";

    public LocalizerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new LocalizerBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {

        LocalizerBlockEntity blockEntity = (LocalizerBlockEntity) world.getBlockEntity(pos);

        if (player.getStackInHand(hand).isEmpty()) {
                if (player.isSneaking()) {
                    blockEntity.cycleBeamHeading();
                    if (world.isClient) {
                        player.sendMessage(new TranslatableText(CYCLE_BEAM_HEADING_LANG_KEY, blockEntity.getHeading()));
                    }
                } else {
                    blockEntity.cycleBeamPitch();
                    if (world.isClient) {
                        player.sendMessage(new TranslatableText(CYCLE_BEAM_PITCH_LANG_KEY, blockEntity.getPitch()));

                    }
                }
                return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}