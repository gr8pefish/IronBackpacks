package gr8pefish.ironbackpacks.container.slot;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * A backpack slot that allows other backpacks if they are of a previous tier
 */
public class NestingBackpackSlot extends BackpackSlot {

    private ItemStack mainBackpack;

    public NestingBackpackSlot(IInventory iInventory, int index, int x, int y, ItemStack backpackStack) {
        super(iInventory, index, x, y);
        this.mainBackpack = backpackStack;
    }

    @Override
    public boolean acceptsStack(ItemStack toCheck){
        if (toCheck.getItem() instanceof IBackpack){
            ItemBackpack mainBackpackItem = (ItemBackpack) mainBackpack.getItem();
            return (!mainBackpackItem.getBackpacksAbove(mainBackpack).contains(toCheck)); //if current backpack's higher tier backpacks doesn't contain the itemstack in question, you can nest them
        }
        return true;
    }
}
