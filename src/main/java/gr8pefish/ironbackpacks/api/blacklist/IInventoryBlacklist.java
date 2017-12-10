package gr8pefish.ironbackpacks.api.blacklist;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IInventoryBlacklist {

    /**
     * Blacklists an item based only on it's meta value
     *
     * @param stack Item to blacklist
     */
    void blacklist(@Nonnull ItemStack stack);

    /**
     * Blacklists an NBT key. Any item with this key will be caught.
     *
     * @param tagKey NBT key to blacklist
     */
    void blacklist(@Nonnull String tagKey);

    /**
     * Returns whether or not this stack matches a provided blacklist.
     *
     * First the item and meta are checked since that is much cheaper. After that, NBT is checked to see if any blacklisted
     * keys are listed in the compounds.
     *
     * @param stack The stack to check
     * @return if this item is blacklisted
     */
    boolean isBlacklisted(@Nonnull ItemStack stack);
}
