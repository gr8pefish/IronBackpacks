package gr8pefish.ironbackpacks.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * A slot that extends BackpackSlot that can accept any items. Currently unmodified from any general slot, but keeping it in case I decide something can't be nested.
 */
public class AdvancedNestingBackpackSlot extends NestingBackpackSlot{

    public AdvancedNestingBackpackSlot(IInventory iInventory, int index, int x, int y, ItemStack backpackStack) {
        super(iInventory, index, x, y, backpackStack);
    }

    @Override
    public boolean acceptsStack(ItemStack stack){
        return true;
    }
}
