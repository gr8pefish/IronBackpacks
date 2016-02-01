package gr8pefish.ironbackpacks.api.items.backpacks.interfaces;

import net.minecraft.item.ItemStack;

/**
 * This interface should be used for all backpacks that can accept upgrades.
 */
public interface IUpgradableBackpack extends IBackpack {

    /**
     * Get the total number of upgrade points available for the backpack.
     * Note: DOES NOT include the additional upgrade points.
     *
     * @param backpack - the backpack as an items stack (needed for sub items)
     * @return - an integer amount of the total points
     */
    int getUpgradePoints(ItemStack backpack);

    /**
     * Return the amount of additional upgrade points that can be applied to the backpack.
     * @param backpack - the backpack as an items stack (needed for sub items)
     * @return - an integer amount of the total points
     */
    int getAdditionalUpgradePoints(ItemStack backpack);

}
