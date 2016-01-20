package gr8pefish.ironbackpacks.api.register;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemBackpackRegistry {

    private static List<IBackpack> backpacks = new ArrayList<>();
    private static TierNode<ITieredBackpack> backpackTree;

    public static void registerItemBackpack(IBackpack item) {
        if (!backpacks.contains(item))
            backpacks.add(item);
        //TODO: else {print error in logger}
    }

    public static IBackpack getBackpackAtIndex(int index) {
        if (backpacks.get(index) != null)
            return backpacks.get(index);
        else {
            //Logger.info("error retreiving backpack at index "+index);
            return null;
        }
    }

    public static int getIndexOf(IBackpack backpack) {
        return backpacks.indexOf(backpack);
    }

    public static int getSize() {
        return backpacks.size();
    }

    //TODO: move to helper class
    public static List<ITieredBackpack> getBackpacksAbove(ItemStack backpack){
        if (backpack.getItem() instanceof ITieredBackpack && ((ITieredBackpack)backpack.getItem()).hasBackpacksAbove(backpack))
            return ((ITieredBackpack)backpack.getItem()).getBackpacksAbove(backpack);
        else
            return null;
    }
}
