package main.ironbackpacks.container.slot;

import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GhostSlot extends BackpackSlot{ //TODO - currently just filler code

    public GhostSlot(IInventory iInventory, int index, int x, int y) {
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
