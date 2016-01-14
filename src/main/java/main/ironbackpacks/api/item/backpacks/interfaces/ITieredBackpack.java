package main.ironbackpacks.api.item.backpacks.interfaces;

import main.ironbackpacks.api.item.backpacks.interfaces.IBackpack;

/**
 * Implement this if a backpack needs to exist in the tiered system (i.e. there is a backpack inherently better or worse than it).
 * For example, the default system of basic -> iron -> gold -> diamond
 */
public interface ITieredBackpack extends IBackpack {

    //===============================================Methods related to higher tier backpacks=====================================================

    /**
     * Gets the backpack that is higher up than it.
     * @return - the IBackpack that is higher (or null)
     */
    IBackpack getBackpackAbove();

    /**
     * Return if there exists a higher tier backpack
     * @return - true if there is, false otherwise
     */
    boolean hasBackpackAbove();

    /**
     * Sets the backpack that is a higher tier
     * @param backpack - the backpack to set as higher
     */
    void setBackpackAbove(IBackpack backpack);

    //==============================================Methods related to lower tier backpacks=======================================================

    /**
     * Gets the backpack that is lower down than it.
     * @return - the IBackpack that is lower (or null)
     */
    IBackpack getBackpackBelow();

    /**
     * Return if there exists a lower tier backpack
     * @return - true if there is, false otherwise
     */
    boolean hasBackpackBelow();

    /**
     * Sets the backpack that is a lower tier
     * @param backpack - the backpack to set as lower
     */
    void setBackpackBelow(IBackpack backpack);



}
