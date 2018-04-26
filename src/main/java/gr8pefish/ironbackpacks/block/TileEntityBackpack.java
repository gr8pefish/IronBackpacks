package gr8pefish.ironbackpacks.block;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.inventory.IronBackpacksInventoryHelper;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackVariant;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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

    //TODO: Test, possibly make non-static
    /**
     * Applies the {@link BackpackInfo} to an {@link TileEntity} of a backpack, giving it the necessary NBT.
     *
     * @param tileEntity        - The backpack as an TileEntity.
     * @param backpackInfoToApply - The backpackInfoToApply to apply.
     * @return - The updated tileEntity with the info applied.
     */
    @Nonnull
    public static TileEntity applyPackInfo(@Nonnull TileEntity tileEntity, @Nonnull BackpackInfo backpackInfoToApply) {
        Preconditions.checkNotNull(tileEntity, "TileEntity cannot be null");
        Preconditions.checkNotNull(backpackInfoToApply, "BackpackInfo cannot be null");

        if (!(tileEntity instanceof TileEntityBackpack)) throw new RuntimeException("Trying to apply backpackInfoToApply to a non TEBackpack TE");
        BackpackInfo info = ((TileEntityBackpack)tileEntity).getBackpackInfo();

        //ToDo: Figure out inventory stuff
        if (info.getInventory() != null) {
//            for (int i = 0; i < info.getInventory().getSlots(); i++)
//                invTagList.appendTag(info.getInventory().getStackInSlot(i).serializeNBT());
//            tagCompound.setTag("packInv", invTag);

            backpackInfoToApply.setInventory(info.getInventory()); //maybe something like this eventually
        }

        //set new pack info to the TE's info field
        ((TileEntityBackpack) tileEntity).setBackpackInfo(backpackInfoToApply);

        return tileEntity;
    }

    //TODO: Test, possibly non-static
    @Nonnull
    public static BackpackInfo fromTileEntity(@Nonnull TileEntity te) {
        Preconditions.checkNotNull(te, "TileEntity cannot be null");

        NBTTagCompound compound = te.getTileData();
        if (compound.hasNoTags())
            return new BackpackInfo(new BackpackVariant(IronBackpacksAPI.getBackpackType(IronBackpacksAPI.NULL), BackpackSpecialty.NONE));

        BackpackInfo tagged = ((TileEntityBackpack)te).getBackpackInfo();
        NBTTagList tagList = compound.getTagList("packInv", 10); //ToDo: Figure out inventory stuff with nbt

        return BackpackInfo.setInventory(tagged, tagList);
    }

}
