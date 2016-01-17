package gr8pefish.ironbackpacks.api.item.upgrades.interfaces;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Implement this if the upgrade cannot be applied to a backpack if another one is already.
 * For example, you cannot have both a quickDepositUpgrade and a preciseQuickDepositUpgrade at the same time.
 */
public interface IConflicitingUpgrade extends IPackUpgrade {

    /**
     * Get the list of upgrades that this upgrade cannot be simultaneously applied alongside.
     * @param upgrade - the upgrade as an item stack (needed for subitems)
     * @return - the list of conflicting upgrades
     */
    ArrayList<IPackUpgrade> getConflictingUpgrades(ItemStack upgrade);
}
