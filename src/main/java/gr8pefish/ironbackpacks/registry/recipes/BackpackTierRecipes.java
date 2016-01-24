package gr8pefish.ironbackpacks.registry.recipes;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.register.ItemBackpackRegistry;
import gr8pefish.ironbackpacks.crafting.BackpackIncreaseTierRecipe;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Collections;
import java.util.List;

/**
 * The recipes to increase a tier of backpack TO GET THIS BACKPACK.
 */
public class BackpackTierRecipes {

    //basic backpack tier upgrade

    private static Object[] basicBackpackTierRecipe = new Object[]{
            "ioi",
            "ibi",
            "iii",
            'i', "ingotIron", 'o', "blockIron", 'b', ItemRegistry.basicBackpack};

    public static List<Object[]> getBasicBackpackTierRecipes(){
        return Collections.singletonList(basicBackpackTierRecipe);
    }

    //iron backpack tier upgrade

    private static Object[] ironBackpackTierRecipe = new Object[]{
            "ioi",
            "ibi",
            "iii",
            'i', "ingotGold", 'o', "blockGold", 'b', ItemRegistry.ironBackpack};

    public static List<Object[]> getIronBackpackTierRecipes(){
        return Collections.singletonList(ironBackpackTierRecipe);
    }

    //gold backpack tier upgrade

    private static Object[] goldBackpackTierRecipe = new Object[]{
            "iii",
            "ibi",
            "iii",
            'i', "gemDiamond", 'b', ItemRegistry.goldBackpack};

    public static List<Object[]> getGoldBackpackTierRecipes(){
        return Collections.singletonList(goldBackpackTierRecipe);
    }

    //=========================================================Registration======================================================================

    public static void registerBackpackTierRecipes(){
        for (int i = 0; i < ItemBackpackRegistry.getSize(); i++){
            IBackpack backpack = ItemBackpackRegistry.getBackpackAtIndex(i);
            if (backpack instanceof ITieredBackpack) {
                ITieredBackpack newPack = (ITieredBackpack) backpack;
                List<Object[]> recipes = newPack.getTierRecipes(null);
                if (recipes == null) break; //if you have no recipe to upgrade, you can't register that, TODO: test
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
