package gr8pefish.ironbackpacks.config;

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

    //TODO: make this and the section below be in a nice, readable order (like my itemRegistry class)

    public static int buttonUpgradeCost;
    public static int buttonUpgradeTier;
    public static int nestingUpgradeCost;
    public static int nestingUpgradeTier;
    public static int renamingUpgradeCost;
    public static int renamingUpgradeTier;
    public static int damageBarUpgradeCost;
    public static int damageBarUpgradeTier;
    public static int filterBasicUpgradeCost;
    public static int filterBasicUpgradeTier;
    public static int filterModSpecificUpgradeCost;
    public static int filterModSpecificUpgradeTier;
    public static int filterFuzzyUpgradeCost;
    public static int filterFuzzyUpgradeTier;
    public static int filterOreDictUpgradeCost;
    public static int filterOreDictUpgradeTier;
    public static int restockingUpgradeCost;
    public static int restockingUpgradeTier;
    public static int craftingUpgradeCost;
    public static int craftingUpgradeTier;
    public static int craftingSmallUpgradeCost;
    public static int craftingSmallUpgradeTier;
    public static int craftingTinyUpgradeCost;
    public static int craftingTinyUpgradeTier;
    public static int eternityUpgradeCost;
    public static int eternityUpgradeTier;
    public static int quickDepositUpgradeCost;
    public static int quickDepositUpgradeTier;
    public static int quickDepositPreciseUpgradeCost;
    public static int quickDepositPreciseUpgradeTier;
    public static int filterAdvancedUpgradeCost;
    public static int filterAdvancedUpgradeTier;
    public static int filterMiningUpgradeCost;
    public static int filterMiningUpgradeTier;
    public static int nestingAdvancedUpgradeCost;
    public static int nestingAdvancedUpgradeTier;
    public static int depthUpgradeCost;
    public static int depthUpgradeTier;
    public static int filterVoidUpgradeCost;
    public static int filterVoidUpgradeTier;


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
        config.addCustomCategoryComment("1) Backpack Names", "Here you can modify the traits of the backpacks.");
        config.addCustomCategoryComment("6) Upgrade Details", "Here you can modify how expensive the upgrades are to add to a backpack. " +
                "A cost of 0 makes the upgrade 'free' to apply, while a higher number makes it more expensive. "+
                "\nIn addition, you can modify the minimum tier of backpack necessary to apply this upgrade to. "+
                "For example, a tier of 0 means it can be applied to any backpack, while a tier of 2 means it can only be applied to gold or diamond backpacks.");
        config.addCustomCategoryComment("7) Miscellaneous", "A variety of miscellaneous configurable tweaks and changes to the mod.");


        //============================================Initializing everything, the numbers keep them in the order I want=======================================

        isConfigManuallyChanged = config.get("0) Config", "1) Manually Changed", false, "If you modify ANY values here set this to true so a future update doesn't override them. Otherwise leave it as false and any new changes in the config defaults will automatically be updated.").getBoolean();

        //if loading for the first time and can change configs
        //then delete the file and let a new one regen (inefficient but effective)
        //TODO: mnake this check versions before doing so, refactor this concept
        if (firstLoad && !isConfigManuallyChanged){
            if(theFile.delete()){
                config = new Configuration(theFile);
                syncConfig(false);
            }
        }

        basicBackpack[0] = config.get("1) Basic Backpack", "1) Upgrade Points", 8, "The number of upgrade points on the backpack. Default 8.").getInt();
        basicBackpack[1] = config.get("1) Basic Backpack", "2) Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        basicBackpack[2] = config.get("1) Basic Backpack", "3) Number of Rows", 2, "The size of the backpack. Between 1 and 7. Default 2.").getInt();

        ironBackpack[0] = config.get("2) Iron Backpack", "1) Upgrade Points", 12, "The number of upgrade points on the backpack. Default 12.").getInt();
        ironBackpack[1] = config.get("2) Iron Backpack", "2) Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        ironBackpack[2] = config.get("2) Iron Backpack", "3) Number of Rows", 4, "The size of the backpack. Between 1 and 7. Default 4.").getInt();

        goldBackpack[0] = config.get("3) Gold Backpack", "1) Upgrade Points", 16, "The number of upgrade points on the backpack. Default 16.").getInt();
        goldBackpack[1] = config.get("3) Gold Backpack", "2) Number of Slots Per Row", 9, "The size of the backpack. Either 9 or 11. Default 9.").getInt();
        goldBackpack[2] = config.get("3) Gold Backpack", "3) Number of Rows", 6, "The size of the backpack. Between 1 and 7. Default 6.").getInt();

        diamondBackpack[0] = config.get("4) Diamond Backpack", "1) Upgrade Points", 20, "The number of upgrade points on the backpack. Default 20.").getInt();
        diamondBackpack[1] = config.get("4) Diamond Backpack", "2) Number of Slots Per Row", 11, "The size of the backpack. Either 9 or 11. Default 11.").getInt();
        diamondBackpack[2] = config.get("4) Diamond Backpack", "3) Number of Rows", 7, "The size of the backpack. Between 1 and 7. Default 7.").getInt();

        buttonUpgradeCost = config.get("6) Upgrade Costs", "1) Button Upgrade Recipe", 2, "The cost for the button upgrade. Default 2.").getInt();
        buttonUpgradeTier = config.get("6) Upgrade Costs", "1.1) Button Upgrade Tier", 0, "The minimum backpack tier for the button upgrade. Default 0.").getInt();
        nestingUpgradeCost = config.get("6) Upgrade Costs", "2) Nesting Upgrade Recipe", 3, "The cost for the nesting upgrade. Default 3.").getInt();
        nestingUpgradeTier = config.get("6) Upgrade Costs", "2.1) Nesting Upgrade Tier", 1, "The minimum backpack tier for the nesting upgrade. Default 1.").getInt();
        nestingAdvancedUpgradeCost = config.get("6) Upgrade Costs", "3) Advanced Nesting Upgrade Recipe", 5, "The cost for the advanced nesting upgrade. Default 5.").getInt();
        nestingAdvancedUpgradeTier = config.get("6) Upgrade Costs", "3.1) Advanced Nesting Upgrade Tier", 0, "The minimum backpack tier for the advanced nesting upgrade. Default 0.").getInt();
        renamingUpgradeCost = config.get("6) Upgrade Costs", "4) Renaming Upgrade Recipe", 1, "The cost for the renaming upgrade. Only used if the upgrade is required (another option in config). Default 1.").getInt();
        renamingUpgradeTier = config.get("6) Upgrade Costs", "4.1) Renaming Upgrade Tier", 0, "The minimum backpack tier for the renaming upgrade. Only used if the upgrade is required (another option in config). Default 0.").getInt();
        damageBarUpgradeCost = config.get("6) Upgrade Costs", "5) Damage Bar Upgrade Recipe", 2, "The cost for the damage bar upgrade. Default 2.").getInt();
        damageBarUpgradeTier = config.get("6) Upgrade Costs", "5.1) Damage Bar Upgrade Tier", 0, "The minimum backpack tier for the damage bar upgrade. Default 0.").getInt();
        filterBasicUpgradeCost = config.get("6) Upgrade Costs", "6) Basic Filter Upgrade Recipe", 3, "The cost for the basic filter upgrade. Default 3.").getInt();
        filterBasicUpgradeTier = config.get("6) Upgrade Costs", "6.1) Basic Filter Upgrade Tier", 0, "The minimum backpack tier for the basic filter upgrade. Default 0.").getInt();
        filterFuzzyUpgradeCost = config.get("6) Upgrade Costs", "7) Fuzzy Filter Upgrade Recipe", 4, "The cost for the fuzzy filter upgrade. Default 4.").getInt();
        filterFuzzyUpgradeTier = config.get("6) Upgrade Costs", "7.1) Fuzzy Filter Upgrade Tier", 1, "The minimum backpack tier for the fuzzy filter upgrade. Default 1.").getInt();
        filterOreDictUpgradeCost = config.get("6) Upgrade Costs", "8) Ore Dictionary Filter Upgrade Recipe", 4, "The cost for the ore dictionary filter upgrade. Default 4.").getInt();
        filterOreDictUpgradeTier = config.get("6) Upgrade Costs", "8.1) Ore Dictionary Filter Upgrade Tier", 1, "The minimum backpack tier for the ore dictionary filter upgrade. Default 1.").getInt();
        filterModSpecificUpgradeCost = config.get("6) Upgrade Costs", "9) Mod Specific Filter Upgrade Recipe", 4, "The cost for the mod specific filter upgrade. Default 4.").getInt();
        filterModSpecificUpgradeTier = config.get("6) Upgrade Costs", "9.1) Mod Specific Filter Upgrade Tier", 1, "The minimum backpack tier for the mod specific filter upgrade. Default 1.").getInt();
        filterAdvancedUpgradeCost = config.get("6) Upgrade Costs", "10) Advanced Filter Upgrade Recipe", 6, "The cost for the advanced filter upgrade. Default 6.").getInt();
        filterAdvancedUpgradeTier = config.get("6) Upgrade Costs", "10.1) Advanced Filter Upgrade Tier", 3, "The minimum backpack tier for the advanced filter upgrade. Default 3.").getInt();
        filterMiningUpgradeCost = config.get("6) Upgrade Costs", "11) Mining Upgrade Recipe", 4, "The cost for the mining filter upgrade. Default 4.").getInt();
        filterMiningUpgradeTier = config.get("6) Upgrade Costs", "11.1) Mining Upgrade Tier", 1, "The minimum backpack tier for the mining filter upgrade. Default 1.").getInt();
        filterVoidUpgradeCost = config.get("6) Upgrade Costs", "12) Void Upgrade Recipe", 2, "The cost for the void filter upgrade. Default 2.").getInt();
        filterVoidUpgradeTier = config.get("6) Upgrade Costs", "12.1) Void Upgrade Tier", 0, "The minimum backpack tier for the void filter upgrade. Default 0.").getInt();
        restockingUpgradeCost = config.get("6) Upgrade Costs", "13) Restocking Upgrade Recipe", 2, "The cost for the restocking upgrade. Default 2.").getInt();
        restockingUpgradeTier = config.get("6) Upgrade Costs", "13.1) Restocking Upgrade Tier", 1, "The minimum backpack tier for the restocking upgrade. Default 1.").getInt();
        craftingUpgradeCost = config.get("6) Upgrade Costs", "14) Crafting Upgrade Recipe", 3, "The cost for the crafting upgrade. Default 3.").getInt();
        craftingUpgradeTier = config.get("6) Upgrade Costs", "14.1) Crafting Upgrade Tier", 1, "The minimum backpack tier for the crafting upgrade. Default 1.").getInt();
        craftingSmallUpgradeCost = config.get("6) Upgrade Costs", "15) Small Crafting Upgrade Recipe", 2, "The cost for the small crafting upgrade. Default 2.").getInt();
        craftingSmallUpgradeTier = config.get("6) Upgrade Costs", "15.1) Small Crafting Upgrade Tier", 1, "The minimum backpack tier for the small crafting upgrade. Default 1.").getInt();
        craftingTinyUpgradeCost = config.get("6) Upgrade Costs", "16) Tiny Crafting Upgrade Recipe", 2, "The cost for the tiny crafting upgrade. Default 2.").getInt();
        craftingTinyUpgradeTier = config.get("6) Upgrade Costs", "16.1) Tiny Crafting Upgrade Tier", 1, "The minimum backpack tier for the tiny crafting upgrade. Default 1.").getInt();
        eternityUpgradeCost = config.get("6) Upgrade Costs", "17) Eternity Upgrade Recipe", 5, "The cost for the upgrade that allows you to keep the backpack upon dying. Default 5.").getInt();
        eternityUpgradeTier = config.get("6) Upgrade Costs", "17.1) Eternity Upgrade Tier", 0, "The minimum backpack tier for the eternity upgrade. Default 0.").getInt();
        quickDepositUpgradeCost = config.get("6) Upgrade Costs", "18) Quick Deposit Upgrade Recipe", 2, "The cost for the quick deposit upgrade. Default 2.").getInt();
        quickDepositUpgradeTier = config.get("6) Upgrade Costs", "18.1) Quick Deposit Upgrade Tier", 1, "The minimum backpack tier for the quick deposit upgrade. Default 1.").getInt();
        quickDepositPreciseUpgradeCost = config.get("6) Upgrade Costs", "19) Precise Quick Deposit Upgrade Recipe", 2, "The cost for the precise quick deposit upgrade. Default 2.").getInt();
        quickDepositPreciseUpgradeTier = config.get("6) Upgrade Costs", "19.1) Precise Quick Deposit Upgrade Tier", 1, "The minimum backpack tier for the precise quick deposit upgrade. Default 1.").getInt();
        depthUpgradeCost = config.get("6) Upgrade Costs", "20) Depth Upgrade Recipe", 2, "The cost for the depth deposit upgrade. Default 2.").getInt();
        depthUpgradeTier = config.get("6) Upgrade Costs", "20.1) Depth Upgrade Tier", 0, "The minimum backpack tier for the depth upgrade. Default 0.").getInt();

        disableRendering = config.get("7) Miscellaneous", "0) Disable Rendering", false, "To disable the model rendering on the player when they have an equipped backpack. Default false.").getBoolean();
        disableFPPrendering = config.get("7) Miscellaneous", "1) Enable FPP Rendering", false, "To disable the model rendering on the player when they have an equipped backpack and are in first person perspective. " +
                "You sometimes see it if you spin to the side quickly, the backpack takes longer to readjust than your forward vision as it swings back onto your back. Default false.").getBoolean();
        renamingUpgradeRequired = config.get("7) Miscellaneous", "2) Renaming Upgrade Required", false, "If the renaming upgrade is required to rename the backpack. Default is false (so you can rename backpacks natively).").getBoolean();
        tooltipDelay = config.get("7) Miscellaneous", "3) Tooltip Delay", 1500, "The delay (in milliseconds) until a tooltip will appear over the buttons. Default is 1500 (so 1.5 seconds).").getInt();
        additionalUpgradePointsLimit = config.get("7) Miscellaneous", "4) Additional Upgrades Limit", 1, "The maximum number of times you can apply the 'additional upgrade points' upgrade. This number represents the starting number (for the basic backpack), each backpack tier increments this number by one. " +
                "Negative numbers are allowed (ex: -1 will allow none on basic or iron backpacks, once on gold backpacks, and twice on diamond). Default is 1.").getInt();
        additionalUpgradePointsIncrease = config.get("7) Miscellaneous", "5) Additional Upgrades Increase", 2, "The amount of extra upgrade points the 'additional upgrade points' upgrade will apply. Default is 2.").getInt();
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
