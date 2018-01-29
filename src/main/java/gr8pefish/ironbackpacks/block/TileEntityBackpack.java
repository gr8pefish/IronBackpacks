package gr8pefish.ironbackpacks.block;

import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.inventory.IronBackpacksInventoryHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Tile Entity that holds an inventory
 */
public class TileEntityBackpack extends TileEntity {

    //Add 9 "slots"
    @Nonnull
    private ItemStackHandler inventory; //ToDo: Dynamic
    @Nonnull
    private BackpackInfo backpackInfo; //ToDo: include localized name?

    public TileEntityBackpack() {
        //default constructor
    }

    //Use BackpackInfo

    public TileEntityBackpack(@Nonnull BackpackInfo backpackInfo) {
        this.backpackInfo = backpackInfo;
        this.inventory = backpackInfo.getInventory();
    }

    public void setBackpackInfo(@Nonnull BackpackInfo backpackInfo) {
        this.backpackInfo = backpackInfo;
    }

    public BackpackInfo getBackpackInfo() {
        return this.backpackInfo;
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
        if (capability == IronBackpacksInventoryHelper.BACKPACK_INV_CAPABILITY)
            return true;
        else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) //May need to alter/test this a little, doesn't exactly have the cap
            return true;
        else
            return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == IronBackpacksInventoryHelper.BACKPACK_INV_CAPABILITY ? (T) this : super.getCapability(capability, facing);
    }

    // Other
    

    //Still have to (re)figure out how the inventory works
    public IItemHandler getInventory() {
        return inventory;
    }
}
