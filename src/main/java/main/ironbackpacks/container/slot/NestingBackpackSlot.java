package main.ironbackpacks.container.slot;

import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * A backpack slot that allows other backpacks if they are of a previous tier
 */
public class NestingBackpackSlot extends BackpackSlot {

    private IronBackpackType type;

    public NestingBackpackSlot(IInventory iInventory, int index, int x, int y, IronBackpackType type) {
        super(iInventory, index, x, y);
        this.type = type;
    }

    @Override
    public boolean acceptsStack(ItemStack stack) {
        if (stack.getItem() instanceof ItemBaseBackpack) {
            ItemBaseBackpack baseBackpack = (ItemBaseBackpack) stack.getItem();
            return (type.getId() > baseBackpack.getTypeId()); //if current backpack is a higher tier than the other backpack, you can nest them
        }
        return true;
    }
}
