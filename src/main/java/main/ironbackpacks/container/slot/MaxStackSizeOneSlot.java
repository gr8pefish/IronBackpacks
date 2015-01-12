package main.ironbackpacks.container.slot;

import net.minecraft.inventory.IInventory;

public class MaxStackSizeOneSlot extends BackpackSlot{

    public MaxStackSizeOneSlot(IInventory iInventory, int index, int x, int y) {
        super(iInventory, index, x, y);
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
}
