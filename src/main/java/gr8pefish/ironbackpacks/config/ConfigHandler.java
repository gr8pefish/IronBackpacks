package gr8pefish.ironbackpacks.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * The configuration file is handled by this class
 */
public class ConfigHandler {

    //================ Backpack Enums (for easy reference by name rather than some number) ============================

    public static int[] basicBackpack;
    public static int[] ironBackpackStorageEmphasis;
    public static int[] ironBackpackUpgradeEmphasis;
    public static int[] goldBackpackStorageEmphasis;
    public static int[] goldBackpackUpgradeEmphasis;
    public static int[] diamondBackpackStorageEmphasis;
    public static int[] diamondBackpackUpgradeEmphasis;

    private static final int valuesToLoadBasic = 3; //upgradePoints, sizeX, sizeY
    private static final int valuesToLoadAdditional = 4; //upgradePoints, sizeX, sizeY, additionalPointsLimit

    //==================== All the publicly accessible values====================================

    public static Configuration config;

    public static boolean isConfigManuallyChanged;

    //TODO: make this and the section below be in a nice, readable order (like my itemRegistry class)

    public static boolean buttonUpgradeDisabled;
    public static int buttonUpgradeCost;
    public static int buttonUpgradeTier;
    public static boolean nestingUpgradeDisabled;
    public static int nestingUpgradeCost;
    public static int nestingUpgradeTier;
    public static boolean renamingUpgradeDisabled;
    public static int renamingUpgradeCost;
    public static int renamingUpgradeTier;
    public static boolean damageBarUpgradeDisabled;
    public static int damageBarUpgradeCost;
    public static int damageBarUpgradeTier;
    public static boolean filterBasicUpgradeDisabled;
    public static int filterBasicUpgradeCost;
    public static int filterBasicUpgradeTier;
    public static boolean filterModSpecificUpgradeDisabled;
    public static int filterModSpecificUpgradeCost;
    public static int filterModSpecificUpgradeTier;
    public static boolean filterFuzzyUpgradeDisabled;
    public static int filterFuzzyUpgradeCost;
    public static int filterFuzzyUpgradeTier;
    public static boolean filterOreDictUpgradeDisabled;
    public static int filterOreDictUpgradeCost;
    public static int filterOreDictUpgradeTier;
    public static boolean restockingUpgradeDisabled;
    public static int restockingUpgradeCost;
    public static int restockingUpgradeTier;
    public static boolean craftingUpgradeDisabled;
    public static int craftingUpgradeCost;
    public static int craftingUpgradeTier;
    public static boolean craftingSmallUpgradeDisabled;
    public static int craftingSmallUpgradeCost;
    public static int craftingSmallUpgradeTier;
    public static boolean craftingTinyUpgradeDisabled;
    public static int craftingTinyUpgradeCost;
    public static int craftingTinyUpgradeTier;
    public static boolean eternityUpgradeDisabled;
    public static int eternityUpgradeCost;
    public static int eternityUpgradeTier;
    public static boolean quickDepositUpgradeDisabled;
    public static int quickDepositUpgradeCost;
    public static int quickDepositUpgradeTier;
    public static boolean quickDepositPreciseUpgradeDisabled;
    public static int quickDepositPreciseUpgradeCost;
    public static int quickDepositPreciseUpgradeTier;
    public static boolean filterAdvancedUpgradeDisabled;
    public static int filterAdvancedUpgradeCost;
    public static int filterAdvancedUpgradeTier;
    public static boolean filterMiningUpgradeDisabled;
    public static int filterMiningUpgradeCost;
    public static int filterMiningUpgradeTier;
    public static boolean nestingAdvancedUpgradeDisabled;
    public static int nestingAdvancedUpgradeCost;
    public static int nestingAdvancedUpgradeTier;
    public static boolean depthUpgradeDisabled;
    public static int depthUpgradeCost;
    public static int depthUpgradeTier;
    public static boolean filterVoidUpgradeDisabled;
    public static int filterVoidUpgradeCost;
    public static int filterVoidUpgradeTier;


    public static boolean disableRendering;
    public static boolean disableFPPrendering;
    public static boolean renamingUpgradeRequired;
    public static int tooltipDelay;
    public static int additionalUpgradePointsIncrease;
    public static boolean makeRenamedBackpacksNamesItalic;


