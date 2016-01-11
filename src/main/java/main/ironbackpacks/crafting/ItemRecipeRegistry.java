package main.ironbackpacks.crafting;

import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.items.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

/**
 * Register all the recipes here.
 */
public class ItemRecipeRegistry {

	/**
	 * Main method that registers all the recipes
	 */
	public static void registerItemRecipes() {

		registerBasicRecipe(ItemRegistry.basicBackpack, ConfigHandler.basicBackpackRecipe); //register the basic backpack as a recipe
		registerUpgradeRecipes(); //register the recipes to make the upgrades
		registerMiscRecipes(); //register the miscellaneous items' recipes

		RecipeSorter.register("RemoveUpgrade", BackpackRemoveUpgradeRecipe.class, RecipeSorter.Category.SHAPELESS, ""); //register my special recipe
		RecipeSorter.register("BackpackUpgrade", BackpackCraftWithUpgradeRecipe.class, RecipeSorter.Category.SHAPELESS, ""); //register my special recipe
		RecipeSorter.register("BackpackTier", BackpackIncreaseTierRecipe.class, RecipeSorter.Category.SHAPED, ""); //register my special recipe
		registerBackpackUpgradeAndRemovalRecipes(); //register the recipes to add upgrades to backpacks and to remove upgrades
		registerBackpackTierRecipes(); //register the recipes to upgrade a backpack to the next tier
	}

	//=================================================================================Helper Registers==========================================================

	//Registers the miscellaneous recipes
	private static void registerMiscRecipes(){
		registerBasicRecipe(ItemRegistry.nest, ConfigHandler.nestRecipe);
		registerBasicRecipe(ItemRegistry.upgradeCore, ConfigHandler.upgradeCoreRecipe);

		registerShapelessRecipe(ItemRegistry.jeweledFeather, ConfigHandler.jeweledFeatherRecipe);
		registerShapelessRecipe(ItemRegistry.treatedLeather, ConfigHandler.treatedLeatherRecipe);
	}

	//Registers the recipes to create the upgrades
	private static void registerUpgradeRecipes(){
		registerBasicRecipe(ItemRegistry.buttonUpgrade, ConfigHandler.buttonUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.nestingUpgrade, ConfigHandler.nestingUpgradeRecipe);
		if (ConfigHandler.renamingUpgradeRequired){
			registerBasicRecipe(ItemRegistry.renamingUpgrade, ConfigHandler.renamingUpgradeRecipe);
		}
		registerBasicRecipe(ItemRegistry.damageBarUpgrade, ConfigHandler.damageBarUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.filterBasicUpgrade, ConfigHandler.filterBasicUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.filterFuzzyUpgrade, ConfigHandler.filterFuzzyUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.filterOreDictUpgrade, ConfigHandler.filterOreDictUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.filterModSpecificUpgrade, ConfigHandler.filterModSpecificUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.hopperUpgrade, ConfigHandler.hopperUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.condenserUpgrade, ConfigHandler.condenserUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.condenserSmallUpgrade, ConfigHandler.condenserSmallUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.condenserTinyUpgrade, ConfigHandler.condenserTinyUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.keepOnDeathUpgrade, ConfigHandler.keepOnDeathUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.additionalUpgradePointsUpgrade, ConfigHandler.additionalUpgradePointsUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.quickDepositUpgrade, ConfigHandler.quickDepositUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.filterAdvancedUpgrade, ConfigHandler.filterAdvancedUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.nestingAdvancedUpgrade, ConfigHandler.nestingAdvancedUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.depthUpgrade, ConfigHandler.depthUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.filterMiningUpgrade, ConfigHandler.filterMiningUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.quickDepositPreciseUpgrade, ConfigHandler.quickDepositPreciseUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.filterVoidUpgrade, ConfigHandler.filterVoidUpgradeRecipe);
	}

	//Registers the recipes that allow you to shapelessly craft an upgrade with a backpack to add/remove said upgrade
	private static void registerBackpackUpgradeAndRemovalRecipes(){
		ArrayList<Item> backpacks = ItemRegistry.getBackpacks();
		ArrayList<Item> upgrades = ItemRegistry.getUpgrades();

		for (Item backpack : backpacks){
			GameRegistry.addRecipe(new BackpackRemoveUpgradeRecipe(new ItemStack(backpack), new ItemStack(backpack)));
			for (Item upgrade : upgrades){
				GameRegistry.addRecipe(new BackpackCraftWithUpgradeRecipe(new ItemStack(backpack), new ItemStack(upgrade), new ItemStack(backpack)));
			}
		}
	}

	//Registers the recipes that allow you to upgrade a backpack to a new tier (ex: iron -> gold)
	private static void registerBackpackTierRecipes(){
		ArrayList<Item> backpacks = ItemRegistry.getBackpacks();

		String[] ironBackpackRecipe = ConfigHandler.ironBackpackRecipe;
		String[] goldBackpackRecipe = ConfigHandler.goldBackpackRecipe;
		String[] diamondBackpackRecipe = ConfigHandler.diamondBackpackRecipe;
		String[][] recipes = {ironBackpackRecipe, goldBackpackRecipe, diamondBackpackRecipe};

		for (int i = 1; i < backpacks.size(); i++){ //start at 1 b/c first backpack can't be upgraded
			Object[] theRecipe = getOreRecipe(recipes[i-1]);
			GameRegistry.addRecipe(new BackpackIncreaseTierRecipe(new ItemStack(backpacks.get(i)), theRecipe));
		}
	}


