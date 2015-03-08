package main.ironbackpacks.container.slot;

import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class AdvancedNestingBackpackSlot extends NestingBackpackSlot{

    private IronBackpackType type;

    public AdvancedNestingBackpackSlot(IInventory iInventory, int index, int x, int y, IronBackpackType type) {
        super(iInventory, index, x, y, type);
        this.type = type;
    }

    @Override
    public boolean acceptsStack(ItemStack stack){
        return true;
    }
}
