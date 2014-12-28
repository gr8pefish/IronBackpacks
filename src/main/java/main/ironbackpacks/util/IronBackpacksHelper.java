package main.ironbackpacks.util;


import main.ironbackpacks.items.ItemBaseBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class IronBackpacksHelper {

    public static ItemStack getBackpack(EntityPlayer player) {
        ItemStack backpack = null;

        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBaseBackpack) { //TODO - check for any child of base backpack
            backpack = player.getHeldItem();
        }

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);

            if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBaseBackpack) {
                backpack = player.inventory.getStackInSlot(i);
            }
        }

//        if (!player.worldObj.isRemote && iBench != null) {
//            NBTHelper.setUUID(iBench); //TODO - set UUID?
//        }

        return backpack;
    }
}
