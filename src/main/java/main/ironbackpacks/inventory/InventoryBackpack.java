package main.ironbackpacks.inventory;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.container.IronBackpackType;
import main.ironbackpacks.items.ItemBasicBackpack;
import main.ironbackpacks.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

public class InventoryBackpack implements IInventory { //extend IInvBasic ?

    public ItemStack stack;
    public EntityPlayer player;

    protected ItemStack[] inventory;

    private Container eventHandler;
    private IronBackpackType type;

    //Instantiated from GuiHandler
    public InventoryBackpack(EntityPlayer player, ItemStack itemStack, IronBackpackType type){

        this.stack = itemStack;
        this.player = player;

        this.type = type;
        this.inventory = new ItemStack[this.getSizeInventory()];

        readFromNBT(stack.getTagCompound());
    }

    public IronBackpackType getType(){
        return type;
    }

    @Override
    public int getSizeInventory() {
        return IronBackpackType.BASIC.getSize(); //TODO - multiple sizes
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
//                markDirty();
                return itemstack;
            }
            ItemStack itemstack1 = inventory[slotIndex].splitStack(amount);
            if (inventory[slotIndex].stackSize == 0) {
                inventory[slotIndex] = null;
            }
//            markDirty();
            return itemstack1;
        }
        else {
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
    public String getInventoryName() {
        return IronBackpackType.BASIC.name;
    } //TODO multiple types

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
    //
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {
    //
    }

    @Override
    public void closeInventory() {
    //
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack itemStack) {
        return true; //handled by BackpackSlot
    }

    public void onGuiSaved(EntityPlayer entityPlayer){
        if (stack != null){
            save();
        }
    }

    public void save(){
        NBTTagCompound nbtTagCompound = stack.getTagCompound();

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }

        writeToNBT(nbtTagCompound);
        stack.setTagCompound(nbtTagCompound);
    }

    public ItemStack findParentItemStack(EntityPlayer entityPlayer){
        if (NBTHelper.hasUUID(stack)){
            UUID parentUUID = new UUID(stack.getTagCompound().getLong(ModInformation.MOST_SIG_UUID), stack.getTagCompound().getLong(ModInformation.LEAST_SIG_UUID));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++){
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

                if (itemStack != null && itemStack.getItem() instanceof ItemBasicBackpack && NBTHelper.hasUUID(itemStack)){
                    if (itemStack.getTagCompound().getLong(ModInformation.MOST_SIG_UUID) == parentUUID.getMostSignificantBits() && itemStack.getTagCompound().getLong(ModInformation.LEAST_SIG_UUID) == parentUUID.getLeastSignificantBits()){
                        return itemStack;
                    }
                }
            }
        }
        return null;
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound){
        stack = findParentItemStack(player);
        if (stack != null) {
            nbtTagCompound = stack.getTagCompound();

            if (nbtTagCompound != null && nbtTagCompound.hasKey("Items")) {
                NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
                this.inventory = new ItemStack[this.getSizeInventory()];

                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    int j = stackTag.getByte("Slot");
                    if (i >= 0 && i <= inventory.length) {
                        this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                    }
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound){
        nbtTagCompound = findParentItemStack(player).getTagCompound();

        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                inventory[i].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag("Items", tagList);
    }


}
