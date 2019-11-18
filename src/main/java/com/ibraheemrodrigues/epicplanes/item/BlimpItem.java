package com.ibraheemrodrigues.epicplanes.item;

import java.util.List;
import java.util.function.Predicate;

import com.google.common.base.Preconditions;
import com.ibraheemrodrigues.epicplanes.entity.blimp.BlimpEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class BlimpItem extends Item {

    private static final Predicate<Entity> COLLISION_CHECKER;

    static {
        COLLISION_CHECKER = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides);
    }

    public BlimpItem(Settings item$Settings_1) {
        super(item$Settings_1);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        HitResult hitResult = rayTrace(world, player, RayTraceContext.FluidHandling.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(stack);
        } else {
            Vec3d facing = player.getRotationVec(1.0F);
            List<Entity> collsionEntities = world.getEntities(player,
                    player.getBoundingBox().stretch(facing.multiply(5.0D)).expand(1.0D), COLLISION_CHECKER);

            if (!collsionEntities.isEmpty()) {
                Vec3d cameraPos = player.getCameraPosVec(1.0F);

                for (Entity entity : collsionEntities) {
                    Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
                    if (box.contains(cameraPos)) {
                        return TypedActionResult.pass(stack);
                    }
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlimpEntity blimpEntity = Preconditions.checkNotNull(BlimpEntity.BLIMP.create(world));

                Vec3d pos = hitResult.getPos();
                blimpEntity.setPosition(pos.x, pos.y, pos.z);
                blimpEntity.yaw = player.yaw;

                if (!world.doesNotCollide(blimpEntity, blimpEntity.getBoundingBox().expand(-0.1D))) {
                    return TypedActionResult.fail(stack);
                } else {
                    if (!world.isClient) {
                        world.spawnEntity(blimpEntity);
                        if (!player.abilities.creativeMode) {
                            stack.decrement(1);
                        }
                    }
                    // TODO: STATS
                    return TypedActionResult.success(stack);
                }
            } else {
                return TypedActionResult.pass(stack);
            }
        }
    }
}