package main.ironbackpacks.container.alternateGui;

import main.ironbackpacks.api.client.gui.button.ButtonNames;
import main.ironbackpacks.container.slot.BackpackSlot;
import main.ironbackpacks.container.slot.NestingBackpackSlot;
import main.ironbackpacks.items.backpacks.BackpackTypes;
import main.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.registry.GuiButtonRegistry;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;

import java.util.Arrays;
import java.util.UUID;

/**
 * The inventory used when in the alternate gui of the backpack
 */
public class InventoryAlternateGui implements IInventory {

    private ItemStack stack; //the itemstack of the backpack
    private EntityPlayer player; //the player
    private ItemStack[] inventory; //the items in the backpack
    private BackpackTypes type; //the type of backpack
    private int[] upgrades; //the upgrades applied
    private int invSize; //the size of the inventory

    protected ItemStack[] advFilterStacks; //the items in the advanced filter
    protected byte[] advFilterButtonStates; //the button states of the advanced filter
    protected int advFilterButtonStartPoint; //the start point of the advanced filter


    public InventoryAlternateGui(EntityPlayer player, ItemStack itemStack, BackpackTypes type) {
        this.stack = itemStack;
        this.player = player;
        this.type = type;
        this.upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(itemStack);
        this.invSize = UpgradeMethods.getAlternateGuiUpgradeSlots(this.upgrades); //dynamic, size is based on number of alt. gui. upgrades
        this.inventory = new ItemStack[this.getSizeInventory()];

        advFilterButtonStates = new byte[18];
        Arrays.fill(advFilterButtonStates, (byte) GuiButtonRegistry.getButton(ButtonNames.EXACT).getId()); //default value of all buttons
        advFilterButtonStartPoint = 0; //default start point
        advFilterStacks = new ItemStack[18];

        readFromNBT(stack.getTagCompound()); //to initialize data
    }

