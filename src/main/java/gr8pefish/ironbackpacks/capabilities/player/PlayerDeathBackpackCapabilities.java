package gr8pefish.ironbackpacks.capabilities.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class PlayerDeathBackpackCapabilities implements ICapabilitySerializable<NBTTagCompound> {

    //ToDo: All this

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }

    public static void register(){

    }

    public static ArrayList<ItemStack> getEternityBackpacks(EntityPlayer player) {
        return null;
    }

    public static ItemStack getEquippedBackpack(EntityPlayer player) {
        return null;
    }

    public static void setEquippedBackpack(EntityPlayer player, Object o) {

    }

    public static void setEternityBackpacks(EntityPlayer player, ArrayList<ItemStack> backpacks) {

    }
}
