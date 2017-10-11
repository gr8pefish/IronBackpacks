package gr8pefish.ironbackpacks.capabilities.player;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import java.util.concurrent.Callable;

public class PlayerWearingBackpackCapabilities implements ICapabilitySerializable<NBTTagCompound> {

    public static final String CAP_PACK_TAG = Constants.MODID;

    private ItemStack equippedBackpack = ItemStack.EMPTY;
    private ItemStack currentBackpack = ItemStack.EMPTY;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY != null && capability == IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY != null && capability == IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY ? IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY.cast(this) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {

        //make new list
        NBTTagList tagList = new NBTTagList();

        //make new compound for the equipped pack
        NBTTagCompound equipped = new NBTTagCompound();
        ItemStack equippedBackpack = getEquippedBackpack();
        if (equippedBackpack != null) {
            equippedBackpack.writeToNBT(equipped);
        }else{
            equipped.setBoolean("noEquipped", false);
        }
        tagList.appendTag(equipped);

        //make another for the saved one
        NBTTagCompound current = new NBTTagCompound();
        ItemStack currentBackpack = getCurrentBackpack();
        if (!currentBackpack.isEmpty()) {
            currentBackpack.writeToNBT(current);
        }else{
            current.setBoolean("noCurrent", false);
        }
        tagList.appendTag(current);

        //save all to the tag
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag(CAP_PACK_TAG, tagList);

        //return compound
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {

        NBTTagList tagList = compound.getTagList(CAP_PACK_TAG, net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);

        //get the equipped backpack without crashing
        if (!tagList.getCompoundTagAt(0).hasKey("noEquipped")){ //if the key doesn't exist
            try {
                setEquippedBackpack(new ItemStack(tagList.getCompoundTagAt(0)));
            } catch (NullPointerException e) { //might as well keep this catch statement
                setEquippedBackpack(ItemStack.EMPTY);
            }
        } else {
            setEquippedBackpack(ItemStack.EMPTY);
        }

        //get the current backpack without crashing
        if (!tagList.getCompoundTagAt(1).hasKey("noCurrent")) {
            try {
                setCurrentBackpack(new ItemStack(tagList.getCompoundTagAt(1)));
            } catch (NullPointerException e) {
                setCurrentBackpack(ItemStack.EMPTY);
            }
        } else {
            setCurrentBackpack(ItemStack.EMPTY);
        }

    }

    // Not sure what this does honestly
    public static class Storage implements Capability.IStorage<PlayerWearingBackpackCapabilities> {

        @Override
        public NBTBase writeNBT(Capability<PlayerWearingBackpackCapabilities> capability, PlayerWearingBackpackCapabilities instance, EnumFacing side) {
           return null; //unused?
        }

        @Override
        public void readNBT(Capability<PlayerWearingBackpackCapabilities> capability, PlayerWearingBackpackCapabilities instance, EnumFacing side, NBTBase nbt) {
            //empty
        }

    }

    // Empty factory, just implemented here for ease of future expansion
    public static class Factory implements Callable<PlayerWearingBackpackCapabilities> {
        @Override
        public PlayerWearingBackpackCapabilities call() throws Exception {
            return null;
        }
    }


    //Getters and setters

    public ItemStack getEquippedBackpack() {
        return equippedBackpack;
    }

    public void setEquippedBackpack(ItemStack stack) {
        this.equippedBackpack = stack;
    }

    public ItemStack getCurrentBackpack() {
        return currentBackpack;
    }

    public void setCurrentBackpack(ItemStack stack) {
        this.currentBackpack = stack;
    }

    //Other helper methods

    public static void register() {
        CapabilityManager.INSTANCE.register(PlayerWearingBackpackCapabilities.class, new PlayerWearingBackpackCapabilities.Storage(), new PlayerWearingBackpackCapabilities.Factory());
    }

    //Static methods

    public static ItemStack getEquippedBackpack(EntityLivingBase livingBase) {
        PlayerWearingBackpackCapabilities cap = IronBackpacksCapabilities.getWearingBackpackCapability((EntityPlayer)livingBase);
        if (cap != null) //can this ever be null?
            return cap.getEquippedBackpack();
        else
            return ItemStack.EMPTY;
    }

    public static void setEquippedBackpack(EntityLivingBase livingBase, ItemStack stack) {
        PlayerWearingBackpackCapabilities cap = IronBackpacksCapabilities.getWearingBackpackCapability((EntityPlayer)livingBase);
        if (cap != null)
            cap.setEquippedBackpack(stack);
    }

    public static ItemStack getCurrentBackpack(EntityLivingBase livingBase) {
        PlayerWearingBackpackCapabilities cap = IronBackpacksCapabilities.getWearingBackpackCapability((EntityPlayer)livingBase);
        if (cap != null)
            return cap.getCurrentBackpack();
        else
            return ItemStack.EMPTY;
    }

    public static void setCurrentBackpack(EntityLivingBase livingBase, ItemStack stack) {
        PlayerWearingBackpackCapabilities cap = IronBackpacksCapabilities.getWearingBackpackCapability((EntityPlayer)livingBase);
        if (cap != null)
            cap.setCurrentBackpack(stack);
    }

    public static void reset(EntityLivingBase livingBase) {
        setCurrentBackpack(livingBase, ItemStack.EMPTY);
        setEquippedBackpack(livingBase, ItemStack.EMPTY);
    }
}
