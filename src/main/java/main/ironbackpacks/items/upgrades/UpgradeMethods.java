package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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

    public static boolean hasFilterUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int upgrade: upgrades) {
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_UPGRADE_ID) {
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

    public static boolean hasKeepOnDeathUpgrade(ItemStack stack){
        int[] upgrades = IronBackpacksHelper.getUpgradesFromNBT(stack);
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

    //note - doesn't include renaming upgrade
    public static int getAlternateGuiUpgradesCount(int[] upgrades){
        int numberOfUpgrades = 0;
        if (!ConfigHandler.renamingUpgradeRequired) {
            numberOfUpgrades = 1;
            for (int upgrade: upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.FILTER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                        || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                    numberOfUpgrades++;
                }
            }
        }else{
            for (int upgrade: upgrades) {
                if (upgrade == IronBackpacksConstants.Upgrades.FILTER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
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
            if (upgrade == IronBackpacksConstants.Upgrades.FILTER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID
                    || upgrade == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                slots += 9; //hardcoded
            }
        }
        return slots;
    }

    public static ArrayList<ItemStack> getFilterItems(ItemStack stack){
        ArrayList<ItemStack> returnArray = new ArrayList<ItemStack>();
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey("Filter")) {
                NBTTagList tagList = nbtTagCompound.getTagList("Filter", Constants.NBT.TAG_COMPOUND);
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


}
