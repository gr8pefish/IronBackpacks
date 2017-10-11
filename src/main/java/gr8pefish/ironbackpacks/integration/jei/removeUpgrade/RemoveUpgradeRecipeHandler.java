package gr8pefish.ironbackpacks.integration.jei.removeUpgrade;

import gr8pefish.ironbackpacks.crafting.BackpackRemoveUpgradeRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class RemoveUpgradeRecipeHandler implements IRecipeHandler<BackpackRemoveUpgradeRecipe>{

    @Nonnull
    @Override
    public Class<BackpackRemoveUpgradeRecipe> getRecipeClass() {
        return BackpackRemoveUpgradeRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull BackpackRemoveUpgradeRecipe recipe) {
        return "ironbackpacks.removeUpgrade";
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull BackpackRemoveUpgradeRecipe recipe) {
        return new RemoveUpgradeRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@Nonnull BackpackRemoveUpgradeRecipe recipe) {
        return recipe.getRecipeOutput() != null;
    }

}
