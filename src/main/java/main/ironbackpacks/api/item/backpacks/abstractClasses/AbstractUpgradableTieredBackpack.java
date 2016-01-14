package main.ironbackpacks.api.item.backpacks.abstractClasses;

import main.ironbackpacks.api.crafting.IAddUpgradeRecipe;
import main.ironbackpacks.api.crafting.IIncreaseBackpackTierRecipe;
import main.ironbackpacks.api.crafting.IRemoveUpgradeRecipe;
import main.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import main.ironbackpacks.api.item.backpacks.interfaces.IUpgradableBackpack;

/**
 * To use for backpacks that are both tiered and upgradable.
 */
public abstract class AbstractUpgradableTieredBackpack implements IUpgradableBackpack, ITieredBackpack, IAddUpgradeRecipe, IRemoveUpgradeRecipe, IIncreaseBackpackTierRecipe {

}
