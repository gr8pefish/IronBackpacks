package gr8pefish.ironbackpacks.client;

import net.minecraft.item.ItemStack;

/**
 * Provides a color based on the item stack. This interface
 * is used during backpack registration to customize dyeability.
 *
 * @author EngiN33R
 * @since 4.0.0
 */
@FunctionalInterface
public interface StackColorProvider {
    int accept(ItemStack stack);
}
