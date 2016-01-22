package gr8pefish.ironbackpacks.config;

import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The configuration file is handled by this class
 */
public class ConfigHandler {

    //================ Backpack Enums (for easy reference by name rather than some number) ============================

    public static int[] basicBackpack;
    public static int[] ironBackpack;
    public static int[] goldBackpack;
    public static int[] diamondBackpack;

    private static final int valuesToLoad = 3; //upgradePoints, sizeX, sizeY

    //==================== All the publicly accessible values====================================

    public static Configuration config;

    public static boolean isConfigManuallyChanged;

//    public static String[] basicBackpackRecipe;
//    public static String[] ironBackpackToUpgradeRecipe;
//    public static String[] goldBackpackToUpgradeRecipe;
//    public static String[] diamondBackpackToUpgradeRecipe;
//
//    public static String[] buttonUpgradeRecipe;
//    public static String[] nestingUpgradeRecipe;
//    public static String[] renamingUpgradeRecipe;
//    public static String[] damageBarUpgradeRecipe;
//    public static String[] filterBasicUpgradeRecipe;
//    public static String[] filterFuzzyUpgradeRecipe;
//    public static String[] filterOreDictUpgradeRecipe;
//    public static String[] filterModSpecificUpgradeRecipe;
//    public static String[] restockingUpgradeRecipe;
//    public static String[] craftingUpgradeRecipe;
//    public static String[] craftingSmallUpgradeRecipe;
//    public static String[] craftingTinyUpgradeRecipe;
//    public static String[] eternityUpgradeRecipe;
//    public static String[] additionalUpgradePointsUpgradeRecipe;
//    public static String[] quickDepositUpgradeRecipe;
//    public static String[] quickDepositPreciseUpgradeRecipe;
//    public static String[] filterAdvancedUpgradeRecipe;
//    public static String[] filterMiningUpgradeRecipe;
//    public static String[] filterVoidUpgradeRecipe;
//    public static String[] nestingAdvancedUpgradeRecipe;
//    public static String[] depthUpgradeRecipe;

    public static int buttonUpgradeCost;
    public static int nestingUpgradeCost;
    public static int renamingUpgradeCost;
    public static int damageBarUpgradeCost;
    public static int filterBasicUpgradeCost;
    public static int filterModSpecificUpgradeCost;
    public static int filterFuzzyUpgradeCost;
    public static int filterOreDictUpgradeCost;
    public static int restockingUpgradeCost;
    public static int craftingUpgradeCost;
    public static int craftingSmallUpgradeCost;
    public static int craftingTinyUpgradeCost;
    public static int eternityUpgradeCost;
    public static int quickDepositUpgradeCost;
    public static int quickDepositPreciseUpgradeCost;
    public static int filterAdvancedUpgradeCost;
    public static int filterMiningUpgradeCost;
    public static int nestingAdvancedUpgradeCost;
    public static int depthUpgradeCost;
    public static int filterVoidUpgradeCost;

//    public static String[] nestRecipe;
//    public static String[] upgradeCoreRecipe;
//    public static String[] jeweledFeatherRecipe;
//    public static String[] treatedLeatherRecipe;

    public static boolean disableRendering;
    public static boolean disableFPPrendering;
    public static boolean renamingUpgradeRequired;
    public static int tooltipDelay;
    public static int additionalUpgradePointsLimit;
    public static int additionalUpgradePointsIncrease;
    public static boolean makeRenamedBackpacksNamesItalic;

    public static ArrayList<String> itemBlacklist; //TODO


    //========================initialization====================================

    private static File theFile;

    public static void init(File file) {
        config = new Configuration(file);
        theFile = file;
        syncConfig(true);
    }

