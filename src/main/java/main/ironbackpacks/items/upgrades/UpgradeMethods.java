package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public class UpgradeMethods {

    //===============================hasUpgradeX Methods=====================================

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

    //note - includes renaming upgrade
    public static int getAlternateGuiUpgradesCount(int[] upgrades){
        int numberOfUpgrades = 0;
        if (!ConfigHandler.renamingUpgradeRequired) {
            numberOfUpgrades = 1;
            for (int upgrade: upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                    numberOfUpgrades++;
                }
            }
        }else{
            for (int upgrade: upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                    numberOfUpgrades++;
                }
            }
        }
        return numberOfUpgrades;
    }

    public static int getAlternateGuiUpgradeSlots(int[] upgrades) {
        int slots = 0;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                    || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID
                    || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                slots += 9; //hardcoded
            }
        }
        return slots;
    }

    public static int getAltGuiUpgradesUsed(int[] upgrades){
        int counter = 0;
        if (!ConfigHandler.renamingUpgradeRequired) {
            for (int upgrade : upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                    counter++;
                }
            }
        }else{
            for (int upgrade : upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public static int getFilterUpgradesUsed(int[] upgrades){
        int counter = 0;
        for (int upgrade : upgrades){
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                    || upgrade == IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID){
                counter++;
            }
        }
        return counter;
    }

    public static ArrayList<ItemStack> getBasicFilterItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey("FilterBasic")) {
                NBTTagList tagList = nbtTagCompound.getTagList("FilterBasic", Constants.NBT.TAG_COMPOUND);
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
            if (nbtTagCompound.hasKey("FilterFuzzy")) {
                NBTTagList tagList = nbtTagCompound.getTagList("FilterFuzzy", Constants.NBT.TAG_COMPOUND);
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
            if (nbtTagCompound.hasKey("FilterOreDict")) {
                NBTTagList tagList = nbtTagCompound.getTagList("FilterOreDict", Constants.NBT.TAG_COMPOUND);
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
            if (nbtTagCompound.hasKey("FilterModSpecific")) {
                NBTTagList tagList = nbtTagCompound.getTagList("FilterModSpecific", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static ArrayList<ItemStack> getHopperItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey("Hopper")) {
                NBTTagList tagList = nbtTagCompound.getTagList("Hopper", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }

    public static ArrayList<ItemStack> getCondenserItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey("Condenser")) {
                NBTTagList tagList = nbtTagCompound.getTagList("Condenser", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    returnArray.add(ItemStack.loadItemStackFromNBT(stackTag));
                }
            }
        }
        return returnArray;
    }


    //======================================================================Transfer From Backpack To Inventory========================================================================

    public static boolean transferFromBackpackToInventory(EntityPlayer player, ItemStack backpack, World world, int x, int y, int z){
        TileEntity targeted = world.getTileEntity(x, y, z);
        if (targeted != null){
            IInventory inv = getTargetedInventory(targeted);
            if (inv != null) {
                return transferItemsToContainer(player, backpack, inv);
            }
        }
        return false;
    }

    private static IInventory getTargetedInventory(TileEntity tile){
        if (tile == null || !(tile instanceof IInventory)) {
            return null;
        }
        return (IInventory) tile;
    }


    private static boolean transferItemsToContainer(EntityPlayer player, ItemStack backpack, IInventory transferTo){
        boolean returnValue = false;
        IronBackpackType type = IronBackpackType.values()[((ItemBaseBackpack) backpack.getItem()).getGuiId()];
        InventoryBackpack inventoryBackpack = new InventoryBackpack(player, backpack, type);
        if (transferTo.getSizeInventory() > 0 && !(inventoryBackpack.isEmpty())){ //if have a valid inventory to transfer to and have items to transfer
            for (int i = 0; i < inventoryBackpack.getSizeInventory(); i++){
                ItemStack stackToMove = inventoryBackpack.getStackInSlot(i);
                if (stackToMove != null && stackToMove.stackSize > 0){
                    ItemStack remainder = putInFirstValidSlot(transferTo, stackToMove);
                    inventoryBackpack.setInventorySlotContents(i, remainder);
                    inventoryBackpack.onGuiSaved(player);
                    returnValue = true;
                }
            }
        }
        return returnValue;
    }

    private static ItemStack putInFirstValidSlot(IInventory transferTo, ItemStack stackToTransfer){
        for (int i = 0; i < transferTo.getSizeInventory(); i++){
            ItemStack tempStack = transferTo.getStackInSlot(i);
            if (tempStack == null){
                if (transferTo.isItemValidForSlot(i, stackToTransfer)){
                    transferTo.setInventorySlotContents(i, stackToTransfer);
                    transferTo.markDirty();
                    return null;
                }
            }else if (tempStack.stackSize <= 0){//leave it alone
            }else{ //stack present, check if merge possible
                if (tempStack.isItemEqual(stackToTransfer) && tempStack.stackSize < tempStack.getMaxStackSize()){ //can merge
                    int amountToResupply = tempStack.getMaxStackSize() - tempStack.stackSize;
                    if (stackToTransfer.stackSize >= amountToResupply) { //stackToTransfer will leave a remainder if merged
                        //merge what you can and set stackToTransfer to the remainder
                        transferTo.setInventorySlotContents(i, new ItemStack(tempStack.getItem(), tempStack.getMaxStackSize(), tempStack.getItemDamage()));
                        transferTo.markDirty();
                        int oldStackSize = stackToTransfer.stackSize;
                        stackToTransfer.stackSize = oldStackSize - amountToResupply;
                        //don't return, try to use up the stack completely by continuing to iterate
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

}
