package main.ironbackpacks.util;


import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

//credit to sapient
public class IronBackpacksHelper {

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

    public static ItemStack getFilterBackpack(EntityPlayer player){
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);

            if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBaseBackpack) {
                ItemStack backpack = player.inventory.getStackInSlot(i);
                int[] upgrades = getUpgradesFromNBT(backpack);
                if (UpgradeMethods.hasFilterUpgrade(upgrades)){
                    return backpack;
                }
            }
        }
        return null;
    }

    public static int[] getUpgradesFromNBT(ItemStack stack) { //TODO - Make this go everywhere
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
