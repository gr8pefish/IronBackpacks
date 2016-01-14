package main.ironbackpacks.api.item.backpacks.abstractClasses;

import main.ironbackpacks.api.crafting.IIncreaseBackpackTierRecipe;
import main.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;

/**
 * For use in backpacks that are tiered but not upgradable.
 */
public abstract class AbstractTieredBackpack implements ITieredBackpack, IIncreaseBackpackTierRecipe {
}
