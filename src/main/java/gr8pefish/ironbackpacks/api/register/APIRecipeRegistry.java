package gr8pefish.ironbackpacks.api.register;

import gr8pefish.ironbackpacks.api.crafting.IAddUpgradeRecipe;
import gr8pefish.ironbackpacks.api.crafting.IIncreaseBackpackTierRecipe;
import gr8pefish.ironbackpacks.api.crafting.IRemoveUpgradeRecipe;

import java.util.ArrayList;

public class APIRecipeRegistry {

    public static ArrayList<IRemoveUpgradeRecipe> upgradeRemovalRecipes;

    public static void registerUpgradeRemovalRecipe(IRemoveUpgradeRecipe recipe){
        upgradeRemovalRecipes.add(recipe);
    }

    public static ArrayList<IRemoveUpgradeRecipe> getUpgradeRemovalRecipes(){
        return upgradeRemovalRecipes;
    }

    public static ArrayList<IAddUpgradeRecipe> upgradeAdditionRecipes;

    public static void registerUpgradeAdditionRecipe(IAddUpgradeRecipe recipe){
        upgradeAdditionRecipes.add(recipe);
    }

    public static ArrayList<IAddUpgradeRecipe> getUpgradeAdditionRecipes(){
        return upgradeAdditionRecipes;
    }

    public static ArrayList<IIncreaseBackpackTierRecipe> tierIncreaseRecipes;

    public static void registerTierIncreaseRecipe(IIncreaseBackpackTierRecipe recipe){
        tierIncreaseRecipes.add(recipe);
    }

    public static ArrayList<IIncreaseBackpackTierRecipe> getTierIncreaseRecipes(){
        return tierIncreaseRecipes;
    }

}
