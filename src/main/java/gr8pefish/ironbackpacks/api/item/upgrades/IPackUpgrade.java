package gr8pefish.ironbackpacks.api.item.upgrades;

import java.util.List;

/**
 * The base interface for all upgrades that can be applied to a backpack.
 */
public interface IPackUpgrade {

    /**
     * The unlocalized name (i.e. buttonUpgrade)
     * @return - the string name
     */
    String getName();

    /**
     * The internal id of the upgrade
     * @return - the id of the item
     */
    int getId();

    /**
     * The cost (in upgrade points) to apply this upgrade
     * @return - the integer number of points
     */
    int getUpgradeCost();

    /**
     * The tooltip, each element is a new line
     * @return - the tooltip
     */
    List<String> getTooltip();
}
