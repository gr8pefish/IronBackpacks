package gr8pefish.ironbackpacks.api.item.upgrades;

import java.util.ArrayList;

/**
 * Implement this if the upgrade cannot be crafted with another one.
 */
public interface IConflicitingUpgrade extends IPackUpgrade{

    ArrayList<IPackUpgrade> getConflictingUpgrades();
}
