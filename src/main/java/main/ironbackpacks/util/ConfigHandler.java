package main.ironbackpacks.util;

import main.ironbackpacks.items.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;


public class ConfigHandler {

    public static int[] basicBackpack;
    public enum enumBasicBackpack{ //TODO - there must be a better way...
        upgradeSlots(basicBackpack[0]),
        sizeX(basicBackpack[1]),
        sizeY(basicBackpack[2]);
        private int value;
        private enumBasicBackpack(int value){
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    public static int[] ironBackpack;
    public enum enumIronBackpack{
        upgradeSlots(ironBackpack[0]),
        sizeX(ironBackpack[1]),
        sizeY(ironBackpack[2]);
        private int value;
        private enumIronBackpack(int value){
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    public static int[] goldBackpack;
    public enum enumGoldBackpack{
        upgradeSlots(goldBackpack[0]),
        sizeX(goldBackpack[1]),
        sizeY(goldBackpack[2]);
        private int value;
        private enumGoldBackpack(int value){
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    public static int[] diamondBackpack;
    public enum enumDiamondBackpack{
        upgradeSlots(diamondBackpack[0]),
        sizeX(diamondBackpack[1]),
        sizeY(diamondBackpack[2]);
        private int value;
        private enumDiamondBackpack(int value){
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    private static final int valuesToLoad = 3;

    public static String[] basicBackpackRecipe;
    public static String[] ironBackpackRecipe;
    public static String[] goldBackpackRecipe;
    public static String[] diamondBackpackRecipe;

    public static void init(File file){
        Configuration config = new Configuration(file);

        basicBackpack = new int[valuesToLoad];
        ironBackpack = new int[valuesToLoad];
        goldBackpack = new int[valuesToLoad];
        diamondBackpack = new int[valuesToLoad];

        String[] basicRecipe = {"leather", "leather", "leather", "leather", "plankWood", "leather", "leather", "leather", "leather"};
        String[] ironRecipe = {"iron_ingot", "iron_ingot", "iron_ingot", "iron_ingot", "ironbackpacks:basicBackpack", "iron_ingot", "iron_ingot", "iron_ingot", "iron_ingot"};
        String[] goldRecipe = {"gold_ingot", "gold_ingot", "gold_ingot", "gold_ingot", "ironbackpacks:ironBackpack", "gold_ingot", "gold_ingot", "gold_ingot", "gold_ingot"};
        String[] diamondRecipe = {"diamond", "diamond", "diamond", "diamond", "ironbackpacks:goldBackpack", "diamond", "diamond", "diamond", "diamond"};


        config.load();

        //======================Upgrade Slots========================
//        ConfigCategory upgradeSlotsCategory = config.getCategory("Upgrade Slots");
//        upgradeSlotsCategory.setComment(" The number of upgrade slots for each type of backpack.");
        //TODO - the categories are sorted alphabetically instead of in this order, so it looks weird - new TreeMap of categories using reflection
        //TODO - config.setCategoryPropertyOrder [maybe, if I deem it necessary]
        basicBackpack[0] = config.get("Basic Backpack", "Upgrade Slots", 0, "The number of upgrades on the backpack. Default 0.").getInt();
        basicBackpack[1] = config.get("Basic Backpack", "Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        basicBackpack[2] = config.get("Basic Backpack", "Number of Rows", 2, "The size of the backpack. Between 1 and 7. Default 2.").getInt();
        basicBackpackRecipe = config.get("Basic Backpack", "Recipe", basicRecipe, "The recipe for the backpack. Each line is the next item in the crafting grid (so 9 lines). " +
                "Supports ore dictionary. Write in 'none' (without the parenthesis) for a blank spot in the crafting grid. Default is a plank surrounded by leather.").getStringList();


        ironBackpack[0] = config.get("Iron Backpack", "Upgrade Slots", 1, "The number of upgrades on the backpack. Default 1.").getInt();
        ironBackpack[1] = config.get("Iron Backpack", "Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        ironBackpack[2] = config.get("Iron Backpack", "Number of Rows", 4, "The size of the backpack. Between 1 and 7. Default 4.").getInt();
        ironBackpackRecipe = config.get("Iron Backpack", "Recipe", ironRecipe, "The recipe to upgrade the backpack. Each line is the next item in the crafting grid (so 9 lines). " +
                "Supports ore dictionary. Write in 'none' (without the parenthesis) for a blank spot in the crafting grid. Default is ironbackpacks:basicBackpack surrounded by iron ingots.").getStringList();

        goldBackpack[0] = config.get("Gold Backpack", "Upgrade Slots", 2, "The number of upgrades on the backpack. Default 2.").getInt();
        goldBackpack[1] = config.get("Gold Backpack", "Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        goldBackpack[2] = config.get("Gold Backpack", "Number of Rows", 6, "The size of the backpack. Between 1 and 7. Default 6.").getInt();
        goldBackpackRecipe = config.get("Gold Backpack", "Recipe", goldRecipe, "The recipe to upgrade the backpack. Each line is the next item in the crafting grid (so 9 lines). " +
                "Supports ore dictionary. Write in 'none' (without the parenthesis) for a blank spot in the crafting grid. Default is ironbackpacks:ironBackpack surrounded by gold ingots.").getStringList();

        diamondBackpack[0] = config.get("Diamond Backpack", "Upgrade Slots", 3, "The number of upgrades on the backpack. Default 3.").getInt();
        diamondBackpack[1] = config.get("Diamond Backpack", "Number of Slots Per Row", 11, "The size of the backpack. Either 9 or 11. Default 11.").getInt();
        diamondBackpack[2] = config.get("Diamond Backpack", "Number of Rows", 7, "The size of the backpack. Between 1 and 7. Default 7.").getInt();
        diamondBackpackRecipe = config.get("Diamond Backpack", "Recipe", diamondRecipe, "The recipe to upgrade the backpack. Each line is the next item in the crafting grid (so 9 lines). " +
                "Supports ore dictionary. Write in 'none' (without the parenthesis) for a blank spot in the crafting grid. Default is ironbackpacks:goldBackpack surrounded by diamonds.").getStringList();


        //getStringList
        config.save();
    }
}
