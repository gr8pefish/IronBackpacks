package main.ironbackpacks.container.slot;

import main.ironbackpacks.items.backpacks.BackpackTypes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * A slot that extends BackpackSlot that can accept any item
 */
public class AdvancedNestingBackpackSlot extends NestingBackpackSlot{

    private BackpackTypes type; //The backpack type

    public AdvancedNestingBackpackSlot(IInventory iInventory, int index, int x, int y, BackpackTypes type) {
        super(iInventory, index, x, y, type);
        this.type = type;
    }

    @Override
    public boolean acceptsStack(ItemStack stack){
        return true;
    }
}
