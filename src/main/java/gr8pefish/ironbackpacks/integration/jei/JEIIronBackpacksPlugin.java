package gr8pefish.ironbackpacks.integration.jei;

import gr8pefish.ironbackpacks.api.recipes.IAddUpgradeRecipe;
import gr8pefish.ironbackpacks.api.recipes.IIncreaseBackpackTierRecipe;
import gr8pefish.ironbackpacks.api.recipes.IRemoveUpgradeRecipe;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.integration.jei.addUpgrade.AddUpgradeRecipeCategory;
import gr8pefish.ironbackpacks.integration.jei.addUpgrade.AddUpgradeRecipeWrapper;
import gr8pefish.ironbackpacks.integration.jei.increaseTier.IncreaseTierRecipeCategory;
import gr8pefish.ironbackpacks.integration.jei.increaseTier.IncreaseTierRecipeWrapper;
import gr8pefish.ironbackpacks.integration.jei.removeUpgrade.RemoveUpgradeRecipeCategory;
import gr8pefish.ironbackpacks.integration.jei.removeUpgrade.RemoveUpgradeRecipeWrapper;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import gr8pefish.ironbackpacks.registry.RecipeRegistry;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIIronBackpacksPlugin implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {

		registry.handleRecipes(IAddUpgradeRecipe.class, AddUpgradeRecipeWrapper::new, AddUpgradeRecipeCategory.NAME);
		registry.handleRecipes(IRemoveUpgradeRecipe.class, RemoveUpgradeRecipeWrapper::new, RemoveUpgradeRecipeCategory.NAME);
		registry.handleRecipes(IIncreaseBackpackTierRecipe.class, IncreaseTierRecipeWrapper::new, IncreaseTierRecipeCategory.NAME);

		registry.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), AddUpgradeRecipeCategory.NAME, RemoveUpgradeRecipeCategory.NAME, IncreaseTierRecipeCategory.NAME);

		registry.addRecipes(RecipeRegistry.UPGRADE_ADD, AddUpgradeRecipeCategory.NAME);
		registry.addRecipes(RecipeRegistry.UPGRADE_REMOVE, RemoveUpgradeRecipeCategory.NAME);
		registry.addRecipes(RecipeRegistry.INCREASE, IncreaseTierRecipeCategory.NAME);

		//Add backpack descriptions
		registry.addIngredientInfo(new ItemStack(ItemRegistry.basicBackpack), ItemStack.class, "jei.description.backpack.basic", "jei.description.backpack.generic");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.ironBackpackStorageEmphasis), ItemStack.class, "jei.description.backpack.iron", "jei.description.backpack.storage", "jei.description.backpack.generic");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.ironBackpackUpgradeEmphasis), ItemStack.class, "jei.description.backpack.iron", "jei.description.backpack.upgrade", "jei.description.backpack.generic");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.goldBackpackStorageEmphasis), ItemStack.class, "jei.description.backpack.gold", "jei.description.backpack.storage", "jei.description.backpack.generic");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.goldBackpackUpgradeEmphasis), ItemStack.class, "jei.description.backpack.gold", "jei.description.backpack.upgrade", "jei.description.backpack.generic");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.diamondBackpackStorageEmphasis), ItemStack.class, "jei.description.backpack.diamond", "jei.description.backpack.storage", "jei.description.backpack.generic");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.diamondBackpackUpgradeEmphasis), ItemStack.class, "jei.description.backpack.diamond", "jei.description.backpack.upgrade", "jei.description.backpack.generic");

		//Add upgrade descriptions
		//basic
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.additionalUpgradePointsUpgrade)), ItemStack.class, "jei.description.upgrade.additionalUpgradePoints");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.buttonUpgrade)), ItemStack.class, "jei.description.upgrade.button");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.damageBarUpgrade)), ItemStack.class, "jei.description.upgrade.damageBar");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.depthUpgrade)), ItemStack.class, "jei.description.upgrade.depth");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.eternityUpgrade)), ItemStack.class, "jei.description.upgrade.eternity");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.renamingUpgrade)), ItemStack.class, "jei.description.upgrade.renaming");
		//conflicting
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)), ItemStack.class, "jei.description.upgrade.nesting", "jei.description.upgrade.conflicting");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingAdvancedUpgrade)), ItemStack.class, "jei.description.upgrade.nestingAdvanced", "jei.description.upgrade.conflicting");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositUpgrade)), ItemStack.class, "jei.description.upgrade.quickDeposit", "jei.description.upgrade.conflicting");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositPreciseUpgrade)), ItemStack.class, "jei.description.upgrade.quickDepositPrecise", "jei.description.upgrade.conflicting");
		//configurable
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingUpgrade)), ItemStack.class, "jei.description.upgrade.crafting", "jei.description.upgrade.configurable");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingSmallUpgrade)), ItemStack.class, "jei.description.upgrade.craftingSmall", "jei.description.upgrade.configurable");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingTinyUpgrade)), ItemStack.class, "jei.description.upgrade.craftingTiny", "jei.description.upgrade.configurable");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterBasicUpgrade)), ItemStack.class, "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.basic", "jei.description.upgrade.configurable");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterFuzzyUpgrade)), ItemStack.class, "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.fuzzy", "jei.description.upgrade.configurable"); //ToDo: replace ordering of filters so specifics first? Do it just for non-basic ones?
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterOreDictUpgrade)), ItemStack.class, "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.oreDict", "jei.description.upgrade.configurable");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterModSpecificUpgrade)), ItemStack.class, "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.modSpecific", "jei.description.upgrade.configurable");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterVoidUpgrade)), ItemStack.class, "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.void", "jei.description.upgrade.configurable");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterAdvancedUpgrade)), ItemStack.class, "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.advanced", "jei.description.upgrade.configurable");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterMiningUpgrade)), ItemStack.class, "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.mining", "jei.description.upgrade.configurable");
		registry.addIngredientInfo(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.restockingUpgrade)), ItemStack.class, "jei.description.upgrade.restocking", "jei.description.upgrade.configurable");

		//        IItemBlacklist itemBlacklist = registry.getJeiHelpers().getItemBlacklist();
		//        itemBlacklist.addItemToBlacklist(); //ToDo
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IGuiHelper paul = registry.getJeiHelpers().getGuiHelper();
		registry.addRecipeCategories(new AddUpgradeRecipeCategory(paul), new RemoveUpgradeRecipeCategory(paul), new IncreaseTierRecipeCategory(paul));
	}
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime run) {
	}

}
