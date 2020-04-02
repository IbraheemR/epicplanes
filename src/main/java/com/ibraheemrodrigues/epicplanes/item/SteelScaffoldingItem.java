package com.ibraheemrodrigues.epicplanes.item;

import com.ibraheemrodrigues.epicplanes.block.SteelScaffoldingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SteelScaffoldingItem extends BlockItem {
    public SteelScaffoldingItem(Block block, Settings settings) {
        super(block, settings);
    }


    @Override
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos placementPos = context.getBlockPos();
        World world = context.getWorld();


        // If targeting a SteelScaffolding block, this will be that block, since `SteelScaffoldingBlock#canReplace` returns true for SteelScaffolding
        BlockState currentBlockState = world.getBlockState(placementPos);

    if (currentBlockState.getBlock() instanceof SteelScaffoldingBlock) {
            Direction placementDirection;
            if (context.shouldCancelInteraction()) {
                placementDirection = context.hitsInsideBlock() ? context.getSide().getOpposite() : context.getSide();
            } else {
                placementDirection = context.getSide() == Direction.UP ? context.getPlayerFacing() : Direction.UP;
            }

            int i = 0;
            BlockPos.Mutable offsetPlacementPosition = new BlockPos.Mutable(placementPos).setOffset(placementDirection);

            while (i < SteelScaffoldingBlock.MAX_DISTANCE) {
                if (!world.isClient() && !World.isValid(offsetPlacementPosition)) {
                    PlayerEntity player = context.getPlayer();
                    int worldHeight = world.getHeight();

                    if (player instanceof ServerPlayerEntity && offsetPlacementPosition.getY() >= worldHeight) {
                        ChatMessageS2CPacket buildHeightMessage = new ChatMessageS2CPacket(
                                new TranslatableText("build.tooHigh", worldHeight)
                                        .formatted(Formatting.RED),
                                MessageType.GAME_INFO
                        );

                        ((ServerPlayerEntity) player).networkHandler.sendPacket(buildHeightMessage);
                    }

                    break;
                }

                currentBlockState = world.getBlockState(offsetPlacementPosition);
                if (!(currentBlockState.getBlock() instanceof SteelScaffoldingBlock)) {
                    if (currentBlockState.canReplace(context)) {
                        return ItemPlacementContext.offset(context, offsetPlacementPosition, placementDirection);
                    }

                    break;
                }

                offsetPlacementPosition.setOffset(placementDirection);
                if (placementDirection.getAxis().isHorizontal()) {
                    ++i;
                }
            }
        } else {
            // Place as normal block
            return SteelScaffoldingBlock.calculateDistance(world, placementPos) >= SteelScaffoldingBlock.MAX_DISTANCE? null : context;
        }

        return null;
    }
}
