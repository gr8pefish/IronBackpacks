package gr8pefish.ironbackpacks.libs;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.util.TextUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Many of the constant values used in this mod.
 */
public class IronBackpacksConstants {

    //TODO: move to api.constants? Also refactor this so hard

    //General constants
    public static final class General{
        public static final String CLIENTPROXY = "gr8pefish.ironbackpacks.proxies.ClientProxy";
        public static final String COMMONPROXY = "gr8pefish.ironbackpacks.proxies.CommonProxy";
        public static final String GUIFACTORY = "main.ironbackpacks.client.gui.config.ConfigGuiFactory";
    }

    //The backpack constants
    public static final class Backpacks{

        public static final String BASIC_BACKPACK_NAME = "basicBackpack";
        public static final String IRON_BACKPACK_NAME = "ironBackpack";
        public static final String GOLD_BACKPACK_NAME = "goldBackpack";
        public static final String DIAMOND_BACKPACK_NAME = "diamondBackpack";

        public static final int BASIC_ID = 1; //gui ID 0,1,2,3
        public static final int IRON_ID = 2;
        public static final int GOLD_ID = 3;
        public static final int DIAMOND_ID = 4;

    }

    //The upgrade constants
    public static final class Upgrades{

        //================Upgrade Info===================
        public static final String[] BUTTON_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.button.desc"));
        public static final String[] NESTING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.nesting.desc"));
        public static final String[] DAMAGE_BAR_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.bar.desc"));
        public static final String[] ETERNITY_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.soulbound.desc"));
        public static final String[] QUICK_DEPOSIT_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.quickDeposit.desc"));
        public static final String[] QUICK_DEPOSIT_PRECISE_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.quickDepositPrecise.desc"));
        public static final String[] NESTING_ADVANCED_DESRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.advNesting.desc"));
        public static final String[] DEPTH_UPGRADE_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.depth.desc"));

        //Alternate Gui
        public static final String[] RENAMING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.naming.desc"));
        public static final String[] FILTER_BASIC_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.basicFilter.desc"));
        public static final String[] FILTER_MOD_SPECIFIC_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.modSpecificFilter.desc"));
        public static final String[] FILTER_FUZZY_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.fuzzyFilter.desc"));
        public static final String[] FILTER_OREDICT_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.oreDictFilter.desc"));
        public static final String[] FILTER_MINING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.miningFilter.desc"));
        public static final String[] FILTER_VOID_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.voidFilter.desc"));
        public static final String[] FILTER_ADVANCED_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.advFilter.desc"));
        public static final String[] RESTOCKING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.hopperFilter.desc"));
        public static final String[] CRAFTING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.condenser.desc"));
        public static final String[] CRAFTING_SMALL_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.condenser.small.desc"));
        public static final String[] CRAFTING_TINY_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.condenser.tiny.desc"));

        //===========================Misc==================================
        public static final int ALT_GUI_UPGRADES_ALLOWED = ConfigHandler.renamingUpgradeRequired ? 4 : 3;
        private static String formatting1 = ConfigHandler.additionalUpgradePointsIncrease == 1 ? TextUtils.localize("tooltip.ironbackpacks.upgrade.addUpgrades.amount.1") : TextUtils.localize("tooltip.ironbackpacks.upgrade.addUpgrades.amount.mult", ConfigHandler.additionalUpgradePointsIncrease);
        private static String formatting2 = ConfigHandler.additionalUpgradePointsLimit + 3 == 1 ? TextUtils.localize("tooltip.ironbackpacks.upgrade.addUpgrades.once") : TextUtils.localize("tooltip.ironbackpacks.upgrade.addUpgrades.mult", ConfigHandler.additionalUpgradePointsLimit + 3);
        public static final String[] ADDITIONAL_UPGRADE_POINTS_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.addUpgrades.desc", formatting1, formatting2));

    }

    //Gui constants
    public static final class Gui{

    }

    //Messages
    public static final class Messages{

        public static final String CHANNEL = "irnbkpks";

        public static final class SingleByte{
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

            public static final byte EQUIP_BACKPACK_KEYBINDING = 10;
            public static final byte OPEN_BACKPACK_KEYBINDING = 11;
        }

    }

    //Visual resources
    public static final class Resources {
        public static final ResourceLocation WIDGETS = new ResourceLocation(Constants.MODID, "textures/guis/widgets.png");

        //TODO: add to backpack initialization
        public static final ResourceLocation MODEL_BASIC = new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackBasic.png");
        public static final ResourceLocation MODEL_IRON = new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackIron.png");
        public static final ResourceLocation MODEL_GOLD = new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackGold.png");
        public static final ResourceLocation MODEL_DIAMOND = new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackDiamond.png");
        public static final ResourceLocation[] MODEL_TEXTURES = {MODEL_BASIC, MODEL_IRON, MODEL_GOLD, MODEL_DIAMOND};
    }

    //The commonly used NBT keys
    public static final class NBTKeys {

        public static final String UPGRADES = "Upgrades";
        public static final String ADDITIONAL_POINTS = "AdditionalPoints";
        public static final String FILTER_BASIC = "FilterBasic";
        public static final String FILTER_FUZZY = "FilterFuzzy";
        public static final String FILTER_ORE_DICT = "FilterOreDict";
        public static final String FILTER_MOD_SPECIFIC = "FilterModSpecific";
        public static final String FILTER_MINING = "FilterMining";
        public static final String FILTER_VOID = "FilterVoid";
        public static final String FILTER_ADV_ALL_SLOTS = "FilterAdvAllSlots";
        public static final String FILTER_ADV_BUTTONS = "FilterAdvButtons";
        public static final String FILTER_ADV_START = "FilterAdvStart";
        public static final String RESTOCKING = "Hopper";
        public static final String CRAFTING = "Condenser";
        public static final String CRAFTING_SMALL = "CondenserSmall";
        public static final String CRAFTING_TINY = "CondenserTiny";
        public static final String SLOT = "Slot";
        public static final String ITEMS = "Items";
        public static final String ADDED_ALT_GUI = "AddedAltGui";
        public static final String REMOVED = "Removed";
    }

    public static final class Items {
        public static final String BACKPACK_ITEM_BASE_NAME = "backpack";
        public static final String UPGRADE_ITEM_BASE_NAME = "upgrade";
        public static final String CRAFTING_ITEM_BASE_NAME = "crafting";
    }

    //Miscellaneous
    public static final class Miscellaneous{

        //UUID constant strings
        public static final String MOST_SIG_UUID = "MostSigUUID";
        public static final String LEAST_SIG_UUID = "LeastSigUUID";

        //Button upgrade call
        public static final String MOVE_RIGHT = "right";
        public static final String MOVE_LEFT = "left";

        //To get the most updated 1.7.10 of the mod
        public static final String URL_UPDATED_VERSION = "https://raw.githubusercontent.com/gr8pefish/IronBackpacks/master-1.7.10/version/1.7.10"; //HARDCODED to 1.7.10
    }

}
