package gr8pefish.ironbackpacks.integration.jei.recipe.upgrade;

import gr8pefish.ironbackpacks.core.recipe.BackpackTierRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class RecipeHandlerTier implements IRecipeHandler<BackpackTierRecipe> {

    @Override
    public Class<BackpackTierRecipe> getRecipeClass() {
        return BackpackTierRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid(BackpackTierRecipe recipe) {
        return RecipeCategoryTier.ID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(BackpackTierRecipe recipe) {
        return new RecipeWrapperTier(recipe);
    }

    @Override
    public boolean isRecipeValid(BackpackTierRecipe recipe) {
        return true;
    }
}
