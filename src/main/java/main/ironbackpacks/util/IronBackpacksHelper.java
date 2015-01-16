package main.ironbackpacks.util;


import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;


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
                int[] upgrades = getUpgradesFromNBT(backpack);
                if (UpgradeMethods.hasFilterUpgrade(upgrades)){
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

    public static int[] getUpgradesFromNBT(ItemStack stack) {
        int[] upgrades = new int[3]; //default [0,0,0]
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if(nbtTagCompound.hasKey("Upgrades")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Upgrades", Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int hasUpgrade = stackTag.getByte("Upgrade");
                        if (hasUpgrade != 0){ //true
                            upgrades[i] = hasUpgrade;
                        }
                    }
                }
            }
        }
        return upgrades;
    }

}
