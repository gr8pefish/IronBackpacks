package main.ironbackpacks.proxies;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
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
                tempStack.writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);

                player.inventory.setInventorySlotContents(i, null); //set to null so it doesn't drop
            }
        }

        NBTTagCompound compound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        compound.setTag(key, tagList);

        System.out.println("saved");

    }


    public static void loadBackpackOnDeath(EntityPlayer player) {
        System.out.println("loading");

        NBTTagCompound rootPersistentCompound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        System.out.println(rootPersistentCompound.hasKey(key)); //print statement
        if (rootPersistentCompound != null && rootPersistentCompound.hasKey(key)){
            System.out.println("has key");
            NBTTagList tagList = rootPersistentCompound.getTagList(key, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tagList.tagCount(); i++) {
                System.out.println("I: "+i);
                player.inventory.addItemStackToInventory(ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i)));
            }
        }


    }
}
