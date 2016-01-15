package gr8pefish.ironbackpacks.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * A slot that extends BackpackSlot that can accept any item
 */
public class AdvancedNestingBackpackSlot extends NestingBackpackSlot{

    private ItemStack backpackStack; //The backpack type

    public AdvancedNestingBackpackSlot(IInventory iInventory, int index, int x, int y, ItemStack backpackStack) {
        super(iInventory, index, x, y, backpackStack);
        this.backpackStack = backpackStack;
    }

    @Override
    public boolean acceptsStack(ItemStack stack){
        return true;
    }
}
