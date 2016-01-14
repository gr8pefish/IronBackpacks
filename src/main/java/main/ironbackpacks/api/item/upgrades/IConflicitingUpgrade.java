package main.ironbackpacks.api.item.upgrades;

import java.util.ArrayList;

/**
 * Implement this if the upgrade cannot be crafted with another one.
 */
public interface IConflicitingUpgrade {

    ArrayList<IPackUpgrade> getConflictingUpgrades();
}
