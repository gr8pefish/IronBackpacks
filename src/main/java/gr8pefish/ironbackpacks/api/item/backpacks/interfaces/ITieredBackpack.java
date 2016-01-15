package gr8pefish.ironbackpacks.api.item.backpacks.interfaces;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

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
    ArrayList<ItemStack> getBackpacksAbove(ItemStack backpack);

    /**
     * Return if there exists a higher tier backpack
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - true if there is, false otherwise
     */
    boolean hasBackpacksAbove(ItemStack backpack);

    /**
     * Sets the backpack that is a higher tier
     * @param baseBackpack - the backpack as an item stack (needed for sub items) to set this value for
     * @param aboveBackpacks - the backpacks as an array to set as higher
     */
    void setBackpacksAbove(ItemStack baseBackpack, ArrayList<ItemStack> aboveBackpacks);

    //==============================================Methods related to lower tier backpacks=======================================================

    /**
     * Gets the backpack that is lower down than it.
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the IBackpack that is lower (or null)
     */
    ArrayList<ItemStack> getBackpacksBelow(ItemStack backpack);

    /**
     * Return if there exists a lower tier backpack
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - true if there is, false otherwise
     */
    boolean hasBackpacksBelow(ItemStack backpack);

    /**
     * Sets the backpack that is a lower tier
     * @param baseBackpack - the backpack as an item stack (needed for sub items) to set this value for
     * @param belowBackpacks - the backpacks as an array to set as lower
     */
    void setBackpacksBelow(ItemStack baseBackpack, ArrayList<ItemStack> belowBackpacks);



}
