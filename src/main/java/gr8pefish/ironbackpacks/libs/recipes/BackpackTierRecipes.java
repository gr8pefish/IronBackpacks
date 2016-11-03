package gr8pefish.ironbackpacks.libs.recipes;

import gr8pefish.ironbackpacks.api.register.ItemICraftingRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The recipes to increase a tier of backpack TO GET THIS BACKPACK.
 */
public class BackpackTierRecipes {

    //basic backpack tier upgrade

    private static Object[] basicBackpackStorageEmphasisTierRecipe = new Object[]{
            "ici",
            "ibi",
            "iii",
            'i', "ingotIron", 'c', "chestWood", 'b', ItemRegistry.basicBackpack};

    private static Object[] basicBackpackUpgradeEmphasisTierRecipe = new Object[]{
            "iui",
            "ibi",
            "iii",
            'i', "ingotIron", 'b', ItemRegistry.basicBackpack,
            'u', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore))};

    public static List<Object[]> getBasicBackpackTierRecipes(){
        ArrayList<Object[]> recipes = new ArrayList<>();
        recipes.add(basicBackpackStorageEmphasisTierRecipe);
        recipes.add(basicBackpackUpgradeEmphasisTierRecipe);
        return recipes;
    }

    //iron backpack tier upgrade

    private static Object[] ironBackpackStorageEmphasisTierRecipe = new Object[]{
            "ici",
            "ibi",
            "iii",
            'i', "ingotGold", 'c', "chestWood", 'b', ItemRegistry.ironBackpackStorageEmphasis};

    public static List<Object[]> getIronBackpackStorageEmphasisTierRecipes(){
        return Collections.singletonList(ironBackpackStorageEmphasisTierRecipe);
    }

    private static Object[] ironBackpackUpgradeEmphasisTierRecipe = new Object[]{
            "iui",
            "ibi",
            "iii",
            'i', "ingotGold", 'b', ItemRegistry.ironBackpackUpgradeEmphasis,
            'u', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore))};

    public static List<Object[]> getIronBackpackUpgradeEmphasisTierRecipes(){
        return Collections.singletonList(ironBackpackUpgradeEmphasisTierRecipe);
    }

    //gold backpack tier upgrade

    private static Object[] goldBackpackStorageEmphasisTierRecipe = new Object[]{
            "iii",
            "cbc",
            "iii",
            'i', "gemDiamond", 'c', "chestWood", 'b', ItemRegistry.goldBackpackStorageEmphasis, 'g', Blocks.GLASS};

    public static List<Object[]> getGoldBackpackStorageEmphasisTierRecipes(){
        return Collections.singletonList(goldBackpackStorageEmphasisTierRecipe);
    }

    private static Object[] goldBackpackUpgradeEmphasisTierRecipe = new Object[]{
            "iii",
            "ubu",
            "iii",
            'i', "gemDiamond", 'b', ItemRegistry.goldBackpackUpgradeEmphasis, 'g', Blocks.GLASS,
            'u', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore))};

    public static List<Object[]> getGoldBackpackUpgradeEmphasisTierRecipes(){
        return Collections.singletonList(goldBackpackUpgradeEmphasisTierRecipe);
    }

}
