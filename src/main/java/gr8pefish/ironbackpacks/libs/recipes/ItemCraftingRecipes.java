package gr8pefish.ironbackpacks.libs.recipes;

import gr8pefish.ironbackpacks.api.register.ItemICraftingRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemCraftingRecipes {

    private static ShapedOreRecipe upgradeCoreRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)),
            "sps",
            "plp",
            "sps",
            'l', Items.leather,
            's', Items.string, 'p', Items.paper);

    public static void registerItemCraftingRecipes(){
        GameRegistry.addRecipe(upgradeCoreRecipe);
    }
}
