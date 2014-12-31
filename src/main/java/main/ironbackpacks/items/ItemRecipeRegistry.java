package main.ironbackpacks.items;

/*
 * General place to do all your item related recipe stuff and things.
 */

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemRecipeRegistry {

	public static void registerItemRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.singleBackpack),  new Object[]{"LSL", "LSL", "LLL", 'L', Items.leather, 'S', "plankWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.doubleBackpack),  new Object[]{"LLL", "LSL", "LLL", 'L', Items.leather, 'S', "plankWood"}));
	}
}
