package main.ironbackpacks.proxies;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.util.PlayerBackpackProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

/**
 * Class for saving data common to the client and the server
 */
public class CommonProxy {

    public void preInit(){
        //nothing to see here
    }

    public void init(){
        //nothing here either
    }

    public String getModVersion(){
        return null;
    }

    public String getRemoteUpdatedVersion(){
        return null;
    }

    public EntityPlayer getClientPlayer(){
        throw new RuntimeException("You idiot, you can't get the client player on the server side.");
    }

    //==================================================== Handles the backpack persisting through death ==============================================================

    //TODO: move to an IEEP?

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

        boolean gameruleKeepInv = player.worldObj.getGameRules().hasRule("keepInventory");

        ItemStack equippedPack = PlayerBackpackProperties.getEquippedBackpack(player);
        if (equippedPack != null && !gameruleKeepInv) {
            if (!UpgradeMethods.hasKeepOnDeathUpgrade(equippedPack)) {
                shouldStorePack = true;
                packToStore = equippedPack;
                PlayerBackpackProperties.setEquippedBackpack(player, null); //removes backpack
            } else {
                ItemStack updatedEquippedPack = removeKeepOnDeathUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(equippedPack), equippedPack); //remove upgrade
                PlayerBackpackProperties.setEquippedBackpack(player, updatedEquippedPack);
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
            player.dropItem(packToStore, true, false); //drop it in world
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
}
