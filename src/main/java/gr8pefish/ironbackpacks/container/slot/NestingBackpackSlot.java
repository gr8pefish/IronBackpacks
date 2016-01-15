package gr8pefish.ironbackpacks.container.slot;

import gr8pefish.ironbackpacks.items.backpacks.ItemBackpackSubItems;
import gr8pefish.ironbackpacks.items.backpacks.BackpackTypes;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * A backpack slot that allows other backpacks if they are of a previous tier
 */
public class NestingBackpackSlot extends BackpackSlot {

    private BackpackTypes type;

    public NestingBackpackSlot(IInventory iInventory, int index, int x, int y, BackpackTypes type) {
        super(iInventory, index, x, y);
        this.type = type;
    }

    @Override
    public boolean acceptsStack(ItemStack stack){
        if (stack.getItem() instanceof IBackpack){
            ItemBackpackSubItems baseBackpack = (ItemBackpackSubItems) stack.getItem();
            return (type.getId() > baseBackpack.getId()); //if current backpack is a higher tier than the other backpack, you can nest them
        }
        return true;
    }
}
