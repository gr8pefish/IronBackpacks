package main.ironbackpacks.util;


import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.proxies.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

/**
 * Houses helper methods used throughout the mod
 */
public class IronBackpacksHelper {


    //======================================================= Gets the player's relevant backpack ===============================================

    /**
     * Gets the backpack to open. Checks for a backpack stored in the proxy first, then checks for an equipped backpack, and finally checks the player's inventory.
     *
     * @param player - the player with the backpack
     * @return - null if it can't be found, the itemstack otherwise
     */
    public static ItemStack getBackpack(EntityPlayer player) {
        ItemStack backpack = CommonProxy.getCurrBackpack(player) != null ? CommonProxy.getCurrBackpack(player) : getBackpackFromPlayersInventory(player);

        if (!player.worldObj.isRemote && backpack != null)
            NBTHelper.setUUID(backpack);

        return backpack;
    }

    /**
     * Gets the backpack form the player's inventory
     *
     * @param player - the player with the backpack
     * @return - null if nothing can be found, the itemstack otherwise
     */
    public static ItemStack getBackpackFromPlayersInventory(EntityPlayer player) {
        ItemStack backpack = null;
        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBaseBackpack) {
            backpack = player.getHeldItem();
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);

                if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBaseBackpack) {
                    backpack = player.inventory.getStackInSlot(i);
                }
            }
        }
        if (!player.worldObj.isRemote && backpack != null) {
            NBTHelper.setUUID(backpack);
        }

        return backpack;
    }


    //============================================ Methods relating to Upgrades Applied ======================================================

    /**
     * Get upgrades stored in the backpack's NBT data
     *
     * @param stack - the backpack to check
     * @return - an int[] of the upgrades applied (only contains what is applied, no empty values)
     */
    public static int[] getUpgradesAppliedFromNBT(ItemStack stack) {
        ArrayList<Integer> upgradesArrayList = new ArrayList<Integer>();
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.UPGRADES)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.UPGRADES, Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int hasUpgrade = stackTag.getByte(IronBackpacksConstants.NBTKeys.UPGRADE);
                        if (hasUpgrade != 0) { //if has an upgrade
                            upgradesArrayList.add(hasUpgrade);
                        }
                    }
                }
            }
        }
        //converts ArrayList to int[]
        int[] ret = new int[upgradesArrayList.size()];
        int i = 0;
        for (Integer e : upgradesArrayList)
            ret[i++] = e;
        return ret;
    }

    /**
     * Gets the point value of upgrades used.
     *
     * @param upgrades - the upgrades applied
     * @return - how many upgrade points have been applied
     */
    public static int getUpgradePointsUsed(int[] upgrades) {
        int counter = 0;
        for (int upgrade : upgrades) {
            counter += IronBackpacksConstants.Upgrades.UPGRADE_POINTS[upgrade];
        }
        return counter;
    }

    /**
     * Returns the total possible upgrade points available.
     *
     * @param stack - the itemstack to check
     * @return - integer value
     */
    public static int getTotalUpgradePointsFromNBT(ItemStack stack) {
        ItemBaseBackpack backpack = (ItemBaseBackpack) stack.getItem();
        int upgradeCount = backpack.getUpgradeSlots(); //from initialization via config
        int extraPoints = getAdditionalUpgradesUpgradeCount(stack);
        return (upgradeCount + extraPoints);
    }

    /**
     * Gets how many 'additional upgrade' points have been applied to the backpack.
     *
     * @param stack - the backpack
     * @return - integer value
     */
    public static int getAdditionalUpgradesUpgradeCount(ItemStack stack) {
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS)) {
                    return nbtTagCompound.getIntArray(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS)[0];  //[pointsAdded, upgradesApplied]
                }
            }
        }
        return 0;
    }

    /**
     * Gets how many 'additional upgrade' upgrades have been applied to the backpack.
     *
     * @param stack - the backpack
     * @return - integer value
     */
    public static int getAdditionalUpgradesTimesApplied(ItemStack stack) {
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS)) {
                    return nbtTagCompound.getIntArray(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS)[1];  //[pointsAdded, upgradesApplied]
                }
            }
        }
        return 0;
    }


}
