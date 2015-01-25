package main.ironbackpacks.proxies;

import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class CommonProxy {

    protected static UUID deadPlayerID = null;
    protected static ArrayList<ItemStack> keepOnDeathBackpacks = new ArrayList<ItemStack>();

    public static void saveBackpackOnDeath(EntityPlayer player) {

        deadPlayerID = player.getGameProfile().getId();
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack tempStack = player.inventory.getStackInSlot(i);
            if (tempStack != null && UpgradeMethods.hasKeepOnDeathUpgrade(tempStack)) {
                keepOnDeathBackpacks.add(tempStack);
                player.inventory.setInventorySlotContents(i, null); //set to null so it doesn't drop
            }
        }
    }


    public static void loadBackpackOnDeath(EntityPlayer player) {

        if (deadPlayerID != null && player.getGameProfile().getId().equals(deadPlayerID)) {
            for (ItemStack backpack : keepOnDeathBackpacks) {
                IronBackpacksHelper.removeKeepOnDeathUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack), backpack);
                player.inventory.addItemStackToInventory(backpack);
            }
            keepOnDeathBackpacks.clear();
            deadPlayerID = null;
        }

    }
}
