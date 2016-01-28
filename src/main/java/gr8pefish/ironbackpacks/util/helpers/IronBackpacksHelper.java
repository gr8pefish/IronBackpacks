package gr8pefish.ironbackpacks.util.helpers;


import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IUpgradableBackpack;
import gr8pefish.ironbackpacks.api.register.ItemUpgradeRegistry;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.entity.EntityBackpack;
import gr8pefish.ironbackpacks.entity.extendedProperties.PlayerBackpackDeathProperties;
import gr8pefish.ironbackpacks.entity.extendedProperties.PlayerBackpackProperties;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.client.ClientEquippedPackMessage;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.Logger;
import gr8pefish.ironbackpacks.util.NBTUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

/**
 * Houses helper methods used throughout the mod
 */
public class IronBackpacksHelper {


    //======================================================= Gets the player's relevant backpack ===============================================

    /**
     * Gets the backpack to open. Checks for the current backpack first, then checks for an equipped backpack, and finally checks the player's inventory.
     * @param player - the player with the backpack
     * @return - null if it can't be found, the itemstack otherwise
     */
    public static ItemStack getBackpack(EntityPlayer player) {
        ItemStack backpack;

        ItemStack currPack = PlayerBackpackProperties.getCurrentBackpack(player);
        if (currPack != null) {
            backpack = currPack;
        }else if(PlayerBackpackProperties.getEquippedBackpack(player)!= null){
            backpack = PlayerBackpackProperties.getEquippedBackpack(player);
        }else {
            backpack = getBackpackFromPlayersInventory(player);
        }

        if (!player.worldObj.isRemote && backpack != null)
            NBTUtils.setUUID(backpack);

        return backpack;
    }

