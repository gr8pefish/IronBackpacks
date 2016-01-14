package gr8pefish.ironbackpacks.api.item.backpacks.interfaces;

import net.minecraft.item.ItemStack;

/**
 * This interface should be used for all backpacks.
 * Note that tiered backpacks and upgradable backpacks have separate interfaces.
 */
public interface IBackpack {

    /**
     * The internal ID of the backpack
     * @return - the ID as in integer
     */
    int getId();

    /**
     * The internal name of the backpack (i.e. "ironBackpack")
     * @return - the name
     */
    String getName();

    /**
     * Get the internal texture of the item to display (i.e. "backpack_iron")
     * @return - the texture name
     */
    String getTexture();

    /**
     * The total size/slots of the backpack's normal storage (could be calculated from rowLength and numberOfRows)
     * @return - integer of the size
     */
    int getSize();

    /**
     * The number of the rows in the backpack
     * @return - integer of the row count
     */
    int getRowCount();

    /**
     * The length of each row in the backpack
     * @return - integer of the row length
     */
    int getRowLength();

    /**
     * Gets the fullness of the backpack for the durability bar.
     * Note: Checks total fullness used, so if all slots hold 1 stackable item (itemX) it will show the fullness of only (type.size * itemX) and not as full.
     *
     * @param stack - The Backpack item
     * @return - double representing the fullness
     */
    double getFullness(ItemStack stack);

}
