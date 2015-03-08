package main.ironbackpacks.container.alternateGui;

import main.ironbackpacks.client.gui.buttons.TooltipButton;
import main.ironbackpacks.container.slot.BackpackSlot;
import main.ironbackpacks.container.slot.NestingBackpackSlot;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.util.Logger;
import main.ironbackpacks.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.Arrays;
import java.util.UUID;

public class InventoryAlternateGui implements IInventory {

    public ItemStack stack;
    public EntityPlayer player;
    protected ItemStack[] inventory;
    public IronBackpackType type;
    private int[] upgrades;


    public ItemStack[] advFilterStacks;
    public byte[] advFilterButtonStates;
    public int advFilterButtonStartPoint;

    protected int invSize;

    public InventoryAlternateGui(EntityPlayer player, ItemStack itemStack, IronBackpackType type) {

        this.stack = itemStack;
        this.player = player;
        this.type = type;
        this.upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(itemStack);

        this.invSize = UpgradeMethods.getAlternateGuiUpgradeSlots(this.upgrades); //dynamic, size is based on number of alt. gui. upgrades
        this.inventory = new ItemStack[this.getSizeInventory()];

        advFilterButtonStates = new byte[18];
        Arrays.fill(advFilterButtonStates, (byte) TooltipButton.EXACT);
        advFilterButtonStartPoint = 0;

        advFilterStacks = new ItemStack[18];

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
            Logger.error("Impossible error in isItemValidForSLot in InventoryAlternateGui");
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
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.REMOVED)){
                    upgradeRemoved = nbtTagCompound.getInteger(IronBackpacksConstants.NBTKeys.REMOVED) - 1; //-1 b/c renaming upgrade (no slots)
                    if (upgradeRemoved < 0) upgradeRemoved = 100; //naming upgrade shouldn't affect slots
                    nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.REMOVED);
                }
                int upgradeAdded = 100; //high enough to not matter
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ADDED)){
                    upgradeAdded = nbtTagCompound.getInteger(IronBackpacksConstants.NBTKeys.ADDED) - 1; //-1 b/c renaming upgrade (no slots)
                    if (upgradeAdded < 0) upgradeAdded = 100; //naming upgrade shouldn't affect slots
                    nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.ADDED);
                }

                if (!UpgradeMethods.hasFilterBasicUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_BASIC);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_BASIC)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_BASIC, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }

                if (!UpgradeMethods.hasFilterFuzzyUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_FUZZY);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_FUZZY)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_FUZZY, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 1) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT); //index (1) is hardcoded
                        if (upgradeAdded < 1) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasFilterOreDictUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 2) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (upgradeAdded < 2) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasFilterModSpecificUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 3) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (upgradeAdded < 3) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasFilterAdvancedUpgrade(this.upgrades)) {
                    nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_ALL_SLOTS);
                    nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_BUTTONS);
                    nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_START);
                }else {
                    if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_ADV_ALL_SLOTS)) {
                        NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_ADV_ALL_SLOTS, Constants.NBT.TAG_COMPOUND);
                        for (int i = 0; i < tagList.tagCount(); i++) {
                            NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                            advFilterStacks[stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT)] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                    if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_ADV_BUTTONS)) {
                        byte[] bytes = ((NBTTagByteArray) nbtTagCompound.getTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_BUTTONS)).func_150292_c(); //gets byte array
                        for (int i = 0; i < bytes.length; i++) {
                            if (bytes[i] == 0) bytes[i] = (byte)TooltipButton.EXACT;
                            advFilterButtonStates[i] = bytes[i];
                        }
                    }
                    if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_ADV_START)) {
                        advFilterButtonStartPoint = nbtTagCompound.getByte(IronBackpacksConstants.NBTKeys.FILTER_ADV_START);
                    }
                }
                if (!UpgradeMethods.hasHopperUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.HOPPER);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.HOPPER)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.HOPPER, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 5) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (upgradeAdded < 5) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasCondenserUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.CONDENSER);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CONDENSER)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CONDENSER, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 6) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (upgradeAdded < 6) j+=9;
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
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_BASIC, tagList);
        }
        if (UpgradeMethods.hasFilterFuzzyUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_FUZZY, tagList);
        }
        if (UpgradeMethods.hasFilterOreDictUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT, tagList);
        }
        if (UpgradeMethods.hasFilterModSpecificUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC, tagList);
        }
        if (UpgradeMethods.hasFilterAdvancedUpgrade(this.upgrades)) {
            NBTTagList tagListAllSlots = new NBTTagList();
            for (int i = 0; i < 18; i++){
                if (advFilterStacks[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    advFilterStacks[i].writeToNBT(tagCompound);
                    tagListAllSlots.appendTag(tagCompound);
                }
            }

            byte[] byteArray = new byte[18];
            for (int i = 0; i < 18; i++){
                byteArray[i] = advFilterButtonStates[i];
            }
            startIndex += 9;
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_ALL_SLOTS, tagListAllSlots);
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_BUTTONS, new NBTTagByteArray(byteArray));
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_START, new NBTTagByte((byte)advFilterButtonStartPoint));
        }
        if (UpgradeMethods.hasHopperUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.HOPPER, tagList);
        }
        if (UpgradeMethods.hasCondenserUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            startIndex += 9;
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.CONDENSER, tagList);
        }
    }


    public void setAdvFilterButtonType(int index, int typeToSetTo){
        advFilterButtonStates[index] = (byte)typeToSetTo;
    }



}

