package gr8pefish.ironbackpacks.api.item.backpacks.interfaces;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement this if a backpack needs to exist in the tiered system (i.e. there is a backpack inherently better or worse than it).
 * For example, the default system of basic -> iron -> gold -> diamond.
 */
public interface ITieredBackpack extends IBackpack { //TODO: use a tree datatype?

    //===============================================Methods related to higher tier backpacks=====================================================

    /**
     * Gets the backpacks that are higher up than it. Will only be multiple items if the backpack has 2 different "paths" for upgrading.
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the IBackpack that is higher (or null)
     */
    List<ITieredBackpack> getBackpacksAbove(ItemStack backpack);

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
    void setBackpacksAbove(ItemStack baseBackpack, List<ITieredBackpack> aboveBackpacks);

    /**
     * Se the tier of the backpack (equal to the number of backpacks beneath it).
     * For example, basic backpack is tier 0 and iron backpack is tier 1, gold is tier 2, etc.
     *
     * @param backpack - the backpack as an item stack (needed for sub items) to set this value for
     * @param tier - the tier of the backpack
     */
    void setTier(ItemStack backpack, int tier);

    /**
     * Get the tier of the backpack.
     * @param backpack - the backpack as an item stack (needed for sub items) to get this value for.
     * @return - integer value representing the tier
     */
    int getTier(ItemStack backpack);


}
