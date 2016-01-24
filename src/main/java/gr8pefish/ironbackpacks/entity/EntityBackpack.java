package gr8pefish.ironbackpacks.entity;

import com.google.common.collect.MapMaker;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class EntityBackpack extends Entity implements IEntityAdditionalSpawnData {

    private EntityPlayer player;

    //all the backpacks in one map
    public static Map<EntityPlayer, EntityBackpack> backpacksSpawnedMap = new MapMaker().weakKeys().weakValues().makeMap();

    public EntityBackpack(World world){
        super(world);
    }

    public EntityBackpack(World world, EntityPlayer player){
        super(world);
        this.player = player;
    }

    @Override
    protected void entityInit() {
        //nothing extra needed here
    }

    /**
     * Kills the backpack if it needs it.
     */
    @Override
    public void setDead() {
        super.setDead();
        backpacksSpawnedMap.remove(player);
    }

    /**
     * Check if the backpack should exist
     * @param player - the player with the backpack
     * @param backpack - the backpack
     * @return - boolean shouldExist
     */
    private static boolean isBackpackValid(EntityPlayer player, EntityBackpack backpack) {
        if (player == null || player.isDead || backpack == null || backpack.isDead) return false;
        if (player.worldObj.provider.getDimensionId() != backpack.worldObj.provider.getDimensionId()) return false;
        return true;
    }

    /**
     * Update the backpacks visually
     * @param worldObj - the world to update them in
     */
    @SideOnly(Side.CLIENT)
    public static void updateBackpacks(World worldObj) {
        for (Map.Entry<EntityPlayer, EntityBackpack> map : backpacksSpawnedMap.entrySet()) {
            EntityPlayer player = map.getKey();
            EntityBackpack backpack = map.getValue();
            if (isBackpackValid(player, backpack)) backpack.fixPositions(player, player instanceof EntityPlayerSP);
            else backpack.setDead();
        }
    }

    //make sure it is updated to the right position

    /**
     * Helper method to alter the backpacks' positions
     * @param thePlayer - the player with the pack
     * @param localPlayer - the local player
     */
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

    }

    @Override
    public void writeSpawnData(ByteBuf data) {
        if (player == null) {
            data.writeInt(-42);
        } else {
            data.writeInt(player.getEntityId());
        }
    }

    //NBT unused because it is just an entity linked to a player and the items are stored in my IB custom NBT
    @Override
    protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {

    }
}
