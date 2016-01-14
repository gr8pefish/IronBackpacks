package main.ironbackpacks.api.item.backpacks;

import net.minecraft.item.ItemStack;

/**
 * This interface should be used for all backpacks that do not accept upgrades
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

    int getUpgradeSlots(); //TODO: remove

    String getFancyName();

    int getGuiId();
}
