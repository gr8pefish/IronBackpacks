package gr8pefish.ironbackpacks.container.alternateGui;

import gr8pefish.ironbackpacks.api.client.gui.button.ButtonNames;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.items.upgrades.ItemIConfigurableUpgrade;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.container.slot.BackpackSlot;
import gr8pefish.ironbackpacks.container.slot.NestingBackpackSlot;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.registry.GuiButtonRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.Logger;
import gr8pefish.ironbackpacks.util.NBTUtils;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * The inventory used when in the alternate gui of the backpack
 */
public class InventoryAlternateGui implements IInventory {

    private ItemStack backpackStack; //the itemstack of the backpack
    private EntityPlayer player; //the player
    private ItemStack[] inventory; //the items in the backpack
    private ArrayList<ItemStack> upgrades; //the upgrades applied
    private int invSize; //the size of the inventory

    protected ItemStack[] advFilterStacks; //the items in the advanced filter
    protected byte[] advFilterButtonStates; //the button states of the advanced filter
    protected int advFilterButtonStartPoint; //the start point of the advanced filter


    public InventoryAlternateGui(EntityPlayer player, ItemStack backpackStack) {
        this.backpackStack = backpackStack;
        this.player = player;
        this.upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpackStack);
        this.invSize = UpgradeMethods.getAlternateGuiUpgradeSlots(this.upgrades); //dynamic, size is based on number of alt. gui. upgrades
        this.inventory = new ItemStack[this.getSizeInventory()];

        advFilterButtonStates = new byte[18];
        Arrays.fill(advFilterButtonStates, (byte) GuiButtonRegistry.getButton(ButtonNames.EXACT).getId()); //default value of all buttons
        advFilterButtonStartPoint = 0; //default start point
        advFilterStacks = new ItemStack[18];

