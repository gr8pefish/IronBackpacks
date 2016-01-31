package gr8pefish.ironbackpacks.integration.jei.increaseTier;

import gr8pefish.ironbackpacks.crafting.BackpackIncreaseTierRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class IncreaseTierRecipeHandler implements IRecipeHandler<BackpackIncreaseTierRecipe> {

    @Nonnull
    @Override
    public Class<BackpackIncreaseTierRecipe> getRecipeClass() {
        return BackpackIncreaseTierRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return "ironbackpacks.increaseTier";
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull BackpackIncreaseTierRecipe recipe) {
        return new IncreaseTierRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@Nonnull BackpackIncreaseTierRecipe recipe) {
        return recipe.getRecipeOutput() != null;
    }

}

