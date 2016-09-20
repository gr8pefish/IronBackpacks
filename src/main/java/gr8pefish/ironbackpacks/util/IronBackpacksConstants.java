package gr8pefish.ironbackpacks.util;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import net.minecraft.util.ResourceLocation;

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
        public static final String IRON_BACKPACK_NAME_EMPHASIS_STORAGE = "ironBackpackStorageEmphasis";
        public static final String IRON_BACKPACK_NAME_EMPHASIS_UPGRADES = "ironBackpackUpgradeEmphasis";
        public static final String GOLD_BACKPACK_NAME_EMPHASIS_STORAGE = "goldBackpackStorageEmphasis";
        public static final String GOLD_BACKPACK_NAME_EMPHASIS_UPGRADES = "goldBackpackUpgradeEmphasis";
        public static final String DIAMOND_BACKPACK_NAME_EMPHASIS_STORAGE = "diamondBackpackStorageEmphasis";
        public static final String DIAMOND_BACKPACK_NAME_EMPHASIS_UPGRADES = "diamondBackpackUpgradeEmphasis";

    }

    //The upgrade constants
    public static final class Upgrades{

        //===========================Misc==================================
        public static final int ALT_GUI_UPGRADES_ALLOWED = ConfigHandler.renamingUpgradeRequired ? 4 : 3;

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
        public static final String REMOVED_ALT_GUI = "RemovedAltGui";
    }

    //Miscellaneous
    public static final class Miscellaneous{

        //UUID constant strings
        public static final String MOST_SIG_UUID = "MostSigUUID";
        public static final String LEAST_SIG_UUID = "LeastSigUUID";

        //Button upgrade call
        public static final String MOVE_RIGHT = "right";
        public static final String MOVE_LEFT = "left";

        //To get the most updated 1.10.2 of the mod
        public static final String URL_UPDATED_VERSION = "https://raw.githubusercontent.com/gr8pefish/IronBackpacks/master-1.10/version/1.10.2"; //HARDCODED
    }

}
