package gr8pefish.ironbackpacks.api.item.backpacks.abstractClasses;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.crafting.IIncreaseBackpackTierRecipe;
import net.minecraft.item.Item;

/**
 * For use in backpacks that are tiered but not upgradable.
 */
public abstract class AbstractTieredBackpack extends Item implements ITieredBackpack, IIncreaseBackpackTierRecipe {
}
