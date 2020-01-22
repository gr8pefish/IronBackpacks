package gr8pefish.ironbackpacks.item;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * Interface describing the basic contract all backpack implementations
 * are expected to fulfill. It is recommended you extend {@link BaseBackpackItem}
 * instead, unless you plan on completely overhauling standard backpack behavior.
 *
 * @see BaseBackpackItem
 * @author EngiN33R
 * @since 4.0.0
 */
public interface BackpackItem extends ItemConvertible {
    /**
     * Gets the base tier of the backpack type. This counts for crafting recipes
     * and cannot be changed.
     *
     * @return backpack type base tier
     * @since 4.0.0
     */
    int getBaseTier();

    /**
     * Gets the tier of the given backpack as counts for installing upgrades.
     * This may be modified via upgrades or other ways.
     *
     * @return backpack type base tier
     * @since 4.0.0
     */
    int getUpgradeTier();

    /**
     * Gets the number of maximum upgrade points for the given
     * backpack, applying modifiers e.g. from upgrades.
     *
     * @param stack backpack {@link ItemStack}
     * @return maximum upgrade points
     * @since 4.0.0
     */
    int getMaxUpgradePoints(ItemStack stack);

    /**
     * Gets the number of upgrade points used by installed upgrades
     * on the given backpack.
     *
     * @param stack backpack {@link ItemStack}
     * @return used upgrade points
     * @since 4.0.0
     */
    int getUsedUpgradePoints(ItemStack stack);

    /**
     * Gets the number of free upgrade points for the given backpack.
     * Convenience wrapper for {@link #getMaxUpgradePoints(ItemStack)}
     * minus {@link #getUsedUpgradePoints(ItemStack)}.
     *
     * @param stack backpack {@link ItemStack}
     * @return free upgrade points
     * @since 4.0.0
     */
    default int getFreeUpgradePoints(ItemStack stack) {
        return getMaxUpgradePoints(stack) - getUsedUpgradePoints(stack);
    }

    /**
     * Gets a list of all upgrades installed in the given backpack.
     *
     * @param stack backpack {@link ItemStack}
     * @return list of installed upgrades
     * @since 4.0.0
     */
    List<Identifier> getUpgrades(ItemStack stack);

    /**
     * Check whether the given backpack has an upgrade installed.
     *
     * @param stack backpack {@link ItemStack}
     * @param id upgrade identifier
     * @return <tt>true</tt> if upgrade is installed
     * @since 4.0.0
     */
    boolean hasUpgrade(ItemStack stack, Identifier id);

    /**
     * Gets the number of inventory columns for the given backpack
     * (e.g. for purposes of container rendering).
     *
     * @param stack backpack {@link ItemStack}
     * @return columns in the backpack container
     * @since 4.0.0
     */
    int getContainerColumns(ItemStack stack);

    /**
     * Gets the number of inventory rows for the given backpack
     * (e.g. for purposes of container rendering).
     *
     * @param stack backpack {@link ItemStack}
     * @return rows in the backpack container
     * @since 4.0.0
     */
    int getContainerRows(ItemStack stack);

    /**
     * Gets the total stack capacity of the given backpack, i.e.
     * how many slots there are (commonly <tt>columns * rows</tt>).
     *
     * @param stack backpack {@link ItemStack}
     * @return total stack capacity
     * @since 4.0.0
     */
    int getCapacity(ItemStack stack);
}
