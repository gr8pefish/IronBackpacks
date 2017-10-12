package gr8pefish.ironbackpacks.integration.jei;

import gr8pefish.ironbackpacks.core.RegistrarIronBackpacks;
import gr8pefish.ironbackpacks.core.recipe.BackpackTierRecipe;
import gr8pefish.ironbackpacks.integration.jei.recipe.upgrade.RecipeCategoryTier;
import gr8pefish.ironbackpacks.integration.jei.recipe.upgrade.RecipeWrapperTier;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class IronBackpacksJEIPlugin extends BlankModPlugin {

    public static IJeiRuntime runtime;
    public static IJeiHelpers helpers;

    @Override
    public void register(IModRegistry registry) {
        helpers = registry.getJeiHelpers();

        registry.handleRecipes(BackpackTierRecipe.class, RecipeWrapperTier::new, RecipeCategoryTier.ID);
        registry.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), RecipeCategoryTier.ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new RecipeCategoryTier(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.useNbtForSubtypes(
                RegistrarIronBackpacks.BACKPACK,
                RegistrarIronBackpacks.UPGRADE
        );
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }
}
