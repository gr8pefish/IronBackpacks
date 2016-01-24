package gr8pefish.ironbackpacks.registry.recipes;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IUpgradableBackpack;
import gr8pefish.ironbackpacks.api.register.ItemBackpackRegistry;
import gr8pefish.ironbackpacks.api.register.ItemUpgradeRegistry;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.crafting.BackpackAddUpgradeRecipe;
import gr8pefish.ironbackpacks.crafting.BackpackIncreaseTierRecipe;
import gr8pefish.ironbackpacks.crafting.BackpackRemoveUpgradeRecipe;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

import java.util.ArrayList;

/**
 * Register all the recipes here.
 */
public class RecipeRegistry {

	/**
	 * Main method that registers all the recipes
	 */
	public static void registerAllRecipes() {

        //Basic Item Recipes
        ItemCraftingRecipes.registerItemCraftingRecipes(); //register the recipes to get the crafting items
        ItemUpgradeRecipes.registerItemUpgradeRecipes(); //register the recipes to get the the upgrades as items //broken (crashes) //TODO: figure this bope out
		ItemBackpackRecipes.registerItemBackpackRecipes(); //register all the recipes to get the backpacks directly

        //=============Fancy Iron Backpacks Recipes===========

        //Register as special recipes
        RecipeSorter.register("RemoveUpgrade", BackpackRemoveUpgradeRecipe.class, RecipeSorter.Category.SHAPELESS, ""); //register my special recipe
        RecipeSorter.register("BackpackUpgrade", BackpackAddUpgradeRecipe.class, RecipeSorter.Category.SHAPELESS, ""); //register my special recipe
        RecipeSorter.register("BackpackTier", BackpackIncreaseTierRecipe.class, RecipeSorter.Category.SHAPED, ""); //register my special recipe

        //Register the recipes themselves
        BackpackTierRecipes.registerBackpackTierRecipes(); //register the recipes to upgrade a backpack to the next tier
		registerBackpackUpgradeRemovalRecipes(); //register the recipes to remove upgrades from backpacks
        registerBackpackUpgradeAdditionRecipes(); //register the recipes to add upgrades from backpacks


	}

    /**
     * Sets all the non tiered backpack recipes.
     * Not in item initialization in case one item uses another item from this mod, as that item may not be initialized yet (very likely).
     */
    public static void setAllNonTierRecipes(){
        setItemBackpackRecipes();
        setUpgradeRecipes();
    }

	//===========================================================================Set Recipes==================================================================

    private static void setItemBackpackRecipes(){
        ItemRegistry.basicBackpack.setItemRecipe(ItemBackpackRecipes.basicBackpackRecipe);
    }

