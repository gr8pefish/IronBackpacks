package main.ironbackpacks.api.item.backpacks;

import main.ironbackpacks.api.item.upgrades.IPackUpgrade;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * This interface should be used for all backpacks that can accept upgrades.
 */
public interface IUpgradableBackpack {

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

    int getUpgradeSlots();

//    ArrayList<IPackUpgrade> getUpgrades();

    String getFancyName();

    int getGuiId();
}
