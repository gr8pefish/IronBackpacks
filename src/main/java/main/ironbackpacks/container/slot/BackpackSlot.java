package main.ironbackpacks.container.slot;

import main.ironbackpacks.api.item.backpacks.IBackpack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Very basic slot that all the other BackpackSlots will extend if they need additional functionality
 */
public class BackpackSlot extends Slot {

    public BackpackSlot(IInventory iInventory, int index, int x, int y) {
        super(iInventory, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return acceptsStack(itemStack);
    }

    //Can't accept other backpacks by default
    public boolean acceptsStack(ItemStack itemStack){
        return !(itemStack.getItem() instanceof IBackpack);
    }

}
