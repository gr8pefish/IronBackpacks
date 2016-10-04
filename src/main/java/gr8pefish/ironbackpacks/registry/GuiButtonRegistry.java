package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.api.client.gui.button.ButtonNames;
import gr8pefish.ironbackpacks.api.client.gui.button.IGuiButton;
import gr8pefish.ironbackpacks.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class GuiButtonRegistry {

    private static Map<ButtonNames, IGuiButton> buttons = new HashMap<>();
    private static IGuiButton[] advFilterButtons;

    /**
     * Initializes the buttons
     */
    public static void registerButtons(){

        //register all the buttons
        registerButton(ButtonNames.RENAME, new IGuiButton(0, 25, 10, 1, 1, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.rename.tooltip")))); //id, width, height, iconOffsetX, iconOffsetY, tooltip delay, tooltip
        registerButton(ButtonNames.EXACT, new IGuiButton(1, 16, 5, 0, 13, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.exact.tooltip"))));
        registerButton(ButtonNames.FUZZY, new IGuiButton(2, 16, 5, 0, 19, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.fuzzy.tooltip"))));
        registerButton(ButtonNames.ORE_DICT, new IGuiButton(3, 16, 5, 0, 25, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.oreDict.tooltip"))));
        registerButton(ButtonNames.MOD_SPECIFIC, new IGuiButton(4, 16, 5, 0, 31, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.modSpecific.tooltip"))));
        registerButton(ButtonNames.VOID, new IGuiButton(5, 16, 5, 0, 115, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.void.tooltip"))));
        registerButton(ButtonNames.CLEAR_ROW, new IGuiButton(6, 11, 12, 64, 64, true, TextUtils.cutLongString(TextUtils.localizeEffect("")))); //6,7,8 because can clear 3 rows
        registerButton(ButtonNames.BACKPACK_TO_INVENTORY, new IGuiButton(9, 11, 12, 64, 0, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.packToInv.tooltip"))));
        registerButton(ButtonNames.INVENTORY_TO_BACKPACK, new IGuiButton(10, 11, 12, 64, 16, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.invToPack.tooltip"))));
        registerButton(ButtonNames.HOTBAR_TO_BACKPACK, new IGuiButton(11, 11, 12, 64, 32, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.barToPack.tooltip"))));
        registerButton(ButtonNames.SORT_BACKPACK, new IGuiButton(12, 11, 12, 64, 48, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.sort.tooltip.id"))));
        registerButton(ButtonNames.MOVE_LEFT, new IGuiButton(13, 4, 9, 18, 57, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.left.tooltip"))));
        registerButton(ButtonNames.MOVE_RIGHT, new IGuiButton(14, 4, 9, 18, 37, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.right.tooltip"))));
        registerButton(ButtonNames.INFO, new IGuiButton(15, 7, 7, 36, 13, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.info.tooltip"))));

        //set the buttons shown in the advanced filter
        setAdvancedFilterButtons();
    }

    /**
     * Helper method to register the buttons. Leaving it here in case I want to manipulate it later.
     * @param name - the name of the button
     * @param iGuiButton - the button itself
     */
    private static void registerButton(ButtonNames name, IGuiButton iGuiButton){
        buttons.put(name, iGuiButton);
    }

    /**
     * Get the button by passing in the button name
     * @param name - the name of the button
     * @return - the IGuiButton
     */
    public static IGuiButton getButton(ButtonNames name){
        return buttons.get(name);
    }

    /**
     * Set the buttons used in the advanced filter. Currently hardcoded, this is just laying the groundwork.
     */
    public static void setAdvancedFilterButtons(){
        advFilterButtons = new IGuiButton[]{getButton(ButtonNames.EXACT), getButton(ButtonNames.FUZZY), getButton(ButtonNames.ORE_DICT), getButton(ButtonNames.MOD_SPECIFIC), getButton(ButtonNames.VOID)};
    }

    /**
     * Get the buttons used in the advanced filter.
     * @return - the buttons in an array
     */
    public static IGuiButton[] getAdvFilterButtons(){
        return advFilterButtons;
    }
}
