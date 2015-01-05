package main.ironbackpacks.crafting;

/*
 * General place to do all your item related recipe stuff and things.
 */

import cpw.mods.fml.common.registry.GameRegistry;
import main.ironbackpacks.crafting.BackpackUpgradeRecipe;
import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.util.ConfigHandler;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Arrays;
import java.util.List;


public class ItemRecipeRegistry {

	public static String[] oreNames = OreDictionary.getOreNames();
	public static List ores = Arrays.asList(oreNames);

	public static void registerItemRecipes() {

//		System.out.println(Item.itemRegistry.getObject("ironbackpacks:basicBackpack").toString());

		registerBasicRecipe(ItemRegistry.basicBackpack, ConfigHandler.basicBackpackRecipe);

		RecipeSorter.register("BackpackUpgrade", BackpackUpgradeRecipe.class, RecipeSorter.Category.SHAPELESS, "");
		RecipeSorter.register("BackpackTier", BackpackTierRecipe.class, RecipeSorter.Category.SHAPED, "");
		registerUpgradeRecipes();
		registerTierRecipes();
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

	public static void registerTierRecipes(){
		Item[] backpacks = ItemRegistry.getBackpacks();

		String[] ironBackpackRecipe = ConfigHandler.ironBackpackRecipe;
		String[] goldBackpackRecipe = ConfigHandler.goldBackpackRecipe;
		String[] diamondBackpackRecipe = ConfigHandler.diamondBackpackRecipe;
		String[][] recipes = {ironBackpackRecipe, goldBackpackRecipe, diamondBackpackRecipe};


//		Item[] tierRecipeItems = new Item[3];
//		tierRecipeItems[0] = Items.iron_ingot;
//		tierRecipeItems[1] = Items.gold_ingot;
//		tierRecipeItems[2] = Items.diamond;

		for (int i = 1; i < backpacks.length; i++){ //start at 1 b/c first backpack can't be upgraded
			Object[] theRecipe = getOreRecipe(recipes[i-1]);
			GameRegistry.addRecipe(new BackpackTierRecipe(new ItemStack(backpacks[i]), theRecipe));
//			GameRegistry.addRecipe(new BackpackTierRecipe(new ItemStack(backpacks[i]),
//					new ItemStack(tierRecipeItems[i-1]), new ItemStack(tierRecipeItems[i-1]), new ItemStack(tierRecipeItems[i-1]),
//							new ItemStack(tierRecipeItems[i-1]), new ItemStack(backpacks[i-1]), new ItemStack(tierRecipeItems[i-1]),
//									new ItemStack(tierRecipeItems[i-1]), new ItemStack(tierRecipeItems[i-1]), new ItemStack(tierRecipeItems[i-1])));
		}
	}

	private static void registerBasicRecipe(Item output, String[] recipe){
		if (hasOreDictItem(recipe)){
			registerBasicOreRecipe(output, recipe);
		}else{
			registerBasicNonOreRecipe(output, recipe);
		}
	}

	private static void registerBasicNonOreRecipe(Item output, String[] recipe){
		ItemStack[] theRecipe = new ItemStack[9];
		for (int i = 0; i < theRecipe.length; i++){
			theRecipe[i] = new ItemStack((Item) Item.itemRegistry.getObject(recipe[i].trim()));
		}

		GameRegistry.addRecipe(new ShapedRecipes(3,3, theRecipe, new ItemStack(output)));
	}

	private static void registerBasicOreRecipe(Item output, String[] recipe){
		Object[] theRecipe = getOreRecipe(recipe);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output), theRecipe));
	}

	private static Boolean hasOreDictItem(String[] input){
		boolean useOreDic = false;
		for (String item : input){
			if (ores.contains(item.trim())){
				useOreDic = true;
			}
		}
		return useOreDic;
	}

	protected static boolean isOreDict(String input){
		return ores.contains(input.trim());
	}

	private static Object[] getOreRecipe(String[] input){
		Object[] returnArray = initOreArray(input);
		for (int i = 0; i < (input.length * 2); i++){
			if (i % 2 == 0) {
				returnArray[3+i] = (char) ('0' + (i/2));
			}else if (input[i/2].trim().equals("none")){
				returnArray[3+i] = "none";
			}else{
				returnArray[3+i] = isOreDict(input[i/2].trim()) ? input[i/2].trim() : (Item) Item.itemRegistry.getObject(input[i/2].trim());
			}
		}
		return returnArray;
	}

	private static Object[] initOreArray(String[] input){
		Object[] array = new Object[21];

		for (int j = 0; j < 3; j++) { //each chunk, ex: "012", "345", "678"
			String theChunk = "";
			for (int i = 0; i < 3; i++) {
				if (input[(j*3) + i].trim().equals("none")){
					theChunk += " "; //empty spot in crafting grid
				}else{
					theChunk += Integer.toString((j*3) + i);
				}
			}
			array[j] = theChunk;
		}
		return array;
	}


}
