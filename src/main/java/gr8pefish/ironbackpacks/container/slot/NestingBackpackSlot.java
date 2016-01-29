package gr8pefish.ironbackpacks.container.slot;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * A backpack slot that allows other backpacks if they are of a previous tier.
 */
public class NestingBackpackSlot extends BackpackSlot {

    private ItemStack mainBackpack;

    public NestingBackpackSlot(IInventory iInventory, int index, int x, int y, ItemStack backpackStack) {
        super(iInventory, index, x, y);
        this.mainBackpack = backpackStack;
    }

    @Override
    public boolean acceptsStack(ItemStack toCheck){
        return isOfLowerTier(toCheck);
    }

    /**
     * Helper method that checks if the backpack is of a previous tier than the one containing this slot.
     * @param packToCheck - the backpack that needs to be proven as a lower tier to accept it
     * @return - true if the backpack is of a lower tier (and can be accepted in the slot), false otherwise
     */
    private boolean isOfLowerTier(ItemStack packToCheck){ //TODO: config option for equal to?
        if (packToCheck.getItem() instanceof ITieredBackpack){ //if a tiered backpack
            return ((ITieredBackpack)packToCheck.getItem()).getTier(packToCheck) < ((ITieredBackpack)mainBackpack.getItem()).getTier(mainBackpack);
        } else if (packToCheck.getItem() instanceof IBackpack) { //non-tiered backpack should be accepted (as it is by default just first tier)
            return true;
        }
        return true; //default return true
    }
}
