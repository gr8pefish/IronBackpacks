package gr8pefish.ironbackpacks.api.item.backpacks.interfaces;

import net.minecraft.item.ItemStack;

/**
 * Implement this if a backpack needs to exist in the tiered system (i.e. there is a backpack inherently better or worse than it).
 * For example, the default system of basic -> iron -> gold -> diamond.
 */
public interface ITieredBackpack extends IBackpack {

    //===============================================Methods related to higher tier backpacks=====================================================

    /**
     * Gets the backpack that is higher up than it.
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the IBackpack that is higher (or null)
     */
    IBackpack getBackpackAbove(ItemStack backpack);

    /**
     * Return if there exists a higher tier backpack
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - true if there is, false otherwise
     */
    boolean hasBackpackAbove(ItemStack backpack);

    /**
     * Sets the backpack that is a higher tier
     * @param baseBackpack - the backpack as an item stack (needed for sub items) to set this value for
     * @param aboveBackpack - the backpack as an item stack (needed for sub items) to set as higher
     */
    void setBackpackAbove(ItemStack baseBackpack, ItemStack aboveBackpack);

    //==============================================Methods related to lower tier backpacks=======================================================

    /**
     * Gets the backpack that is lower down than it.
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the IBackpack that is lower (or null)
     */
    IBackpack getBackpackBelow(ItemStack backpack);

    /**
     * Return if there exists a lower tier backpack
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - true if there is, false otherwise
     */
    boolean hasBackpackBelow(ItemStack backpack);

    /**
     * Sets the backpack that is a lower tier
     * @param baseBackpack - the backpack as an item stack (needed for sub items) to set this value for
     * @param belowBackpack - the backpack as an item stack (needed for sub items) to set as lower
     */
    void setBackpackBelow(ItemStack baseBackpack, ItemStack belowBackpack);



}
