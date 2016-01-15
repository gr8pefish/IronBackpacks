package gr8pefish.ironbackpacks.api.item.backpacks.interfaces;

import net.minecraft.item.ItemStack;

/**
 * This interface should be used for all backpacks.
 * Note that tiered backpacks and upgradable backpacks have separate interfaces.
 */
public interface IBackpack {

    /**
     * The internal name of the backpack (i.e. "ironBackpack")
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the name
     */
    String getName(ItemStack backpack);

    /**
     * The number of the rows in the backpack
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - integer of the row count
     */
    int getRowCount(ItemStack backpack);

    /**
     * The length of each row in the backpack
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - integer of the row length
     */
    int getRowLength(ItemStack backpack);

}
