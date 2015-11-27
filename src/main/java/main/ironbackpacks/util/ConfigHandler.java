package main.ironbackpacks.util;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

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

    public static String[] basicBackpackRecipe;
    public static String[] ironBackpackRecipe;
    public static String[] goldBackpackRecipe;
    public static String[] diamondBackpackRecipe;

    public static String[] buttonUpgradeRecipe;
    public static String[] nestingUpgradeRecipe;
    public static String[] renamingUpgradeRecipe;
    public static String[] damageBarUpgradeRecipe;
    public static String[] filterBasicUpgradeRecipe;
    public static String[] filterFuzzyUpgradeRecipe;
    public static String[] filterOreDictUpgradeRecipe;
    public static String[] filterModSpecificUpgradeRecipe;
    public static String[] hopperUpgradeRecipe;
    public static String[] condenserUpgradeRecipe;
    public static String[] keepOnDeathUpgradeRecipe;
    public static String[] additionalUpgradesUpgradeRecipe;
    public static String[] quickDepositUpgradeRecipe;
    public static String[] quickDepositPreciseUpgradeRecipe;
    public static String[] filterAdvancedUpgradeRecipe;
    public static String[] filterMiningUpgradeRecipe;
    public static String[] nestingAdvancedUpgradeRecipe;
    public static String[] depthUpgradeRecipe;

    public static int buttonUpgradeCost;
    public static int nestingUpgradeCost;
    public static int renamingUpgradeCost;
    public static int damageBarUpgradeCost;
    public static int filterBasicUpgradeCost;
    public static int filterModSpecificUpgradeCost;
    public static int filterFuzzyUpgradeCost;
    public static int filterOreDictUpgradeCost;
    public static int hopperUpgradeCost;
    public static int condenserUpgradeCost;
    public static int keepOnDeathUpgradeCost;
    public static int quickDepositUpgradeCost;
    public static int quickDepositPreciseUpgradeCost;
    public static int filterAdvancedUpgradeCost;
    public static int filterMiningUpgradeCost;
    public static int nestingAdvancedUpgradeCost;
    public static int depthUpgradeCost;

    public static String[] nestRecipe;
    public static String[] upgradeCoreRecipe;
    public static String[] jeweledFeatherRecipe;
    public static String[] treatedLeatherRecipe;

    public static boolean renamingUpgradeRequired;
    public static int tooltipDelay;
    public static int additionalUpgradesLimit;
    public static int additionalUpgradesIncrease;
    public static boolean useAlternateBackpackTextures;
    public static boolean makeRenamedBackpacksNamesItalic;


    //========================initialization====================================

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {

        basicBackpack = new int[valuesToLoad];
        ironBackpack = new int[valuesToLoad];
        goldBackpack = new int[valuesToLoad];
        diamondBackpack = new int[valuesToLoad];

        //======================================================Recipe defaults================================

        String[] basicRecipe = {"items.leather", "items.leather", "items.leather", "items.leather", "blocks.chest", "items.leather", "items.leather", "items.leather", "items.leather"};//Items.
        String[] ironRecipe = {"ingotIron", "blockIron", "ingotIron", "ingotIron", "items.ironbackpacks:basicBackpack", "ingotIron", "ingotIron", "ingotIron", "ingotIron"};
        String[] goldRecipe = {"ingotGold", "blockGold", "ingotGold", "ingotGold", "items.ironbackpacks:ironBackpack", "ingotGold", "ingotGold", "ingotGold", "ingotGold"};
        String[] diamondRecipe = {"gemDiamond", "gemDiamond", "gemDiamond", "gemDiamond", "items.ironbackpacks:goldBackpack", "gemDiamond", "gemDiamond", "gemDiamond", "gemDiamond"};
        //done - this comment refers to balance changes, if the recipe defaults need to be tweaked or are good for the time being

        String[] buttonRecipe = {"items.ironbackpacks:treatedLeather","blocks.stone_button","items.ironbackpacks:treatedLeather", "blocks.wooden_button", "items.ironbackpacks:upgradeCore", "blocks.wooden_button", "items.ironbackpacks:treatedLeather", "blocks.stone_button", "items.ironbackpacks:treatedLeather"};
        //done
        String[] nestingRecipe = {"items.ironbackpacks:jeweledFeather","items.ironbackpacks:nest","items.ironbackpacks:jeweledFeather", "items.ironbackpacks:nest", "items.ironbackpacks:upgradeCore", "items.ironbackpacks:nest", "items.ironbackpacks:jeweledFeather", "items.ironbackpacks:nest", "items.ironbackpacks:jeweledFeather"};
        //done
        String[] nestingAdvancedRecipe = {"items.ironbackpacks:jeweledFeather","items.ironbackpacks:nest","items.ironbackpacks:jeweledFeather", "items.ironbackpacks:nest", "items.ironbackpacks:nestingUpgrade", "items.ironbackpacks:nest", "items.ironbackpacks:jeweledFeather", "items.ironbackpacks:nest", "items.ironbackpacks:jeweledFeather"};
        //done
        String[] renamingRecipe = {"items.writable_book","items.paper","items.writable_book", "items.ironbackpacks:treatedLeather", "items.ironbackpacks:upgradeCore", "items.ironbackpacks:treatedLeather", "items.writable_book", "items.paper", "items.writable_book"};
        //done
        String[] damageBarRecipe = {"items.ironbackpacks:treatedLeather","items.bowl","items.ironbackpacks:treatedLeather", "items.string", "items.ironbackpacks:upgradeCore", "items.string", "items.ironbackpacks:treatedLeather", "items.bowl", "items.ironbackpacks:treatedLeather"};
        //done
        String[] filterBasicRecipe = {"items.ironbackpacks:treatedLeather","items.writable_book","items.ironbackpacks:treatedLeather", "items.paper", "items.ironbackpacks:upgradeCore", "items.paper", "items.ironbackpacks:treatedLeather", "items.writable_book", "items.ironbackpacks:treatedLeather"};
        //done
        String[] hopperRecipe = {"none","blocks.hopper","none", "blocks.hopper", "items.ironbackpacks:upgradeCore", "blocks.hopper", "none", "blocks.hopper", "none"};
        //done
        String[] condenserRecipe = {"gemLapis","blockLapis","gemLapis", "blocks.crafting_table", "items.ironbackpacks:upgradeCore", "blocks.crafting_table", "dustRedstone", "blockRedstone", "dustRedstone"};
        //done
        String[] keepOnDeathRecipe = {"blockDiamond","record","blockDiamond", "record", "items.ironbackpacks:upgradeCore", "record", "blockDiamond", "record", "blockDiamond"};
        //done
        String[] filterModSpecificRecipe = {"none","items.compass","none", "items.compass", "items.ironbackpacks:filterBasicUpgrade", "items.compass", "none", "items.compass", "none"};
        //done
        String[] filterFuzzyRecipe = {"blocks.clay","blocks.clay","blocks.clay", "blocks.red_flower", "items.ironbackpacks:filterBasicUpgrade", "blocks.red_flower", "blocks.clay", "blocks.clay", "blocks.clay"};
        //done
        String[] filterOreDictRecipe = {"none","items.clock","none", "items.clock", "items.ironbackpacks:filterBasicUpgrade", "items.clock", "none", "items.clock", "none"};
        //done
        String[] filterAdvancedRecipe = {"items.ironbackpacks:buttonUpgrade","items.ironbackpacks:filterBasicUpgrade","items.ender_eye", "items.ironbackpacks:filterFuzzyUpgrade", "items.ironbackpacks:upgradeCore", "items.ironbackpacks:filterOreDictUpgrade", "items.ender_eye", "items.ironbackpacks:filterModSpecificUpgrade", "items.ironbackpacks:buttonUpgrade"};
        //done
        String[] filterMiningRecipe = {"oreGold","items.iron_pickaxe","oreGold", "items.iron_pickaxe", "items.ironbackpacks:filterBasicUpgrade", "items.iron_pickaxe", "oreRedstone", "items.iron_pickaxe", "oreRedstone"};
        //TODO: tweak?
        String[] additionalUpgradesRecipe = {"blockEmerald","record","blockEmerald", "record", "items.ironbackpacks:upgradeCore", "record", "blockEmerald", "record", "blockEmerald"};
        //done
        String[] quickDepositRecipe = {"slimeball","blocks.piston","slimeball", "slimeball", "items.ironbackpacks:upgradeCore", "slimeball", "blocks.piston", "slimeball", "blocks.piston"};
        //done
        String[] quickDepositPreciseRecipe = {"slimeball","blocks.sticky_piston","slimeball", "slimeball", "items.ironbackpacks:upgradeCore", "slimeball", "blocks.sticky_piston", "slimeball", "blocks.sticky_piston"};
        //TODO: tweak?
        String[] depthRecipe = {"items.ironbackpacks:jeweledFeather","items.ironbackpacks:nest","items.ironbackpacks:jeweledFeather", "items.ironbackpacks:nestingUpgrade", "items.ironbackpacks:upgradeCore", "items.ironbackpacks:nestingUpgrade", "items.ironbackpacks:jeweledFeather", "items.ironbackpacks:nest", "items.ironbackpacks:jeweledFeather"};
        //TODO: tweak?

        String[] upgradeCoreRecipeDefault = {"items.leather","items.string","items.leather", "items.string", "items.paper", "items.string", "items.leather", "items.string", "items.leather"};
        //done
        String[] nestRecipeDefault = {"stickWood","stickWood","stickWood", "stickWood", "items.egg", "stickWood", "stickWood", "stickWood", "stickWood"};
        //done
        String[] jeweledFeatherDefault = {"items.feather", "nuggetGold", "nuggetGold"};
        //done
        String[] treatedLeatherDefault = {"items.leather", "items.water_bucket", "dyeLime", "items.flint"};
        //done


        //===================================================Categories======================================

        config.load();

        config.addCustomCategoryComment("5) Recipes", "Each recipe has 9 lines, each corresponding to the next slot in the crafting grid. " +
                "The recipes are registered with the oreDictionary, so putting in blocks.wool will allow you to use any color of wool while crafting."+
                "Syntax: use 'items.item_name' or 'blocks.block_name' or 'oreDictionaryName' or 'none' for a blank space. " +
                "\nExample:\n\titems.paper - vanilla item\n\tblocks.gold_block - vanilla block\n\titems.paper - vanilla item\n\t" +
                "plankWood - oreDictionary\n\tingotSilver - oreDictionary(assuming another mod is loaded that adds this)\n\tplankWood - oreDictionary" +
                "\n\titems.modname:itemName - format for mod items (Note: mods may register their items/blocks differently, one possibility is their use of a period instead of a colon. ex: items.modname.itemName)\n\tblockGold - oreDictionary\n\tblocks.modname:blockName - format for mod blocks");

        config.addCustomCategoryComment("0) Backpacks", "Here you can modify the traits of the backpacks.");
        config.addCustomCategoryComment("6) Upgrade Costs", "Here you can modify how expensive the upgrades are to add to a backpack. A cost of 0 makes the upgrade 'free' to apply, while a higher number makes it more expensive.");
        config.addCustomCategoryComment("7) Miscellaneous", "A variety of miscellaneous configurable tweaks and changes to the mod.");


        //============================================Initializing everything, the numbers keep them in the order I want=======================================

        basicBackpack[0] = config.get("1) Basic Backpack", "1) Upgrade Points", 8, "The number of upgrade points on the backpack. Default 8.").getInt();
        basicBackpack[1] = config.get("1) Basic Backpack", "2) Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        basicBackpack[2] = config.get("1) Basic Backpack", "3) Number of Rows", 2, "The size of the backpack. Between 1 and 7. Default 2.").getInt();
        basicBackpackRecipe = config.get("5) Recipes", "1) Basic Backpack Recipe", basicRecipe, "The recipe for the basic backpack. Default is blocks.chest surrounded by items.leather.").getStringList();

        ironBackpack[0] = config.get("2) Iron Backpack", "1) Upgrade Points", 12, "The number of upgrade points on the backpack. Default 12.").getInt();
        ironBackpack[1] = config.get("2) Iron Backpack", "2) Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        ironBackpack[2] = config.get("2) Iron Backpack", "3) Number of Rows", 4, "The size of the backpack. Between 1 and 7. Default 4.").getInt();
        ironBackpackRecipe = config.get("5) Recipes", "2) Iron Backpack Recipe", ironRecipe, "The recipe to upgrade the backpack to an Iron Backpack. Default is surrounded by ingotIron.").getStringList();

        goldBackpack[0] = config.get("3) Gold Backpack", "1) Upgrade Points", 16, "The number of upgrade points on the backpack. Default 16.").getInt();
        goldBackpack[1] = config.get("3) Gold Backpack", "2) Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        goldBackpack[2] = config.get("3) Gold Backpack", "3) Number of Rows", 6, "The size of the backpack. Between 1 and 7. Default 6.").getInt();
        goldBackpackRecipe = config.get("5) Recipes", "3) Gold Backpack Recipe", goldRecipe, "The recipe to upgrade the backpack to a Gold Backpack. Default is surrounded by ingotGold.").getStringList();

        diamondBackpack[0] = config.get("4) Diamond Backpack", "1) Upgrade Points", 20, "The number of upgrade points on the backpack. Default 20.").getInt();
        diamondBackpack[1] = config.get("4) Diamond Backpack", "2) Number of Slots Per Row", 11, "The size of the backpack. Either 9 or 11. Default 11.").getInt();
        diamondBackpack[2] = config.get("4) Diamond Backpack", "3) Number of Rows", 7, "The size of the backpack. Between 1 and 7. Default 7.").getInt();
        diamondBackpackRecipe = config.get("5) Recipes", "4) Diamond Backpack Recipe", diamondRecipe, "The recipe to upgrade the backpack to a Diamond Backpack. Default is surrounded by gemDiamond.").getStringList();

        buttonUpgradeRecipe = config.get("5) Recipes", "5) Upgrade Recipe", buttonRecipe, "The recipe for the button upgrade.").getStringList();
        nestingUpgradeRecipe = config.get("5) Recipes", "6) Nesting Upgrade Recipe", nestingRecipe, "The recipe for the nesting upgrade.").getStringList();
        nestingAdvancedUpgradeRecipe = config.get("5) Recipes", "7) Advanced Nesting Upgrade Recipe", nestingAdvancedRecipe, "The recipe for the advanced nesting upgrade.").getStringList();
        renamingUpgradeRecipe = config.get("5) Recipes", "8) Renaming Upgrade Recipe", renamingRecipe, "The recipe for the renaming upgrade. Only used if the upgrade is required (another option in config)").getStringList();
        damageBarUpgradeRecipe = config.get("5) Recipes", "9) Damage Bar Upgrade Recipe", damageBarRecipe, "The recipe for the damage bar upgrade.").getStringList();
        filterBasicUpgradeRecipe = config.get("5) Recipes", "10) Basic Filter Upgrade Recipe", filterBasicRecipe, "The recipe for the basic filter upgrade.").getStringList();
        filterFuzzyUpgradeRecipe = config.get("5) Recipes", "11) Fuzzy Filter Upgrade Recipe", filterFuzzyRecipe, "The recipe for the fuzzy filter upgrade.").getStringList();
        filterOreDictUpgradeRecipe = config.get("5) Recipes", "12) Ore Dictionary Filter Upgrade Recipe", filterOreDictRecipe, "The recipe for the ore dictionary filter upgrade.").getStringList();
        filterModSpecificUpgradeRecipe = config.get("5) Recipes", "13) Mod Specific Filter Upgrade Recipe", filterModSpecificRecipe, "The recipe for the mod specific filter upgrade.").getStringList();
        filterAdvancedUpgradeRecipe = config.get("5) Recipes", "14) Advanced Filter Upgrade Recipe", filterAdvancedRecipe, "The recipe for the advanced filter upgrade.").getStringList();
        filterMiningUpgradeRecipe = config.get("5) Recipes", "15) Mining Upgrade Recipe", filterMiningRecipe, "The recipe for the mining filter upgrade.").getStringList();
        hopperUpgradeRecipe = config.get("5) Recipes", "16) Resupply Upgrade Recipe", hopperRecipe, "The recipe for the resupply upgrade.").getStringList();
        condenserUpgradeRecipe = config.get("5) Recipes", "17) Condenser Upgrade Recipe", condenserRecipe, "The recipe for the condenser upgrade.").getStringList();
        keepOnDeathUpgradeRecipe = config.get("5) Recipes", "18) Keep On Death Upgrade Recipe", keepOnDeathRecipe, "The recipe for the upgrade that allows you to keep the backpack upon dying.").getStringList();
        additionalUpgradesUpgradeRecipe = config.get("5) Recipes", "19) Additional Upgrade Slots Recipe", additionalUpgradesRecipe, "The recipe for the upgrade which gives the backpack a configurable amount of additional upgrade points.").getStringList();
        quickDepositUpgradeRecipe = config.get("5) Recipes", "20) Quick Deposit Upgrade Recipe", quickDepositRecipe, "The recipe for the upgrade which gives the backpack the ability to empty it's contents into an inventory.").getStringList();
        quickDepositPreciseUpgradeRecipe = config.get("5) Recipes", "21) Quick Deposit Precise Upgrade Recipe", quickDepositPreciseRecipe, "The recipe for the upgrade which gives the backpack the ability to empty it's contents into an inventory if the items already exist in the inventory.").getStringList();
        depthUpgradeRecipe = config.get("5) Recipes", "22) Depth Upgrade Recipe", depthRecipe, "The recipe for the upgrade which gives a backpack the ability to check fort nested backpacks before applying effects.").getStringList();

        nestRecipe = config.get("5) Recipes", "23) Nest Recipe", nestRecipeDefault, "The recipe for the crafting item - nest.").getStringList();
        upgradeCoreRecipe = config.get("5) Recipes", "24) Upgrade Core Recipe", upgradeCoreRecipeDefault, "The recipe for the crafting item - upgrade core.").getStringList();
        jeweledFeatherRecipe = config.get("5) Recipes", "25) Jeweled Feather Recipe", jeweledFeatherDefault, "The recipe for the crafting item - jeweled feather. Shapeless Recipe, must be less than 9 items.").getStringList();
        treatedLeatherRecipe = config.get("5) Recipes", "26) Treated Leather Recipe", treatedLeatherDefault, "The recipe for the crafting item - treated leather. Shapeless Recipe, must be less than 9 items. Default includes lime dye.").getStringList();

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
        hopperUpgradeCost = config.get("6) Upgrade Costs", "12) Resupply Upgrade Recipe", 2, "The cost for the resupply upgrade. Default 2.").getInt();
        condenserUpgradeCost = config.get("6) Upgrade Costs", "13) Condenser Upgrade Recipe", 3, "The cost for the crafting upgrade. Default 3.").getInt();
        keepOnDeathUpgradeCost = config.get("6) Upgrade Costs", "14) Keep On Death Upgrade Recipe", 5, "The cost for the upgrade that allows you to keep the backpack upon dying. Default 5.").getInt();
        quickDepositUpgradeCost = config.get("6) Upgrade Costs", "15) Quick Deposit Upgrade Recipe", 2, "The cost for the quick deposit upgrade. Default 2.").getInt();
        quickDepositPreciseUpgradeCost = config.get("6) Upgrade Costs", "16) Precise Quick Deposit Upgrade Recipe", 2, "The cost for the precise quick deposit upgrade. Default 2.").getInt();
        depthUpgradeCost = config.get("6) Upgrade Costs", "17) Depth Upgrade Recipe", 2, "The cost for the depth deposit upgrade. Default 2.").getInt();

        renamingUpgradeRequired = config.get("7) Miscellaneous", "1) Renaming Upgrade Required", false, "If the renaming upgrade is required to rename the backpack. Default is false (so you can rename backpacks natively).").getBoolean();
        tooltipDelay = config.get("7) Miscellaneous", "2) Tooltip Delay", 1500, "The delay (in milliseconds) until a tooltip will appear over the buttons. Default is 1500 (so 1.5 seconds).").getInt();
        additionalUpgradesLimit = config.get("7) Miscellaneous", "3) Additional Upgrades Limit", 1, "The maximum number of times you can apply the 'additional upgrade slots' upgrade. This number represents the starting number (for the basic backpack), each backpack tier increments this number by one. " +
                "Negative numbers are allowed (ex: -1 will allow none on basic or iron backpacks, once on gold backpacks, and twice on diamond). Default is 1.").getInt();
        additionalUpgradesIncrease = config.get("7) Miscellaneous", "4) Additional Upgrades Increase", 2, "The amount of extra upgrade points the 'additional upgrade slots' upgrade will apply. Default is 2.").getInt();
        useAlternateBackpackTextures = config.get("7) Miscellaneous", "5) Alternate Backpack Textures", false, "Use alternate, lower definition textures for the backpacks. Default is false.").getBoolean();
        makeRenamedBackpacksNamesItalic = config.get("7) Miscellaneous", "6) Italic Renames", false, "Make the styling of the letters on a renamed backpack be in italics. Default false.").getBoolean();

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
