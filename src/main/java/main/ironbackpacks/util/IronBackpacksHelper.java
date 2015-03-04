package main.ironbackpacks.util;


import main.ironbackpacks.client.gui.buttons.AdvancedFilterButtons;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;


public class IronBackpacksHelper {

    //Helper methods used throughout the mod

    //credit to sapient
    public static ItemStack getBackpack(EntityPlayer player) {
        ItemStack backpack = null;

        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBaseBackpack) {
            backpack = player.getHeldItem();
        }else {
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

    public static ArrayList<ArrayList<ItemStack>> getFilterCondenserAndHopperBackpacks(EntityPlayer player){
        ArrayList<ItemStack> filterBackpacks = new ArrayList<ItemStack>();
        ArrayList<ItemStack> condenserBackpacks = new ArrayList<ItemStack>();
        ArrayList<ItemStack> hopperBackpacks = new ArrayList<ItemStack>();
        ArrayList<ArrayList<ItemStack>> returnArray = new ArrayList<ArrayList<ItemStack>>();
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);

            if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBaseBackpack) {
                ItemStack backpack = player.inventory.getStackInSlot(i);
                int[] upgrades = getUpgradesAppliedFromNBT(backpack);
                if (UpgradeMethods.hasFilterBasicUpgrade(upgrades) || UpgradeMethods.hasFilterModSpecificUpgrade(upgrades) ||
                        UpgradeMethods.hasFilterFuzzyUpgrade(upgrades) || UpgradeMethods.hasFilterOreDictUpgrade(upgrades)){
                    filterBackpacks.add(backpack);
                }
                if (UpgradeMethods.hasCondenserUpgrade(upgrades)){
                    condenserBackpacks.add(backpack);
                }
                if (UpgradeMethods.hasHopperUpgrade(upgrades)){
                    hopperBackpacks.add(backpack);
                }
            }
        }
        returnArray.add(filterBackpacks);
        returnArray.add(condenserBackpacks);
        returnArray.add(hopperBackpacks);
        return returnArray;
    }

    public static int[] getUpgradesAppliedFromNBT(ItemStack stack) {
        ArrayList<Integer> upgradesArrayList = new ArrayList<Integer>();
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if(nbtTagCompound.hasKey("Upgrades")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Upgrades", Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int hasUpgrade = stackTag.getByte("Upgrade");
                        if (hasUpgrade != 0){ //true
                            upgradesArrayList.add(hasUpgrade);
                        }
                    }
                }
            }
        }
        return toIntArray(upgradesArrayList);
    }

    private static int[] toIntArray(ArrayList<Integer> list)  {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e.intValue();
        return ret;
    }

    public static int getUpgradePointsUsed(int[] upgrades){
        int counter = 0;
        for (int upgrade : upgrades){
            counter += IronBackpacksConstants.Upgrades.UPGRADE_POINTS[upgrade];
        }
        return counter;
    }

    public static int getTotalUpgradePointsFromNBT(ItemStack stack){
        ItemBaseBackpack backpack = (ItemBaseBackpack) stack.getItem();
        int upgradeCount = backpack.getUpgradeSlots(); //from initialization via config
        int extraPoints = getAdditionalUpgradesUpgradeCount(stack);
        return (upgradeCount + extraPoints);
    }

    public static int getAdditionalUpgradesUpgradeCount(ItemStack stack){
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey("AdditionalPoints")) {
                    return nbtTagCompound.getIntArray("AdditionalPoints")[0];  //[pointsAdded, upgradesApplied]
                }
            }
        }
        return 0;
    }

    public static int getAdditionalUpgradesTimesApplied(ItemStack stack){
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey("AdditionalPoints")) {
                    return nbtTagCompound.getIntArray("AdditionalPoints")[1];  //[pointsAdded, upgradesApplied]
                }
            }
        }
        return 0;
    }

    public static ItemStack removeKeepOnDeathUpgrade(int[] upgrades, ItemStack stack){
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            NBTTagList tagList = new NBTTagList();
            for (int upgrade: upgrades) {
                if (!(upgrade == IronBackpacksConstants.Upgrades.KEEP_ON_DEATH_UPGRADE_ID)) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Upgrade", (byte) upgrade);
                    tagList.appendTag(tagCompound);
                }
            }
            nbtTagCompound.setTag("Upgrades", tagList);
            return stack;
        }
        return null;
    }

    //return the single int created form combining the two inputs
    public static int setOneNumberFromTwo(int numberOne, int numberTwo){
        System.out.println(numberOne + ":"+numberTwo);
        if (numberOne == 1 && numberTwo == 1) return 11;

        String stringOne;
        if (numberOne < 10)
            stringOne = String.format("%02d", numberOne);
        else
            stringOne = String.valueOf(numberOne);
        String stringTwo;
        if (numberTwo < 10)
            stringTwo = String.format("%02d", numberTwo);
        else
            stringTwo = String.valueOf(numberTwo);
        return Integer.valueOf(stringOne+stringTwo);
    }

    //returns two numbers from the input one number
    public static ArrayList<Integer> getNumbersFromOneNumber(int data){

        ArrayList<Integer> returnList = new ArrayList<Integer>();

        if (data == 11){ //special cases
            returnList.add(0);
            returnList.add(1);
            return returnList;
        }else if (data == 101){
            returnList.add(10);
            returnList.add(1);
            return returnList;
        }else if (data == 110){
            returnList.add(1);
            returnList.add(10);
            return returnList;
        }else{
            String allNumbers = String.valueOf(data);
            if (allNumbers.length() == 4){
                returnList.add(Integer.parseInt(Integer.toString(data / 100)) - 1);
                returnList.add(Integer.parseInt(Integer.toString(data % 100)));
                return returnList;
            }else{ //3 numbers
                returnList.add(Integer.parseInt(allNumbers.substring(0, 1)) - 1);
                returnList.add(Integer.parseInt(allNumbers.substring(1, 3)));
                return returnList;
            }
        }

    }

    public static int getAdvFilterTypeFromNBT(int index, ItemStack stack){
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey("AdvFilterTypes")) {
//                    System.out.println("SAVED NBT: "+index+", "+nbtTagCompound.getIntArray("AdvFilterTypes")[index]);
                    return nbtTagCompound.getIntArray("AdvFilterTypes")[index];
                }
            }
        }
        System.out.println("ERROR retrieving advFilterTypeFromNBT");
        return AdvancedFilterButtons.EXACT;
    }



}
