package main.ironbackpacks.api.item.backpacks.interfaces;

import main.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import main.ironbackpacks.api.item.upgrades.IPackUpgrade;

import java.util.ArrayList;

/**
 * This interface should be used for all backpacks that can accept upgrades.
 */
public interface IUpgradableBackpack extends IBackpack {

    /**
     * Get the total number of upgrade points available for the backpack.
     * @return - an integer amount of the total points
     */
    int getUpgradePoints();

    /**
     * Get the upgrades applied to the backpack.
     * @return - the upgrades applied
     */
    ArrayList<IPackUpgrade> getUpgrades();

}
