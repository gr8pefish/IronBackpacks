package main.ironbackpacks.crafting;

/*
 * General place to do all your item related recipe stuff and things.
 */

import cpw.mods.fml.common.registry.GameRegistry;
import main.ironbackpacks.crafting.BackpackUpgradeRecipe;
import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.util.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ItemRecipeRegistry {

	public static String[] oreNames = OreDictionary.getOreNames();
	public static List ores = Arrays.asList(oreNames);

	public static void registerItemRecipes() {

		registerBasicRecipe(ItemRegistry.basicBackpack, ConfigHandler.basicBackpackRecipe);
		registerUpgradeRecipes();
		registerMiscRecipes();

		RecipeSorter.register("BackpackUpgrade", BackpackUpgradeRecipe.class, RecipeSorter.Category.SHAPELESS, "");
		RecipeSorter.register("BackpackTier", BackpackTierRecipe.class, RecipeSorter.Category.SHAPED, "");
		registerBackpackUpgradeRecipes();
		registerBackpackTierRecipes();
	}

	public static  void registerMiscRecipes(){
		registerBasicRecipe(ItemRegistry.nest, ConfigHandler.nestRecipe);
		registerBasicRecipe(ItemRegistry.upgradeCore, ConfigHandler.upgradeCoreRecipe);
	}

	public static void registerUpgradeRecipes(){
		registerBasicRecipe(ItemRegistry.buttonUpgrade, ConfigHandler.buttonUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.nestingUpgrade, ConfigHandler.nestingUpgradeRecipe);
		if (ConfigHandler.renamingUpgradeRequired){
			registerBasicRecipe(ItemRegistry.renamingUpgrade, ConfigHandler.renamingUpgradeRecipe);
		}
		registerBasicRecipe(ItemRegistry.damageBarUpgrade, ConfigHandler.damageBarUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.filterUpgrade, ConfigHandler.filterUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.hopperUpgrade, ConfigHandler.hopperUpgradeRecipe);
		registerBasicRecipe(ItemRegistry.condenserUpgrade, ConfigHandler.condenserUpgradeRecipe);
	}

	public static void registerBackpackUpgradeRecipes(){
		ArrayList<Item> backpacks = ItemRegistry.getBackpacks();
		ArrayList<Item> upgrades = ItemRegistry.getUpgrades();

		for (Item backpack : backpacks){
			for (Item upgrade : upgrades){
				GameRegistry.addRecipe(new BackpackUpgradeRecipe(new ItemStack(backpack), new ItemStack(upgrade), new ItemStack(backpack)));
			}
		}
	}

	public static void registerBackpackTierRecipes(){
		ArrayList<Item> backpacks = ItemRegistry.getBackpacks();

		String[] ironBackpackRecipe = ConfigHandler.ironBackpackRecipe;
		String[] goldBackpackRecipe = ConfigHandler.goldBackpackRecipe;
		String[] diamondBackpackRecipe = ConfigHandler.diamondBackpackRecipe;
		String[][] recipes = {ironBackpackRecipe, goldBackpackRecipe, diamondBackpackRecipe};

		for (int i = 1; i < backpacks.size(); i++){ //start at 1 b/c first backpack can't be upgraded
			Object[] theRecipe = getOreRecipe(recipes[i-1]);
			GameRegistry.addRecipe(new BackpackTierRecipe(new ItemStack(backpacks.get(i)), theRecipe));
		}
	}

	private static void registerBasicRecipe(Item output, String[] recipe){
//		if (hasOreDictItem(recipe)){
		registerBasicOreRecipe(output, recipe);
//		}else{
//			registerBasicNonOreRecipe(output, recipe);
//		}
	}

//	private static void registerBasicNonOreRecipe(Item output, String[] recipe){
//		ItemStack[] theRecipe = new ItemStack[9];
//		for (int i = 0; i < theRecipe.length; i++){
//			theRecipe[i] = new ItemStack((Item) Item.itemRegistry.getObject(recipe[i].trim()));
//		}
//
//		GameRegistry.addRecipe(new ShapedRecipes(3,3, theRecipe, new ItemStack(output)));
//	}

	private static void registerBasicOreRecipe(Item output, String[] recipe){
		Object[] theRecipe = getOreRecipe(recipe);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output), theRecipe));
	}

//	private static Boolean hasOreDictItem(String[] input){
//		boolean useOreDic = false;
//		for (String item : input){
//			if (ores.contains(item.trim())){
//				useOreDic = true;
//			}
//		}
//		return useOreDic;
//	}

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
				returnArray[3+i] = isOreDict(input[i/2].trim()) ? input[i/2].trim() : isItem(input[i/2].trim()) ? getItem(input[i/2].trim()) : getBlock(input[i/2].trim());; //(Item) Item.itemRegistry.getObject(input[i/2].trim());
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

	private static Block getBlock(String item){
		if (item.contains(".")) {
			String[] splitString = item.split("\\.");
			return (Block) Block.blockRegistry.getObject(splitString[1]);
		}
		return null;
	}

	private static Item getItem(String item){
		if (item.contains(".")) {
			String[] splitString = item.split("\\.");
			return (Item) Item.itemRegistry.getObject(splitString[1]);
		}
		return null;
	}


}