    /**
     * Gets the backpack form the player's inventory. WARNING: won't get the equipped backpack.
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
            NBTUtils.setUUID(backpack);
        }

        return backpack;
    }


    //============================================ Methods relating to Upgrades Applied ======================================================

    /**
     * Get upgrades stored in the backpack's NBT data
//     * @param stack - the backpack to check
     * @return - an int[] of the upgrades applied (only contains what is applied, no empty values)
     */
    public static ArrayList<ItemStack> getUpgradesAppliedFromNBT(ItemStack backpack) {
        ArrayList<ItemStack> upgradesArrayList = new ArrayList<>();
        if (backpack != null) {
            NBTTagCompound nbtTagCompound = backpack.getTagCompound();
            if (nbtTagCompound != null) {
                if(nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.UPGRADES)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.UPGRADES, net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        ItemStack upgrade = ItemStack.loadItemStackFromNBT(stackTag);
                        if (upgrade != null)
                            upgradesArrayList.add(upgrade);
                    }
                }
            }
        }
        return upgradesArrayList;
    }

    /**
     * Get the number of upgrade points used.
     * @param upgrades - the upgrades to check
     * @return - integer value
     */
    public static int getUpgradePointsUsed(ArrayList<ItemStack> upgrades){
        int counter = 0;
        for (ItemStack stack : upgrades){
            counter += ItemUpgradeRegistry.getItemUpgrade(stack).getUpgradeCost(stack);
        }
        return counter;
    }

    /**
     * Returns the total possible upgrade points available.
     * @param stack - the itemstack to check
     * @return - integer value
     */
    public static int getTotalUpgradePointsFromNBT(ItemStack stack){
        IUpgradableBackpack backpack = (IUpgradableBackpack) stack.getItem();
        int upgradeCount = backpack.getUpgradePoints(stack); //from initialization via config
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

        ItemStack backpack = PlayerBackpackProperties.getEquippedBackpack(player);

        if (backpack != null) { //need to unequip backpack

            boolean hasEmptySlot = false;
            for (int i = 0; i < player.inventory.getSizeInventory() - 4; i++){ //don't care about armor slots
                if (player.inventory.getStackInSlot(i) == null) hasEmptySlot = true; //can only take it off if there is a place to put it
            }

            if (hasEmptySlot) {
                //take off backpack and put in player's inventory
                player.inventory.addItemStackToInventory(backpack);

                //update equipped backpack to null
                PlayerBackpackProperties.setEquippedBackpack(player, null);

                //update equipped backpack on client side, not ideal but it works
                NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(null), (EntityPlayerMP)player);

                //stop the render - kill entity
                killEntityBackpack(player);
            }

        }
        else if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IBackpack) { //need to equip backpack

            ItemStack backpackStack = player.getHeldItem();
            NBTUtils.setUUID(backpackStack);

            //equip backpack from the backpack the player is holding
            PlayerBackpackProperties.setEquippedBackpack(player, backpackStack);

            //update equipped backpack on client side, not ideal but it works
            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpackStack), (EntityPlayerMP)player); //works on SSP

            //delete the held item
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

            //start the render - spawn entity
            spawnEntityBackpack(backpackStack, player);
        }
    }

    //spawns a backpack as an entity so it can render on the player
    public static void spawnEntityBackpack(ItemStack backpack, EntityPlayer player){ //TODO: remove useless backpack param
        EntityBackpack entityBackpack = new EntityBackpack(player.worldObj, player);
        System.out.println("testing");
        entityBackpack.setPositionAndRotation(player.posX, player.posY, player.posZ-.5, player.rotationPitch, player.rotationYaw);
        player.worldObj.spawnEntityInWorld(entityBackpack);
        EntityBackpack.updatePlayersBackpack(player, entityBackpack);
    }

    //kills the backpack on the player
    public static void killEntityBackpack(EntityPlayer player){
        EntityBackpack.killBackpack(player);
//        if (EntityBackpack.backpacksSpawnedMap.containsKey(player) && EntityBackpack.backpacksSpawnedMap.get(player) != null)
//            EntityBackpack.backpacksSpawnedMap.get(player).setDead();
//        else
//            Logger.warn("Couldn't kill entity backpack");
    }


    //==================================================== Handles the backpack persisting through death ==============================================================

    /**
     * Saves the backpack to the player so that it isn't lost.
     * @param player - the player who died with the backpack
     */
    public static void saveBackpackOnDeath(EntityPlayer player) {
        ArrayList<ItemStack> backpacks = new ArrayList<>(); //the backpacks to save

        boolean shouldStorePack = false; //to store the pack in the inventory for a "normal death"
        boolean stored = false; //the act of storing it
        ItemStack packToStore = null;

        boolean gameruleKeepInv = player.worldObj.getGameRules().getBoolean("keepInventory");

        //deal with storing the equipped pack
        ItemStack equippedPack = PlayerBackpackProperties.getEquippedBackpack(player);
        if (equippedPack != null){
            if (gameruleKeepInv || UpgradeMethods.hasEternityUpgrade(getUpgradesAppliedFromNBT(equippedPack))) {
                ItemStack updatedEquippedPack = equippedPack;
                if (!gameruleKeepInv) {
                     updatedEquippedPack = removeEternityUpgrade(getUpgradesAppliedFromNBT(equippedPack), equippedPack); //remove upgrade
                }
                PlayerBackpackDeathProperties.setEquippedBackpack(player, updatedEquippedPack);
            } else {
                shouldStorePack = true;
                packToStore = equippedPack;
                PlayerBackpackDeathProperties.setEquippedBackpack(player, null); //removes backpack
            }
            IronBackpacksHelper.killEntityBackpack(player);
        }

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack tempStack = player.inventory.getStackInSlot(i);
            if (tempStack != null) {
                if (tempStack.getItem() instanceof IBackpack) {
                    if (gameruleKeepInv || UpgradeMethods.hasEternityUpgrade(getUpgradesAppliedFromNBT(tempStack))) {
                        ItemStack stackToAdd = tempStack;
                        if (!gameruleKeepInv) {
                            stackToAdd = removeEternityUpgrade(getUpgradesAppliedFromNBT(tempStack), tempStack); //removes upgrade
                        }
                        backpacks.add(stackToAdd);
                        player.inventory.setInventorySlotContents(i, null); //set to null so it doesn't drop
                    }
                }
            } else { //empty slot
                if (shouldStorePack && !stored) {
                    player.inventory.setInventorySlotContents(i, packToStore);
                    stored = true;
                }
            }
        }

        if (shouldStorePack && !stored){ //no open inventory slots (and has to be gameruleKeepInventory false)
            player.dropItem(packToStore, true, false); //drop it in world
        }
        PlayerBackpackDeathProperties.setEternityBackpacks(player, backpacks);

    }

    /**
     * Loads the backpack(s) that need to be 'respawned' in.
     * @param player - the player who lads the packs in
     */
    public static void loadBackpackOnDeath(EntityPlayer player) {
        //get equipped pack
        ItemStack equipped = PlayerBackpackDeathProperties.getEquippedBackpack(player);
        //respawn it
        if (equipped != null) {

            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(equipped), (EntityPlayerMP) player); //update client on correct pack
            PlayerBackpackProperties.setEquippedBackpack(player, equipped); //update server on correct pack

            if ((!EntityBackpack.containsPlayer(player)) && (!ConfigHandler.disableRendering)) {
                IronBackpacksHelper.spawnEntityBackpack(equipped, player);
            }
        }

        //get eternity packs and add them to inventory
        ArrayList<ItemStack> packs = PlayerBackpackDeathProperties.getEternityBackpacks(player);
        if (packs != null && !packs.isEmpty()) {
            for (ItemStack stack : packs) {
                player.inventory.addItemStackToInventory(stack);
            }
        }
    }

    /**
     * Helper method to remove the upgrade when the player dies so they have to craft it again to keep the functionality through their next death.
     * @param upgrades - the upgrades of the backpack
     * @param stack - the backpack to check
     * @return - the itemstack with the 'keepOnDeath' upgrade removed (if valid/applicable)
     */
    private static ItemStack removeEternityUpgrade(ArrayList<ItemStack> upgrades, ItemStack stack){
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            NBTTagList tagList = new NBTTagList();
            for (ItemStack upgrade : upgrades) {
                if (!(ItemUpgradeRegistry.getItemPackUpgrade(stack.getItemDamage()).equals(ItemRegistry.eternityUpgrade))) {
                    tagList.appendTag(upgrade.writeToNBT(new NBTTagCompound()));
                }
            }
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, tagList);
            return stack;
        }
        return null;
    }


    //===========================================================================Miscellaneous===========================================

    /**
     * Check if items are exactly the same.
     * @param itemStack1
     * @param itemStack2
     * @return
     */
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

}
