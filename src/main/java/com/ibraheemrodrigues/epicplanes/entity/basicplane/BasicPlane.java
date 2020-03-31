package com.ibraheemrodrigues.epicplanes.entity.basicplane;

import com.ibraheemrodrigues.epicplanes.network.PlanePackets;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class BasicPlane extends BoatEntity {

    // Sync vars
    private int stepsToSync;
    private double syncX;
    private double syncY;
    private double syncZ;
    private double syncYaw;
    private double syncPitch;

    //
    // Physics vars
    //
    protected final double mass = 1;

    // Engine Power
    protected final String ENGINE_POWER_NBT_KEY = "EnginePower";
//    protected float enginePower = 0;

    protected float enginePower = 0;
    protected float setEnginePower = 0;

    protected final float maxEnginePower = 0.3f;
    protected final float enginePowerStep = maxEnginePower/100;

    protected final double gravity = -0.1;

    protected final double decayCoef = 0.1;

    public BasicPlane(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    //
    // Data tracking & NBT
    //


    @Override
    public Packet<?> createSpawnPacket() {
        return PlanePackets.newSpawnPacket(this);
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putFloat(ENGINE_POWER_NBT_KEY, this.enginePower);
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains(ENGINE_POWER_NBT_KEY)) {
            this.enginePower = tag.getFloat(ENGINE_POWER_NBT_KEY);
        }
    }


    //
    // Tick & Physics
    //

    @Override
    public void tick() {

        if (this.getDamageWobbleTicks() > 0) {
            this.setDamageWobbleTicks(this.getDamageWobbleTicks() - 1);
        }

        if (this.getDamageWobbleStrength() > 0.0F) {
            this.setDamageWobbleStrength(this.getDamageWobbleStrength() - 1.0F);
        }

        this.fakeBaseTick();
        this.interpolationSyncUpdate();
        if (this.isLogicalSideForUpdatingMovement()) {
            this.doPhysics();
        } else {
            // Prevent updating velocity if not logical update side - only copy from position packets
            this.setVelocity(Vec3d.ZERO);
        }

        this.checkBlockCollision();
        this.handleEntityCollision();

        sendEngineDataPacket();
    }

    private void fakeBaseTick() {
        // Can't directly call Entity.tick()
        if (!this.world.isClient) {
            this.setFlag(6, this.isGlowing());
        }

        this.baseTick();
    }

    private void interpolationSyncUpdate() {
        if (this.isLogicalSideForUpdatingMovement()) {
            this.stepsToSync = 0;
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
        }

        if (this.stepsToSync > 0) {
            double x = this.getX() + (this.syncX - this.getX()) / (double)this.stepsToSync;
            double y = this.getY() + (this.syncY - this.getY()) / (double)this.stepsToSync;
            double z = this.getZ() + (this.syncZ - this.getZ()) / (double)this.stepsToSync;
            double yaw = MathHelper.wrapDegrees(this.syncYaw - (double)this.yaw);
            this.yaw = (float)((double)this.yaw + yaw / (double)this.stepsToSync);
            this.pitch = (float)((double)this.pitch + (this.syncPitch - (double)this.pitch) / (double)this.stepsToSync);
            --this.stepsToSync;
            this.setPosition(x, y, z);
            this.setRotation(this.yaw, this.pitch);
        }
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.syncX = x;
        this.syncY = y;
        this.syncZ = z;
        this.syncYaw = yaw;
        this.syncPitch = pitch;
        this.stepsToSync = 10;
    }

    private void doPhysics() {
        final double tickDelta = 0.05;

        // TODO: Proper physics

        //
        // Force
        //
        this.enginePower  += (float) ((this.setEnginePower - this.enginePower) * decayCoef);

        // engine
        Vec3d netForce = Vec3d.fromPolar(this.pitch, this.yaw).multiply(this.enginePower)
        // gravity
        .add(0, this.gravity * (1 - this.enginePower/this.maxEnginePower), 0)
        // upthrust = -g *2 * sin(pitch+PI/6) * engineProportion
//        .add(0, -this.gravity * 2 * Math.sin(2 * this.pitch + Math.PI/6) * this.enginePower/this.maxEnginePower, 0)
        // Air resistance
        .subtract(this.getVelocity().multiply(0.1))

        // Ground Resistance
        .subtract(this.getVelocity().multiply(1, 0, 1).multiply(0.5))
                ;


        // a = F/m
        Vec3d acceleration = netForce.multiply(1d/this.mass);

        // v = u + at
        Vec3d velocity = this.getVelocity().add(acceleration.multiply(tickDelta));

        this.setVelocity(velocity);

        // Move
        this.move(MovementType.SELF, this.getVelocity());

        // Rotational
        if (this.hasPassengers()) {
            Entity pilot = this.getPrimaryPassenger();

            this.yaw += (pilot.yaw / 2 - this.yaw) * decayCoef * this.enginePower / this.maxEnginePower;
            this.pitch+= (pilot.pitch / 2 - this.pitch) * decayCoef * this.enginePower / this.maxEnginePower;

            this.yaw = MathHelper.wrapDegrees(this.yaw);
            this.pitch = MathHelper.clamp(this.pitch, -90, 90);
        }
    }

    @Override
    protected void copyEntityData(Entity entity) {
        // Override to do nothing
    }

    public void setEnginePower(float power) {
        this.enginePower = power;
    }
    public float getEnginePower() { return this.enginePower; }

    private void handleEntityCollision() {
        List<Entity> list = this.world.getEntities(this, this.getBoundingBox().expand(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntityPredicates.canBePushedBy(this));
        if (!list.isEmpty()) {
            boolean bl = !this.world.isClient && !(this.getPrimaryPassenger() instanceof PlayerEntity);

            for (Entity entity : list) {
                if (!entity.hasPassenger(this)) {
                    if (bl && this.getPassengerList().size() < 2 && !entity.hasVehicle() && entity.getWidth() < this.getWidth() && entity instanceof LivingEntity && !(entity instanceof WaterCreatureEntity) && !(entity instanceof PlayerEntity)) {
                        entity.startRiding(this);
                    } else {
                        this.pushAwayFrom(entity);
                    }
                }
            }
        }
    }

    @Override
    public void setInputs(boolean pressingLeft, boolean pressingRight, boolean pressingForward, boolean pressingBack) {

        if (pressingForward) {
            this.setEnginePower += this.enginePowerStep;
        }
        if (pressingBack) {
            this.setEnginePower -= this.enginePowerStep;
        }

        this.setEnginePower = MathHelper.clamp(this.setEnginePower, 0, this.maxEnginePower);
    }

    private void sendEngineDataPacket() {
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());

        passedData.writeInt(this.getEntityId());
        passedData.writeFloat(this.enginePower);

        ClientSidePacketRegistry.INSTANCE.sendToServer(PlanePackets.C2S_PLANE_DATA_PACKET, passedData);
    }
}
