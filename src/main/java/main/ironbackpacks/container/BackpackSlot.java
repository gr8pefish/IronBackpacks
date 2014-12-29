package main.ironbackpacks.container;

import main.ironbackpacks.items.ItemBaseBackpack;
import main.ironbackpacks.items.ItemBasicBackpack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BackpackSlot extends Slot {

    public BackpackSlot(IInventory iInventory, int index, int x, int y) {
        super(iInventory, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return !(itemStack.getItem() instanceof ItemBaseBackpack);
    }

    public static boolean acceptsStack(ItemStack itemStack){
        return !(itemStack.getItem() instanceof ItemBaseBackpack);
    }
}
