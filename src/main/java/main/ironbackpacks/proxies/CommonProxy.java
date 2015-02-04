package main.ironbackpacks.proxies;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class CommonProxy {

    private static String key = ModInformation.ID+"PersistedData";

    public static void saveBackpackOnDeath(EntityPlayer player) {
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack tempStack = player.inventory.getStackInSlot(i);
            if (tempStack != null && UpgradeMethods.hasKeepOnDeathUpgrade(tempStack)) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                ItemStack stackToAdd = IronBackpacksHelper.removeKeepOnDeathUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(tempStack), tempStack); //removes upgrade
                stackToAdd.writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);

                player.inventory.setInventorySlotContents(i, null); //set to null so it doesn't drop
            }
        }

        NBTTagCompound rootPersistentCompound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        rootPersistentCompound.setTag(key, tagList);
        if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
            player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, rootPersistentCompound);

    }


    public static void loadBackpackOnDeath(EntityPlayer player) {
        NBTTagCompound rootPersistentCompound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        if (rootPersistentCompound != null && rootPersistentCompound.hasKey(key)){// && rootPersistentCompound.hasKey(key)){
            NBTTagList tagList = rootPersistentCompound.getTagList(key, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tagList.tagCount(); i++) {
                player.inventory.addItemStackToInventory(ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i)));
            }
            rootPersistentCompound.removeTag(key); //clears the data
        }
    }
}
