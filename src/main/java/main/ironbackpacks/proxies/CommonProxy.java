package main.ironbackpacks.proxies;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

/**
 * Class for saving data common to the client and the server
 */
public class CommonProxy {

    public void init(){}

    public EntityPlayer getClientPlayer(){
        throw new RuntimeException("You idiot, you can't get the client player on the server side.");
    }

    //==================================================== Handles the backpack persisting through death ==============================================================
    private static String deathBackpack = ModInformation.ID+"PersistedPack";

    /**
     * Saves the backpack to the player so that it isn't lost.
     * @param player - the player who died with the backpack
     */
    public void saveBackpackOnDeath(EntityPlayer player) {
        NBTTagList tagList = new NBTTagList();
        boolean shouldStorePack = false; //to store the pack in the inventory for a "normal death"
        boolean stored = false; //the act of storing it
        ItemStack packToStore = null;

        boolean gameruleKeepInv = player.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory");

        ItemStack equippedPack = getEquippedBackpack(player);
        if (equippedPack != null && !gameruleKeepInv) {
            if (!UpgradeMethods.hasKeepOnDeathUpgrade(equippedPack)) {
                shouldStorePack = true;
                packToStore = equippedPack;
                updateEquippedBackpack(player, null); //removes backpack
            } else {
                ItemStack updatedEquippedPack = removeKeepOnDeathUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(equippedPack), equippedPack); //remove upgrade
                updateEquippedBackpack(player, updatedEquippedPack);
            }
        }


        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack tempStack = player.inventory.getStackInSlot(i);
            if (tempStack != null && !gameruleKeepInv) {
                if (UpgradeMethods.hasKeepOnDeathUpgrade(tempStack)) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    ItemStack stackToAdd = removeKeepOnDeathUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(tempStack), tempStack); //removes upgrade
                    stackToAdd.writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);

                    player.inventory.setInventorySlotContents(i, null); //set to null so it doesn't drop
                }
            } else { //empty slot
                if (shouldStorePack && !stored) {
                    player.inventory.setInventorySlotContents(i, packToStore);
                    stored = true;
                }
            }
        }

        if (shouldStorePack && !stored){ //no open inventory slots (and has to be gameruleKeepInventory false)
            player.func_146097_a(packToStore, true, false); //drop it in world
        }

        NBTTagCompound rootPersistentCompound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        rootPersistentCompound.setTag(deathBackpack, tagList);
        if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
            player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, rootPersistentCompound);

    }

    /**
     * Loads the backpack(s) that need to be 'respawned' in.
     * @param player - the player who lads the packs in
     */
    public void loadBackpackOnDeath(EntityPlayer player) {
        NBTTagCompound rootPersistentCompound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        if (rootPersistentCompound != null && rootPersistentCompound.hasKey(deathBackpack)){
            NBTTagList tagList = rootPersistentCompound.getTagList(deathBackpack, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tagList.tagCount(); i++) {
                player.inventory.addItemStackToInventory(ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i)));
            }
            rootPersistentCompound.removeTag(deathBackpack); //clears the data
        }
    }

    /**
     * Helper method to remove the upgrade when the player dies so they have to craft it again to keep the functionality through their next death.
     * @param upgrades - the upgrades of the backpack
     * @param stack - the backpack to check
     * @return - the itemstack with the 'keepOnDeath' upgrade removed (if valid/applicable)
     */
    private ItemStack removeKeepOnDeathUpgrade(int[] upgrades, ItemStack stack){
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            NBTTagList tagList = new NBTTagList();
            for (int upgrade: upgrades) {
                if (!(upgrade == IronBackpacksConstants.Upgrades.KEEP_ON_DEATH_UPGRADE_ID)) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.UPGRADE, (byte) upgrade);
                    tagList.appendTag(tagCompound);
                }
            }
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, tagList);
            return stack;
        }
        return null;
    }


    //============================================================ The current backpack to open ================================================================
    private static String currBackpack = ModInformation.ID+"CurrentPack";

    /**
     * Updates the data stored as the current backpack to the parameter 'stack' passed in.
     * @param player - the player to update
     * @param stack - the backpack to update to
     */
    public void updateCurrBackpack(EntityPlayer player, ItemStack stack){
        NBTTagCompound rootPersistentCompound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        if (stack != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.writeToNBT(tagCompound);

            rootPersistentCompound.setTag(currBackpack, tagCompound);
            if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
                player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, rootPersistentCompound);
        }else{
            rootPersistentCompound.removeTag(currBackpack);
            if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
                player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, rootPersistentCompound);
        }
    }

    /**
     * Returns the backpack stored with the player as the current backpack
     * @param player - the player to retrieve the data from
     * @return - null if not found, otherwise the backpack itemstack
     */
    public ItemStack getCurrBackpack(EntityPlayer player){
        NBTTagCompound rootPersistentCompound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        if (rootPersistentCompound != null && rootPersistentCompound.hasKey(currBackpack)){
            return ItemStack.loadItemStackFromNBT(rootPersistentCompound.getCompoundTag(currBackpack));
        }
        return null;
    }


    //==============================================================The equipped backpack=========================================================================
    private static String equippedBackpack = ModInformation.ID+"EquippedPack";

    /**
     * Updates the data stored as the equipped backpack.
     * @param player - the player with the backpack
     * @param stack - the backpack
     */
    public void updateEquippedBackpack(EntityPlayer player, ItemStack stack){
        NBTTagCompound rootPersistentCompound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        if (stack != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.writeToNBT(tagCompound);

            rootPersistentCompound.setTag(equippedBackpack, tagCompound);
            if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
                player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, rootPersistentCompound);
        }else{
            rootPersistentCompound.removeTag(equippedBackpack);
            if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
                player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, rootPersistentCompound);
        }
    }

    /**
     * Gets the backpack stored with the player as the equipped backpack.
     * @param player - the player to check
     * @return - null if not found, the itemstack otherwise
     */
    public ItemStack getEquippedBackpack(EntityPlayer player) {
        NBTTagCompound rootPersistentCompound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        if (rootPersistentCompound != null && rootPersistentCompound.hasKey(equippedBackpack)){
            return ItemStack.loadItemStackFromNBT(rootPersistentCompound.getCompoundTag(equippedBackpack));
        }
        return null;
    }
}
