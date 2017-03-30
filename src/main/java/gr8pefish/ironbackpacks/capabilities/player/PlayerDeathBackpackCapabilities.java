package gr8pefish.ironbackpacks.capabilities.player;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class PlayerDeathBackpackCapabilities implements ICapabilitySerializable<NBTTagCompound> {

    public static final String CAP_DEATH_PACK_TAG = IronBackpacks.MODID + ".death";

    private NonNullList<ItemStack> eternityPacks;
    private ItemStack equippedBackpack;

    public PlayerDeathBackpackCapabilities() {
        this.eternityPacks = NonNullList.create();
        this.equippedBackpack = ItemStack.EMPTY;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY != null && capability == IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY != null && capability == IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY ? (T) this : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {

        //make new list
        NBTTagList tagList = new NBTTagList();

        //save the equipped pack
        NBTTagCompound equipped = new NBTTagCompound();
        if (equippedBackpack != null)
            equippedBackpack.writeToNBT(equipped);
        else
            equipped.setBoolean("noEquipped", false);
        tagList.appendTag(equipped);

        //save all the eternity packs
        if (eternityPacks != null && eternityPacks.size() > 0) {
            for (ItemStack pack : eternityPacks) {
                NBTTagCompound saved = new NBTTagCompound();
                pack.writeToNBT(saved);
                tagList.appendTag(saved);
            }
        }

        //save all to the tag
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag(CAP_DEATH_PACK_TAG, tagList);

        //return compound
        return compound;

    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {

        NBTTagList tagList = compound.getTagList(CAP_DEATH_PACK_TAG, net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);

        //get the equipped pack without crashing
        if (!tagList.getCompoundTagAt(0).hasKey("noEquipped")) { //if the key doesn't exist
            try {
                equippedBackpack = new ItemStack(tagList.getCompoundTagAt(0));
            } catch (NullPointerException e) { //might as well keep this catch statement
                equippedBackpack = null;
            }
        } else {
            equippedBackpack = null;
        }

        //get all the eternity packs
        if (tagList.tagCount() >= 1) {
            for (int i = 1; i < tagList.tagCount(); i++) {
                if (!tagList.getCompoundTagAt(i).hasNoTags())
                    eternityPacks.add(new ItemStack(tagList.getCompoundTagAt(i)));
            }
        }

    }

    // Not sure what this does honestly
    public static class Storage implements Capability.IStorage<PlayerDeathBackpackCapabilities> {

        @Override
        public NBTBase writeNBT(Capability<PlayerDeathBackpackCapabilities> capability, PlayerDeathBackpackCapabilities instance, EnumFacing side) {
            return null; //unused?
        }

        @Override
        public void readNBT(Capability<PlayerDeathBackpackCapabilities> capability, PlayerDeathBackpackCapabilities instance, EnumFacing side, NBTBase nbt) {
            //empty
        }

    }

    // Empty factory, just implemented here for ease of future expansion
    public static class Factory implements Callable<PlayerDeathBackpackCapabilities> {
        @Override
        public PlayerDeathBackpackCapabilities call() throws Exception {
            return null;
        }
    }

    //Getters and setters

    @Nonnull
    public ItemStack getEquippedBackpack() {
        return equippedBackpack;
    }

    public void setEquippedBackpack(@Nonnull ItemStack stack) {
        this.equippedBackpack = stack;
    }

    @Nonnull
    public NonNullList<ItemStack> getEternityBackpacks() {
        return eternityPacks;
    }

    public void setEternityBackpacks(@Nonnull NonNullList<ItemStack> packs) {
        this.eternityPacks = packs;
    }

    //Other helper methods

    public static void register() {
        CapabilityManager.INSTANCE.register(PlayerDeathBackpackCapabilities.class, new PlayerDeathBackpackCapabilities.Storage(), new PlayerDeathBackpackCapabilities.Factory());
    }

    //Static methods

    @Nonnull
    public static ItemStack getEquippedBackpack(EntityLivingBase livingBase) {
        PlayerDeathBackpackCapabilities cap = IronBackpacksCapabilities.getDeathBackpackCapability((EntityPlayer) livingBase);
        if (cap != null) //can this ever be null?
            return cap.getEquippedBackpack();

        return ItemStack.EMPTY;
    }

    public static void setEquippedBackpack(EntityLivingBase livingBase, @Nonnull ItemStack stack) {
        PlayerDeathBackpackCapabilities cap = IronBackpacksCapabilities.getDeathBackpackCapability((EntityPlayer) livingBase);
        if (cap != null)
            cap.setEquippedBackpack(stack);
    }

    @Nonnull
    public static NonNullList<ItemStack> getEternityBackpacks(EntityLivingBase livingBase) {
        PlayerDeathBackpackCapabilities cap = IronBackpacksCapabilities.getDeathBackpackCapability((EntityPlayer) livingBase);
        if (cap != null)
            return cap.getEternityBackpacks();

        return NonNullList.create();
    }

    public static void setEternityBackpacks(EntityLivingBase livingBase, NonNullList<ItemStack> stacks) {
        PlayerDeathBackpackCapabilities cap = IronBackpacksCapabilities.getDeathBackpackCapability((EntityPlayer) livingBase);
        if (cap != null)
            cap.setEternityBackpacks(stacks);
    }

    public static void reset(EntityLivingBase livingBase) {
        setEquippedBackpack(livingBase, ItemStack.EMPTY);
        setEternityBackpacks(livingBase, NonNullList.create()); //empty list
    }

}
