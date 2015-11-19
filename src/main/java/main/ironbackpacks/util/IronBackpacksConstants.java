package main.ironbackpacks.util;

import main.ironbackpacks.ModInformation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Many of the constant values used in this mod.
 */
public class IronBackpacksConstants {

    //General constants
    public static final class General {

    }

    //The backpack constants
    public static final class Backpacks {
        public static final int BASIC_ID = 1;
        public static final int IRON_ID = 2;
        public static final int GOLD_ID = 3;
        public static final int DIAMOND_ID = 4;
        public static final int ENDER_ID = 5;
    }

    //The upgrade constants
    public static final class Upgrades {
        //=============IDs==================== (not in order so that it doesn't break when updating)
        public static final int BUTTON_UPGRADE_ID = 1;
        public static final int NESTING_UPGRADE_ID = 2;
        public static final int DAMAGE_BAR_UPGRADE_ID = 3;
        public static final int KEEP_ON_DEATH_UPGRADE_ID = 4;
        public static final int ADDITIONAL_UPGRADE_SLOTS_UPGRADE_ID = 10;
        public static final int QUICK_DEPOSIT_UPGRADE_ID = 13;
        public static final int ADVANCED_NESTING_UPGRADE_ID = 15;
        //Alternate Gui
        public static final int RENAMING_UPGRADE_ID = 5;
        public static final int FILTER_BASIC_UPGRADE_ID = 6;
        public static final int FILTER_MOD_SPECIFIC_UPGRADE_ID = 7;
        public static final int HOPPER_UPGRADE_ID = 8;
        public static final int CONDENSER_UPGRADE_ID = 9;
        public static final int FILTER_FUZZY_UPGRADE_ID = 11;
        public static final int FILTER_OREDICT_UPGRADE_ID = 12;
        public static final int FILTER_ADVANCED_UPGRADE_ID = 14;


        //================Upgrade Info=================== //TODO: localize
        public static final String[] BUTTON_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.button.desc"));
        public static final String[] NESTING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.nesting.desc"));
        public static final String[] DAMAGE_BAR_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.bar.desc"));
        public static final String[] KEEP_ON_DEATH_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.soulbound.desc"));
        public static final String[] QUICK_DEPOSIT_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.quickDep.desc"));
        public static final String[] ADVANCED_NESTING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.advNesting.desc"));
        //Alternate Gui
        public static final String[] RENAMING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.naming.desc"));
        public static final String[] FILTER_BASIC_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.basicFilter.desc"));
        public static final String[] FILTER_MOD_SPECIFIC_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.modSpecificFilter.desc"));
        public static final String[] FILTER_FUZZY_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.fuzzyFilter.desc"));
        public static final String[] FILTER_OREDICT_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.oreDictFilter.desc"));
        public static final String[] HOPPER_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.hopperFilter.desc"));
        public static final String[] CONDENSER_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.condenser.desc"));
        public static final String[] FILTER_ADVANCED_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.advFilter.desc"));
        //All the alternate gui upgrade ids
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
        //All the localized names
        public static final String[] LOCALIZED_NAMES = {
                StatCollector.translateToLocal("gui.ironbackpacks.emptyUpgradeSlot"),
                StatCollector.translateToLocal("item.ironbackpacks:buttonUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:nestingUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:damageBarUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:keepOnDeathUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:renamingUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:filterBasicUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:filterModSpecificUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:hopperUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:condenserUpgrade.name"),
                StatCollector.translateToLocal("gui.ironbackpacks.emptyUpgradeSlot") + "...", //additional upgrade slot
                StatCollector.translateToLocal("item.ironbackpacks:filterFuzzyUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:filterOreDictUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:quickDepositUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:filterAdvancedUpgrade.name"),
                StatCollector.translateToLocal("item.ironbackpacks:nestingAdvancedUpgrade.name")
        };
        //All the upgrade costs
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
                ConfigHandler.filterAdvancedUpgradeCost,
                ConfigHandler.nestingAdvancedUpgradeCost
        };
        //===========================Misc==================================
        public static final int ALT_GUI_UPGRADES_ALLOWED = ConfigHandler.renamingUpgradeRequired ? 4 : 3;
        private static String formatting1 = ConfigHandler.additionalUpgradesIncrease == 1 ? TextUtils.localize("tooltip.ironbackpacks.upgrade.addUpgrades.amount.1") : TextUtils.localize("tooltip.ironbackpacks.upgrade.addUpgrades.amount.mult", ConfigHandler.additionalUpgradesIncrease);
        private static String formatting2 = ConfigHandler.additionalUpgradesLimit + 3 == 1 ? TextUtils.localize("tooltip.ironbackpacks.upgrade.addUpgrades.once") : TextUtils.localize("tooltip.ironbackpacks.upgrade.addUpgrades.mult", ConfigHandler.additionalUpgradesLimit + 3);
        public static final String[] ADDITIONAL_UPGRADE_SLOTS_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.addUpgrades.desc", formatting1, formatting2));

    }

    //Gui constants
    public static final class Gui {

    }

    //Messages
    public static final class Messages {

        public static final class SingleByte {
            //the actions stored as (arbitrary) byte values
            public static final byte CLEAR_ROW_1 = 1;
            public static final byte CLEAR_ROW_2 = 2;
            public static final byte CLEAR_ROW_3 = 3;

            public static final byte BACKPACK_TO_INVENTORY = 4;
            public static final byte INVENTORY_TO_BACKPACK = 5;
            public static final byte HOTBAR_TO_BACKPACK = 6;
            public static final byte SORT_BACKPACK = 7;

            public static final byte MOVE_LEFT = 8;
            public static final byte MOVE_RIGHT = 9;
        }


    }

    //Visual resources
    public static final class Resources {
        public static final ResourceLocation WIDGETS = new ResourceLocation(ModInformation.ID, "textures/guis/widgets.png");
    }

    //The commonly used NBT keys
    public static final class NBTKeys {
        public static final String UPGRADES = "Upgrades";
        public static final String UPGRADE = "Upgrade";
        public static final String ADDITIONAL_POINTS = "AdditionalPoints";
        public static final String FILTER_BASIC = "FilterBasic";
        public static final String FILTER_FUZZY = "FilterFuzzy";
        public static final String FILTER_ORE_DICT = "FilterOreDict";
        public static final String FILTER_MOD_SPECIFIC = "FilterModSpecific";
        public static final String FILTER_ADV_ALL_SLOTS = "FilterAdvAllSlots";
        public static final String FILTER_ADV_BUTTONS = "FilterAdvButtons";
        public static final String FILTER_ADV_START = "FilterAdvStart";
        public static final String SLOT = "Slot";
        public static final String HOPPER = "Hopper";
        public static final String CONDENSER = "Condenser";
        public static final String ITEMS = "Items";
        public static final String ADDED = "Added";
        public static final String REMOVED = "Removed";
    }

    //Miscellaneous
    public static final class Miscellaneous {

        //UUID constant strings
        public static final String MOST_SIG_UUID = "MostSigUUID";
        public static final String LEAST_SIG_UUID = "LeastSigUUID";

        //Button upgrade call
        public static final String MOVE_RIGHT = "right";
        public static final String MOVE_LEFT = "left";

    }

}
