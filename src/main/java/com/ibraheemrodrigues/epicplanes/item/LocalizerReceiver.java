package com.ibraheemrodrigues.epicplanes.item;

import com.ibraheemrodrigues.epicplanes.block.PlaneBlocks;

import com.ibraheemrodrigues.epicplanes.block.entity.LocalizerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LocalizerReceiver extends Item {

    public static final String POS_NBT_KEY = "target_block";
    public static final String SHOULD_DISPLAY_NBT_KEY = "should_display";


    public static final String SET_LANG_KEY = "item.epicplanes.localizer_receiver.set";
    public static final String SET_INVALID_LANG_KEY = "item.epicplanes.localizer_receiver.set.invalid";

    public static final String GET_LANG_KEY = "item.epicplanes.localizer_receiver.get";
    public static final String GET_CANTFIND_LANG_KEY = "item.epicplanes.localizer_receiver.get.cantfind";
    public static final String GET_UNSET_LANG_KEY = "item.epicplanes.localizer_receiver.get.unset";

    public LocalizerReceiver(Settings settings) {
        super(settings);
    }

    static private float blockPosToFreq(BlockPos blockPos) {
        return Math.abs(((float) blockPos.asLong() % 100000) / 100);
    }

    public static boolean shouldDisplay(World world, ItemStack stack) {

        CompoundTag nbt = stack.getTag();
        if (nbt == null) {
            nbt = new CompoundTag();
        }

        if (world.isClient) {
            if (nbt.contains(SHOULD_DISPLAY_NBT_KEY)) {
                return nbt.getBoolean(SHOULD_DISPLAY_NBT_KEY);
            } else {
                return false;
            }
        } else {
            if (nbt.contains(POS_NBT_KEY)) {
                BlockPos pos = BlockPos.fromLong(nbt.getLong(POS_NBT_KEY));



                return world.getBlockState(pos).getBlock() == PlaneBlocks.LOCALIZER_BLOCK && world.getBlockState(pos.up()).isAir();
            } else {
                return false;
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            CompoundTag itemNBT = stack.getTag();
            if (itemNBT == null) {
                itemNBT = new CompoundTag();
            }

            boolean canDisplay = shouldDisplay(world, stack);

            itemNBT.putBoolean(SHOULD_DISPLAY_NBT_KEY, canDisplay);

            if (canDisplay && itemNBT.contains(POS_NBT_KEY)) {
                BlockPos pos = BlockPos.fromLong(itemNBT.getLong(POS_NBT_KEY));

                LocalizerBlockEntity localizerEntity = (LocalizerBlockEntity)world.getBlockEntity(pos);

                itemNBT.putString(LocalizerBlockEntity.HEADING_KEY, localizerEntity.getHeading().asString());
                itemNBT.putInt(LocalizerBlockEntity.PITCH_KEY, localizerEntity.getPitch());
            }

            stack.setTag(itemNBT);
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        ItemStack itemStack = context.getStack();
        CompoundTag nbt = itemStack.getTag();

        PlayerEntity user = context.getPlayer();

        if (nbt == null) {
            nbt = new CompoundTag();
        }

        if ((blockState.getBlock() == PlaneBlocks.LOCALIZER_BLOCK) && (blockState != null)) {

            nbt.putLong(POS_NBT_KEY, blockPos.asLong());

            if (world.isClient) {
                user.sendMessage(new TranslatableText(SET_LANG_KEY, blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPosToFreq(blockPos)));
            }

            itemStack.setTag(nbt);

            return ActionResult.SUCCESS;
        } else {
            if (world.isClient) {
                user.sendMessage(new TranslatableText(SET_INVALID_LANG_KEY));
            }

            return ActionResult.FAIL;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);
        CompoundTag nbt = itemStack.getTag();

        if (nbt == null) {
            nbt = new CompoundTag();
        }

        if (nbt.contains(POS_NBT_KEY)) {
            BlockPos blockPos = BlockPos.fromLong(nbt.getLong(POS_NBT_KEY));

            if (shouldDisplay(world, itemStack)) {
                if (world.isClient) {
                    user.sendMessage(new TranslatableText(GET_LANG_KEY, blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPosToFreq(blockPos)));
                }
            } else {
                if (world.isClient) {
                    user.sendMessage(new TranslatableText(GET_CANTFIND_LANG_KEY, blockPosToFreq(blockPos)));
                }
            }


        } else {
            if (world.isClient) {
                user.sendMessage(new TranslatableText(GET_UNSET_LANG_KEY));
            }
        }

        return TypedActionResult.success(itemStack);
    }
}