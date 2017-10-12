package gr8pefish.ironbackpacks.libs.recipes;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.register.ItemICraftingRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemCraftingRecipes {

    private static IRecipe upgradeCoreRecipe = new ShapedOreRecipe(null, new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)),
            "sps",
            "plp",
            "sps",
            'l', "leather",
            's', "string",
            'p', "paper").setRegistryName(Constants.MODID, "upgrade_core");

    public static void registerItemCraftingRecipes(){
        ForgeRegistries.RECIPES.register(upgradeCoreRecipe);
    }
}
