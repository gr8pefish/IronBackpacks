package gr8pefish.ironbackpacks.container.slot;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.register.ItemBackpackRegistry;
import gr8pefish.ironbackpacks.util.Logger;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private boolean isOfLowerTier(ItemStack packToCheck){
        if (packToCheck.getItem() instanceof ITieredBackpack){ //if a tiered backpack

            ITieredBackpack currentPack =  //current pack to check for higher tier packs
                    (ITieredBackpack)mainBackpack.getItem(); //initialize it to the main backpack at first

            List<ITieredBackpack> abovePacks; //the backpacks higher in tier (relative to the current pack)
            List<ITieredBackpack> uncheckedBackpacks = new ArrayList<>(); //the backpacks that are not checked (if abovePacks.size() > 1 then store these to check later)

            boolean obtainedFromUnchecked = false; //if you got the value from abovePacks or from unchecked (so you know to get a new current pack at the end of the current iteration or not)
            boolean finalReturnValue = true; //the final return value, the absolute answer to if it is of a lower tier or not

            while (currentPack != null) { //while you have a backpack to check

                abovePacks = currentPack.getBackpacksAbove(); //using non-itemStack specific method (no parameter)

                //check all cases to end the check
                if (abovePacks == null) { //some error while initializing
                    Logger.warn("Null backpacks above. Report to mod author with details.");
                    finalReturnValue = false; //default don't accept it
                    break;
                } else if (abovePacks.isEmpty()) { //there are no higher tier backpacks, so it must be lower
                    if (uncheckedBackpacks.isEmpty()){ //if there are no additional backpacks to check, end as true
                        finalReturnValue = true;
                        break;
                    } else {
                        currentPack = uncheckedBackpacks.get(0); //get a new current pack from the list of unchecked packs
                        uncheckedBackpacks.remove(0); //remove it from the list of unchecked packs
                        obtainedFromUnchecked = true; //note that you already have a current pack
                    }
                } else if (abovePacks.contains(packToCheck.getItem())) { //backpacks above has the pack we are looking for so it can't be lower tier
                    finalReturnValue = false;
                    break; //always immediately return
                }

                if (!obtainedFromUnchecked) { //if don't already have a current pack

                    if (abovePacks.size() > 1)  //more than 1 pack higher tier
                        uncheckedBackpacks.addAll(0, abovePacks.subList(1, abovePacks.size())); //add all the other backpacks to this list to be dealt with later

                    currentPack = abovePacks.get(0); //get the first element
                    obtainedFromUnchecked = false; //got it from abovePacks, not from unchecked
                }
            }

            return finalReturnValue;

        } else if (packToCheck.getItem() instanceof IBackpack) { //non-tiered backpack should be accepted (as it is by default just first tier)
            return true;
        }
        return true; //default return true
    }
}
