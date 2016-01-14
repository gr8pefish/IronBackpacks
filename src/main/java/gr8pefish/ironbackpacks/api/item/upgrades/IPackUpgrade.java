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
     *
     * @return
     */
    int getId();

    /**
     *
     * @return
     */
    int getUpgradeCost();

    /**
     *
     * @return
     */
    List<String> getTooltip();
}
