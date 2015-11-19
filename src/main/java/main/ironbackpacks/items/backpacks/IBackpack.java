package main.ironbackpacks.items.backpacks;

import net.minecraft.item.ItemStack;

/**
 * An interface for all backbacks to implement.
 *
 * The plan is to have this contain all the data required for backpack items for easy compatibility
 * support via an API.
 */
public interface IBackpack {

    /**
     * Gets the fullness of the backpack for the durability bar.
     * Note: Checks total fullness used, so if all slots hold 1 stackable item (itemX) it will show the fullness of only (type.size * itemX) and not as full.
     *
     * @param stack - The Backpack item
     * @return - double representing the fullness
     */
    double getFullness(ItemStack stack);

    int getId();

    int getSize();

    int getRowLength();

    int getUpgradePoints();

    String getName();

    int getGuiId();
}
