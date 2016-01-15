package gr8pefish.ironbackpacks.items.upgrades;

import gr8pefish.ironbackpacks.api.client.gui.button.ButtonNames;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.registry.GuiButtonRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.IronBackpacksHelper;
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

    public static boolean hasButtonUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int upgrade: upgrades){
            if (upgrade == IronBackpacksConstants.Upgrades.BUTTON_UPGRADE_ID){
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasNestingUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int upgrade: upgrades){
            if (upgrade == IronBackpacksConstants.Upgrades.NESTING_UPGRADE_ID){
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasNestingAdvancedUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int upgrade: upgrades){
            if (upgrade == IronBackpacksConstants.Upgrades.ADVANCED_NESTING_UPGRADE_ID){
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasRenamingUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        if (!ConfigHandler.renamingUpgradeRequired){
            return true;
        }else {
            for (int upgrade: upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasDamageBarUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.DAMAGE_BAR_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterBasicUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterFuzzyUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterOreDictUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterModSpecificUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterAdvancedUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_ADVANCED_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasHopperUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasCondenserUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasCondenserSmallUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.CONDENSER_SMALL_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasCondenserTinyUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.CONDENSER_TINY_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasQuickDepositUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasQuickDepositPreciseUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasDepthUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.DEPTH_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterMiningUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_MINING_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterVoidUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_VOID_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasKeepOnDeathUpgrade(ItemStack stack){ //only one that takes itemStack as a parameter
        int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(stack);
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.KEEP_ON_DEATH_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }


    //=============================Other Methods====================================


    /**
     * Gets the number of alternate gui upgrades in the backpack. Note - includes renaming upgrade (if applicable).
     * @param upgrades - the upgrades to check
     * @return integer amount
     */
    public static int getAlternateGuiUpgradesCount(int[] upgrades){
        int numberOfUpgrades = 0;
        if (!ConfigHandler.renamingUpgradeRequired) {
            numberOfUpgrades = 1;
            for (int upgrade: upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_ADVANCED_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_SMALL_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_TINY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MINING_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_VOID_UPGRADE_ID) {
                    numberOfUpgrades++;
                }
            }
        }else{
            for (int upgrade: upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_ADVANCED_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MINING_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_SMALL_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_TINY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_VOID_UPGRADE_ID) {
                    numberOfUpgrades++;
                }
            }
        }
        return numberOfUpgrades;
    }

    /**
     * Gets the number of alternate gui slots from the upgrades. Hardcoded at 9 each (even advanced filter).
     * @param upgrades - the upgrades to check
     * @return integer value
     */
    public static int getAlternateGuiUpgradeSlots(int[] upgrades) {
        int slots = 0;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                    || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID
                    || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MINING_UPGRADE_ID
                    || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_SMALL_UPGRADE_ID
                    || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_TINY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_VOID_UPGRADE_ID) {
                slots += 9; //hardcoded
            }else if (upgrade == IronBackpacksConstants.Upgrades.FILTER_ADVANCED_UPGRADE_ID){
                slots += 9;
            }
        }
        return slots;
    }

    /**
     * Gets the number of alternate gui upgrades used.
     * @param upgrades - the upgrades to check
     * @return integer value
     */
    public static int getAltGuiUpgradesUsed(int[] upgrades){
        int counter = 0;
        if (!ConfigHandler.renamingUpgradeRequired) {
            for (int upgrade : upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_TINY_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_SMALL_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_ADVANCED_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MINING_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_VOID_UPGRADE_ID) {
                    counter++;
                }
            }
        }else{
            for (int upgrade : upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_ADVANCED_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MINING_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_SMALL_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_TINY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_VOID_UPGRADE_ID) {
                    counter++;
                }
            }
        }
        return counter;
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
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
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
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
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
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
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
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        for (int i = 0; i < itemStacks.length; i++){
            if (itemStacks[i] != null){
                if (buttonStates[i] == (byte)GuiButtonRegistry.getButton(ButtonNames.ORE_DICT).getId()){
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
    public static ArrayList<ItemStack> getHopperItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.HOPPER)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.HOPPER, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    /**
     * Gets the items in the condenser/crafting slots
     * @param stack - the backpack to check
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getCondenserItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CONDENSER)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CONDENSER, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    /**
     * Gets the items in the small crafting slots
     * @param stack - the backpack to check
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getCondenserSmallItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CONDENSER_SMALL)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CONDENSER_SMALL, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    /**
     * Gets the items in the tiny crafting slots
     * @param stack - the backpack to check
     * @return - the items that fit this criteria
     */
    public static ArrayList<ItemStack> getCondenserTinyItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CONDENSER_TINY)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CONDENSER_TINY, Constants.NBT.TAG_COMPOUND);
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
     * @param usePrecise - to check if the item has to be in the inventory already or if it can be put in any empty slot
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
     * @param usePrecise - to check if the item has to be in the inventory already or if it can be put in any empty slot
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
     * Finds the first valid slot and puts the item inside it. Tries to merge if possible, otherwise it goes in an empty slot.
     * @param transferTo - the inventory to put the stack into
     * @param stackToTransfer - the itemstack to put into the inventory
     * @param usePrecise - to check if the item has to be in the inventory already or if it can be put in any empty slot
     * @return whatever wasn't transferred
     */
    private static ItemStack putInFirstValidSlot(IInventory transferTo, ItemStack stackToTransfer, boolean usePrecise){
        for (int i = 0; i < transferTo.getSizeInventory(); i++){
            ItemStack tempStack = transferTo.getStackInSlot(i);
            if (tempStack == null){
                if (usePrecise){ //precise, have to check if the item is in the inventory already
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
                } else { //just check if the slot can accept the item
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
     * Checks if the item is already in the inventory somewhere. Not particularly efficient.
     * @param transferTo - the inventory to check
     * @param stackToTransfer - the stack to check if the inventory has
     * @return boolean of if it has the item
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