    public static void syncConfig(boolean firstLoad) {

        basicBackpack = new int[valuesToLoad];
        ironBackpack = new int[valuesToLoad];
        goldBackpack = new int[valuesToLoad];
        diamondBackpack = new int[valuesToLoad];

        //======================================================Recipe defaults================================


        //===================================================Categories======================================

        config.load();

        config.addCustomCategoryComment("0) Config", "IMPORTANT: If you ever manually edit ANY of these values, please change the value below to true. " +
                "This will prevent them being overwritten if an update changes the config file.");

        config.addCustomCategoryComment("1) BackpackNames", "Here you can modify the traits of the backpacks.");
        config.addCustomCategoryComment("6) Upgrade Costs", "Here you can modify how expensive the upgrades are to add to a backpack. " +
                "A cost of 0 makes the upgrade 'free' to apply, while a higher number makes it more expensive.");
        config.addCustomCategoryComment("5) ItemUpgradeRecipes", "Each recipe has 9 lines, each corresponding to the next slot in the crafting grid. " +
                "The recipes are registered with the oreDictionary, so putting in blocks.wool will allow you to use any color of wool while crafting."+
                "Syntax: use 'items.item_name' or 'blocks.block_name' or 'oreDictionaryName' or 'none' for a blank space. " +
                "\nExample:\n\titems.paper - vanilla item\n\tblocks.gold_block - vanilla block\n\titems.paper - vanilla item\n\t" +
                "plankWood - oreDictionary\n\tingotSilver - oreDictionary(assuming another mod is loaded that adds this)\n\tplankWood - oreDictionary" +
                "\n\titems.modname:itemName - format for mod items (Note: mods may register their items/blocks differently, one possibility is their use of a period instead of a colon. ex: items.modname.itemName)" +
                "\n\tblockGold - oreDictionary\n\tblocks.modname:blockName - format for mod blocks");
        config.addCustomCategoryComment("7) Miscellaneous", "A variety of miscellaneous configurable tweaks and changes to the mod.");


        //============================================Initializing everything, the numbers keep them in the order I want=======================================

        isConfigManuallyChanged = config.get("0) Config", "1) Manually Changed", false, "If you modify ANY values here set this to true so a future update doesn't override them. Otherwise leave it as false and any new changes in the config defaults will automatically be updated.").getBoolean();

        //if loading for the first time and can change configs
        //then delete the file and let a new one regen (inefficient but effective)
        //TODO: remove this
        if (firstLoad && !isConfigManuallyChanged){
            if(theFile.delete()){
                config = new Configuration(theFile);
                syncConfig(false);
            }
        }

        basicBackpack[0] = config.get("1) Basic Backpack", "1) Upgrade Points", 8, "The number of upgrade points on the backpack. Default 8.").getInt();
        basicBackpack[1] = config.get("1) Basic Backpack", "2) Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        basicBackpack[2] = config.get("1) Basic Backpack", "3) Number of Rows", 2, "The size of the backpack. Between 1 and 7. Default 2.").getInt();
//        basicBackpackRecipe = config.get("5) ItemUpgradeRecipes", "01) Basic Backpack Recipe", basicBackpackRecipe, "The recipe for the basic backpack. Default is blocks.chest surrounded by items.leather.").getStringList();

        ironBackpack[0] = config.get("2) Iron Backpack", "1) Upgrade Points", 12, "The number of upgrade points on the backpack. Default 12.").getInt();
        ironBackpack[1] = config.get("2) Iron Backpack", "2) Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        ironBackpack[2] = config.get("2) Iron Backpack", "3) Number of Rows", 4, "The size of the backpack. Between 1 and 7. Default 4.").getInt();
//        ironBackpackToUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "02) Iron Backpack Recipe", ironBackpackToUpgradeRecipe, "The recipe to upgrade the backpack to an Iron Backpack. Default is surrounded by ingotIron.").getStringList();

        goldBackpack[0] = config.get("3) Gold Backpack", "1) Upgrade Points", 16, "The number of upgrade points on the backpack. Default 16.").getInt();
        goldBackpack[1] = config.get("3) Gold Backpack", "2) Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        goldBackpack[2] = config.get("3) Gold Backpack", "3) Number of Rows", 6, "The size of the backpack. Between 1 and 7. Default 6.").getInt();
