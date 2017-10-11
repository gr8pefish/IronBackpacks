package gr8pefish.ironbackpacks.util.helpers;


import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IUpgradableBackpack;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.capabilities.player.PlayerDeathBackpackCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.client.ClientEquippedPackMessage;
import gr8pefish.ironbackpacks.network.client.ClientEquippedPackPlayerSensitiveMessage;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.NBTUtils;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraftforge.oredict.OreDictionary;

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
        ItemStack backpack = ItemStack.EMPTY;

        ItemStack currPack = PlayerWearingBackpackCapabilities.getCurrentBackpack(player);
        if (!currPack.isEmpty()) {
            backpack = currPack;
        }else if(!PlayerWearingBackpackCapabilities.getEquippedBackpack(player).isEmpty()){
            backpack = PlayerWearingBackpackCapabilities.getEquippedBackpack(player);
        }else {
            backpack = getBackpackFromPlayersInventory(player);
        }

        if (!player.world.isRemote && !backpack.isEmpty())
            NBTUtils.setUUID(backpack);

        return backpack;
    }

    /**
     * Gets the backpack form the player's inventory. WARNING: won't get the equipped backpack.
     * @param player - the player with the backpack
     * @return - null if nothing can be found, the itemstack otherwise
     */
    public static ItemStack getBackpackFromPlayersInventory(EntityPlayer player){
        ItemStack backpack = ItemStack.EMPTY;
        if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof IBackpack) {
            backpack = player.getHeldItemMainhand();
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);

                if (stack != null && stack.getItem() != null && stack.getItem() instanceof IBackpack) {
                    backpack = player.inventory.getStackInSlot(i);
                }
            }
        }
        if (!player.world.isRemote && backpack != null) {
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
                        ItemStack upgrade = new ItemStack(stackTag);
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
            counter += ItemIUpgradeRegistry.getItemUpgrade(stack).getUpgradeCost(stack);
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

        ItemStack backpack = PlayerWearingBackpackCapabilities.getEquippedBackpack(player);

        if (backpack != null) { //need to unequip backpack

            //if hotbar isn't full
                //if currently selected slot is empty
                    //put backpack there
                //else
                    //put backpack in first available empty slot
            //else if hotbar is full
                //if offhand is empty
                    //put item in offhand, put backpack in mainhand
                //otherwise
                    //if have other empty slot in inventory
                        //move currently selected item to somewhere in inventory, put backpack in main hand

            boolean hasEmptyHotbarSlot = false;
            boolean hasEmptyOffhand = false;
            boolean hasEmptyInventorySlot = false;

            for (int i = 0; i < 9; i++) {
                if (player.inventory.getStackInSlot(i) == null) hasEmptyHotbarSlot = true;
            }

            if (hasEmptyHotbarSlot) {
                if (player.getHeldItemMainhand() == null) {
                    player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, backpack);
                } else {
                    //take off backpack and put in player's inventory
                    player.inventory.addItemStackToInventory(backpack);
                }
            } else {
                if (player.getHeldItemOffhand() == null) hasEmptyOffhand = true;

                if (hasEmptyOffhand) {
                    ItemStack selected = player.getHeldItem(EnumHand.MAIN_HAND);
                    //put item in offhand
                    player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, selected);
                    //put backpack in mainhand
                    player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, backpack);
                } else {

                    for (int i = 9; i < player.inventory.getSizeInventory() - 5; i++) { //don't care about armor slots or offhand
                        if (player.inventory.getStackInSlot(i) == null) hasEmptyInventorySlot = true;
                    }
                    if (hasEmptyInventorySlot) {
                        ItemStack selected = player.getHeldItem(EnumHand.MAIN_HAND).copy();
                        //put backpack in mainhand
                        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, backpack);
                        //put item in offhand
                        player.inventory.addItemStackToInventory(selected);
                    }
                }
            }

            if (hasEmptyHotbarSlot || hasEmptyOffhand || hasEmptyInventorySlot) { //backpack was unequipped, need to "save"

                //update equipped backpack to null
                PlayerWearingBackpackCapabilities.setEquippedBackpack(player, null);

                //update equipped backpack on client side, not ideal but it works
                NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(null), (EntityPlayerMP)player);

                //update backpacks for multiplayer
                EntityTracker tracker = ((EntityPlayerMP) player).world.getMinecraftServer().getWorld(player.dimension).getEntityTracker();
                tracker.sendToTracking(player, NetworkingHandler.network.getPacketFrom(new ClientEquippedPackPlayerSensitiveMessage(player.getEntityId(), null)));
            }

        }
        else if (!ConfigHandler.disableEquipping && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof IBackpack) { //need to equip backpack

            ItemStack backpackStack = player.getHeldItemMainhand();
            NBTUtils.setUUID(backpackStack);

            //equip backpack from the backpack the player is holding
            PlayerWearingBackpackCapabilities.setEquippedBackpack(player, backpackStack);

            //update equipped backpack on client side, not ideal but it works
            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpackStack), (EntityPlayerMP)player); //works on SSP

            //delete the held items
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

            //update backpacks for multiplayer
            EntityTracker tracker = ((EntityPlayerMP) player).world.getMinecraftServer().getWorld(player.dimension).getEntityTracker();
            tracker.sendToTracking(player, NetworkingHandler.network.getPacketFrom(new ClientEquippedPackPlayerSensitiveMessage(player.getEntityId(), backpackStack)));
        }
    }


    //==================================================== Handles the backpack persisting through death ==============================================================

    /**
     * Saves the equipped backpacks when the player dies. Does a bunch of logic, outlined below.
     *
     * Equipped backpacks:
     *  !Eternity
     *     !KeepInvTrue
     *          Add to drops
     *
     * @param player - the dying player
     * @return null if no drop, the backpack as an itemstack otherwise
     */
    public static EntityItem savePlayerDeathDrops(EntityPlayer player) {

        EntityItem entityItem = null; //the equipped backpack to drop

        boolean gameruleKeepInv = player.world.getGameRules().getBoolean("keepInventory");

        //ToDo: Try with gravestone mods and get it working
        //test with full inventories as well

        //deal with dropping the equipped pack
        ItemStack equippedPack = PlayerWearingBackpackCapabilities.getEquippedBackpack(player);
        if (equippedPack != null) {
            if (!UpgradeMethods.hasEternityUpgrade(getUpgradesAppliedFromNBT(equippedPack))) {
                if (!gameruleKeepInv) {
                    ItemStack deathEquipped = PlayerDeathBackpackCapabilities.getEquippedBackpack(player);
                    if (deathEquipped == null || (!deathEquipped.equals(equippedPack))) { //for when an eternity backpack recently lost its upgrade and then this fires right after
                        entityItem = new EntityItem(player.world, player.posX, player.posY, player.posZ, equippedPack); //works
                        PlayerDeathBackpackCapabilities.setEquippedBackpack(player, null); //removes backpack if it was present before
                    }
                }
            }
        }

        return entityItem;
    }


    /**
     * Saves the inventory eternity backpack(s) to the player so that they aren't lost.
     * Used because it fires before everything is dropped, which is necessary for iterating through inventory.
     *
     * Equipped backpacks:
     *  Eternity
     *      KeepInvTrue
     *          Save capEquipped
     *     !KeepInvTrue
     *          Remove Eternity upgrade
     *          Save capEquipped
     *  !Eternity
     *      KeepInvTrue
     *          Save capEquipped
     *
     * Normal backpacks:
     *  Eternity
     *      KeepInvTrue
     *          Nothing
     *     !KeepInvTrue
     *          Remove Eternity upgrade
     *          Save capDeath
     *  !Eternity
     *      KeepInvTrue
     *          Nothing
     *     !KeepInvTrue
     *          Nothing
     *
     * @param player - the player who died with the backpack
     */
    public static void saveEternityBackpacksOnDeath(EntityPlayer player) {

        ArrayList<ItemStack> backpacks = new ArrayList<>(); //the backpacks to save

        boolean gameruleKeepInv = player.world.getGameRules().getBoolean("keepInventory");

        //deal with equipped packs (if you keep them through death)
        ItemStack equippedPack = PlayerWearingBackpackCapabilities.getEquippedBackpack(player);
        if (equippedPack != null) {
            if (UpgradeMethods.hasEternityUpgrade(getUpgradesAppliedFromNBT(equippedPack))) {
                if (gameruleKeepInv) { //works
                    PlayerDeathBackpackCapabilities.setEquippedBackpack(player, equippedPack.copy());
                } else { //works
                    ItemStack updatedEquippedPack = removeEternityUpgrade(getUpgradesAppliedFromNBT(equippedPack), equippedPack); //remove upgrade
                    PlayerDeathBackpackCapabilities.setEquippedBackpack(player, updatedEquippedPack);
                }
            } else {
                if (gameruleKeepInv) { //works
                    PlayerDeathBackpackCapabilities.setEquippedBackpack(player, equippedPack.copy());
                }
            }
        }

        //deal with storing other packs (only care if they have the eternity upgrade)
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack tempStack = player.inventory.getStackInSlot(i);
            if (tempStack != null) {
                if (tempStack.getItem() instanceof IBackpack) {
                    if (UpgradeMethods.hasEternityUpgrade(getUpgradesAppliedFromNBT(tempStack))) {
                        if (!gameruleKeepInv) {
                            ItemStack stackToAdd = removeEternityUpgrade(getUpgradesAppliedFromNBT(tempStack), tempStack); //removes upgrade
                            backpacks.add(stackToAdd); //works
                            player.inventory.setInventorySlotContents(i, null); //set to null so it doesn't drop
                        }
                    }
                }
            }
        }

        PlayerDeathBackpackCapabilities.setEternityBackpacks(player, backpacks);

    }

    /**
     * Loads the backpack(s) that need to be 'respawned' in.
     * @param player - the player who lads the packs in
     */
    public static void loadBackpackOnDeath(EntityPlayer player) {
        //get equipped pack
        ItemStack equipped = PlayerDeathBackpackCapabilities.getEquippedBackpack(player);
        //respawn it
        if (equipped != null) {
            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(equipped), (EntityPlayerMP) player); //update client on correct pack
            PlayerWearingBackpackCapabilities.setEquippedBackpack(player, equipped); //update server on correct pack
        }

        //get eternity packs and add them to inventory
        ArrayList<ItemStack> packs = PlayerDeathBackpackCapabilities.getEternityBackpacks(player);
        if (packs != null && !packs.isEmpty()) {
            for (ItemStack stack : packs) {
                boolean added = player.inventory.addItemStackToInventory(stack);
                if (!added) { //if can't add to inventory
                    player.dropItem(stack, false); //just drop in world at that player's location
                }
            }
        }

        //after loading in, reset the death capabilities
        PlayerDeathBackpackCapabilities.reset(player);
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
                if (!(ItemIUpgradeRegistry.isInstanceOfIUpgrade(upgrade) && ItemIUpgradeRegistry.getItemIUpgrade(upgrade.getItemDamage()).equals(ItemRegistry.eternityUpgrade))) {
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
        return (itemStack1.getItem().equals(itemStack2.getItem())) && (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2) && (itemStack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || itemStack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || itemStack1.getItemDamage() == itemStack2.getItemDamage()));
    }

    /**
     * Note - only checks the first itemStack for stackability, the other is assumed to be a filler amount and isn;t checked
     * @param itemStack1
     * @param itemStack2
     * @return
     */
    public static boolean areItemsEqualAndStackable(ItemStack itemStack1, ItemStack itemStack2){
        return (itemStack1.isStackable() && itemStack1.getCount() < itemStack1.getMaxStackSize() && areItemsEqualForStacking(itemStack1, itemStack2));
    }

}
