package gr8pefish.ironbackpacks.block;

import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.inventory.IronBackpacksInventoryHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Tile Entity that holds an inventory
 */
public class TileEntityBackpack extends TileEntity {

    //Add 9 "slots"
    private ItemStackHandler inventory; //ToDo: Dynamic
    private BackpackInfo backpackInfo; //ToDo

    public TileEntityBackpack(@Nonnull BackpackInfo backpackInfo) {
        this.backpackInfo = backpackInfo;
        this.inventory = backpackInfo.getInventory();
    }

    //Write/Read inventory to/from NBT

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("packInfo", backpackInfo.serializeNBT()); //ToDo: Inventory
        compound.setTag("inventory", inventory.serializeNBT()); //idk anymore
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        backpackInfo.deserializeNBT(compound.getCompoundTag("packInfo"));
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }

    //Use Item Handler Capability

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == IronBackpacksInventoryHelper.BACKPACK_INV_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == IronBackpacksInventoryHelper.BACKPACK_INV_CAPABILITY ? (T) this : null;
    }

    // Other


    @Override
    public NBTTagCompound getTileData() {
        return super.getTileData();
    }

    //Unused, trying to keep consistent styling so doing BackpackInfo.fromTE(this), but that may be the wrong approach
    public BackpackInfo getBackpackInfo() {
        return backpackInfo;
    }

    //Still have to (re)figure out how the inventory works
    public IItemHandler getInventory() {
        return inventory;
    }
}