        readFromNBT(backpackStack.getTagCompound()); //to initialize data
    }

    public int getAdvFilterButtonStartPoint(){
        return advFilterButtonStartPoint;
    }
    public byte[] getAdvFilterButtonStates(){
        return advFilterButtonStates;
    }

    public ItemStack getBackpackStack(){
        return backpackStack;
    }

    public EntityPlayer getPlayer(){
        return player;
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
        return ((ItemBackpack)backpackStack.getItem()).getName(backpackStack);
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
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

    //TODO: make this prettier
    @Override
    public boolean isItemValidForSlot(int index, ItemStack itemStack) {
        if (UpgradeMethods.hasFilterBasicUpgrade(this.upgrades) || UpgradeMethods.hasFilterFuzzyUpgrade(this.upgrades)
                || UpgradeMethods.hasFilterModSpecificUpgrade(this.upgrades) || UpgradeMethods.hasFilterOreDictUpgrade(this.upgrades)
                || UpgradeMethods.hasFilterAdvancedUpgrade(this.upgrades) || UpgradeMethods.hasFilterMiningUpgrade(this.upgrades)
                || UpgradeMethods.hasFilterVoidUpgrade(this.upgrades)){
            if (UpgradeMethods.hasNestingUpgrade(this.upgrades)) {
                NestingBackpackSlot myslot = new NestingBackpackSlot(this, index, 0, 0, this.backpackStack);
                return myslot.acceptsStack(itemStack);
            }else{
                BackpackSlot mySlot = new BackpackSlot(this, index, 0,0);
                return mySlot.acceptsStack(itemStack);
            }
        }else if (UpgradeMethods.hasRestockingUpgrade(this.upgrades)){
            return itemStack.isStackable();
        }else if (UpgradeMethods.hasCraftingTinyUpgrade(this.upgrades)){
            return itemStack.isStackable();
        }else if (UpgradeMethods.hasCraftingSmallUpgrade(this.upgrades)){
            return itemStack.isStackable();
        }else if (UpgradeMethods.hasCraftingUpgrade(this.upgrades)){
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
        if (backpackStack != null) {
            save();
        }
    }

    /**
     * Updates the NBT data of the backpack to save it.
     */
    public void save() {
        NBTTagCompound nbtTagCompound = backpackStack.getTagCompound();

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }

        writeToNBT(nbtTagCompound);
        backpackStack.setTagCompound(nbtTagCompound);
    }

    /**
     * Writes the data of the backpack to NBT form. The information added varies depending on the upgrades applied.
     * @param nbtTagCompound - the tag compound
     */
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound = findParentItemStack(player).getTagCompound();
        if (nbtTagCompound == null) Logger.warn("Error saving in inventory alternate gui.");
        int startIndex = 0; //need to start/increment at the slot number appropriate to the amount of valid upgrades

        // Write the ItemStacks in the inventory to NBT
        if (UpgradeMethods.hasCraftingUpgrade(this.upgrades)) {
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
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.CRAFTING, tagList);
        }
        if (UpgradeMethods.hasCraftingSmallUpgrade(this.upgrades)) {
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
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.CRAFTING_SMALL, tagList);
        }
        if (UpgradeMethods.hasCraftingTinyUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            } startIndex += 9;
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.CRAFTING_TINY, tagList);
        }
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
        if (UpgradeMethods.hasRestockingUpgrade(this.upgrades)) {
            NBTTagList tagList = new NBTTagList();
            for (int i = startIndex; i < startIndex + 9; i++) {
                if (inventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            //no need to increment, as this is the last upgrade that appears. CAREFUL with this if adding more upgrades.
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.RESTOCKING, tagList);
        }
    }

    /**
     * Loads in the items via the NBT data. However, since upgrades can be removed, the items must be loaded into their correct indices.
     * @param nbtTagCompound - the tag compound
     */
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        backpackStack = findParentItemStack(player);
        if (backpackStack != null) {

            nbtTagCompound = backpackStack.getTagCompound();

            if (nbtTagCompound != null) {
                this.inventory = new ItemStack[this.getSizeInventory()];

                //sets the 'upgradeRemoved/Added' value to reflect how many upgrades have been added or removed so the loaded items can be shifted to go in their correct indices.
                boolean hasUpgradeRemoved = false;
                int indexRemoved = 0;
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.REMOVED_ALT_GUI)){
                    ItemStack stack = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(IronBackpacksConstants.NBTKeys.REMOVED_ALT_GUI));
                    ItemIConfigurableUpgrade altGuiUpgrade = ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack);
                    indexRemoved = ItemIUpgradeRegistry.getUninflatedIndexOfConfigurableUpgrade(altGuiUpgrade);
                    hasUpgradeRemoved = true;
                    nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.REMOVED_ALT_GUI);
                }
                boolean hasUpgradeAdded = false;
                int indexAdded = 0;
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ADDED_ALT_GUI)){
                    ItemStack stack = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(IronBackpacksConstants.NBTKeys.ADDED_ALT_GUI));
                    ItemIConfigurableUpgrade altGuiUpgrade = ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack);
                    indexAdded = ItemIUpgradeRegistry.getUninflatedIndexOfConfigurableUpgrade(altGuiUpgrade);
                    hasUpgradeAdded = true;
                    nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.ADDED_ALT_GUI);
                }


                //first one is special since no shifting
                if (!UpgradeMethods.hasCraftingUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.CRAFTING);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CRAFTING)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CRAFTING, Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (i >= 0 && i <= 9) {
                            this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                }

                if (!UpgradeMethods.hasCraftingSmallUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.CRAFTING_SMALL);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CRAFTING_SMALL)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CRAFTING_SMALL, Constants.NBT.TAG_COMPOUND);
                    loadStacksWithShifting(tagList, hasUpgradeRemoved, indexRemoved, hasUpgradeAdded, indexAdded, ItemRegistry.craftingSmallUpgrade);
                }

                if (!UpgradeMethods.hasCraftingTinyUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.CRAFTING_TINY);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CRAFTING_TINY)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CRAFTING_TINY, Constants.NBT.TAG_COMPOUND);
                    loadStacksWithShifting(tagList, hasUpgradeRemoved, indexRemoved, hasUpgradeAdded, indexAdded, ItemRegistry.craftingTinyUpgrade);
                }

                if (!UpgradeMethods.hasFilterBasicUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_BASIC); //remove the data if the upgrade has been removed
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_BASIC)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_BASIC, Constants.NBT.TAG_COMPOUND);
                    loadStacksWithShifting(tagList, hasUpgradeRemoved, indexRemoved, hasUpgradeAdded, indexAdded, ItemRegistry.filterBasicUpgrade);
                }

                if (!UpgradeMethods.hasFilterFuzzyUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_FUZZY);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_FUZZY)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_FUZZY, Constants.NBT.TAG_COMPOUND);
                    loadStacksWithShifting(tagList, hasUpgradeRemoved, indexRemoved, hasUpgradeAdded, indexAdded, ItemRegistry.filterFuzzyUpgrade);
                }

                if (!UpgradeMethods.hasFilterOreDictUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT, Constants.NBT.TAG_COMPOUND);
                    loadStacksWithShifting(tagList, hasUpgradeRemoved, indexRemoved, hasUpgradeAdded, indexAdded, ItemRegistry.filterOreDictUpgrade);
                }

                if (!UpgradeMethods.hasFilterModSpecificUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC, Constants.NBT.TAG_COMPOUND);
                    loadStacksWithShifting(tagList, hasUpgradeRemoved, indexRemoved, hasUpgradeAdded, indexAdded, ItemRegistry.filterModSpecificUpgrade);
                }

                if (!UpgradeMethods.hasFilterVoidUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.FILTER_VOID);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_VOID)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_VOID, Constants.NBT.TAG_COMPOUND);
                    loadStacksWithShifting(tagList, hasUpgradeRemoved, indexRemoved, hasUpgradeAdded, indexAdded, ItemRegistry.filterVoidUpgrade);
                }

                //advanced filter is also special
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
                    loadStacksWithShifting(tagList, hasUpgradeRemoved, indexRemoved, hasUpgradeAdded, indexAdded, ItemRegistry.filterMiningUpgrade);
                }

                if (!UpgradeMethods.hasRestockingUpgrade(this.upgrades)) nbtTagCompound.removeTag(IronBackpacksConstants.NBTKeys.RESTOCKING);
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.RESTOCKING)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.RESTOCKING, Constants.NBT.TAG_COMPOUND);
                    loadStacksWithShifting(tagList, hasUpgradeRemoved, indexRemoved, hasUpgradeAdded, indexAdded, ItemRegistry.restockingUpgrade);
                }
            }
        }
    }

    private boolean shouldShiftAdded(boolean hasUpgradeAdded, int indexAdded, ItemIConfigurableUpgrade ItemIConfigurableUpgrade){
        return (hasUpgradeAdded && (indexAdded < ItemIUpgradeRegistry.getUninflatedIndexOfConfigurableUpgrade(ItemIConfigurableUpgrade)));
    }

    private boolean shouldShiftRemoved(boolean hasUpgradeRemoved, int indexRemoved, ItemIConfigurableUpgrade ItemIConfigurableUpgrade){
        return (hasUpgradeRemoved && (indexRemoved < ItemIUpgradeRegistry.getUninflatedIndexOfConfigurableUpgrade(ItemIConfigurableUpgrade)));
    }

    /**
     * Loads the items stacks present in each filter into the inventory. Takes into account if upgrades were added or removed and shifts the indices accordingly.
     * @param tagList - the tagList of the upgrade, with all the slots and items
     * @param hasUpgradeRemoved - if it has to check for a removed upgrade
     * @param indexRemoved - the index of the upgrade removed in relation to the ItemIUpgradeRegistry and the itemsAltGui within
     * @param hasUpgradeAdded - if it has to check for an added upgrade
     * @param indexAdded - the index of teh upgrade added in relation to the ItemIUpgradeRegistry and the itemsAltGui within
     * @param ItemIConfigurableUpgrade - the items upgrade to specifically check
     */
    private void loadStacksWithShifting(NBTTagList tagList, boolean hasUpgradeRemoved, int indexRemoved, boolean hasUpgradeAdded, int indexAdded, ItemIConfigurableUpgrade ItemIConfigurableUpgrade) {
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
            int j = shouldShiftRemoved(hasUpgradeRemoved, indexRemoved, ItemIConfigurableUpgrade) ? stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT) - 9 : stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
            if (shouldShiftAdded(hasUpgradeAdded, indexAdded, ItemIConfigurableUpgrade)) j+=9;
            if (i >= 0 && i <= 9) {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
            }
        }
    }

    /**
     * Helper method to get the stack, and make sure it is unique.
     * @param entityPlayer - the player to check
     * @return - the itemstack if it is found, null otherwise
     */
    public ItemStack findParentItemStack(EntityPlayer entityPlayer) {
        if (NBTUtils.hasUUID(backpackStack)) {
            UUID parentUUID = new UUID(backpackStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID), backpackStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++) {
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);
                if (itemStack != null && itemStack.getItem() instanceof IBackpack && NBTUtils.hasUUID(itemStack)) {
                    if (itemStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID) == parentUUID.getMostSignificantBits() && itemStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID) == parentUUID.getLeastSignificantBits()) {
                        return itemStack;
                    }
                }
            }
            ItemStack equipped = PlayerWearingBackpackCapabilities.getEquippedBackpack(entityPlayer);
            if (equipped != null && equipped.getItem() instanceof IBackpack && NBTUtils.hasUUID(equipped)) {
                if (equipped.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID) == parentUUID.getMostSignificantBits() && equipped.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID) == parentUUID.getLeastSignificantBits()) {
                    return equipped;
                }
            }

        }
        return null;
    }

}

