package gr8pefish.ironbackpacks.api.item.backpacks.interfaces;

import net.minecraft.item.ItemStack;

/**
 * This interface should be used for all backpacks.
 * Note that tiered backpacks and upgradable backpacks have separate interfaces.
 */
public interface IBackpack {

    /**
     * The internal ID of the backpack
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the ID as in integer
     */
    int getId(ItemStack backpack);

    /**
     * The internal name of the backpack (i.e. "ironBackpack")
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the name
     */
    String getName(ItemStack backpack);

    /**
     * The total size/slots of the backpack's normal storage (could be calculated from rowLength and numberOfRows)
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - integer of the size
     */
    int getSize(ItemStack backpack);

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

    /**
     * Gets the fullness of the backpack for the durability bar.
     * Note: Checks total fullness used, so if all slots hold 1 stackable item (itemX) it will show the fullness of only (type.size * itemX) and not as full.
     *
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - double representing the fullness
     */
    double getFullness(ItemStack backpack);

}
