package gr8pefish.ironbackpacks.registry.recipes;

import gr8pefish.ironbackpacks.api.register.ItemCraftingRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ItemCraftingRecipes {

    private static ShapelessOreRecipe jeweledFeatherRecipe = new ShapelessOreRecipe(new ItemStack(ItemRegistry.craftingItem, 1, ItemCraftingRegistry.getIndexOf(ItemRegistry.jeweledFeather)),
            Items.feather, "nuggetGold", "nuggetGold");

    private static ShapedOreRecipe nestRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.craftingItem, 1, ItemCraftingRegistry.getIndexOf(ItemRegistry.nest)),
            "sss",
            "ses",
            "sss",
            's', "stickWood", 'e', Items.egg);

    private static ShapelessOreRecipe treatedLeatherRecipe = new ShapelessOreRecipe(new ItemStack(ItemRegistry.craftingItem, 1, ItemCraftingRegistry.getIndexOf(ItemRegistry.treatedLeather)),
            Items.leather, Items.water_bucket, "dyeLime", Items.flint);

    private static ShapedOreRecipe upgradeCoreRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.craftingItem, 1, ItemCraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)),
            "tst",
            "sps",
            "tst",
            't', new ItemStack(ItemRegistry.craftingItem, 1, ItemCraftingRegistry.getIndexOf(ItemRegistry.treatedLeather)),
            's', Items.string, 'p', Items.paper);

    public static void registerItemCraftingRecipes(){
        GameRegistry.addRecipe(jeweledFeatherRecipe);
        GameRegistry.addRecipe(nestRecipe);
        GameRegistry.addRecipe(treatedLeatherRecipe);
        GameRegistry.addRecipe(upgradeCoreRecipe);
    }
}
