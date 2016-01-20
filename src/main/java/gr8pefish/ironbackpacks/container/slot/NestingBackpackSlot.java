package gr8pefish.ironbackpacks.container.slot;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.register.ItemBackpackRegistry;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.util.Logger;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
        if (toCheck.getItem() instanceof ITieredBackpack){
            List<ITieredBackpack> backpacksAbove = ItemBackpackRegistry.getBackpacksAbove(mainBackpack);
            if (backpacksAbove != null){
                if (backpacksAbove.isEmpty()) return true; //if no backpacks higher
                //TODO: while loop to check the connections (like a tree)
                return !(backpacksAbove.contains(toCheck.getItem())); //if current backpack's higher tier backpacks doesn't contain the itemstack in question, you can nest them
            } else {
                Logger.warn("Null backpacks above. Report to mod author with details.");
                return false; //some error
            }
        }
        return true;
    }
}
