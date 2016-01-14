package main.ironbackpacks.api.item.backpacks.abstractClasses;

import main.ironbackpacks.api.crafting.IAddUpgradeRecipe;
import main.ironbackpacks.api.crafting.IRemoveUpgradeRecipe;
import main.ironbackpacks.api.item.backpacks.interfaces.IUpgradableBackpack;

/**
 * To use for backpacks that are upgradable but not tiered.
 */
public abstract class AbstractUpgradablePack implements IUpgradableBackpack, IAddUpgradeRecipe, IRemoveUpgradeRecipe {


}