	//=====================================================================================Register Helper Methods=======================================================

	/**
	 * Registers a basic shaped recipe.
	 * @param output - the output item
	 * @param recipe - the recipe string
	 */
	private static void registerBasicRecipe(Item output, String[] recipe){
		registerBasicOreRecipe(output, recipe); //currently just using ore recipes, but keeping this method for future ease of change
	}

	/**
	 * Registers a shapeless recipe.
	 * @param output - the output item
	 * @param recipe - the recipe string
	 */
	private static void registerShapelessRecipe(Item output, String[] recipe) {
		Object[] theRecipe = getShapelessOreRecipe(recipe);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(output), theRecipe));
	}

	/**
	 * Registers a shaped ore dictionary recipe.
	 * @param output - the output item
	 * @param recipe - the recipe string
	 */
	private static void registerBasicOreRecipe(Item output, String[] recipe){
		Object[] theRecipe = getOreRecipe(recipe);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output), theRecipe));
	}

	//====================================================================================Ore Dictionary Recipe Helper Methods=======================================================

	/**
	 * Gets a shaped ore recipe (from a config input string).
	 * @param input - the recipe as a string array
	 * @return - an object array to be used to create the shaped ore recipe
	 */
	private static Object[] getOreRecipe(String[] input){
		Object[] returnArray = initOreArray(input);
		for (int i = 0; i < (input.length * 2); i++){
			if (i % 2 == 0) {
				returnArray[3+i] = (char) ('0' + (i/2));
			}else if (input[i/2].trim().equals("none")){
				returnArray[3+i] = "none";
			}else{ //return the oreDict entry or the item/block
				returnArray[3+i] = isOreDict(input[i/2].trim()) ? input[i/2].trim() : isItem(input[i/2].trim()) ? getItem(input[i/2].trim()) : getBlock(input[i/2].trim()); //Double ternary!? Say what?
			}
		}
		return returnArray;
	}

	/**
	 * Gets the shapeless ore recipe (from a config input string).
	 * @param input - the recipe as a string array
	 * @return - an object array to be used to create the shapeless ore recipe
	 */
	private static Object[] getShapelessOreRecipe(String[] input){
		Object[] returnArray = new Object[input.length];
		for (int i = 0; i < input.length; i++){
			if (input[i].contains(";")){
				String[] splitString = input[i].split(";");
				if (isItem(splitString[0])) {
					returnArray[i] = new ItemStack(getItem(splitString[0]), 1, Integer.valueOf(splitString[1]));
				}else{
					returnArray[i] = new ItemStack(getBlock(splitString[0]), 1, Integer.valueOf(splitString[1]));
				}
			}else{
				returnArray[i] = isOreDict(input[i].trim()) ? input[i].trim() : isItem(input[i].trim()) ? getItem(input[i].trim()) : getBlock(input[i].trim());
			}
		}
		return returnArray;
	}

	//=============================================================================Helper methods for the above methods=========================================

	/**
	 * Gets the array in proper form (3x3, that the ShapedOreRecipe will recognize) from a line of inputs.
	 * @param input - the config string recipe
	 * @return - object array to be used to create the recipe
	 */
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

	/**
	 * Checks if the item has an oreDictionary entry
	 * @param input - the item to check
	 * @return - boolean success
	 */
	@SuppressWarnings("unchecked")
	private static boolean isOreDict(String input){
		try {
			Field nameToId = OreDictionary.class.getDeclaredField("nameToId");
			nameToId.setAccessible(true);
			Map<String, Integer> nameToIdMap = (Map<String, Integer>) nameToId.get(null);
			return nameToIdMap.get(input) != null;
		} catch (NoSuchFieldException e) {
			// Catch 'em all!
		} catch (IllegalAccessException e) {
			// Catch 'em all!
		}
		return false;
	}

	/**
	 * Checks if the string is an item or a block.
	 * @param item - the string input
	 * @return - boolean true if it is an item, false otherwise (block)
	 */
	private static boolean isItem(String item){
		if (item.contains(".")){
			String[] splitString = item.split("\\.");
			if (splitString[0].equals("items")){
				return true;
			}else if (splitString[0].equals("blocks")){
				return false;
			}
		}
		return false;
	}

	/**
	 * Gets the item entry by cutting out the 'items.' from the string
	 * @param item - the item string
	 * @return - an Item from the String
	 */
	private static Item getItem(String item){
		if (item.contains(".")) {
			String[] splitString = item.split("\\.");
			return (Item) Item.itemRegistry.getObject(new ResourceLocation(splitString[1]));
		}
		return null;
	}

	/**
	 * Gets the block entry by cutting out the 'blocks.' from the string
	 * @param item - the block string
	 * @return - a Block from the String
	 */
	private static Block getBlock(String item){ //no isBlock() method because if it isn't an item it must be a block
		if (item.contains(".")) {
			String[] splitString = item.split("\\.");
			return (Block) Block.blockRegistry.getObject(new ResourceLocation(splitString[1]));
		}
		return null;
	}


}
