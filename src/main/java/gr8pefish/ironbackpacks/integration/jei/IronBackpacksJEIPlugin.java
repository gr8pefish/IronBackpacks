package gr8pefish.ironbackpacks.integration.jei;

import gr8pefish.ironbackpacks.core.ModObjects;
import gr8pefish.ironbackpacks.integration.jei.recipe.upgrade.RecipeCategoryTier;
import gr8pefish.ironbackpacks.integration.jei.recipe.upgrade.RecipeHandlerTier;
import mezz.jei.api.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class IronBackpacksJEIPlugin extends BlankModPlugin {

    public static IJeiRuntime runtime;
    public static IJeiHelpers helpers;

    @Override
    public void register(IModRegistry registry) {
        helpers = registry.getJeiHelpers();

        registry.addRecipeCategories(
                new RecipeCategoryTier(helpers.getGuiHelper())
        );

        registry.addRecipeHandlers(
                new RecipeHandlerTier()
        );

        registry.addRecipeCategoryCraftingItem
                (new ItemStack(Blocks.CRAFTING_TABLE),
                RecipeCategoryTier.ID
        );
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.useNbtForSubtypes(
                ModObjects.BACKPACK,
                ModObjects.UPGRADE
        );
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }
}