    //========================initialization====================================

    private static File theFile;

    public static void init(File file) {
        config = new Configuration(file);
        theFile = file;
        syncConfig(true);
    }

    public static void syncConfig(boolean firstLoad) {

        basicBackpack = new int[valuesToLoadBasic];
        ironBackpackStorageEmphasis = new int[valuesToLoadAdditional];
        ironBackpackUpgradeEmphasis = new int[valuesToLoadAdditional];
        goldBackpackStorageEmphasis = new int[valuesToLoadAdditional];
        goldBackpackUpgradeEmphasis = new int[valuesToLoadAdditional];
        diamondBackpackStorageEmphasis = new int[valuesToLoadAdditional];
        diamondBackpackUpgradeEmphasis= new int[valuesToLoadAdditional];

        //======================================================Recipe defaults================================


        //===================================================Categories======================================

        config.load();

        String category;

        //============================================Initializing everything, the numbers keep them in the order I want=======================================

        category = "0) Config Changes";
        config.addCustomCategoryComment(category, "IMPORTANT: If you ever manually edit ANY of these values, please change the value below to true. " +
                "This will prevent them being overwritten if an update changes the config file.");
        isConfigManuallyChanged = config.getBoolean("Manually Changed", category, false, "If you modify ANY values here set this to true so a future update doesn't override them. Otherwise leave it as false and any new changes in the config defaults will automatically be updated.");

        //if loading for the first time and can change configs
        //then delete the file and let a new one regen (inefficient but effective)
        //TODO: make this check versions before doing so, refactor this concept
        if (firstLoad && !isConfigManuallyChanged){
            if(theFile.delete()){
                config = new Configuration(theFile);
                syncConfig(false);
            }
        }

        //TODO: balance all these out

        category = "1) Backpack Traits";
        config.addCustomCategoryComment(category + " 1) (Basic)", "The configurable traits of the basic backpack.");
        basicBackpack[0] = config.getInt("Upgrade Points", category+" 1) (Basic)", 6, 0, 100, "The number of upgrade points on the backpack.");
        basicBackpack[1] = config.getInt("Number of Slots Per Row", category+" 1) (Basic)", 9, 9, 11, "The size of the backpack. Either 9 or 11.");
        basicBackpack[2] = config.getInt("Number of Rows", category+" 1) (Basic)", 2, 1, 7, "The size of the backpack.");

        config.addCustomCategoryComment(category + " 2) (Iron - Storage Emphasis)", "The configurable traits of the iron backpack (storage emphasis).");
        ironBackpackStorageEmphasis[0] = config.getInt("Upgrade Points", category+" 2) (Iron - Storage Emphasis)", 8, 0, 100, "The number of upgrade points on the backpack.");
        ironBackpackStorageEmphasis[1] = config.getInt("Number of Slots Per Row", category+" 2) (Iron - Storage Emphasis)", 9, 9, 11, "The size of the backpack. Either 9 or 11.");
        ironBackpackStorageEmphasis[2] = config.getInt("Number of Rows", category+" 2) (Iron - Storage Emphasis)", 4, 1, 7, "The size of the backpack.");
        ironBackpackStorageEmphasis[3] = config.getInt("Number of Additional Upgrade Points", category+" 2) (Iron - Storage Emphasis)", 0, 0, 100, "The total number of additional upgrade points that can be applied to the backpack.");

        config.addCustomCategoryComment(category + " 3) (Iron - Upgrade Emphasis)", "The configurable traits of the iron backpack (upgrade emphasis).");
        ironBackpackUpgradeEmphasis[0] = config.getInt("Upgrade Points", category+" 3) (Iron - Upgrade Emphasis)", 12, 0, 100, "The number of upgrade points on the backpack.");
        ironBackpackUpgradeEmphasis[1] = config.getInt("Number of Slots Per Row", category+" 3) (Iron - Upgrade Emphasis)", 9, 9, 11, "The size of the backpack. Either 9 or 11.");
        ironBackpackUpgradeEmphasis[2] = config.getInt("Number of Rows", category+" 3) (Iron - Upgrade Emphasis)", 3, 1, 7, "The size of the backpack.");
        ironBackpackUpgradeEmphasis[3] = config.getInt("Number of Additional Upgrade Points", category+" 3) (Iron - Upgrade Emphasis)", 2, 0, 100, "The total number of additional upgrade points that can be applied to the backpack.");

        config.addCustomCategoryComment(category + " 4) (Gold - Storage Emphasis)", "The configurable traits of the gold backpack (storage emphasis).");
        goldBackpackStorageEmphasis[0] = config.getInt("Upgrade Points", category+" 4) (Gold - Storage Emphasis)", 10, 0, 100, "The number of upgrade points on the backpack.");
        goldBackpackStorageEmphasis[1] = config.getInt("Number of Slots Per Row", category+" 4) (Gold - Storage Emphasis)", 9, 9, 11, "The size of the backpack. Either 9 or 11.");
        goldBackpackStorageEmphasis[2] = config.getInt("Number of Rows", category+" 4) (Gold - Storage Emphasis)", 6, 1, 7, "The size of the backpack.");
        goldBackpackStorageEmphasis[3] = config.getInt("Number of Additional Upgrade Points", category+" 4) (Gold - Storage Emphasis)", 1, 0, 100, "The total number of additional upgrade points that can be applied to the backpack.");

        config.addCustomCategoryComment(category + " 5) (Gold - Upgrade Emphasis)", "The configurable traits of the gold backpack (upgrade emphasis).");
        goldBackpackUpgradeEmphasis[0] = config.getInt("Upgrade Points", category+" 5) (Gold - Upgrade Emphasis)", 16, 0, 100, "The number of upgrade points on the backpack.");
        goldBackpackUpgradeEmphasis[1] = config.getInt("Number of Slots Per Row", category+" 5) (Gold - Upgrade Emphasis)", 9, 9, 11, "The size of the backpack. Either 9 or 11.");
        goldBackpackUpgradeEmphasis[2] = config.getInt("Number of Rows", category+" 5) (Gold - Upgrade Emphasis)", 5, 1, 7, "The size of the backpack.");
        goldBackpackUpgradeEmphasis[3] = config.getInt("Number of Additional Upgrade Points", category+" 5) (Gold - Upgrade Emphasis)", 4, 0, 100, "The total number of additional upgrade points that can be applied to the backpack.");

        config.addCustomCategoryComment(category + " 6) (Diamond - Storage Emphasis)", "The configurable traits of the diamond backpack (storage emphasis).");
        diamondBackpackStorageEmphasis[0] = config.getInt("Upgrade Points", category+" 6) (Diamond - Storage Emphasis)", 12, 0, 100, "The number of upgrade points on the backpack.");
        diamondBackpackStorageEmphasis[1] = config.getInt("Number of Slots Per Row", category+" 6) (Diamond - Storage Emphasis)", 11, 9, 11, "The size of the backpack. Either 9 or 11.");
        diamondBackpackStorageEmphasis[2] = config.getInt("Number of Rows", category+" 6) (Diamond - Storage Emphasis)", 7, 1, 7, "The size of the backpack.");
        diamondBackpackStorageEmphasis[3] = config.getInt("Number of Additional Upgrade Points", category+" 6) (Diamond - Storage Emphasis)", 2, 0, 100, "The total number of additional upgrade points that can be applied to the backpack.");

        config.addCustomCategoryComment(category + " 7) (Diamond - Upgrade Emphasis)", "The configurable traits of the diamond backpack (upgrade emphasis).");
        diamondBackpackUpgradeEmphasis[0] = config.getInt("Upgrade Points", category+" 7) (Diamond - Upgrade Emphasis)", 20, 0, 100, "The number of upgrade points on the backpack.");
        diamondBackpackUpgradeEmphasis[1] = config.getInt("Number of Slots Per Row", category+" 7) (Diamond - Upgrade Emphasis)", 9, 9, 11, "The size of the backpack. Either 9 or 11.");
        diamondBackpackUpgradeEmphasis[2] = config.getInt("Number of Rows", category+" 7) (Diamond - Upgrade Emphasis)", 7, 1, 7, "The size of the backpack.");
        diamondBackpackUpgradeEmphasis[3] = config.getInt("Number of Additional Upgrade Points", category+" 7) (Diamond - Upgrade Emphasis)", 6, 0, 100, "The total number of additional upgrade points that can be applied to the backpack.");



        String category_upgrade = "4) Upgrade Traits";
        config.addCustomCategoryComment(category_upgrade, "The configurable traits of the upgrades. Be warned that the ability to disable an upgrade is experimental, it may cause odd errors.");

        category = category_upgrade + "- 1)Basic Upgrades";
        config.addCustomCategoryComment(category, "The configurable traits of the \"normal\" upgrades.");

        buttonUpgradeCost = config.getInt("Button Upgrade Cost", category, 2, 0, 100, "The cost (in upgrade points) for the button upgrade.\n" +
                "This upgrade allows the player to move items to and from the backpack quickly by use of some button.");
        buttonUpgradeTier = config.getInt("Button Upgrade Tier", category, 0, 0, 3, "The minimum backpack tier for the button upgrade.");
        buttonUpgradeDisabled = config.getBoolean("Button Upgrade Disabled", category, false, "If the button upgrade should not exist.");

        damageBarUpgradeCost = config.getInt("Damage Bar Upgrade Cost", category, 2, 0, 100, "The cost (in upgrade points) for the damage bar upgrade.\n" +
                "This upgrade adds a damage bar that visually represents how full the backpack is at a glance.");
        damageBarUpgradeTier = config.getInt("Damage Bar Upgrade Tier", category, 0, 0, 3, "The minimum backpack tier for the damage bar upgrade.");
        damageBarUpgradeDisabled = config.getBoolean("Damage Bar Upgrade Disabled", category, false, "If the damage bar upgrade should not exist.");

        depthUpgradeCost = config.getInt("Depth Upgrade Cost", category, 2, 0, 100, "The cost (in upgrade points) for the depth deposit upgrade.\n" +
                "This upgrade allows other backpacks's configurable upgrades to still function when they are nested inside a backpack with this upgrade.");
        depthUpgradeTier = config.getInt("Depth Upgrade Tier", category, 0, 0, 3, "The minimum backpack tier for the depth upgrade.");
        depthUpgradeDisabled = config.getBoolean("Depth Upgrade Disabled", category, false, "If the depth upgrade should not exist.");

        eternityUpgradeCost = config.getInt("Eternity Upgrade Cost", category, 5, 0, 100, "The cost (in upgrade points) for the eternity upgrade.\n" +
                "This upgrade allows you to keep the backpack upon dying, but it disappears after it is used and must be reapplied.");
        eternityUpgradeTier = config.getInt("Eternity Upgrade Tier", category, 0, 0, 3, "The minimum backpack tier for the eternity upgrade.");
        eternityUpgradeDisabled = config.getBoolean("Eternity Upgrade Disabled", category, false, "If the eternity upgrade should not exist.");

        renamingUpgradeCost = config.getInt("Renaming Upgrade Cost", category, 1, 0, 100,"The cost (in upgrade points) for the renaming upgrade. \n" +
                "This upgrade allows the player to rename the backpack to anything they want, by usage of a text input box in the configurable inventory.\n" +
                "Only used if the upgrade is required (another option in config, miscellaneous section).");
        renamingUpgradeTier = config.getInt("Renaming Upgrade Tier", category, 0, 0, 3, "The minimum backpack tier for the renaming upgrade. Only used if the upgrade is required (another option in config, miscellaneous section).");
        renamingUpgradeDisabled = config.getBoolean("Renaming Upgrade Disabled", category, false, "If the renaming upgrade should not exist.");

        category = category_upgrade + "- 2)Conflicting Upgrades";
        config.addCustomCategoryComment(category, "The configurable traits of the upgrades that cannot be simultaneously applied with another upgrade (e.g. nesting + advanced nesting).");

        nestingUpgradeCost = config.getInt("Nesting Upgrade Cost", category, 3, 0, 100, "The cost (in upgrade points) for the nesting upgrade.\n" +
                "This upgrade allows the player to put any backpack inside another if it is of a lower tier.");
        nestingUpgradeTier = config.getInt("Nesting Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the nesting upgrade.");
        nestingUpgradeDisabled = config.getBoolean("Nesting Upgrade Disabled", category, false, "If the nesting upgrade should not exist.");

        nestingAdvancedUpgradeCost = config.getInt("Advanced Nesting Upgrade Cost", category, 5, 0, 100, "The cost (in upgrade points) for the advanced nesting upgrade.\n" +
                "This upgrade allows the player to put any backpack inside another, regardless of tier.");
        nestingAdvancedUpgradeTier = config.getInt("Advanced Nesting Upgrade Tier", category, 0, 0, 3, "The minimum backpack tier for the advanced nesting upgrade.");
        nestingAdvancedUpgradeDisabled = config.getBoolean("Advanced Nesting Upgrade Disabled", category, false, "If the advanced nesting upgrade should not exist.");

        quickDepositUpgradeCost = config.getInt("Quick Deposit Upgrade Cost", category, 2, 0, 100, "The cost (in upgrade points) for the quick deposit upgrade.\n" +
                "This upgrade allows you to shift right click an inventory to drop off the contents of the backpack directly into the inventory.");
        quickDepositUpgradeTier = config.getInt("Quick Deposit Upgrade Tier", category, 1, 0, 3,"The minimum backpack tier for the quick deposit upgrade.");
        quickDepositUpgradeDisabled = config.getBoolean("Quick Deposit Upgrade Disabled", category, false, "If the quick deposit upgrade should not exist.");

        quickDepositPreciseUpgradeCost = config.getInt("Precise Quick Deposit Upgrade Cost", category, 2, 0, 100, "The cost (in upgrade points) for the precise quick deposit upgrade.\n" +
                "This upgrade allows you to shift right click an inventory to drop off the contents of the backpack directly into the inventory, but only the items that are already present in the inventory will be transferred from the backpack.");
        quickDepositPreciseUpgradeTier = config.getInt("Precise Quick Deposit Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the precise quick deposit upgrade.");
        quickDepositPreciseUpgradeDisabled = config.getBoolean("Precise Quick Deposit Upgrade Disabled", category, false, "If the precise quick deposit upgrade should not exist.");

        category = category_upgrade + "- 3)Configurable Upgrades";
        config.addCustomCategoryComment(category, "The configurable traits of the upgrades that add something to the configurable inventory of the backpack (in-game).");

        craftingUpgradeCost = config.getInt("Crafting Upgrade Cost", category, 3, 0, 100, "The cost (in upgrade points) for the recipes upgrade.\n" +
                "This upgrade allows you to craft the items in a 3x3 grid (ex: wheat -> hay bale)");
        craftingUpgradeTier = config.getInt("Crafting Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the recipes upgrade.");
        craftingUpgradeDisabled = config.getBoolean("Crafting Upgrade Disabled", category, false, "If the recipes upgrade should not exist.");

        craftingSmallUpgradeCost = config.getInt("Small Crafting Upgrade Cost", category, 2, 0, 100, "The cost (in upgrade points) for the small recipes upgrade.\n" +
                "This upgrade allows you to craft the items in a 2x2 grid (ex: sand -> sandstone)");
        craftingSmallUpgradeTier = config.getInt("Small Crafting Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the small recipes upgrade.");
        craftingSmallUpgradeDisabled = config.getBoolean("Small Crafting Upgrade Disabled", category, false, "If the small recipes upgrade should not exist.");

        craftingTinyUpgradeCost = config.getInt("Tiny Crafting Upgrade Cost", category, 2, 0, 100, "The cost (in upgrade points) for the tiny recipes upgrade.\n" +
                "This upgrade allows you to craft the items in a 1x1 grid (ex: log -> planks)");
        craftingTinyUpgradeTier = config.getInt("Tiny Crafting Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the tiny recipes upgrade.");
        craftingTinyUpgradeDisabled = config.getBoolean("Tiny Crafting Upgrade Disabled", category, false, "If the tiny recipes upgrade should not exist.");

        filterBasicUpgradeCost = config.getInt("Basic Filter Upgrade Cost", category, 3, 0, 100, "The cost (in upgrade points) for the basic filter upgrade.\n" +
                "This upgrade allows you to choose items to go directly into the backpack when they are picked up. They must match exactly.");
        filterBasicUpgradeTier = config.getInt("Basic Filter Upgrade Tier", category, 0, 0, 3, "The minimum backpack tier for the basic filter upgrade.");
        filterBasicUpgradeDisabled = config.getBoolean("Basic Filter Upgrade Disabled", category, false, "If the basic filter upgrade should not exist.");

        filterFuzzyUpgradeCost = config.getInt("Fuzzy Filter Upgrade Cost", category, 4, 0, 100, "The cost (in upgrade points) for the fuzzy filter upgrade.\n" +
                "This upgrade allows you to choose items to go directly into the backpack when they are picked up. They can have different damage values (e.g. any sword, regardless of damage).");
        filterFuzzyUpgradeTier = config.getInt("Fuzzy Filter Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the fuzzy filter upgrade.");
        filterFuzzyUpgradeDisabled = config.getBoolean("Fuzzy Filter Upgrade Disabled", category, false, "If the fuzzy filter upgrade should not exist.");

        filterOreDictUpgradeCost = config.getInt("Ore Dictionary Filter Upgrade Cost", category, 4, 0, 100, "The cost (in upgrade points) for the ore dictionary filter upgrade.\n" +
                "This upgrade allows you to choose items to go directly into the backpack when they are picked up. They can match in the ore dictionary (e.g. any log or any copper ore).");
        filterOreDictUpgradeTier = config.getInt("Ore Dictionary Filter Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the ore dictionary filter upgrade.");
        filterOreDictUpgradeDisabled = config.getBoolean("Ore Dictionary Filter Upgrade Disabled", category, false, "If the ore dictionary filter upgrade should not exist.");

        filterModSpecificUpgradeCost = config.getInt("Mod Specific Filter Upgrade Cost", category, 4, 0, 100, "The cost (in upgrade points) for the mod specific filter upgrade.\n" +
                "This upgrade allows you to choose items to go directly into the backpack when they are picked up. They are filtered by the mod, so anything from Botania placed in the filter will mean any Botania items is automatically routed into the backpack..");
        filterModSpecificUpgradeTier = config.getInt("Mod Specific Filter Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the mod specific filter upgrade.");
        filterModSpecificUpgradeDisabled = config.getBoolean("Mod Specific Filter Upgrade Disabled", category, false, "If the mod specific filter upgrade should not exist.");

        filterVoidUpgradeCost = config.getInt("Void Upgrade Cost", category, 2, 0, 100, "The cost (in upgrade points) for the void filter upgrade.\n" +
                "This upgrade allows you to choose items to go be deleted when they are picked up.");
        filterVoidUpgradeTier = config.getInt("Void Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the void filter upgrade.");
        filterVoidUpgradeDisabled = config.getBoolean("Void Filter Upgrade Disabled", category, false, "If the void filter upgrade should not exist.");

        filterAdvancedUpgradeCost = config.getInt("Advanced Filter Upgrade Cost", category, 6, 0, 100, "The cost (in upgrade points) for the advanced filter upgrade.\n" +
                "This upgrade allows you to choose items to go directly into the backpack when they are picked up. You can choose each configurable items to be filtered based on exact, fuzzy, ore dictionary, mod specific, or void matching.");
        filterAdvancedUpgradeTier = config.getInt("Advanced Filter Upgrade Tier", category, 3, 0, 3, "The minimum backpack tier for the advanced filter upgrade.");
        filterAdvancedUpgradeDisabled = config.getBoolean("Advanced Filter Upgrade Disabled", category, false, "If the advanced filter upgrade should not exist.");

        filterMiningUpgradeCost = config.getInt("Mining Upgrade Cost", category, 4, 0, 100, "The cost (in upgrade points) for the mining filter upgrade.\n" +
                "This upgrade automatically picks up any ores or gems, and has a basic filter for items like cobblestone/granite/etc.");
        filterMiningUpgradeTier = config.getInt("Mining Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the mining filter upgrade.");
        filterMiningUpgradeDisabled = config.getBoolean("Mining Filter Upgrade Disabled", category, false, "If the mining filter upgrade should not exist.");

        restockingUpgradeCost = config.getInt("Restocking Upgrade Cost", category, 2, 0, 100, "The cost (in upgrade points) for the restocking upgrade.\n" +
                "This upgrade automatically restocks the specified items in your inventory from the backpack's inventory.");
        restockingUpgradeTier = config.getInt("Restocking Upgrade Tier", category, 1, 0, 3, "The minimum backpack tier for the restocking upgrade.");
        restockingUpgradeDisabled = config.getBoolean("Restocking Upgrade Disabled", category, false, "If the restocking upgrade should not exist.");


        category = "3) Miscellaneous";
        config.setCategoryComment(category, "Here you can modify all the miscellaneous tweaks regarding this mod.");

        disableRendering = config.getBoolean("Disable Rendering", category, false, "To disable the model rendering on the player when they have an equipped backpack.");
        disableFPPrendering = config.getBoolean("Enable FPP Rendering", category, true, "To disable the model rendering on the player when they have an equipped backpack and are in first person perspective. " +
                "You sometimes see it if you spin to the side or look down quickly, the backpack takes longer to readjust than your forward vision as it takes time to swing onto your back again.");
        renamingUpgradeRequired = config.getBoolean("Renaming Upgrade Required", category, false, "If the renaming upgrade is required to rename the backpack. Default is false (so you can rename backpacks natively).");
        additionalUpgradePointsIncrease = config.getInt("Additional Upgrades Increase", category, 1, 0, 10, "The amount of extra upgrade points the 'additional upgrade points' upgrade will apply.");
        makeRenamedBackpacksNamesItalic = config.getBoolean("Italic Renames", category, false, "Make the styling of the letters on a renamed backpack be in italics.");
        tooltipDelay = config.getInt("Tooltip Delay", category, 1500, 0, 10000, "The delay (in milliseconds) until a tooltip will appear over the buttons. Default is 1.5 seconds.");

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

    public enum enumIronBackpackStorageEmphasis {
        upgradePoints(ironBackpackStorageEmphasis[0]),
        sizeX(ironBackpackStorageEmphasis[1]),
        sizeY(ironBackpackStorageEmphasis[2]),
        additionalPoints(ironBackpackStorageEmphasis[3]);
        private int value;

        enumIronBackpackStorageEmphasis(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum enumIronBackpackUpgradeEmphasis {
        upgradePoints(ironBackpackUpgradeEmphasis[0]),
        sizeX(ironBackpackUpgradeEmphasis[1]),
        sizeY(ironBackpackUpgradeEmphasis[2]),
        additionalPoints(ironBackpackUpgradeEmphasis[3]);
        private int value;

        enumIronBackpackUpgradeEmphasis(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum enumGoldBackpackStorageEmphasis {
        upgradePoints(goldBackpackStorageEmphasis[0]),
        sizeX(goldBackpackStorageEmphasis[1]),
        sizeY(goldBackpackStorageEmphasis[2]),
        additionalPoints(goldBackpackStorageEmphasis[3]);
        private int value;

        enumGoldBackpackStorageEmphasis(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum enumGoldBackpackUpgradeEmphasis {
        upgradePoints(goldBackpackUpgradeEmphasis[0]),
        sizeX(goldBackpackUpgradeEmphasis[1]),
        sizeY(goldBackpackUpgradeEmphasis[2]),
        additionalPoints(goldBackpackUpgradeEmphasis[3]);
        private int value;

        enumGoldBackpackUpgradeEmphasis(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum enumDiamondBackpackStorageEmphasis {
        upgradePoints(diamondBackpackStorageEmphasis[0]),
        sizeX(diamondBackpackStorageEmphasis[1]),
        sizeY(diamondBackpackStorageEmphasis[2]),
        additionalPoints(diamondBackpackStorageEmphasis[3]);
        private int value;

        enumDiamondBackpackStorageEmphasis(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum enumDiamondBackpackUpgradeEmphasis {
        upgradePoints(diamondBackpackUpgradeEmphasis[0]),
        sizeX(diamondBackpackUpgradeEmphasis[1]),
        sizeY(diamondBackpackUpgradeEmphasis[2]),
        additionalPoints(diamondBackpackUpgradeEmphasis[3]);
        private int value;

        enumDiamondBackpackUpgradeEmphasis(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
