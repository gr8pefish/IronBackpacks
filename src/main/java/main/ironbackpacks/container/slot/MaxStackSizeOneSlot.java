package main.ironbackpacks.container.slot;

import net.minecraft.inventory.IInventory;

public class MaxStackSizeOneSlot extends BackpackSlot{

    //stack size of 1 is all that is allowed

    public MaxStackSizeOneSlot(IInventory iInventory, int index, int x, int y) {
        super(iInventory, index, x, y);
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
}
