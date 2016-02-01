package gr8pefish.ironbackpacks.api.register;

import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;

import java.util.ArrayList;
import java.util.List;

public class ItemIBackpackRegistry {

    private static List<IBackpack> backpacks = new ArrayList<>();

    public static void registerItemBackpack(IBackpack item) {
        if (!backpacks.contains(item))
            backpacks.add(item);
    }

    public static IBackpack getBackpackAtIndex(int index) {
        if (backpacks.get(index) != null)
            return backpacks.get(index);
        else {
            return null;
        }
    }

    public static int getIndexOf(IBackpack backpack) {
        return backpacks.indexOf(backpack);
    }

    public static int getSize() {
        return backpacks.size();
    }

}
