package gr8pefish.ironbackpacks.api.item.backpacks.interfaces;

import gr8pefish.ironbackpacks.api.item.upgrades.interfaces.IPackUpgrade;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * This interface should be used for all backpacks that can accept upgrades.
 */
public interface IUpgradableBackpack extends IBackpack {

    /**
     * Get the total number of upgrade points available for the backpack.
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - an integer amount of the total points
     */
    int getUpgradePoints(ItemStack backpack);

    /**
     * Get the upgrades applied to the backpack.
     * The integer return type is each upgrade's internal id, so instead of saving an entire item stack you just save one byte.
     *
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the upgrades applied, as integer values.
     */
//    ArrayList<ItemStack> getUpgrades(ItemStack backpack);

    /**
     * Gets the fullness of the backpack for the durability bar upgrade.
     * Note: Checks total fullness used, so if all slots hold 1 stackable item (itemX) it will show the fullness of only (type.size * itemX) and not as full.
     *
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - double representing the fullness
     */
    double getFullness(ItemStack backpack);

}
