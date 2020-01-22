package gr8pefish.ironbackpacks.item;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Interface describing the basic contract all upgrade implementations
 * are expected to fulfill. It is recommended you extend {@link BaseUpgradeItem}
 * instead, unless you plan on completely overhauling standard upgrade behavior.
 *
 * @see BaseUpgradeItem
 * @author EngiN33R
 * @since 4.0.0
 */
public interface UpgradeItem extends ItemConvertible {
    /**
     * Gets the number of upgrade points this upgrade uses in a backpack.
     *
     * @return upgrade point cost
     * @since 4.0.0
     */
    int getPoints();

    /**
     * Gets the minimum tier of backpack this upgrade can be installed in.
     *
     * @return minimum backpack tier
     * @since 4.0.0
     */
    int getMinTier();

    /**
     * Gets the translation key for the item name and tooltip.
     *
     * @param stack upgrade stack
     * @return translation key
     * @since 4.0.0
     */
    String getTranslationKey(@Nonnull ItemStack stack);
}
