package main.ironbackpacks.util;


import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.network.ClientPackMessage;
import main.ironbackpacks.network.NetworkingHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
     * @param player - the player with the backpack
     * @return - null if it can't be found, the itemstack otherwise
     */
    public static ItemStack getBackpack(EntityPlayer player) {
        ItemStack backpack = null;

        ItemStack proxyPack = IronBackpacks.proxy.getCurrBackpack(player);
        if (proxyPack != null) {
            backpack = proxyPack;
        }else if(IronBackpacks.proxy.getEquippedBackpack(player)!= null){
            backpack = IronBackpacks.proxy.getEquippedBackpack(player); //TODO: testing
        }else {
            backpack = getBackpackFromPlayersInventory(player);
        }

        if (!player.worldObj.isRemote && backpack != null)
            NBTHelper.setUUID(backpack);

        return backpack;
    }

    /**
     * Gets the backpack form the player's inventory
     * @param player - the player with the backpack
     * @return - null if nothing can be found, the itemstack otherwise
     */
    public static ItemStack getBackpackFromPlayersInventory(EntityPlayer player){
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

    /**
     * Gets the player's backpack if they have one equipped.
     * @param player - the player to check
     * @return - null if nothing is found, the itemstack otherwise
     */
    public static ItemStack getEquippedBackpack(EntityPlayer player) { //TODO - remove?
        ItemStack backpack = null;

        ItemStack equippedPack = IronBackpacks.proxy.getEquippedBackpack(player);
        if (equippedPack != null) backpack = equippedPack;

//        if (!player.worldObj.isRemote && backpack != null)
//            NBTHelper.setUUID(backpack);

        return backpack;
    }


    //============================================ Methods relating to Upgrades Applied ======================================================

    /**
     * Get upgrades stored in the backpack's NBT data
     * @param stack - the backpack to check
     * @return - an int[] of the upgrades applied (only contains what is applied, no empty values)
     */
    public static int[] getUpgradesAppliedFromNBT(ItemStack stack) {
        ArrayList<Integer> upgradesArrayList = new ArrayList<Integer>();
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if(nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.UPGRADES)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.UPGRADES, Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int hasUpgrade = stackTag.getByte(IronBackpacksConstants.NBTKeys.UPGRADE);
                        if (hasUpgrade != 0){ //if has an upgrade
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
            ret[i++] = e.intValue();
        return ret;
    }

    /**
     * Gets the point value of upgrades used.
     * @param upgrades - the upgrades applied
     * @return - how many upgrade points have been applied
     */
    public static int getUpgradePointsUsed(int[] upgrades){
        int counter = 0;
        for (int upgrade : upgrades){
            counter += IronBackpacksConstants.Upgrades.UPGRADE_POINTS[upgrade];
        }
        return counter;
    }

    /**
     * Returns the total possible upgrade points available.
     * @param stack - the itemstack to check
     * @return - integer value
     */
    public static int getTotalUpgradePointsFromNBT(ItemStack stack){
        ItemBaseBackpack backpack = (ItemBaseBackpack) stack.getItem();
        int upgradeCount = backpack.getUpgradeSlots(); //from initialization via config
        int extraPoints = getAdditionalUpgradesUpgradeCount(stack);
        return (upgradeCount + extraPoints);
    }

    /**
     * Gets how many 'additional upgrade' points have been applied to the backpack.
     * @param stack - the backpack
     * @return - integer value
     */
    public static int getAdditionalUpgradesUpgradeCount(ItemStack stack){
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
     * @param stack - the backpack
     * @return - integer value
     */
    public static int getAdditionalUpgradesTimesApplied(ItemStack stack){
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

    //==============================================================================Equipping/Removing backpack========================================================

    public static void equipBackpackFromKeybinding(EntityPlayer player) {

        ItemStack backpack = IronBackpacks.proxy.getEquippedBackpack(player);
//        System.out.println("Client: "+player.worldObj.isRemote);

        if (backpack != null) { //need to unequip backpack

            boolean hasEmptySlot = false;
            for (int i = 0; i < player.inventory.getSizeInventory() - 4; i++){ //don't care about armor slots
                if (player.inventory.getStackInSlot(i) == null) hasEmptySlot = true; //can only take it off if there is a place to put it
            }

            if (hasEmptySlot) {
                //take off backpack and put in player's inventory
                player.inventory.addItemStackToInventory(backpack);

                //update equipped backpack to null
                IronBackpacks.proxy.updateEquippedBackpack(player, null);
//                NetworkingHandler.network.sendTo(new ClientPackMessage(null), (EntityPlayerMP)player);

                //stop the render - kill entity
                EntityBackpack.backpacksSpawnedMap.get(player).setDead();
            }

        }
        else if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBaseBackpack) { //need to equip backpack

            ItemStack backpackStack = player.getHeldItem();
            NBTHelper.setUUID(backpackStack);

            //equip backpack from the backpack the player is holding
            IronBackpacks.proxy.updateEquippedBackpack(player, backpackStack);

//            System.out.println("sending updated equipped pack");
//            NetworkingHandler.network.sendTo(new ClientPackMessage(backpackStack), (EntityPlayerMP)player); //works on SSP

            //delete the held item
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

            //start the render - spawn entity
            spawnEntityBackpack(backpackStack, player);
        }
    }

    public static void spawnEntityBackpack(ItemStack backpack, EntityPlayer player){
        EntityBackpack entityBackpack = new EntityBackpack(player.worldObj, player, ((ItemBaseBackpack)backpack.getItem()).getGuiId());
        entityBackpack.setPositionAndRotation(player.posX, player.posY, player.posZ-.5, player.rotationPitch, player.rotationYaw);
        player.worldObj.spawnEntityInWorld(entityBackpack);
        EntityBackpack.backpacksSpawnedMap.put(player, entityBackpack);
    }

}
