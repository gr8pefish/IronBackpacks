package main.ironbackpacks.container.slot;

import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BackpackSlot extends Slot {

    //very basic slot that all the other BackpackSlots will extend if they need additional functionality

    public BackpackSlot(IInventory iInventory, int index, int x, int y) {
        super(iInventory, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return acceptsStack(itemStack);
    }

    public boolean acceptsStack(ItemStack itemStack){
        return !(itemStack.getItem() instanceof ItemBaseBackpack);
    }
}
