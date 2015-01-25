package main.ironbackpacks.container.alternateGui;

import main.ironbackpacks.container.slot.BackpackSlot;
import main.ironbackpacks.container.slot.NestingBackpackSlot;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

public class InventoryAlternateGui implements IInventory {

    public ItemStack stack;
    public EntityPlayer player;
    protected ItemStack[] inventory;
    public IronBackpackType type;
    private int[] upgrades;

    protected int invSize;

    public InventoryAlternateGui(EntityPlayer player, ItemStack itemStack, IronBackpackType type) {

        this.stack = itemStack;
        this.player = player;
        this.type = type;
        this.upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(itemStack);

        this.invSize = UpgradeMethods.getAlternateGuiUpgradeSlots(this.upgrades); //dynamic, size is based on number of alt. gui. upgrades
        this.inventory = new ItemStack[this.getSizeInventory()];

        readFromNBT(stack.getTagCompound()); //to initialize stacks
    }

    @Override
    public int getSizeInventory() {
        return invSize;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return (slotIndex >= this.getSizeInventory() || slotIndex < 0) ? null : this.inventory[slotIndex];
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
        if (slotIndex >= 0 && slotIndex < this.inventory.length) {
            this.inventory[slotIndex] = itemStack;
            if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
                itemStack.stackSize = getInventoryStackLimit();
            }
        }
    }

    @Override
    public String getInventoryName() {
        return type.getName();
    }

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
        if (UpgradeMethods.hasFilterBasicUpgrade(this.upgrades)){
            if (UpgradeMethods.hasNestingUpgrade(this.upgrades)) {
                NestingBackpackSlot myslot = new NestingBackpackSlot(this, index, 0, 0, this.type);
                return myslot.acceptsStack(itemStack);
            }else{
                BackpackSlot mySlot = new BackpackSlot(this, index, 0,0);
                return mySlot.acceptsStack(itemStack);
            }
        }else if (UpgradeMethods.hasHopperUpgrade(this.upgrades)){
            return itemStack.isStackable();
        }else if (UpgradeMethods.hasCondenserUpgrade(this.upgrades)){
            return itemStack.isStackable();
        }else{
            System.out.println("Impossible error in isItemValidForSLot in InventoryAlternateGui");
            return false;
        }
    }

    //=====================HELPER METHODS============================

    //credit to sapient for a lot of this saving code
    public void onGuiSaved(EntityPlayer entityPlayer) {
        if (stack != null) {
            save();
        }
    }

    public void save() {
        NBTTagCompound nbtTagCompound = stack.getTagCompound();

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }

        writeToNBT(nbtTagCompound);
        stack.setTagCompound(nbtTagCompound);
    }

    public ItemStack findParentItemStack(EntityPlayer entityPlayer) {
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

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        stack = findParentItemStack(player);
        if (stack != null) {
            nbtTagCompound = stack.getTagCompound();

            if (nbtTagCompound != null) {
                this.inventory = new ItemStack[this.getSizeInventory()];

                int upgradeRemoved = 100; //high enough to not matter
                if (nbtTagCompound.hasKey("Removed")){
                    upgradeRemoved = nbtTagCompound.getInteger("Removed") - 1; //-1 b/c renaming upgrade (no slots)
                    if (upgradeRemoved < 0) upgradeRemoved = 100; //naming upgrade shouldn't affect slots
                    nbtTagCompound.removeTag("Removed");
                }
                int upgradeAdded = 100; //high enough to not matter
                if (nbtTagCompound.hasKey("Added")){
                    upgradeAdded = nbtTagCompound.getInteger("Added") - 1; //-1 b/c renaming upgrade (no slots)
                    if (upgradeAdded < 0) upgradeAdded = 100; //naming upgrade shouldn't affect slots
                    nbtTagCompound.removeTag("Added");
                }

                if (!UpgradeMethods.hasFilterBasicUpgrade(this.upgrades)) nbtTagCompound.removeTag("FilterBasic");
                if (nbtTagCompound.hasKey("FilterBasic")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("FilterBasic", Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = stackTag.getByte("Slot");
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }

                if (!UpgradeMethods.hasFilterFuzzyUpgrade(this.upgrades)) nbtTagCompound.removeTag("FilterFuzzy");
                if (nbtTagCompound.hasKey("FilterFuzzy")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("FilterFuzzy", Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 1) ? stackTag.getByte("Slot") - 9 : stackTag.getByte("Slot"); //index (1) is hardcoded
                        if (upgradeAdded < 1) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasFilterOreDictUpgrade(this.upgrades)) nbtTagCompound.removeTag("FilterOreDict");
                if (nbtTagCompound.hasKey("FilterOreDict")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("FilterOreDict", Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 2) ? stackTag.getByte("Slot") - 9 : stackTag.getByte("Slot");
                        if (upgradeAdded < 2) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasFilterModSpecificUpgrade(this.upgrades)) nbtTagCompound.removeTag("FilterModSpecific");
                if (nbtTagCompound.hasKey("FilterModSpecific")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("FilterModSpecific", Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 3) ? stackTag.getByte("Slot") - 9 : stackTag.getByte("Slot");
                        if (upgradeAdded < 3) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasHopperUpgrade(this.upgrades)) nbtTagCompound.removeTag("Hopper");
                if (nbtTagCompound.hasKey("Hopper")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Hopper", Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 4) ? stackTag.getByte("Slot") - 9 : stackTag.getByte("Slot");
                        if (upgradeAdded < 4) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasCondenserUpgrade(this.upgrades)) nbtTagCompound.removeTag("Condenser");
                if (nbtTagCompound.hasKey("Condenser")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Condenser", Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 5) ? stackTag.getByte("Slot") - 9 : stackTag.getByte("Slot");
                        if (upgradeAdded < 5) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound = findParentItemStack(player).getTagCompound();
        int startIndex = 0; //need to start/increment at the slot number appropriate to the amount of valid upgrades

        // Write the ItemStacks in the inventory to NBT
        if (UpgradeMethods.hasFilterBasicUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag("FilterBasic", tagList);
        }
        if (UpgradeMethods.hasFilterFuzzyUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag("FilterFuzzy", tagList);
        }
        if (UpgradeMethods.hasFilterOreDictUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag("FilterOreDict", tagList);
        }
        if (UpgradeMethods.hasFilterModSpecificUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag("FilterModSpecific", tagList);
        }
        if (UpgradeMethods.hasHopperUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag("Hopper", tagList);
        }
        if (UpgradeMethods.hasCondenserUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag("Condenser", tagList);
        }
    }



}

