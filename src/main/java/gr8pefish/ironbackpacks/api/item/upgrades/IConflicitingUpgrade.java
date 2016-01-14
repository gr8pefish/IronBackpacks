package gr8pefish.ironbackpacks.api.item.upgrades;

import java.util.ArrayList;

/**
 * Implement this if the upgrade cannot be applied to a backpack if another one is already.
 * For example, you cannot have both a quickDepositUpgrade and a preciseQuickDepositUpgrade at the same time.
 */
public interface IConflicitingUpgrade extends IPackUpgrade{

    /**
     * Get the list of upgrades that this upgrade cannot be simultaneously applied alongside.
     * @return - the list of conflicting upgrades
     */
    ArrayList<IPackUpgrade> getConflictingUpgrades();
}
