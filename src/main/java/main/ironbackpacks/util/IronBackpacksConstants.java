package main.ironbackpacks.util;

import main.ironbackpacks.ModInformation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Arrays;

public class IronBackpacksConstants {

    //A lot (but not all) of the constants used in this mod

    public static final class General{

    }

    public static final class Backpacks{
        public static final int BASIC_ID = 1;
        public static final int IRON_ID = 2;
        public static final int GOLD_ID = 3;
        public static final int DIAMOND_ID = 4;
    }

    public static final class Upgrades{
        //=============IDs==================== (not in order so that it doesn't break when updating)
        public static final int BUTTON_UPGRADE_ID = 1;
        public static final int NESTING_UPGRADE_ID = 2;
        public static final int DAMAGE_BAR_UPGRADE_ID = 3;
        public static final int KEEP_ON_DEATH_UPGRADE_ID = 4;
        public static final int ADDITIONAL_UPGRADE_SLOTS_UPGRADE_ID = 10;
        public static final int QUICK_DEPOSIT_UPGRADE_ID = 13;
        //Alternate Gui
        public static final int RENAMING_UPGRADE_ID = 5;
        public static final int FILTER_BASIC_UPGRADE_ID = 6;
        public static final int FILTER_MOD_SPECIFIC_UPGRADE_ID = 7;
        public static final int HOPPER_UPGRADE_ID = 8;
        public static final int CONDENSER_UPGRADE_ID = 9;
        public static final int FILTER_FUZZY_UPGRADE_ID = 11;
        public static final int FILTER_OREDICT_UPGRADE_ID = 12;
        public static final int FILTER_ADVANCED_UPGRADE_ID =14;

        //================Upgrade Info===================
        public static final String[] BUTTON_DESCRIPTION = {"Adds buttons to quickly move items","to and from your backpack, as well as","a button to sort your backpack quickly."};
        public static final String[] NESTING_DESCRIPTION = {"Allows you to place any backpack","of a previous tier inside the backpack."};
        public static final String[] DAMAGE_BAR_DESCRIPTION = {"Adds a damage bar to visually represent","how full the backpack is at a glance."};
        public static final String[] KEEP_ON_DEATH_DESCRIPTION = {"Allows you to keep the backpack when you die.", "The upgrade disappears after death though", "(you will have to reapply it if you want it again)."};
        private static String formatting1 = ConfigHandler.additionalUpgradesIncrease == 1 ? "upgrade point" : ConfigHandler.additionalUpgradesIncrease + " upgrade points";
        private static String formatting2 = ConfigHandler.additionalUpgradesLimit+3 == 1 ? "once" : ConfigHandler.additionalUpgradesLimit+3 + " times";
        public static final String[] ADDITIONAL_UPGRADE_SLOTS_DESCRIPTION = {"Adds another " + formatting1 + " to", "the backpack. Can be applied " + formatting2, "to the highest tier backpack."};
        public static final String[] QUICK_DEPOSIT_DESCRIPTION = {"When you shift right click on an inventory with this upgrade","the backpack will attempt to empty it's contents into the inventory."};

        //Alternate Gui
        public static final String[] RENAMING_DESCRIPTION = {"Allows you to rename the backpack."};
        public static final String[] FILTER_BASIC_DESCRIPTION = {"Adds a configurable filter of items","that the backpack will automatically collect."};
        public static final String[] FILTER_MOD_SPECIFIC_DESCRIPTION = {"Similar to the basic filter, except the backpack will pick up any item","that is from the same mod as the item(s) in the filter slots."};
        public static final String[] FILTER_FUZZY_DESCRIPTION = {"Similar to the basic filter, except the backpack","will ignore damage values on the items."};
        public static final String[] FILTER_OREDICT_DESCRIPTION = {"Similar to the basic filter, except the backpack will ","pick up any item that is registered in the ore dictionary","as the same as the item(s) in the filter slot."};
        public static final String[] HOPPER_DESCRIPTION = {"Allows you to specify an item to keep stocked","in your inventory/hotbar","which is supplied from your backpack."};
        public static final String[] CONDENSER_DESCRIPTION = {"Allows you to specify a set of items","to be compressed upon entering your backpack."};
        public static final String[] FILTER_ADVANCED_DESCRIPTION = {"Similar to the basic filter, except the filter","has 18 slots and each slots has configurable options."};

        public static final ArrayList<Integer> ALT_GUI_UPGRADE_IDS = new ArrayList<Integer>(Arrays.asList(
                RENAMING_UPGRADE_ID,
                FILTER_BASIC_UPGRADE_ID,
                FILTER_FUZZY_UPGRADE_ID,
                FILTER_OREDICT_UPGRADE_ID,
                FILTER_MOD_SPECIFIC_UPGRADE_ID,
                FILTER_ADVANCED_UPGRADE_ID,
                HOPPER_UPGRADE_ID,
                CONDENSER_UPGRADE_ID
                ));

        //All together now
        public static final String[] LOCALIZED_NAMES = {StatCollector.translateToLocal("emptyUpgradeSlot"),
                StatCollector.translateToLocal("item.ironbackpacks:buttonUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:nestingUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:damageBarUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:keepOnDeathUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:renamingUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:filterBasicUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:filterModSpecificUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:hopperUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:condenserUpgrade.name"),
                StatCollector.translateToLocal("emptyUpgradeSlot")+"...", //additional upgrade slot
                StatCollector.translateToLocal("item.ironbackpacks:filterFuzzyUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:filterOreDictUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:quickDepositUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:filterAdvancedUpgrade.name")
        };


        public static final int[] UPGRADE_POINTS = {
                0, //blank upgrade
                ConfigHandler.buttonUpgradeCost,
                ConfigHandler.nestingUpgradeCost,
                ConfigHandler.damageBarUpgradeCost,
                ConfigHandler.keepOnDeathUpgradeCost,
                ConfigHandler.renamingUpgradeCost,
                ConfigHandler.filterBasicUpgradeCost,
                ConfigHandler.filterModSpecificUpgradeCost,
                ConfigHandler.hopperUpgradeCost,
                ConfigHandler.condenserUpgradeCost,
                0, //additional upgrade slots
                ConfigHandler.filterFuzzyUpgradeCost,
                ConfigHandler.filterOreDictUpgradeCost,
                ConfigHandler.quickDepositUpgradeCost,
                ConfigHandler.filterAdvancedUpgradeCost
        };

        //===========================Misc==================================
        public static final int ALT_GUI_UPGRADES_ALLOWED = ConfigHandler.renamingUpgradeRequired ? 4 : 3;

    }

    public static final class Gui{

    }

    public static final class Resources {
        public static final ResourceLocation WIDGETS = new ResourceLocation(ModInformation.ID, "textures/guis/widgets.png");
    }

    public static final class Miscellaneous{
        public static final String MOST_SIG_UUID = "MostSigUUID";
        public static final String LEAST_SIG_UUID = "LeastSigUUID";
    }

}
