package main.ironbackpacks.items.backpacks;

import net.minecraft.item.ItemStack;

public interface IBackpack {

    /**
     * Gets the fullness of the backpack for the durability bar.
     * Note: Checks total fullness used, so if all slots hold 1 stackable item (itemX) it will show the fullness of only (type.size * itemX) and not as full.
     *
     * @param stack - The Backpack item
     * @return - double representing the fullness
     */
    double getFullness(ItemStack stack);
}
