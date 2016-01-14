package main.ironbackpacks.registry;

import com.google.common.collect.MapMaker;
import main.ironbackpacks.api.client.gui.button.ButtonNames;
import main.ironbackpacks.api.client.gui.button.IBGuiButton;
import main.ironbackpacks.util.Logger;
import main.ironbackpacks.util.TextUtils;

import java.util.ArrayList;
import java.util.Map;

public class IBGuiButtonRegistry {

    private static Map<ButtonNames, IBGuiButton> buttons = new MapMaker().weakKeys().weakValues().makeMap();
    private static IBGuiButton[] advFilterButtons;

    public static void initButtons(){
        Logger.info("HAPPENIGN");
                                    //id, width, height, iconOffsetX, iconOffsetY, tooltip delay, tooltip
        registerButton(ButtonNames.RENAME, new IBGuiButton(0, 25, 10, 1, 1, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.rename.tooltip"))));
        registerButton(ButtonNames.EXACT, new IBGuiButton(1, 16, 5, 0, 13, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.exact.tooltip"))));
        registerButton(ButtonNames.FUZZY, new IBGuiButton(2, 16, 5, 0, 19, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.fuzzy.tooltip"))));
        registerButton(ButtonNames.ORE_DICT, new IBGuiButton(3, 16, 5, 0, 25, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.oreDict.tooltip"))));
        registerButton(ButtonNames.MOD_SPECIFIC, new IBGuiButton(4, 16, 5, 0, 31, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.modSpecific.tooltip"))));
        registerButton(ButtonNames.CLEAR_ROW, new IBGuiButton(5, 11, 12, 64, 64, true, TextUtils.cutLongString(TextUtils.localizeEffect("")))); //5,6,7 because can clear 3 rows
        registerButton(ButtonNames.BACKPACK_TO_INVENTORY, new IBGuiButton(8, 11, 12, 64, 0, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.packToInv.tooltip"))));
        registerButton(ButtonNames.INVENTORY_TO_BACKPACK, new IBGuiButton(9, 11, 12, 64, 16, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.invToPack.tooltip"))));
        registerButton(ButtonNames.HOTBAR_TO_BACKPACK, new IBGuiButton(10, 11, 12, 64, 32, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.barToPack.tooltip"))));
        registerButton(ButtonNames.SORT_BACKPACK, new IBGuiButton(11, 11, 12, 64, 48, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.sort.tooltip"))));
        registerButton(ButtonNames.MOVE_LEFT, new IBGuiButton(12, 4, 9, 18, 57, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.left.tooltip"))));
        registerButton(ButtonNames.MOVE_RIGHT, new IBGuiButton(13, 4, 9, 18, 37, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.right.tooltip"))));

        setAdvancedFilterButtons();
    }

    private static void registerButton(ButtonNames name, IBGuiButton ibGuiButton){
        buttons.put(name, ibGuiButton);
    }

    public static IBGuiButton getButton(ButtonNames name){
        return buttons.get(name);
    }

    public static void setAdvancedFilterButtons(){
        advFilterButtons = new IBGuiButton[]{getButton(ButtonNames.EXACT), getButton(ButtonNames.FUZZY), getButton(ButtonNames.ORE_DICT), getButton(ButtonNames.MOD_SPECIFIC)};
    }

    public static IBGuiButton[] getAdvFilterButtons(){
        return advFilterButtons;
    }
}
