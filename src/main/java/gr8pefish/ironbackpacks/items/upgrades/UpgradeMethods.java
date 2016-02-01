package gr8pefish.ironbackpacks.items.upgrades;

import gr8pefish.ironbackpacks.api.client.gui.button.ButtonNames;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.registry.GuiButtonRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public class UpgradeMethods {

    //===============================hasUpgrade Methods=====================================

    //Remove this one?
    public static boolean hasAdditionalUpgradesUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.getItemIUpgrade(stack.getItemDamage()).equals(ItemRegistry.buttonUpgrade)){
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasButtonUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIUpgrade(stack.getItemDamage()).equals(ItemRegistry.buttonUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasDamageBarUpgrade(ArrayList<ItemStack> upgrades){ //TODO: change this to cached NBTTag value so it doesn't always reference it dynamically to see if I need to render damage bar
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades) {
            if (ItemIUpgradeRegistry.isInstanceOfIUpgrade(stack)) {
                if (stack.getItemDamage() < ItemIUpgradeRegistry.getIPackSize()) {
                    if (ItemIUpgradeRegistry.getItemIUpgrade(stack.getItemDamage()).equals(ItemRegistry.damageBarUpgrade)) {
                        hasUpgrade = true;
                        break;
                    }
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasDepthUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIUpgrade(stack.getItemDamage()).equals(ItemRegistry.depthUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasEternityUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIUpgrade(stack.getItemDamage()).equals(ItemRegistry.eternityUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasRenamingUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIUpgrade(stack.getItemDamage()).equals(ItemRegistry.renamingUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasNestingUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConflictingUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConflictingUpgrade(stack.getItemDamage()).equals(ItemRegistry.nestingUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasNestingAdvancedUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConflictingUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConflictingUpgrade(stack.getItemDamage()).equals(ItemRegistry.nestingAdvancedUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasQuickDepositUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConflictingUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConflictingUpgrade(stack.getItemDamage()).equals(ItemRegistry.quickDepositUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasQuickDepositPreciseUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConflictingUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConflictingUpgrade(stack.getItemDamage()).equals(ItemRegistry.quickDepositPreciseUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasCraftingUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.craftingUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasCraftingSmallUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.craftingSmallUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasCraftingTinyUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.craftingTinyUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterBasicUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.filterBasicUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterFuzzyUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.filterFuzzyUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterOreDictUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.filterOreDictUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterModSpecificUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.filterModSpecificUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterVoidUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.filterVoidUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterAdvancedUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.filterAdvancedUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterMiningUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.filterMiningUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasRestockingUpgrade(ArrayList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(stack)) {
                if (ItemIUpgradeRegistry.getItemIConfingurableUpgrade(stack.getItemDamage()).equals(ItemRegistry.restockingUpgrade)) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    //=============================Other Methods====================================


    /**
     * Gets the number of alternate gui slots from the upgrades. Hardcoded at 9 each (even advanced filter), but keeping this method in case I change that later.
     * @param upgrades - the upgrades to check
     * @return integer value
     */
    public static int getAlternateGuiUpgradeSlots(ArrayList<ItemStack> upgrades) {
        return (getAltGuiUpgradesApplied(upgrades) * 9);
    }

    /**
     * Gets the number of alternate gui upgrades applied.
     * @param upgrades - the upgrades to check
     * @return integer value
     */
    public static int getAltGuiUpgradesApplied(ArrayList<ItemStack> upgrades){
        int counter = 0;
        for (ItemStack upgrade : upgrades){
            if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(upgrade)){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Check if the upgrades are the same by comparing their items and the damage value.
     * @param upgrade1 - the first upgrade to check
     * @param upgrade2 - the second upgrade to check
     * @return - boolean if they are equal (enough)
     */
    public static boolean areUpgradesFunctionallyEquivalent(ItemStack upgrade1, ItemStack upgrade2){
        return (upgrade1.getItem().equals(upgrade2.getItem()) && upgrade1.getItemDamage() == upgrade2.getItemDamage());
    }


    //===================================================================Get Filter Items==========================================================
    //used in the event handler

    public static ArrayList<ItemStack> getBasicFilterItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_BASIC)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_BASIC, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static ArrayList<ItemStack> getFuzzyFilterItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_FUZZY)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_FUZZY, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static ArrayList<ItemStack> getOreDictFilterItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static ArrayList<ItemStack> getModSpecificFilterItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static ArrayList<ItemStack> getVoidFilterItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_VOID)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_VOID, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static ArrayList<ItemStack> getMiningFilterItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_MINING)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_MINING, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    //============================================================Advanced Filter Methods=================================================================
    //used in the event handler

    /**
     * Gets the items stored in the advanced filter.
     * @param stack - the backpack with the filter
     * @return - array of the non-null items
     */
    public static ItemStack[] getAdvFilterAllItems(ItemStack stack) {
        ItemStack[] advFilterStacks = new ItemStack[18];
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_ADV_ALL_SLOTS)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_ADV_ALL_SLOTS, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    advFilterStacks[stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT)] = ItemStack.loadItemStackFromNBT(stackTag);
                }
            }
        }
        return advFilterStacks;
    }

    /**
     * Gets the states of each button in the advanced filter.
     * @param stack - the backpack
     * @return - a byte array corresponding to the state of each button
     */
    public static byte[] getAdvFilterButtonStates(ItemStack stack) {
        byte[] advFilterButtonStates = new byte[18];
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_ADV_BUTTONS)) {
                byte[] bytes = ((NBTTagByteArray) nbtTagCompound.getTag(IronBackpacksConstants.NBTKeys.FILTER_ADV_BUTTONS)).getByteArray(); //gets byte array
                for (int i = 0; i < bytes.length; i++) {
                    if (bytes[i] == 0) bytes[i] = (byte) GuiButtonRegistry.getButton(ButtonNames.EXACT).getId();
                    advFilterButtonStates[i] = bytes[i];
                }
            }
        }
        return advFilterButtonStates;
    }

    /**
     * Gets the items in the advanced filter that are on the basic matching
     * @param itemStacks - the items in the advanced filter
     * @param buttonStates - the filter states of each button
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getAdvFilterBasicItems(ItemStack[] itemStacks, byte[] buttonStates){
        ArrayList<ItemStack> returnArray = new ArrayList<>();
        for (int i = 0; i < itemStacks.length; i++){
            if (itemStacks[i] != null){
                if (buttonStates[i] == (byte) GuiButtonRegistry.getButton(ButtonNames.EXACT).getId()){
                    returnArray.add(itemStacks[i]);
                }
            }
        }
        return returnArray;
    }

    /**
     * Gets the items in the advanced filter that are on the fuzzy matching
     * @param itemStacks - the items in the advanced filter
     * @param buttonStates - the filter states of each button
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getAdvFilterFuzzyItems(ItemStack[] itemStacks, byte[] buttonStates){
        ArrayList<ItemStack> returnArray = new ArrayList<>();
        for (int i = 0; i < itemStacks.length; i++){
            if (itemStacks[i] != null){
                if (buttonStates[i] == (byte)GuiButtonRegistry.getButton(ButtonNames.FUZZY).getId()){
                    returnArray.add(itemStacks[i]);
                }
            }
        }
        return returnArray;
    }

    /**
     * Gets the items in the advanced filter that are on the mod specific matching
     * @param itemStacks - the items in the advanced filter
     * @param buttonStates - the filter states of each button
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getAdvFilterModSpecificItems(ItemStack[] itemStacks, byte[] buttonStates){
        ArrayList<ItemStack> returnArray = new ArrayList<>();
        for (int i = 0; i < itemStacks.length; i++){
            if (itemStacks[i] != null){
                if (buttonStates[i] == (byte)GuiButtonRegistry.getButton(ButtonNames.MOD_SPECIFIC).getId()){
                    returnArray.add(itemStacks[i]);
                }
            }
        }
        return returnArray;
    }

    /**
     * Gets the items in the advanced filter that are on the ore dictionary matching
     * @param itemStacks - the items in the advanced filter
     * @param buttonStates - the filter states of each button
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getAdvFilterOreDictItems(ItemStack[] itemStacks, byte[] buttonStates){
        ArrayList<ItemStack> returnArray = new ArrayList<>();
        for (int i = 0; i < itemStacks.length; i++){
            if (itemStacks[i] != null){
                if (buttonStates[i] == (byte)GuiButtonRegistry.getButton(ButtonNames.ORE_DICT).getId()){
                    returnArray.add(itemStacks[i]);
                }
            }
        }
        return returnArray;
    }

    /**
     * Gets the items in the advanced filter that are on the void matching
     * @param itemStacks - the items in the advanced filter
     * @param buttonStates - the filter states of each button
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getAdvFilterVoidItems(ItemStack[] itemStacks, byte[] buttonStates){
        ArrayList<ItemStack> returnArray = new ArrayList<>();
        for (int i = 0; i < itemStacks.length; i++){
            if (itemStacks[i] != null){
                if (buttonStates[i] == (byte)GuiButtonRegistry.getButton(ButtonNames.VOID).getId()){
                    returnArray.add(itemStacks[i]);
                }
            }
        }
        return returnArray;
    }

    //========================================================================= Get other alternate gui items ======================================================
    //used in the event handler

    /**
     * Gets the items in the hopper/restocking slots
     * @param stack - the backpack to check
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getRestockingItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.RESTOCKING)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.RESTOCKING, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    /**
     * Gets the items in the condenser/recipes slots
     * @param stack - the backpack to check
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getCrafterItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CRAFTING)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CRAFTING, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    /**
     * Gets the items in the small recipes slots
     * @param stack - the backpack to check
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getCrafterSmallItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CRAFTING_SMALL)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CRAFTING_SMALL, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    /**
     * Gets the items in the tiny recipes slots
     * @param stack - the backpack to check
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getCrafterTinyItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CRAFTING_TINY)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CRAFTING_TINY, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }


    //======================================================================Transfer From Backpack To IInventory========================================================================
    //used with the quick deposit (precise) upgrade

    /**
     * Transfers items from the backpack to the tile entity's inventory at the targeted coordinates
     * @param player - the player performing the action
     * @param backpack - the itemstack
     * @param world - the world object
     * @param pos - the position of the block
     * @param usePrecise - to check if the items has to be in the inventory already or if it can be put in any empty slot
     * @return - boolean success if transferred
     */
    public static boolean transferFromBackpackToInventory(EntityPlayer player, ItemStack backpack, World world, BlockPos pos, boolean usePrecise){
        TileEntity targeted = world.getTileEntity(pos);
        if (targeted != null){
            IInventory inv = getTargetedInventory(targeted);
            if (inv != null) {
                return transferItemsToContainer(player, backpack, inv, usePrecise);
            }
        }
        return false;
    }

    /**
     * Gets the IInventory of the targeted tile entity
     * @param tile - the tile entity to check
     * @return IInventory if it exists, null otherwise
     */
    private static IInventory getTargetedInventory(TileEntity tile){
        if (tile == null || !(tile instanceof IInventory)) {
            return null;
        }
        return (IInventory) tile;
    }

    /**
     * Transfers the items from the backpack to the inventory.
     * @param player - the player doing the action
     * @param backpack - the itemstack backpack
     * @param transferTo - the inventory to transfer to
     * @param usePrecise - to check if the items has to be in the inventory already or if it can be put in any empty slot
     * @return boolean if successful
     */
    private static boolean transferItemsToContainer(EntityPlayer player, ItemStack backpack, IInventory transferTo, boolean usePrecise){
        boolean returnValue = false;
        InventoryBackpack inventoryBackpack = new InventoryBackpack(player, backpack);
        if (transferTo.getSizeInventory() > 0 && !(inventoryBackpack.isEmpty())){ //if have a valid inventory to transfer to and have items to transfer
            for (int i = 0; i < inventoryBackpack.getSizeInventory(); i++){
                ItemStack stackToMove = inventoryBackpack.getStackInSlot(i);
                if (stackToMove != null && stackToMove.stackSize > 0){
                    ItemStack remainder = putInFirstValidSlot(transferTo, stackToMove, usePrecise);
                    inventoryBackpack.setInventorySlotContents(i, remainder);
                    inventoryBackpack.onGuiSaved(player);
                    returnValue = true;
                }
            }
        }
        return returnValue;
    }

    /**
     * Finds the first valid slot and puts the items inside it. Tries to merge if possible, otherwise it goes in an empty slot.
     * @param transferTo - the inventory to put the stack into
     * @param stackToTransfer - the itemstack to put into the inventory
     * @param usePrecise - to check if the items has to be in the inventory already or if it can be put in any empty slot
     * @return whatever wasn't transferred
     */
    private static ItemStack putInFirstValidSlot(IInventory transferTo, ItemStack stackToTransfer, boolean usePrecise){
        for (int i = 0; i < transferTo.getSizeInventory(); i++){
            ItemStack tempStack = transferTo.getStackInSlot(i);
            if (tempStack == null){
                if (usePrecise){ //precise, have to check if the items is in the inventory already
                    if (isStackInInventoryAlready(transferTo, stackToTransfer)){
                        if (transferTo instanceof ISidedInventory){
                            ISidedInventory sidedInventory = (ISidedInventory)transferTo;
                            EnumFacing[] enumFacings = EnumFacing.values();
                            for (int j = 0; j < enumFacings.length; j++) { //try all sides
                                if (sidedInventory.canInsertItem(i, stackToTransfer, enumFacings[j])) {
                                    transferTo.setInventorySlotContents(i, stackToTransfer);
                                    transferTo.markDirty();
                                    return null;
                                }
                            }
                        }else {
                            if (transferTo.isItemValidForSlot(i, stackToTransfer)) {
                                transferTo.setInventorySlotContents(i, stackToTransfer);
                                transferTo.markDirty();
                                return null;
                            }
                        }
                    }
                } else { //just check if the slot can accept the items
                    if (transferTo instanceof ISidedInventory){
                        ISidedInventory sidedInventory = (ISidedInventory)transferTo;
                        EnumFacing[] enumFacings = EnumFacing.values();
                        for (int j = 0; j < enumFacings.length; j++) { //try all sides
                            if (sidedInventory.canInsertItem(i, stackToTransfer, enumFacings[j])) {
                                transferTo.setInventorySlotContents(i, stackToTransfer);
                                transferTo.markDirty();
                                return null;
                            }
                        }
                    }else {
                        if (transferTo.isItemValidForSlot(i, stackToTransfer)) {
                            transferTo.setInventorySlotContents(i, stackToTransfer);
                            transferTo.markDirty();
                            return null;
                        }
                    }
                }
            }else if (tempStack.stackSize <= 0){//leave it alone
            }else{ //stack present, check if merge possible
                if (tempStack.isItemEqual(stackToTransfer) && tempStack.stackSize < tempStack.getMaxStackSize() && ItemStack.areItemStackTagsEqual(tempStack, stackToTransfer)){ //can merge
                    int amountToResupply = tempStack.getMaxStackSize() - tempStack.stackSize;
                    if (stackToTransfer.stackSize >= amountToResupply) { //stackToTransfer will leave a remainder if merged
                        //merge what you can and set stackToTransfer to the remainder
                        transferTo.setInventorySlotContents(i, new ItemStack(tempStack.getItem(), tempStack.getMaxStackSize(), tempStack.getItemDamage()));
                        transferTo.markDirty();
                        int oldStackSize = stackToTransfer.stackSize;
                        stackToTransfer.stackSize = oldStackSize - amountToResupply;
                        //don't return, try to use up the stack completely by continuing to iterate
                        if (stackToTransfer.stackSize == 0) return null;
                        //unless the stack size is 0, then you need to get rid of it
                    }else{
                        //use up the stackToTransfer and increment the stackSize of tempStack
                        transferTo.setInventorySlotContents(i, new ItemStack(tempStack.getItem(), tempStack.stackSize + stackToTransfer.stackSize, tempStack.getItemDamage()));
                        transferTo.markDirty();
                        return null;
                    }

                }
            }
        }
        return stackToTransfer;
    }

    /**
     * Checks if the items is already in the inventory somewhere. Not particularly efficient.
     * @param transferTo - the inventory to check
     * @param stackToTransfer - the stack to check if the inventory has
     * @return boolean of if it has the items
     */
    private static boolean isStackInInventoryAlready(IInventory transferTo, ItemStack stackToTransfer){
        for (int i = 0; i < transferTo.getSizeInventory(); i++) {
            ItemStack tempStack = transferTo.getStackInSlot(i);
            if (tempStack != null && tempStack.stackSize > 0
                    && stackToTransfer.isItemEqual(tempStack)
                    && ItemStack.areItemStackTagsEqual(tempStack, stackToTransfer)) {
                return true;
            }
        }
        return false;
    }

}
