package gr8pefish.ironbackpacks.items.upgrades;

import gr8pefish.ironbackpacks.api.client.gui.button.ButtonNames;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.registry.GuiButtonRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class UpgradeMethods {

    //===============================hasUpgrade Methods=====================================

    //Remove this one?
    public static boolean hasAdditionalUpgradesUpgrade(NonNullList<ItemStack> upgrades){
        boolean hasUpgrade = false;
        for (ItemStack stack : upgrades){
            if (ItemIUpgradeRegistry.getItemIUpgrade(stack.getItemDamage()).equals(ItemRegistry.buttonUpgrade)){
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasButtonUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasDamageBarUpgrade(NonNullList<ItemStack> upgrades){ //TODO: change this to cached NBTTag value so it doesn't always reference it dynamically to see if I need to render damage bar
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

    public static boolean hasDepthUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasEternityUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasRenamingUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasNestingUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasNestingAdvancedUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasQuickDepositUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasQuickDepositPreciseUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasCraftingUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasCraftingSmallUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasCraftingTinyUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasFilterBasicUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasFilterFuzzyUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasFilterOreDictUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasFilterModSpecificUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasFilterVoidUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasFilterAdvancedUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasFilterMiningUpgrade(NonNullList<ItemStack> upgrades){
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

    public static boolean hasRestockingUpgrade(NonNullList<ItemStack> upgrades){
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
    public static int getAlternateGuiUpgradeSlots(NonNullList<ItemStack> upgrades) {
        return (getAltGuiUpgradesApplied(upgrades) * 9);
    }

    /**
     * Gets the number of alternate gui upgrades applied.
     * @param upgrades - the upgrades to check
     * @return integer value
     */
    public static int getAltGuiUpgradesApplied(NonNullList<ItemStack> upgrades){
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

    public static NonNullList<ItemStack> getBasicFilterItems(ItemStack stack){
        NonNullList<ItemStack> returnArray = NonNullList.create();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_BASIC)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_BASIC, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(new ItemStack(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static NonNullList<ItemStack> getFuzzyFilterItems(ItemStack stack){
        NonNullList<ItemStack> returnArray = NonNullList.create();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_FUZZY)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_FUZZY, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(new ItemStack(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static NonNullList<ItemStack> getOreDictFilterItems(ItemStack stack){
        NonNullList<ItemStack> returnArray = NonNullList.create();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_ORE_DICT, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(new ItemStack(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static NonNullList<ItemStack> getModSpecificFilterItems(ItemStack stack){
        NonNullList<ItemStack> returnArray = NonNullList.create();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_MOD_SPECIFIC, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(new ItemStack(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static NonNullList<ItemStack> getVoidFilterItems(ItemStack stack){
        NonNullList<ItemStack> returnArray = NonNullList.create();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_VOID)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_VOID, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(new ItemStack(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static NonNullList<ItemStack> getMiningFilterItems(ItemStack stack){
        NonNullList<ItemStack> returnArray = NonNullList.create();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.FILTER_MINING)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.FILTER_MINING, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(new ItemStack(stackTag));
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
                    advFilterStacks[stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT)] = new ItemStack(stackTag);
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
    public static NonNullList<ItemStack> getAdvFilterBasicItems(ItemStack[] itemStacks, byte[] buttonStates){
        NonNullList<ItemStack> returnArray = NonNullList.create();
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
    public static NonNullList<ItemStack> getAdvFilterFuzzyItems(ItemStack[] itemStacks, byte[] buttonStates){
        NonNullList<ItemStack> returnArray = NonNullList.create();
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
    public static NonNullList<ItemStack> getAdvFilterModSpecificItems(ItemStack[] itemStacks, byte[] buttonStates){
        NonNullList<ItemStack> returnArray = NonNullList.create();
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
    public static NonNullList<ItemStack> getAdvFilterOreDictItems(ItemStack[] itemStacks, byte[] buttonStates){
        NonNullList<ItemStack> returnArray = NonNullList.create();
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
    public static NonNullList<ItemStack> getAdvFilterVoidItems(ItemStack[] itemStacks, byte[] buttonStates){
        NonNullList<ItemStack> returnArray = NonNullList.create();
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
    public static NonNullList<ItemStack> getRestockingItems(ItemStack stack){
    	NonNullList<ItemStack> returnArray = NonNullList.create();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.RESTOCKING)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.RESTOCKING, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(new ItemStack(stackTag));
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
    public static NonNullList<ItemStack> getCrafterItems(ItemStack stack){
        NonNullList<ItemStack> returnArray = NonNullList.create();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CRAFTING)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CRAFTING, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(new ItemStack(stackTag));
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
    public static NonNullList<ItemStack> getCrafterSmallItems(ItemStack stack){
        NonNullList<ItemStack> returnArray = NonNullList.create();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CRAFTING_SMALL)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CRAFTING_SMALL, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(new ItemStack(stackTag));
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
    public static NonNullList<ItemStack> getCrafterTinyItems(ItemStack stack){
        NonNullList<ItemStack> returnArray = NonNullList.create();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.CRAFTING_TINY)) {
                NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.CRAFTING_TINY, Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(new ItemStack(stackTag));
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
    public static boolean transferFromBackpackToInventory(EntityPlayer player, ItemStack backpack, World world, BlockPos pos, EnumFacing side, boolean usePrecise){
        TileEntity targeted = world.getTileEntity(pos);
        if (targeted != null){
            IItemHandler inv = getTargetedInventory(targeted, side);
            if (inv != null) {
                return transferItemsToContainer(player, backpack, inv, usePrecise);
            }
        }
        return false;
    }

    /**
     * Gets the IItemHandler of the targeted tile entity. Thanks to mezz/forestry for this IItemHandler handling code.
     * @param tile - the tile entity to check
     * @return IItemHandler if it exists, null otherwise
     */
    private static IItemHandler getTargetedInventory(TileEntity tile, EnumFacing side){
        if (tile == null) {
            return null;
        }

        if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
            return tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
        }

        if (tile instanceof ISidedInventory) {
            return new SidedInvWrapper((ISidedInventory) tile, side);
        }

        if (tile instanceof IInventory) {
            return new InvWrapper((IInventory) tile);
        }

        return null;
    }

    /**
     * Transfers the items from the backpack to the inventory.
     * @param player - the player doing the action
     * @param backpack - the itemstack backpack
     * @param transferTo - the inventory to transfer to
     * @param usePrecise - to check if the items has to be in the inventory already or if it can be put in any empty slot
     * @return boolean if successful
     */
    private static boolean transferItemsToContainer(EntityPlayer player, ItemStack backpack, IItemHandler transferTo, boolean usePrecise){
        boolean returnValue = false;
        InventoryBackpack inventoryBackpack = new InventoryBackpack(player, backpack);
        if (transferTo.getSlots() > 0 && !(inventoryBackpack.isEmpty())){ //if have a valid inventory to transfer to and have items to transfer
            for (int i = 0; i < inventoryBackpack.getSizeInventory(); i++){
                ItemStack stackToMove = inventoryBackpack.getStackInSlot(i);
                if (stackToMove != null && stackToMove.getCount() > 0){
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
    private static ItemStack putInFirstValidSlot(IItemHandler transferTo, ItemStack stackToTransfer, boolean usePrecise){
        for (int i = 0; i < transferTo.getSlots(); i++) {
            if (stackToTransfer == null) return null; //short circuit to return quickly
            if (usePrecise) { //precise, have to check if the items is in the inventory already
                if (isStackInInventoryAlready(transferTo, stackToTransfer)) {
                    stackToTransfer = transferToInv(transferTo, stackToTransfer, i);
                }
            } else { //just check if the slot can accept the items
                stackToTransfer = transferToInv(transferTo, stackToTransfer, i);
            }
        }
        return stackToTransfer;
    }

    /**
     * Deals with the actual transferring of items.
     * @param transferTo - the IItemHandler to transfer to
     * @param stackToTransfer - the stack to transfer over
     * @param transferToSlotNumber - the slot number of the IItemHandler to transfer items to
     * @return whatever wasn't transferred
     */
    private static ItemStack transferToInv(IItemHandler transferTo, ItemStack stackToTransfer, int transferToSlotNumber) {
        if (transferTo instanceof ISidedInventory) {
            ISidedInventory sidedInventory = (ISidedInventory) transferTo;
            EnumFacing[] enumFacings = EnumFacing.values();
            for (int j = 0; j < enumFacings.length; j++) { //try all sides
                if (sidedInventory.canInsertItem(transferToSlotNumber, stackToTransfer, enumFacings[j])) {
                    ItemStack simulated = transferTo.insertItem(transferToSlotNumber, stackToTransfer, false);
                    sidedInventory.markDirty();
                    return (simulated == null) ? null : simulated.copy(); //important!
                }
            }
        } else {
            ItemStack originalTransferStack = stackToTransfer.copy();
            ItemStack simulated = transferTo.insertItem(transferToSlotNumber, stackToTransfer, true);
            if (!IronBackpacksHelper.areItemStacksTheSame(simulated, originalTransferStack)) { //simulate, if can insert and item stack changes, can insert somewhat
                transferTo.insertItem(transferToSlotNumber, stackToTransfer, false);
                return (simulated == null) ? null : simulated.copy();  //important!
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
    private static boolean isStackInInventoryAlready(IItemHandler transferTo, ItemStack stackToTransfer){
        for (int i = 0; i < transferTo.getSlots(); i++) {
            ItemStack tempStack = transferTo.getStackInSlot(i);
            if (tempStack != null && tempStack.getCount() > 0
                    && stackToTransfer.isItemEqual(tempStack)
                    && ItemStack.areItemStackTagsEqual(tempStack, stackToTransfer)) {
                return true;
            }
        }
        return false;
    }

}