//        goldBackpackToUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "03) Gold Backpack Recipe", goldBackpackToUpgradeRecipe, "The recipe to upgrade the backpack to a Gold Backpack. Default is surrounded by ingotGold.").getStringList();

        diamondBackpack[0] = config.get("4) Diamond Backpack", "1) Upgrade Points", 20, "The number of upgrade points on the backpack. Default 20.").getInt();
        diamondBackpack[1] = config.get("4) Diamond Backpack", "2) Number of Slots Per Row", 11, "The size of the backpack. Either 9 or 11. Default 11.").getInt();
        diamondBackpack[2] = config.get("4) Diamond Backpack", "3) Number of Rows", 7, "The size of the backpack. Between 1 and 7. Default 7.").getInt();
//        diamondBackpackToUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "04) Diamond Backpack Recipe", diamondBackpackToUpgradeRecipe, "The recipe to upgrade the backpack to a Diamond Backpack. Default is surrounded by gemDiamond.").getStringList();

//        buttonUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "10) Button Upgrade Recipe", buttonRecipe, "The recipe for the button upgrade.").getStringList();
//        nestingUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "11) Nesting Upgrade Recipe", nestingRecipe, "The recipe for the nesting upgrade.").getStringList();
//        nestingAdvancedUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "12) Advanced Nesting Upgrade Recipe", nestingAdvancedRecipe, "The recipe for the advanced nesting upgrade.").getStringList();
//        renamingUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "13) Renaming Upgrade Recipe", renamingRecipe, "The recipe for the renaming upgrade. Only used if the upgrade is required (another option in config)").getStringList();
//        damageBarUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "14) Damage Bar Upgrade Recipe", damageBarRecipe, "The recipe for the damage bar upgrade.").getStringList();
//        filterBasicUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "15) Basic Filter Upgrade Recipe", filterBasicRecipe, "The recipe for the basic filter upgrade.").getStringList();
//        filterFuzzyUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "16) Fuzzy Filter Upgrade Recipe", filterFuzzyRecipe, "The recipe for the fuzzy filter upgrade.").getStringList();
//        filterOreDictUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "17) Ore Dictionary Filter Upgrade Recipe", filterOreDictRecipe, "The recipe for the ore dictionary filter upgrade.").getStringList();
//        filterModSpecificUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "18) Mod Specific Filter Upgrade Recipe", filterModSpecificRecipe, "The recipe for the mod specific filter upgrade.").getStringList();
//        filterAdvancedUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "19) Advanced Filter Upgrade Recipe", filterAdvancedRecipe, "The recipe for the advanced filter upgrade.").getStringList();
//        filterMiningUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "20) Mining Upgrade Recipe", filterMiningRecipe, "The recipe for the mining filter upgrade.").getStringList();
//        filterVoidUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "21) Void Upgrade Recipe", filterVoidRecipe, "The recipe for the void filter upgrade.").getStringList();
//        restockingUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "22) Restocking Upgrade Recipe", restockingRecipe, "The recipe for the restocking upgrade.").getStringList();
//        craftingUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "23) Crafting Upgrade Recipe", craftingRecipe, "The recipe for the crafting upgrade.").getStringList();
//        craftingSmallUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "24) Small Crafting Upgrade Recipe", craftingSmallRecipe, "The recipe for the small crafting upgrade.").getStringList();
//        craftingTinyUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "25) Tiny Crafting Upgrade Recipe", craftingTinyRecipe, "The recipe for the tiny crafting upgrade.").getStringList();
//        eternityUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "26) Eternity Upgrade Recipe", eternityRecipe, "The recipe for the upgrade that allows you to keep the backpack upon dying.").getStringList();
//        additionalUpgradePointsUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "27) Additional Upgrade Slots Recipe", additionalUpgradePointsRecipe, "The recipe for the upgrade which gives the backpack a configurable amount of additional upgrade points.").getStringList();
//        quickDepositUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "28) Quick Deposit Upgrade Recipe", quickDepositRecipe, "The recipe for the upgrade which gives the backpack the ability to empty it's contents into an inventory.").getStringList();
//        quickDepositPreciseUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "29) Quick Deposit Precise Upgrade Recipe", quickDepositPreciseRecipe, "The recipe for the upgrade which gives the backpack the ability to empty it's contents into an inventory if the items already exist in the inventory.").getStringList();
//        depthUpgradeRecipe = config.get("5) ItemUpgradeRecipes", "30) Depth Upgrade Recipe", depthRecipe, "The recipe for the upgrade which gives a backpack the ability to check fort nested backpacks before applying effects.").getStringList();
//
//        nestRecipe = config.get("5) ItemUpgradeRecipes", "31) Nest Recipe", nestRecipeDefault, "The recipe for the crafting item - nest.").getStringList();
//        upgradeCoreRecipe = config.get("5) ItemUpgradeRecipes", "32) Upgrade Core Recipe", upgradeCoreRecipeDefault, "The recipe for the crafting item - upgrade core.").getStringList();
//        jeweledFeatherRecipe = config.get("5) ItemUpgradeRecipes", "33) Jeweled Feather Recipe", jeweledFeatherDefault, "The recipe for the crafting item - jeweled feather. Shapeless Recipe, must be less than 9 items.").getStringList();
//        treatedLeatherRecipe = config.get("5) ItemUpgradeRecipes", "34) Treated Leather Recipe", treatedLeatherDefault, "The recipe for the crafting item - treated leather. Shapeless Recipe, must be less than 9 items. Default includes lime dye.").getStringList();

        buttonUpgradeCost = config.get("6) Upgrade Costs", "1) Button Upgrade Recipe", 2, "The cost for the button upgrade. Default 2.").getInt();
        nestingUpgradeCost = config.get("6) Upgrade Costs", "2) Nesting Upgrade Recipe", 3, "The cost for the nesting upgrade. Default 3.").getInt();
        nestingAdvancedUpgradeCost = config.get("6) Upgrade Costs", "3) Advanced Nesting Upgrade Recipe", 5, "The cost for the advanced nesting upgrade. Default 5.").getInt();
        renamingUpgradeCost = config.get("6) Upgrade Costs", "4) Renaming Upgrade Recipe", 1, "The cost for the renaming upgrade. Only used if the upgrade is required (another option in config). Default 1.").getInt();
        damageBarUpgradeCost = config.get("6) Upgrade Costs", "5) Damage Bar Upgrade Recipe", 2, "The cost for the damage bar upgrade. Default 2.").getInt();
        filterBasicUpgradeCost = config.get("6) Upgrade Costs", "6) Basic Filter Upgrade Recipe", 3, "The cost for the basic filter upgrade. Default 3.").getInt();
        filterFuzzyUpgradeCost = config.get("6) Upgrade Costs", "7) Fuzzy Filter Upgrade Recipe", 4, "The cost for the fuzzy filter upgrade. Default 4.").getInt();
        filterOreDictUpgradeCost = config.get("6) Upgrade Costs", "8) Ore Dictionary Filter Upgrade Recipe", 4, "The cost for the ore dictionary filter upgrade. Default 4.").getInt();
        filterModSpecificUpgradeCost = config.get("6) Upgrade Costs", "9) Mod Specific Filter Upgrade Recipe", 4, "The cost for the mod specific filter upgrade. Default 4.").getInt();
        filterAdvancedUpgradeCost = config.get("6) Upgrade Costs", "10) Advanced Filter Upgrade Recipe", 6, "The cost for the advanced filter upgrade. Default 6.").getInt();
        filterMiningUpgradeCost = config.get("6) Upgrade Costs", "11) Mining Upgrade Recipe", 4, "The cost for the mining filter upgrade. Default 4.").getInt();
        filterVoidUpgradeCost = config.get("6) Upgrade Costs", "12) Void Upgrade Recipe", 2, "The cost for the void filter upgrade. Default 2.").getInt();
        restockingUpgradeCost = config.get("6) Upgrade Costs", "13) Restocking Upgrade Recipe", 2, "The cost for the restocking upgrade. Default 2.").getInt();
        craftingUpgradeCost = config.get("6) Upgrade Costs", "14) Crafting Upgrade Recipe", 3, "The cost for the crafting upgrade. Default 3.").getInt();
        craftingSmallUpgradeCost = config.get("6) Upgrade Costs", "15) Small Crafting Upgrade Recipe", 2, "The cost for the small crafting upgrade. Default 2.").getInt();
        craftingTinyUpgradeCost = config.get("6) Upgrade Costs", "16) Tiny Crafting Upgrade Recipe", 2, "The cost for the tiny crafting upgrade. Default 2.").getInt();
        eternityUpgradeCost = config.get("6) Upgrade Costs", "17) Eternity Upgrade Recipe", 5, "The cost for the upgrade that allows you to keep the backpack upon dying. Default 5.").getInt();
        quickDepositUpgradeCost = config.get("6) Upgrade Costs", "18) Quick Deposit Upgrade Recipe", 2, "The cost for the quick deposit upgrade. Default 2.").getInt();
        quickDepositPreciseUpgradeCost = config.get("6) Upgrade Costs", "19) Precise Quick Deposit Upgrade Recipe", 2, "The cost for the precise quick deposit upgrade. Default 2.").getInt();
        depthUpgradeCost = config.get("6) Upgrade Costs", "20) Depth Upgrade Recipe", 2, "The cost for the depth deposit upgrade. Default 2.").getInt();

        disableRendering = config.get("7) Miscellaneous", "0) Disable Rendering", false, "To disable the model rendering on the player when they have an equipped backpack. Default false.").getBoolean();
        disableFPPrendering = config.get("7) Miscellaneous", "1) Enable FPP Rendering", false, "To disable the model rendering on the player when they have an equipped backpack and are in first person perspective. " +
                "You sometimes see it if you spin to the side quickly, the backpack takes longer to readjust than your forward vision as it swings back onto your back. Default false.").getBoolean();
        renamingUpgradeRequired = config.get("7) Miscellaneous", "2) Renaming Upgrade Required", false, "If the renaming upgrade is required to rename the backpack. Default is false (so you can rename backpacks natively).").getBoolean();
        tooltipDelay = config.get("7) Miscellaneous", "3) Tooltip Delay", 1500, "The delay (in milliseconds) until a tooltip will appear over the buttons. Default is 1500 (so 1.5 seconds).").getInt();
        additionalUpgradePointsLimit = config.get("7) Miscellaneous", "4) Additional Upgrades Limit", 1, "The maximum number of times you can apply the 'additional upgrade slots' upgrade. This number represents the starting number (for the basic backpack), each backpack tier increments this number by one. " +
                "Negative numbers are allowed (ex: -1 will allow none on basic or iron backpacks, once on gold backpacks, and twice on diamond). Default is 1.").getInt();
        additionalUpgradePointsIncrease = config.get("7) Miscellaneous", "5) Additional Upgrades Increase", 2, "The amount of extra upgrade points the 'additional upgrade slots' upgrade will apply. Default is 2.").getInt();
        makeRenamedBackpacksNamesItalic = config.get("7) Miscellaneous", "6) Italic Renames", false, "Make the styling of the letters on a renamed backpack be in italics. Default false.").getBoolean();

        //TODO: make this a thing
        itemBlacklist = new ArrayList<>(Arrays.asList(config.get("7) Miscellaneous", "6) Italic Renames", false, "Make the styling of the letters on a renamed backpack be in italics. Default false.").getStringList()));

        config.save(); //Don't forget to save
    }

    //========================initialization====================================

    public enum enumBasicBackpack {
        upgradePoints(basicBackpack[0]),
        sizeX(basicBackpack[1]),
        sizeY(basicBackpack[2]);
        private int value;

        enumBasicBackpack(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum enumIronBackpack {
        upgradePoints(ironBackpack[0]),
        sizeX(ironBackpack[1]),
        sizeY(ironBackpack[2]);
        private int value;

        enumIronBackpack(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum enumGoldBackpack {
        upgradePoints(goldBackpack[0]),
        sizeX(goldBackpack[1]),
        sizeY(goldBackpack[2]);
        private int value;

        enumGoldBackpack(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum enumDiamondBackpack {
        upgradePoints(diamondBackpack[0]),
        sizeX(diamondBackpack[1]),
        sizeY(diamondBackpack[2]);
        private int value;

        enumDiamondBackpack(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
