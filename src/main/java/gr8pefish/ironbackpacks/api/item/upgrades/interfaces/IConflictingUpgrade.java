package gr8pefish.ironbackpacks.api.item.upgrades.interfaces;

import gr8pefish.ironbackpacks.api.item.upgrades.ItemConflictingUpgrade;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Implement this if the upgrade cannot be applied to a backpack if another one is already.
 * For example, you cannot have both a quickDepositUpgrade and a preciseQuickDepositUpgrade at the same time.
 */
public interface IConflictingUpgrade extends IPackUpgrade {

    /**
     * Get the list of upgrades that this upgrade cannot be simultaneously applied alongside.
     * @param upgrade - the upgrade as an item stack (needed for subitems)
     * @return - the list of conflicting upgrades
     */
    List<ItemConflictingUpgrade> getConflictingUpgrades(ItemStack upgrade);

    /**
     * Set the conflicting upgrades.
     * @param upgrade - the upgrade as an item stack (needed for subitems)
     * @param conflictingUpgradeList - the list of conflicting upgrades
     */
    void setConflictingUpgrades(ItemStack upgrade, List<ItemConflictingUpgrade> conflictingUpgradeList);
}
