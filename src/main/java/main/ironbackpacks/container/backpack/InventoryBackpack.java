package main.ironbackpacks.container.backpack;

import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

/**
 * The inventory used when opening the backpack normally.
 */
public class InventoryBackpack implements IInventory {

    private ItemStack stack; //the itemstack instance of the backpack
    private EntityPlayer player; //the player
    private ItemStack[] inventory; //the stored items
    private IronBackpackType type; //the backpack type

    //Instantiated from GuiHandler
    public InventoryBackpack(EntityPlayer player, ItemStack itemStack, IronBackpackType type) {
        this.stack = itemStack;
        this.player = player;
        this.type = type;
        this.inventory = new ItemStack[this.getSizeInventory()];
        readFromNBT(stack.getTagCompound());
    }

    public IronBackpackType getType() {
        return type;
    }

    @Override
    public int getSizeInventory() {
        return type.getSize();
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return slotIndex >= this.getSizeInventory() ? null : this.inventory[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        if (inventory[slotIndex] != null) {
            if (inventory[slotIndex].stackSize <= amount) {
                ItemStack itemstack = inventory[slotIndex];
                inventory[slotIndex] = null;
                return itemstack;
            }
            ItemStack itemstack1 = inventory[slotIndex].splitStack(amount);
            if (inventory[slotIndex].stackSize == 0) {
                inventory[slotIndex] = null;
            }
            return itemstack1;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        inventory[slotIndex] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        //unnecessary, as it saves when closing
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        //unused
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        //unused
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack itemStack) {
        return true; //handled by BackpackSlot
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public String getCommandSenderName() {
        return type.getName();
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(getCommandSenderName());
    }


    //=========================================================HELPER METHODS=========================================================================

    /**
     * Checks if the backpack contains any items
     *
     * @return - true if it does, false otherwise
     */
    public boolean isEmpty() {
        for (ItemStack stack : inventory) {
            if (stack != null && stack.stackSize > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Called from the container and saves the backpack.
     *
     * @param entityPlayer - the player with the backpack
     */
    public void onGuiSaved(EntityPlayer entityPlayer) {
        if (stack != null) {
            save();
        }
    }

    /**
     * Saves the backpack (on the server side only)
     *
     * @param player - the player with the backpack
     */
    public void saveWithSideCheck(EntityPlayer player) {
        if (!player.worldObj.isRemote) {
            onGuiSaved(player);
        }
    }

    /**
     * Updates the NBT data of the itemstack to save it
     */
    public void save() {
        NBTTagCompound nbtTagCompound = stack.getTagCompound();

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }

        writeToNBT(nbtTagCompound);
        stack.setTagCompound(nbtTagCompound);
    }

    /**
     * Writes the data of the backpack to NBT form.
     *
     * @param nbtTagCompound - the tag compound
     */
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        if (!player.worldObj.isRemote) { //server side only
            ItemStack tempStack = findParentItemStack(player);
            ItemStack stackToUse = (tempStack == null) ? stack : tempStack;

            nbtTagCompound = stackToUse.getTagCompound();

            // Write the ItemStacks in the inventory to NBT
            NBTTagList tagList = new NBTTagList();
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ITEMS, tagList);
        }
    }

    /**
     * Loads in the data stored in the NBT of this stack and puts the items in their respective slots.
     *
     * @param nbtTagCompound - the tag compound
     */
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        if (!player.worldObj.isRemote) { //server side only
            ItemStack tempStack = findParentItemStack(player);
            stack = (tempStack == null) ? stack : tempStack;
            if (stack != null) {
                nbtTagCompound = stack.getTagCompound();

                if (nbtTagCompound != null) {
                    if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ITEMS)) {
                        NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.ITEMS, Constants.NBT.TAG_COMPOUND);
                        this.inventory = new ItemStack[this.getSizeInventory()];

                        for (int i = 0; i < tagList.tagCount(); i++) {
                            NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                            int j = stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                            if (i >= 0 && i <= inventory.length) {
                                this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Helper method to get the stack, and make sure it is unique.
     *
     * @param entityPlayer - the player to check
     * @return - the itemstack if it is found, null otherwise
     */
    private ItemStack findParentItemStack(EntityPlayer entityPlayer) {
        if (NBTHelper.hasUUID(stack)) {
            UUID parentUUID = new UUID(stack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID), stack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++) {
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

                if (itemStack != null && itemStack.getItem() instanceof ItemBaseBackpack && NBTHelper.hasUUID(itemStack)) {
                    if (itemStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID) == parentUUID.getMostSignificantBits() && itemStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID) == parentUUID.getLeastSignificantBits()) {
                        return itemStack;
                    }
                }
            }
        }
        return null;
    }


}
