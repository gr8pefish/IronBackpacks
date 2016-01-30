package gr8pefish.ironbackpacks.integration.jei.addUpgrade;

import gr8pefish.ironbackpacks.crafting.BackpackAddUpgradeRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class AddUpgradeRecipeHandler implements IRecipeHandler<BackpackAddUpgradeRecipe> {

//    public AddUpgradeRecipeHandler(){
//    }

    @Nonnull
    @Override
    public Class<BackpackAddUpgradeRecipe> getRecipeClass() {
        return BackpackAddUpgradeRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return "ironbackpacks.addUpgrade";
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull BackpackAddUpgradeRecipe recipe) {
        return new AddUpgradeRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@Nonnull BackpackAddUpgradeRecipe recipe) {
        return recipe.getRecipeOutput() != null;
    }
}
