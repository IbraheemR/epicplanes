package com.ibraheemrodrigues.epicplanes.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RunwayMarkingBlock extends Block {
    public static final BooleanProperty CONNECTION_NORTH = BooleanProperty.of("north");
    public static final BooleanProperty CONNECTION_EAST = BooleanProperty.of("east");
    public static final BooleanProperty CONNECTION_SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty CONNECTION_WEST = BooleanProperty.of("west");

    private final Set<BlockPos> affectedNeighbors = Sets.newHashSet();

    public RunwayMarkingBlock(Block.Settings settings) {
        super(settings);


        this.setDefaultState(this.getStateManager().getDefaultState()
                        .with(CONNECTION_NORTH, false)
                        .with(CONNECTION_SOUTH, false)
                        .with(CONNECTION_EAST, false)
                        .with(CONNECTION_WEST, false)
        );
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView blockView = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        return this.getDefaultState()
                .with(CONNECTION_NORTH, this.shouldConnect(blockView, pos, Direction.NORTH))
                .with(CONNECTION_SOUTH, this.shouldConnect(blockView, pos, Direction.SOUTH))
                .with(CONNECTION_EAST, this.shouldConnect(blockView, pos, Direction.EAST))
                .with(CONNECTION_WEST, this.shouldConnect(blockView, pos, Direction.WEST));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState,
            IWorld world, BlockPos pos, BlockPos neighborPos) {
        if (facing == Direction.DOWN || facing == Direction.UP) {
            return state;
        } else {
            return this.getDefaultState()
                    .with(CONNECTION_NORTH, this.shouldConnect(world, pos, Direction.NORTH))
                    .with(CONNECTION_SOUTH, this.shouldConnect(world, pos, Direction.SOUTH))
                    .with(CONNECTION_EAST, this.shouldConnect(world, pos, Direction.EAST))
                    .with(CONNECTION_WEST, this.shouldConnect(world, pos, Direction.WEST));

        }
    }

    private Boolean shouldConnect(BlockView view, BlockPos pos, Direction dir) {
        Block a = view.getBlockState(pos).getBlock();
        Block b = view.getBlockState(pos.offset(dir)).getBlock();
        return a == b && b instanceof RunwayMarkingBlock;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, Direction.UP);
    }

    private BlockState update(World world, BlockPos pos, BlockState state) {
        List<BlockPos> list = Lists.newArrayList(this.affectedNeighbors);
        this.affectedNeighbors.clear();
        Iterator<BlockPos> var5 = list.iterator();

        while (var5.hasNext()) {
            BlockPos blockPos = (BlockPos) var5.next();
            world.updateNeighborsAlways(blockPos, this);
        }

        return state;
    }

    private void updateNeighbors(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() == this) {
            world.updateNeighborsAlways(pos, this);
            Direction[] var3 = Direction.values();
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Direction direction = var3[var5];
                world.updateNeighborsAlways(pos.offset(direction), this);
            }

        }
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moved) {
        if (oldState.getBlock() != state.getBlock() && !world.isClient) {
            this.update(world, pos, state);
            Iterator<Direction> var6 = Direction.Type.VERTICAL.iterator();

            Direction direction3;
            while (var6.hasNext()) {
                direction3 = (Direction) var6.next();
                world.updateNeighborsAlways(pos.offset(direction3), this);
            }

            var6 = Direction.Type.HORIZONTAL.iterator();

            while (var6.hasNext()) {
                direction3 = (Direction) var6.next();
                this.updateNeighbors(world, pos.offset(direction3));
            }

            var6 = Direction.Type.HORIZONTAL.iterator();

            while (var6.hasNext()) {
                direction3 = (Direction) var6.next();
                BlockPos blockPos = pos.offset(direction3);
                if (world.getBlockState(blockPos).isFullCube(world, blockPos)) {
                    this.updateNeighbors(world, blockPos.up());
                } else {
                    this.updateNeighbors(world, blockPos.down());
                }
            }

        }
    }

    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved && state.getBlock() != newState.getBlock()) {
            super.onBlockRemoved(state, world, pos, newState, moved);
            if (!world.isClient) {
                Direction[] var6 = Direction.values();
                int var7 = var6.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    Direction direction = var6[var8];
                    world.updateNeighborsAlways(pos.offset(direction), this);
                }

                this.update(world, pos, state);
                Iterator<Direction> var10 = Direction.Type.HORIZONTAL.iterator();

                Direction direction3;
                while (var10.hasNext()) {
                    direction3 = (Direction) var10.next();
                    this.updateNeighbors(world, pos.offset(direction3));
                }

                var10 = Direction.Type.HORIZONTAL.iterator();

                while (var10.hasNext()) {
                    direction3 = (Direction) var10.next();
                    BlockPos blockPos = pos.offset(direction3);
                    if (world.getBlockState(blockPos).isFullCube(world, blockPos)) {
                        this.updateNeighbors(world, blockPos.up());
                    } else {
                        this.updateNeighbors(world, blockPos.down());
                    }
                }

            }
        }
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos,
            boolean moved) {
        if (!world.isClient) {
            if (state.canPlaceAt(world, pos)) {
                this.update(world, pos, state);
            } else {
                dropStacks(state, world, pos);
                world.removeBlock(pos, false);
            }

        }
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
        case CLOCKWISE_180:
            return (BlockState) ((BlockState) ((BlockState) ((BlockState) state.with(CONNECTION_NORTH,
                    state.get(CONNECTION_SOUTH))).with(CONNECTION_EAST, state.get(CONNECTION_WEST)))
                            .with(CONNECTION_SOUTH, state.get(CONNECTION_NORTH))).with(CONNECTION_WEST,
                                    state.get(CONNECTION_EAST));
        case COUNTERCLOCKWISE_90:
            return (BlockState) ((BlockState) ((BlockState) ((BlockState) state.with(CONNECTION_NORTH,
                    state.get(CONNECTION_EAST))).with(CONNECTION_EAST, state.get(CONNECTION_SOUTH)))
                            .with(CONNECTION_SOUTH, state.get(CONNECTION_WEST))).with(CONNECTION_WEST,
                                    state.get(CONNECTION_NORTH));
        case CLOCKWISE_90:
            return (BlockState) ((BlockState) ((BlockState) ((BlockState) state.with(CONNECTION_NORTH,
                    state.get(CONNECTION_WEST))).with(CONNECTION_EAST, state.get(CONNECTION_NORTH)))
                            .with(CONNECTION_SOUTH, state.get(CONNECTION_EAST))).with(CONNECTION_WEST,
                                    state.get(CONNECTION_SOUTH));
        default:
            return state;
        }
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        switch (mirror) {
        case LEFT_RIGHT:
            return (BlockState) ((BlockState) state.with(CONNECTION_NORTH, state.get(CONNECTION_SOUTH)))
                    .with(CONNECTION_SOUTH, state.get(CONNECTION_NORTH));
        case FRONT_BACK:
            return (BlockState) ((BlockState) state.with(CONNECTION_EAST, state.get(CONNECTION_WEST)))
                    .with(CONNECTION_WEST, state.get(CONNECTION_EAST));
        default:
            return super.mirror(state, mirror);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CONNECTION_NORTH, CONNECTION_EAST, CONNECTION_SOUTH, CONNECTION_WEST);
    }
}