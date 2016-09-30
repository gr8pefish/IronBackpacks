package main.ironbackpacks.util;


import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.items.backpacks.IBackpack;
import main.ironbackpacks.items.backpacks.ItemBackpack;
import main.ironbackpacks.network.ClientPackMessage;
import main.ironbackpacks.network.NetworkingHandler;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
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
        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IBackpack) {
            backpack = player.getHeldItem();
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);

                if (stack != null && stack.getItem() != null && stack.getItem() instanceof IBackpack) {
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
        ItemBackpack backpack = (ItemBackpack) stack.getItem();
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

                //update equipped backpack on client side, not ideal but it works
                NetworkingHandler.network.sendTo(new ClientPackMessage(null), (EntityPlayerMP)player);

                //stop the render - kill entity
                EntityBackpack.backpacksSpawnedMap.get(player).setDead();
            }

        }
        else if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IBackpack) { //need to equip backpack

            ItemStack backpackStack = player.getHeldItem();
            NBTHelper.setUUID(backpackStack);

            //equip backpack from the backpack the player is holding
            IronBackpacks.proxy.updateEquippedBackpack(player, backpackStack);

            //update equipped backpack on client side, not ideal but it works
            NetworkingHandler.network.sendTo(new ClientPackMessage(backpackStack), (EntityPlayerMP)player); //works on SSP

            //delete the held item
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

            //start the render - spawn entity
            spawnEntityBackpack(backpackStack, player);
        }
    }

    //spawns a backpack as an entity so it can render on the player
    public static void spawnEntityBackpack(ItemStack backpack, EntityPlayer player){
        EntityBackpack entityBackpack = new EntityBackpack(player.worldObj, player, ((ItemBackpack)backpack.getItem()).getGuiId());
        entityBackpack.setPositionAndRotation(player.posX, player.posY, player.posZ-.5, player.rotationPitch, player.rotationYaw);
        player.worldObj.spawnEntityInWorld(entityBackpack);
        EntityBackpack.backpacksSpawnedMap.put(player, entityBackpack);
    }


    //===========================================================================Miscellaneous===========================================

    public static boolean areItemStacksTheSame(ItemStack itemStack1, ItemStack itemStack2){
        return (ItemStack.areItemStacksEqual(itemStack1, itemStack2) && ItemStack.areItemStackTagsEqual(itemStack1, itemStack2));
    }

    /**
     * Note - Doesn't check the stack size or isStackable of either, that must be done elsewhere
     * This just checks if the items are equal enough to be stacked
     * @param itemStack1
     * @param itemStack2
     * @return - boolean true if they are equal enough, false if they are not
     */
    public static boolean areItemsEqualForStacking(ItemStack itemStack1, ItemStack itemStack2){
        return (itemStack1.getItem().equals(itemStack2.getItem())) && (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2) && (itemStack1.getItemDamage() == itemStack2.getItemDamage()));
    }

    /**
     * Note - only checks the first itemStack for stackability, the other is assumed to be a filler amount and isn;t checked
     * @param itemStack1
     * @param itemStack2
     * @return
     */
    public static boolean areItemsEqualAndStackable(ItemStack itemStack1, ItemStack itemStack2){
        return (itemStack1.isStackable() && itemStack1.stackSize < itemStack1.getMaxStackSize() && areItemsEqualForStacking(itemStack1, itemStack2));
    }

    public static ItemStack getFirstBackpackInInventory(EntityPlayer player) {
        ItemStack returnStack = null;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBackpack ) {
                returnStack = stack;
                break;
            }
        }
        return returnStack;
    }
}
