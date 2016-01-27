package gr8pefish.ironbackpacks.registry.recipes;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.register.ItemBackpackRegistry;
import gr8pefish.ironbackpacks.crafting.BackpackIncreaseTierRecipe;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
            "i i",
            'i', "gemDiamond", 'b', ItemRegistry.goldBackpackStorageEmphasis};

    public static List<Object[]> getGoldBackpackStorageEmphasisTierRecipes(){
        return Collections.singletonList(goldBackpackStorageEmphasisTierRecipe);
    }

    private static Object[] goldBackpackUpgradeEmphasisTierRecipe = new Object[]{
            "i i",
            "ibi",
            "iii",
            'i', "gemDiamond", 'b', ItemRegistry.goldBackpackUpgradeEmphasis};

    public static List<Object[]> getGoldBackpackUpgradeEmphasisTierRecipes(){
        return Collections.singletonList(goldBackpackUpgradeEmphasisTierRecipe);
    }

    //=========================================================Registration======================================================================

    public static void registerBackpackTierRecipes(){
        for (int i = 0; i < ItemBackpackRegistry.getSize(); i++){
            IBackpack backpack = ItemBackpackRegistry.getBackpackAtIndex(i);
            if (backpack instanceof ITieredBackpack) {
                ITieredBackpack newPack = (ITieredBackpack) backpack;
                List<Object[]> recipes = newPack.getTierRecipes(null);
                if (recipes == null) break; //if you have no recipe to upgrade, you can't register that
                List<ITieredBackpack> upgradedPacks = newPack.getBackpacksAbove(null); //unused item stack parameter
                if (!recipes.isEmpty() && upgradedPacks != null && upgradedPacks.size() == recipes.size()) {
                    for (int j = 0; j < recipes.size(); j++) {
                        GameRegistry.addRecipe(new BackpackIncreaseTierRecipe(new ItemStack((ItemBackpack)upgradedPacks.get(j)), recipes.get(j))); //hardcoded to ItemBackpack
                    }
                }
            }
        }
    }

}