    private static void setUpgradeRecipes(){
        ItemRegistry.additionalUpgradePointsUpgrade.setItemRecipe(ItemUpgradeRecipes.additionalUpgradePointsRecipe);
        ItemRegistry.buttonUpgrade.setItemRecipe(ItemUpgradeRecipes.buttonUpgradeRecipe);
        ItemRegistry.damageBarUpgrade.setItemRecipe(ItemUpgradeRecipes.damageBarUpgradeRecipe);
        ItemRegistry.depthUpgrade.setItemRecipe(ItemUpgradeRecipes.depthUpgradeRecipe);
        ItemRegistry.eternityUpgrade.setItemRecipe(ItemUpgradeRecipes.eternityUpgradeRecipe);
        if (ConfigHandler.renamingUpgradeRequired) ItemRegistry.renamingUpgrade.setItemRecipe(ItemUpgradeRecipes.renamingUpgradeRecipe);
        ItemRegistry.nestingUpgrade.setItemRecipe(ItemUpgradeRecipes.nestingUpgradeRecipe);
        ItemRegistry.nestingAdvancedUpgrade.setItemRecipe(ItemUpgradeRecipes.nestingAdvancedUpgradeRecipe);
        ItemRegistry.quickDepositUpgrade.setItemRecipe(ItemUpgradeRecipes.quickDepositUpgradeRecipe);
        ItemRegistry.quickDepositPreciseUpgrade.setItemRecipe(ItemUpgradeRecipes.quickDepositPreciseUpgradeRecipe);
        ItemRegistry.craftingUpgrade.setItemRecipe(ItemUpgradeRecipes.craftingUpgradeRecipe);
        ItemRegistry.craftingSmallUpgrade.setItemRecipe(ItemUpgradeRecipes.craftingSmallUpgradeRecipe);
        ItemRegistry.craftingTinyUpgrade.setItemRecipe(ItemUpgradeRecipes.craftingTinyUpgradeRecipe);
        ItemRegistry.filterBasicUpgrade.setItemRecipe(ItemUpgradeRecipes.filterBasicUpgradeRecipe);
        ItemRegistry.filterFuzzyUpgrade.setItemRecipe(ItemUpgradeRecipes.filterFuzzyUpgradeRecipe);
        ItemRegistry.filterOreDictUpgrade.setItemRecipe(ItemUpgradeRecipes.filterOreDictUpgradeRecipe);
        ItemRegistry.filterModSpecificUpgrade.setItemRecipe(ItemUpgradeRecipes.filterModSpecificUpgradeRecipe);
        ItemRegistry.filterAdvancedUpgrade.setItemRecipe(ItemUpgradeRecipes.filterAdvancedUpgradeRecipe);
        ItemRegistry.filterMiningUpgrade.setItemRecipe(ItemUpgradeRecipes.filterMiningUpgradeRecipe);
        ItemRegistry.filterVoidUpgrade.setItemRecipe(ItemUpgradeRecipes.filterVoidUpgradeRecipe);
        ItemRegistry.restockingUpgrade.setItemRecipe(ItemUpgradeRecipes.restockingUpgradeRecipe);
    }

    //=================================================================================Helper Registers==========================================================

    /**
     * Register the upgrade removal recipes for every backpack (if it is an IUpgradableBackpack)
     */
    private static void registerBackpackUpgradeRemovalRecipes(){
        for (int i = 0; i < ItemBackpackRegistry.getSize(); i++){
            IBackpack backpack = ItemBackpackRegistry.getBackpackAtIndex(i);
            if (backpack instanceof IUpgradableBackpack) {
                GameRegistry.addRecipe(new BackpackRemoveUpgradeRecipe(new ItemStack((ItemBackpack)backpack), new ItemStack((ItemBackpack)backpack))); //Hardcoded to ItemBackpack
            }
        }
    }

    /**
     * Register the recipe for the addition of upgrades to an IUpgradeableBackpack.
     * If the backpack is also an ITieredBackpack, then the tier of the upgrade must be lower than or equal to the backpack to allow it to be applied.
     */
    private static void registerBackpackUpgradeAdditionRecipes() {
        ArrayList<ItemStack> upgrades = new ArrayList<>();
        for (int i = 0; i < ItemUpgradeRegistry.getTotalSize(); i++)
            upgrades.add(new ItemStack(ItemRegistry.upgradeItem, 1, i));

        for (int i = 0; i < ItemBackpackRegistry.getSize(); i++){
            IBackpack backpack = ItemBackpackRegistry.getBackpackAtIndex(i);
            if (backpack instanceof IUpgradableBackpack) {
                for (ItemStack upgrade : upgrades){
                    int upgradeTier = ItemUpgradeRegistry.getItemUpgrade(upgrade).getTier(upgrade);
                    if (backpack instanceof ITieredBackpack) {
                        int backpackTier = ((ITieredBackpack) backpack).getTier(null);
                        if (upgradeTier <= backpackTier)
                            GameRegistry.addRecipe(new BackpackAddUpgradeRecipe(new ItemStack((ItemBackpack)backpack), upgrade, new ItemStack((ItemBackpack)backpack))); //Hardcoded to ItemBackpack
                    } else {
                        if (upgradeTier >= 0) //TODO: currently accepts any upgrade, should that be changed?
                            GameRegistry.addRecipe(new BackpackAddUpgradeRecipe(new ItemStack((ItemBackpack)backpack), upgrade, new ItemStack((ItemBackpack)backpack))); //Hardcoded to ItemBackpack
                    }

                }
            }
        }
    }
}
