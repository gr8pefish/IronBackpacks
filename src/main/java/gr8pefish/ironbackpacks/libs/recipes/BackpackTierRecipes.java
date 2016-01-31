package gr8pefish.ironbackpacks.libs.recipes;

import gr8pefish.ironbackpacks.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The recipes to increase a tier of backpack TO GET THIS BACKPACK.
 */
public class BackpackTierRecipes {

    //basic backpack tier upgrade

    private static Object[] basicBackpackStorageEmphasisTierRecipe = new Object[]{
            "ioi",
            "ibi",
            "iii",
            'i', "ingotIron", 'o', "blockIron", 'b', ItemRegistry.basicBackpack};

    private static Object[] basicBackpackUpgradeEmphasisTierRecipe = new Object[]{
            "iii",
            "ibi",
            "ioi",
            'i', "ingotIron", 'o', "blockIron", 'b', ItemRegistry.basicBackpack};

    public static List<Object[]> getBasicBackpackTierRecipes(){
        ArrayList<Object[]> recipes = new ArrayList<>();
        recipes.add(basicBackpackStorageEmphasisTierRecipe);
        recipes.add(basicBackpackUpgradeEmphasisTierRecipe);
        return recipes;
    }

    //iron backpack tier upgrade

    private static Object[] ironBackpackStorageEmphasisTierRecipe = new Object[]{
            "ioi",
            "ibi",
            "iii",
            'i', "ingotGold", 'o', "blockGold", 'b', ItemRegistry.ironBackpackStorageEmphasis};

    public static List<Object[]> getIronBackpackStorageEmphasisTierRecipes(){
        return Collections.singletonList(ironBackpackStorageEmphasisTierRecipe);
    }

    private static Object[] ironBackpackUpgradeEmphasisTierRecipe = new Object[]{
            "iii",
            "ibi",
            "ioi",
            'i', "ingotGold", 'o', "blockGold", 'b', ItemRegistry.ironBackpackUpgradeEmphasis};

    public static List<Object[]> getIronBackpackUpgradeEmphasisTierRecipes(){
        return Collections.singletonList(ironBackpackUpgradeEmphasisTierRecipe);
    }

    //gold backpack tier upgrade

    private static Object[] goldBackpackStorageEmphasisTierRecipe = new Object[]{
            "iii",
            "ibi",
            "iii",
            'i', "gemDiamond", 'b', ItemRegistry.goldBackpackStorageEmphasis};

    public static List<Object[]> getGoldBackpackStorageEmphasisTierRecipes(){
        return Collections.singletonList(goldBackpackStorageEmphasisTierRecipe);
    }

    private static Object[] goldBackpackUpgradeEmphasisTierRecipe = new Object[]{
            "iii",
            "ibi",
            "iii",
            'i', "gemDiamond", 'b', ItemRegistry.goldBackpackUpgradeEmphasis};

    public static List<Object[]> getGoldBackpackUpgradeEmphasisTierRecipes(){
        return Collections.singletonList(goldBackpackUpgradeEmphasisTierRecipe);
    }

}
