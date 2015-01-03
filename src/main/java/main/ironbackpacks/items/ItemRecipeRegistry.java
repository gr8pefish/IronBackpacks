package main.ironbackpacks.items;

/*
 * General place to do all your item related recipe stuff and things.
 */

import cpw.mods.fml.common.registry.GameRegistry;
import main.ironbackpacks.crafting.BackpackUpgradeRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;


public class ItemRecipeRegistry {

	public static void registerItemRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.singleBackpack),  new Object[]{"LSL", "LSL", "LLL", 'L', Items.leather, 'S', "plankWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.doubleBackpack),  new Object[]{"LLL", "LSL", "LLL", 'L', Items.leather, 'S', "plankWood"}));

		RecipeSorter.register("BackpackUpgrade", BackpackUpgradeRecipe.class, RecipeSorter.Category.SHAPELESS, "");
		registerUpgradeRecipes();
	}

	public static void registerUpgradeRecipes(){
		Item[] backpacks = ItemRegistry.getBackpacks();
		Item[] upgrades = ItemRegistry.getUpgrades();

		for (Item backpack : backpacks){
			for (Item upgrade : upgrades){
				GameRegistry.addRecipe(new BackpackUpgradeRecipe(new ItemStack(backpack), new ItemStack(upgrade), new ItemStack(backpack)));
			}
		}
	}


}
