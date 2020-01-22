package gr8pefish.ironbackpacks.item;

import gr8pefish.ironbackpacks.client.StackColorProvider;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;

/**
 * Interface describing the basic contract all dyeable backpack implementations
 * are expected to fulfill. It is recommended you extend {@link BaseDyeableBackpackItem}
 * instead, unless you plan on completely overhauling standard backpack behavior.
 *
 * @see BaseDyeableBackpackItem
 * @author EngiN33R
 * @since 4.0.0
 */
public interface DyeableBackpackItem extends DyeableItem, BackpackItem {
    /**
     * Gets the default color provider, i.e. the provider of the color
     * for undyed backpacks.
     *
     * @return default color provider
     * @since 4.0.0
     */
    StackColorProvider getDefaultColorProvider();

    /**
     * Gets the color provider, i.e. the provider of the color of a dyed
     * backpack.
     *
     * @return default color provider
     * @since 4.0.0
     */
    StackColorProvider getColorProvider();

    /**
     * Gets the color of the backpack based on the item stack data.
     * By default uses the color provider, falling back on the default
     * color provider.
     *
     * @param stack backpack item stack
     * @return backpack color
     * @since 4.0.0
     */
    default int getColor(ItemStack stack) {
        StackColorProvider colorProvider = getColorProvider();
        StackColorProvider defaultColorProvider = getDefaultColorProvider();
        if (colorProvider != null) {
            return colorProvider.accept(stack);
        }
        int defaultColor = DyeableItem.super.getColor(stack);
        // The default color returned by DyeableItem is leather armor brown,
        // so we use our provider and fall back to leather armor brown
        // if there is no provider
        if (defaultColor == 10511680) {
            if (defaultColorProvider != null) {
                return defaultColorProvider.accept(stack);
            }
            return 10511680;
        }
        return defaultColor;
    }
}
