package gr8pefish.ironbackpacks.api.item.backpacks.abstractClasses;

import gr8pefish.ironbackpacks.api.crafting.IRemoveUpgradeRecipe;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IUpgradableBackpack;
import gr8pefish.ironbackpacks.api.crafting.IAddUpgradeRecipe;
import net.minecraft.item.Item;

/**
 * To use for backpacks that are upgradable but not tiered.
 */
public abstract class AbstractUpgradablePack extends Item implements IUpgradableBackpack, IAddUpgradeRecipe, IRemoveUpgradeRecipe {


}
