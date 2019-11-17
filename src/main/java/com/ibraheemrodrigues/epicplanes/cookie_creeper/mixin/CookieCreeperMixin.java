package com.ibraheemrodrigues.epicplanes.cookie_creeper.mixin;

import java.util.List;

import com.ibraheemrodrigues.epicplanes.cookie_creeper.CookieCreeperEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

@Mixin(CreeperEntity.class)
public class CookieCreeperMixin extends HostileEntity {

    public CookieCreeperMixin(EntityType<? extends CreeperEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    // Injects into CreeperEntity tp summon cookies when CookieCreeper explodes,
    // easiest way to hook private explode() method
    @Inject(at = @At("HEAD"), method = "explode")
    private void explode(CallbackInfo info) {
        if (!this.world.isClient && this.getType() == CookieCreeperEntity.COOKIE_CREEPER
                && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {

            int amount = (int) Math.floor(Math.random() * 2);

            for (int i = 0; i < amount; i++) {
                Vec3d pos = getPos();
                double offsetX = (double) (this.world.random.nextFloat() * 0.5F) + 0.25D;
                double offsetY = (double) (this.world.random.nextFloat() * 0.5F) + 0.25D;
                double offsetZ = (double) (this.world.random.nextFloat() * 0.5F) + 0.25D;

                ItemStack cookiesItem = new ItemStack(Items.COOKIE);
                ItemEntity cookiesItemEntity = new ItemEntity(this.world, pos.x + offsetX, pos.y + offsetY,
                        pos.z + offsetZ, cookiesItem);

                cookiesItemEntity.setToDefaultPickupDelay();
                cookiesItemEntity.setInvulnerable(true);
                this.world.spawnEntity(cookiesItemEntity);
            }
        }
    }

    // Checks if CreeperEntity is near a cookie and converts it if it is.
    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        if (!this.world.isClient && this.getType() == EntityType.CREEPER && this.isAlive()) {
            List<Entity> nearby = this.world.getEntities(this, this.getBoundingBox());

            for (Entity e : nearby) {
                if (e.getType() == EntityType.ITEM) {
                    ItemStack stack = ((ItemEntity) e).getStack();

                    if (stack.getItem() == Items.COOKIE) {
                        stack.setCount(stack.getCount() - 1);

                        CookieCreeperEntity cookieCreeperEntity = (CookieCreeperEntity) CookieCreeperEntity.COOKIE_CREEPER
                                .create(this.world);

                        Vec3d pos = getPos();

                        cookieCreeperEntity.setPositionAndAngles(pos.x, pos.y, pos.z, this.yaw, this.pitch);
                        cookieCreeperEntity.setAiDisabled(this.isAiDisabled());
                        if (this.hasCustomName()) {
                            cookieCreeperEntity.setCustomName(this.getCustomName());
                            cookieCreeperEntity.setCustomNameVisible(this.isCustomNameVisible());
                        }

                        Explosion.DestructionType destruction = this.world.getGameRules()
                                .getBoolean(GameRules.MOB_GRIEFING) ? Explosion.DestructionType.DESTROY
                                        : Explosion.DestructionType.NONE;

                        this.world.createExplosion(this, pos.x, pos.y, pos.z, 3.0f, destruction);

                        this.world.spawnEntity(cookieCreeperEntity);

                        this.dead = true;
                        this.remove();
                    }
                }
            }
        }
    }
}
