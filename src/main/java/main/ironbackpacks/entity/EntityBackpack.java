package main.ironbackpacks.entity;

import com.google.common.collect.MapMaker;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Map;

public class EntityBackpack extends Entity implements IEntityAdditionalSpawnData {

    private EntityPlayer player;
    private int backpackType;

    public static Map<EntityPlayer, EntityBackpack> backpacksSpawnedMap = new MapMaker().weakKeys().weakValues().makeMap();

    public EntityBackpack(World world){
        super(world);
    }

    public EntityBackpack(World world, EntityPlayer player, int backpackType){
        super(world);
        this.player = player;
        this.backpackType = backpackType;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void setDead() {
        super.setDead();
        backpacksSpawnedMap.remove(player);
    }

    public int getTextureId(){
        return backpackType;
    }


    private static boolean isBackpackValid(EntityPlayer player, EntityBackpack backpack) {
        if (player == null || player.isDead || backpack == null || backpack.isDead) return false;
        if (player.worldObj.provider.dimensionId != backpack.worldObj.provider.dimensionId) return false;
        return true;
    }

    @SideOnly(Side.CLIENT)
    public static void updateBackpacks(World worldObj) {
        for (Map.Entry<EntityPlayer, EntityBackpack> map : backpacksSpawnedMap.entrySet()) {
            EntityPlayer player = map.getKey();
            EntityBackpack backpack = map.getValue();
            if (isBackpackValid(player, backpack)) backpack.fixPositions(player, player instanceof EntityPlayerSP);
            else backpack.setDead();
        }
    }

    private void fixPositions(EntityPlayer thePlayer, boolean localPlayer) {
        this.lastTickPosX = prevPosX = player.prevPosX;
        this.lastTickPosY = prevPosY = player.prevPosY;
        this.lastTickPosZ = prevPosZ = player.prevPosZ;

        this.posX = player.posX;
        this.posY = player.posY;
        this.posZ = player.posZ;

        setPosition(posX, posY, posZ);
        this.prevRotationYaw = player.prevRenderYawOffset;
        this.rotationYaw = player.renderYawOffset;

        this.prevRotationPitch = player.prevRotationPitch;
        this.rotationPitch = player.rotationPitch;

//        if (!localPlayer) {
//            this.posY += 1.2;
//            this.prevPosY += 1.2;
//            this.lastTickPosY += 1.2;
//        }
    }

    public EntityPlayer getPlayer(){
        return player;
    }

    //deals with login/logout killing/spawning entity
    @Override
    public void readSpawnData(ByteBuf data) {
        int playerId = data.readInt();

        Entity e = worldObj.getEntityByID(playerId);

        if (e instanceof EntityPlayer) {
            player = (EntityPlayer)e;
            backpacksSpawnedMap.put(player, this);
        } else {
            setDead();
        }

        this.backpackType = data.readInt();
    }

    @Override
    public void writeSpawnData(ByteBuf data) {
        if (player == null) {
//            Logger.warn("Got backpack without player id for entityBackpack");
            data.writeInt(-42);
        } else {
            data.writeInt(player.getEntityId());
        }
        data.writeInt(this.backpackType);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }
}
