package com.ibraheemrodrigues.epicplanes.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class SteelScaffoldingBlock extends Block implements Waterloggable {
    public static final int MAX_DISTANCE = 12;

    public static final IntProperty DISTANCE = IntProperty.of("distance", 0, MAX_DISTANCE);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty BOTTOM = Properties.BOTTOM;

    private static final VoxelShape FLOOR_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 2, 16);
    private static final VoxelShape FLOOR_REGION = VoxelShapes.fullCube().offset(0, -1, 0);


    protected SteelScaffoldingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(DISTANCE, MAX_DISTANCE).with(WATERLOGGED, false).with(BOTTOM, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE);
        builder.add(WATERLOGGED);
        builder.add(BOTTOM);

    }

    @Override
    public void scheduledTick(BlockState currentState, ServerWorld world, BlockPos pos, Random random) {
        int distance = this.calculateDistance(world, pos);
        boolean bottom = this.shouldBeBottom(world, pos, distance);
        BlockState newState = currentState.with(DISTANCE, distance).with(BOTTOM, bottom);

        if (distance == MAX_DISTANCE) {
            if (currentState.get(DISTANCE) == MAX_DISTANCE) {
                world.spawnEntity(new FallingBlockEntity(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, newState.with(WATERLOGGED, false)));
            } else {
                world.breakBlock(pos, true);
            }
        } else if (currentState != newState) {
            world.setBlockState(pos, newState);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (!world.isClient()) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }

        return state;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        if (context.isAbove(VoxelShapes.fullCube(), pos, true) && !context.isDescending()) {
            return VoxelShapes.fullCube();
        } else if (state.get(DISTANCE) != 0 && state.get(BOTTOM) && context.isAbove(FLOOR_REGION, pos, true)){

            return FLOOR_SHAPE;
        } else {

            return VoxelShapes.empty();
        }
    }

    @Override
    public VoxelShape getRayTraceShape(BlockState state, BlockView view, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return calculateDistance((World) world, pos) <= MAX_DISTANCE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();

        int distance = calculateDistance(world, blockPos);
        boolean bottom = this.shouldBeBottom(world, blockPos, distance);
        boolean waterlogged = world.getFluidState(blockPos).getFluid() == Fluids.WATER;
        return this.getStateManager().getDefaultState().with(DISTANCE, distance).with(WATERLOGGED, waterlogged).with(BOTTOM, bottom);
    }

    private boolean shouldBeBottom(World world, BlockPos blockPos, int distance) {
        return  distance > 0 && world.getBlockState(blockPos.down()).getBlock() != this;
    }

    public static int calculateDistance(World world, BlockPos blockPos) {
        BlockPos.Mutable mutable = (new BlockPos.Mutable(blockPos)).setOffset(Direction.DOWN);
        BlockState blockStateDown = world.getBlockState(mutable);
        int i = MAX_DISTANCE;
        if (blockStateDown.getBlock() instanceof SteelScaffoldingBlock) {
            i = blockStateDown.get(DISTANCE);
        } else if (blockStateDown.isSideSolidFullSquare(world, mutable, Direction.UP) && blockStateDown.getBlock() != Blocks.SCAFFOLDING) {
            return 0;
        }

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockState horizontalBlock = world.getBlockState(mutable.set(blockPos).setOffset(direction));
            if (horizontalBlock.getBlock() instanceof SteelScaffoldingBlock) {
                i = Math.min(i, horizontalBlock.get(DISTANCE) + 1);
                if (i == 1) {
                    break;
                }
            }
        }

        return i;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moved) {
        if (!world.isClient) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext ctx) {
        return ctx.getStack().getItem() == this.asItem();
    }
}
