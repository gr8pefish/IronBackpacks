package gr8pefish.ironbackpacks.api.register;

import gr8pefish.ironbackpacks.api.recipes.IAddUpgradeRecipe;
import gr8pefish.ironbackpacks.api.recipes.IIncreaseBackpackTierRecipe;
import gr8pefish.ironbackpacks.api.recipes.IRemoveUpgradeRecipe;

import java.util.ArrayList;

public class IAllRecipesRegistry {

    //Remove upgrade recipes

    private static ArrayList<IRemoveUpgradeRecipe> upgradeRemovalRecipes = new ArrayList<>();

    public static void registerUpgradeRemovalRecipe(IRemoveUpgradeRecipe recipe){
        upgradeRemovalRecipes.add(recipe);
    }

    public static ArrayList<IRemoveUpgradeRecipe> getUpgradeRemovalRecipes(){
        return upgradeRemovalRecipes;
    }

    //Add upgrade recipes

    private static ArrayList<IAddUpgradeRecipe> upgradeAdditionRecipes = new ArrayList<>();

    public static void registerUpgradeAdditionRecipe(IAddUpgradeRecipe recipe){
        upgradeAdditionRecipes.add(recipe);
    }

    public static ArrayList<IAddUpgradeRecipe> getUpgradeAdditionRecipes(){
        return upgradeAdditionRecipes;
    }

    //Increase tier recipes

    private static ArrayList<IIncreaseBackpackTierRecipe> tierIncreaseRecipes = new ArrayList<>();

    public static void registerTierIncreaseRecipe(IIncreaseBackpackTierRecipe recipe){
        tierIncreaseRecipes.add(recipe);
    }

    public static ArrayList<IIncreaseBackpackTierRecipe> getTierIncreaseRecipes(){
        return tierIncreaseRecipes;
    }

}
