package gr8pefish.ironbackpacks.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerBackpackHandler implements ICapabilitySerializable<NBTTagCompound> {

    private ItemStack backpack;

    public PlayerBackpackHandler(ItemStack backpack) {
        this.backpack = backpack;
    }

    @Nonnull
    public ItemStack getEquippedBackpack() {
        return backpack;
    }

    public void setEquippedBackpack(@Nonnull ItemStack backpack) {
        this.backpack = backpack;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == IronBackpacksCapabilities.PLAYER_BACKPACK_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability, facing) ? (T) this : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return backpack.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        backpack = new ItemStack(nbt);
    }

    public static class Default extends PlayerBackpackHandler {
        public Default() {
            super(ItemStack.EMPTY);
        }
    }
}
