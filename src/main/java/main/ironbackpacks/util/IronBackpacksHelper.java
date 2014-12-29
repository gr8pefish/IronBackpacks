package main.ironbackpacks.util;


import main.ironbackpacks.items.ItemBaseBackpack;
import main.ironbackpacks.items.ItemBasicBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class IronBackpacksHelper {

    public static ItemStack getBackpack(EntityPlayer player) { //TODO - shouldn't this only return held backpack?
        ItemStack backpack = null;

        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBasicBackpack) { //TODO - check for any child of base backpack
            backpack = player.getHeldItem();
        }

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);

            if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBasicBackpack) {
                backpack = player.inventory.getStackInSlot(i);
            }
        }

        if (!player.worldObj.isRemote && backpack != null) {
            NBTHelper.setUUID(backpack); //TODO - set UUID?
        }

        return backpack;
    }
}
