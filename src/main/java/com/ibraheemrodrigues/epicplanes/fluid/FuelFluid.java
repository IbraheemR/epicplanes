// package com.ibraheemrodrigues.epicplanes.fluid;

// import java.util.Random;
// import net.fabricmc.api.EnvType;
// import net.fabricmc.api.Environment;
// import net.minecraft.block.Block;
// import net.minecraft.block.BlockState;
// import net.minecraft.block.Blocks;
// import net.minecraft.block.FluidBlock;
// import net.minecraft.block.entity.BlockEntity;
// import net.minecraft.fluid.Fluid;
// import net.minecraft.fluid.FluidState;
// import net.minecraft.fluid.Fluids;
// import net.minecraft.item.Item;
// import net.minecraft.item.Items;
// import net.minecraft.particle.ParticleEffect;
// import net.minecraft.particle.ParticleTypes;
// import net.minecraft.sound.SoundCategory;
// import net.minecraft.sound.SoundEvents;
// import net.minecraft.state.StateManager;
// import net.minecraft.tag.FluidTags;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.util.math.Direction;
// import net.minecraft.world.BlockView;
// import net.minecraft.world.IWorld;
// import net.minecraft.world.World;
// import net.minecraft.world.WorldView;

// public abstract class FuelFluid extends BaseFluid {
// public Fluid getFlowing() {
// return Fluids.FLOWING_WATER;
// }

// public Fluid getStill() {
// return Fluids.WATER;
// }

// public Item getBucketItem() {
// return Items.WATER_BUCKET;
// }

// @Environment(EnvType.CLIENT)
// public void randomDisplayTick(World world_1, BlockPos blockPos_1, FluidState
// fluidState_1, Random random_1) {
// if (!fluidState_1.isStill() && !(Boolean) fluidState_1.get(FALLING)) {
// if (random_1.nextInt(64) == 0) {
// world_1.playSound((double) blockPos_1.getX() + 0.5D, (double)
// blockPos_1.getY() + 0.5D,
// (double) blockPos_1.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT,
// SoundCategory.BLOCKS,
// random_1.nextFloat() * 0.25F + 0.75F, random_1.nextFloat() + 0.5F, false);
// }
// } else if (random_1.nextInt(10) == 0) {
// world_1.addParticle(ParticleTypes.UNDERWATER, (double) ((float)
// blockPos_1.getX() + random_1.nextFloat()),
// (double) ((float) blockPos_1.getY() + random_1.nextFloat()),
// (double) ((float) blockPos_1.getZ() + random_1.nextFloat()), 0.0D, 0.0D,
// 0.0D);
// }

// }

// @Environment(EnvType.CLIENT)
// public ParticleEffect getParticle() {
// return ParticleTypes.DRIPPING_WATER;
// }

// protected boolean isInfinite() {
// return true;
// }

// protected void beforeBreakingBlock(IWorld iWorld_1, BlockPos blockPos_1,
// BlockState blockState_1) {
// BlockEntity blockEntity_1 = blockState_1.getBlock().hasBlockEntity() ?
// iWorld_1.getBlockEntity(blockPos_1)
// : null;
// Block.dropStacks(blockState_1, iWorld_1.getWorld(), blockPos_1,
// blockEntity_1);
// }

// public int method_15733(WorldView worldView_1) {
// return 4;
// }

// public BlockState toBlockState(FluidState fluidState_1) {
// return (BlockState) Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL,
// method_15741(fluidState_1));
// }

// public boolean matchesType(Fluid fluid_1) {
// return fluid_1 == Fluids.WATER || fluid_1 == Fluids.FLOWING_WATER;
// }

// public int getLevelDecreasePerBlock(WorldView worldView_1) {
// return 1;
// }

// public int getTickRate(WorldView worldView_1) {
// return 5;
// }

// public boolean method_15777(FluidState fluidState_1, BlockView blockView_1,
// BlockPos blockPos_1, Fluid fluid_1,
// Direction direction_1) {
// return direction_1 == Direction.DOWN && !fluid_1.matches(FluidTags.WATER);
// }

// protected float getBlastResistance() {
// return 100.0F;
// }

// public static class Flowing extends FuelFluid {
// protected void appendProperties(StateManager.Builder<Fluid, FluidState>
// stateManager$Builder_1) {
// super.appendProperties(stateManager$Builder_1);
// stateManager$Builder_1.add(LEVEL);
// }

// public int getLevel(FluidState fluidState_1) {
// return (Integer) fluidState_1.get(LEVEL);
// }

// public boolean isStill(FluidState fluidState_1) {
// return false;
// }
// }

// public static class Still extends FuelFluid {
// public int getLevel(FluidState fluidState_1) {
// return 8;
// }

// public boolean isStill(FluidState fluidState_1) {
// return true;
// }
// }
// }