    public int getAdvFilterButtonStartPoint(){
        return advFilterButtonStartPoint;
    }
    public byte[] getAdvFilterButtonStates(){
        return advFilterButtonStates;
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
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        if (slotIndex >= 0 && slotIndex < this.inventory.length) {
            this.inventory[slotIndex] = itemStack;
            if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
                itemStack.stackSize = getInventoryStackLimit();
            }
        }
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(getName());
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = null;
        if (inventory[index] != null){
            stack = inventory[index];
            inventory[index] = null;
        }
        return stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        //unused
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
    public boolean isItemValidForSlot(int index, ItemStack itemStack) {
        if (UpgradeMethods.hasFilterBasicUpgrade(this.upgrades) || UpgradeMethods.hasFilterFuzzyUpgrade(this.upgrades)
                || UpgradeMethods.hasFilterModSpecificUpgrade(this.upgrades) || UpgradeMethods.hasFilterOreDictUpgrade(this.upgrades)
                || UpgradeMethods.hasFilterAdvancedUpgrade(this.upgrades) || UpgradeMethods.hasFilterMiningUpgrade(this.upgrades)
                || UpgradeMethods.hasFilterVoidUpgrade(this.upgrades)){
            if (UpgradeMethods.hasNestingUpgrade(this.upgrades)) {
                NestingBackpackSlot myslot = new NestingBackpackSlot(this, index, 0, 0, this.type);
                return myslot.acceptsStack(itemStack);
            }else{
                BackpackSlot mySlot = new BackpackSlot(this, index, 0,0);
                return mySlot.acceptsStack(itemStack);
            }
        }else if (UpgradeMethods.hasHopperUpgrade(this.upgrades)){
            return itemStack.isStackable();
        }else if (UpgradeMethods.hasCondenserTinyUpgrade(this.upgrades)){
            return itemStack.isStackable();
        }else if (UpgradeMethods.hasCondenserSmallUpgrade(this.upgrades)){
            return itemStack.isStackable();
        }else if (UpgradeMethods.hasCondenserUpgrade(this.upgrades)){
            return itemStack.isStackable();
        }else{
            Logger.error("Impossible error in isItemValidForSlot in InventoryAlternateGui"); //need to have an alternate gui upgrade to have slots exist
            return false;
        }
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        //unused
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < inventory.length; i++){
            inventory[i] = null;
        }
    }

    //==================================================================HELPER METHODS===================================================================

    /**
     * Sets the advanced filter button specified to the type specified.
     * @param index - the button's index
     * @param typeToSetTo - the button type to set it to
     */
    public void setAdvFilterButtonType(int index, int typeToSetTo){
        advFilterButtonStates[index] = (byte)typeToSetTo;
    }

    //====================================================================SAVING METHODS==============================================

    /**
     * Called from the container to save the items.
     * @param entityPlayer - the player
     */
    public void onGuiSaved(EntityPlayer entityPlayer) {
        if (stack != null) {
            save();
        }
    }

    /**
     * Updates the NBT data of the backpack to save it.
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
     * Writes the data of the backpack to NBT form. The information added varies depending on the upgrades applied.
     * @param nbtTagCompound - the tag compound
     */
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
            startIndex += 9; //increment 9 slots
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
            //saves the button states as bytes
            byte[] byteArray = new byte[18];
            for (int i = 0; i < 18; i++){
                byteArray[i] = advFilterButtonStates[i];
            }
            startIndex += 9; //still increment 9 slots
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_ALL_SLOTS, tagListAllSlots);
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_BUTTONS, new NBTTagByteArray(byteArray));
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_START, new NBTTagByte((byte)advFilterButtonStartPoint)); //need to save the start point too
        }
        if (UpgradeMethods.hasFilterMiningUpgrade(this.upgrades)) {
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
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_MINING, tagList);
        }
        if (UpgradeMethods.hasFilterVoidUpgrade(this.upgrades)) {
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
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.FILTER_VOID, tagList);
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
        if (UpgradeMethods.hasCondenserSmallUpgrade(this.upgrades)) {
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
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.CONDENSER_SMALL, tagList);
        }
        if (UpgradeMethods.hasCondenserTinyUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            } //no need to increment, as this is the last upgrade that appears. CAREFUL with this if adding more upgrades.
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.CONDENSER_TINY, tagList);
        }
    }

    /**
     * Loads in the items via the NBT data. However, since upgrades can be removed, the items must be loaded into their correct indices.
     * @param nbtTagCompound - the tag compound
     */
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        stack = findParentItemStack(player);
        if (stack != null) {
            nbtTagCompound = stack.getTagCompound();

            if (nbtTagCompound != null) {
                this.inventory = new ItemStack[this.getSizeInventory()];

                //sets the 'upgradeRemoved/Added' value to reflect how many upgrades have been added or removed so the loaded items can be shifted to go in their correct indices.
                int upgradeRemoved = 100; //arbitrary high value
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.REMOVED)){
                    upgradeRemoved = nbtTagCompound.getInteger(IronBackpacksConstants.NBTKeys.REMOVED) - 1; //-1 b/c renaming upgrade (no slots) [index of list of ALT_GUI_UPGRADE_IDS]
                    if (upgradeRemoved < 0) upgradeRemoved = 100; //naming upgrade shouldn't affect slots
                    nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.REMOVED);
                }
                int upgradeAdded = 100; //arbitrary high value
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ADDED)){
                    upgradeAdded = nbtTagCompound.getInteger(IronBackpacksConstants.NBTKeys.ADDED) - 1; //-1 b/c renaming upgrade (no slots)
                    if (upgradeAdded < 0) upgradeAdded = 100; //naming upgrade shouldn't affect slots
                    nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.ADDED);
                }

                if (!UpgradeMethods.hasFilterBasicUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_BASIC); //remove the data if the upgrade has been removed
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
                        int j = (upgradeRemoved < 1) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT); //shift the slot to load in if necessary
                        if (upgradeAdded < 1) j+=9; //shift if necessary
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
                        byte[] bytes = ((NBTTagByteArray) nbtTagCompound.getTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_BUTTONS)).getByteArray(); //gets byte array
                        for (int i = 0; i < bytes.length; i++) {
                            if (bytes[i] == 0) bytes[i] = (byte) GuiButtonRegistry.getButton(ButtonNames.EXACT).getId();
                            advFilterButtonStates[i] = bytes[i];
                        }
                    }
                    if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_ADV_START)) {
                        advFilterButtonStartPoint = nbtTagCompound.getByte(IronBackpacksConstants.NBTKeys.FILTER_ADV_START);
                    }
                }
                if (!UpgradeMethods.hasFilterMiningUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_MINING);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_MINING)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_MINING, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 5) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (upgradeAdded < 5) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasFilterVoidUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_VOID);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_VOID)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_VOID, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 6) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (upgradeAdded < 6) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasHopperUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.HOPPER);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.HOPPER)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.HOPPER, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 7) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (upgradeAdded < 7) j+=9;
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
                        int j = (upgradeRemoved < 8) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (upgradeAdded < 8) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasCondenserSmallUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.CONDENSER_SMALL);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CONDENSER_SMALL)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CONDENSER_SMALL, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 9) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (upgradeAdded < 9) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
                if (!UpgradeMethods.hasCondenserTinyUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.CONDENSER_TINY);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CONDENSER_TINY)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CONDENSER_TINY, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = (upgradeRemoved < 10) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (upgradeAdded < 10) j+=9;
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }
            }
        }
    }

    /**
     * Helper method to get the stack, and make sure it is unique.
     * @param entityPlayer - the player to check
     * @return - the itemstack if it is found, null otherwise
     */
    private ItemStack findParentItemStack(EntityPlayer entityPlayer) {
        if (NBTHelper.hasUUID(stack)) {
            UUID parentUUID = new UUID(stack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID), stack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++) {
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

                if (itemStack != null && itemStack.getItem() instanceof IBackpack && NBTHelper.hasUUID(itemStack)) {
                    if (itemStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID) == parentUUID.getMostSignificantBits() && itemStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID) == parentUUID.getLeastSignificantBits()) {
                        return itemStack;
                    }
                }
            }
        }
        return null;
    }

}

