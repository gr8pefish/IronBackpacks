package main.ironbackpacks.client.gui.buttons;

import main.ironbackpacks.util.TextUtils;

/**
 * The different buttons used in this mod, in an enumeration for ease of access
 */
public enum ButtonTypes {

    RENAME(0, 25, 10, 1, 1, true, "button.ironbackpacks.rename.tooltip"), //id, width, height, iconOffsetX, iconOffsetY, tooltip delay, tooltip
    EXACT(1, 16, 5, 0, 13, false, "button.ironbackpacks.exact.tooltip"),
    FUZZY(2, 16, 5, 0, 19, false, "button.ironbackpacks.fuzzy.tooltip"),
    ORE_DICT(3, 16, 5, 0, 25, false, "button.ironbackpacks.oreDict.tooltip"),
    MOD_SPECIFIC(4, 16, 5, 0, 31, false, "button.ironbackpacks.modSpecific.tooltip"),
    CLEAR_ROW(5, 11, 12, 64, 64, true, ""), //5,6,7 because can clear 3 rows
    BACKPACK_TO_INVENTORY(8, 11, 12, 64, 0, true, "button.ironbackpacks.packToInv.tooltip"),
    INVENTORY_TO_BACKPACK(9, 11, 12, 64, 16, true, "button.ironbackpacks.invToPack.tooltip"),
    HOTBAR_TO_BACKPACK(10, 11, 12, 64, 32, true, "button.ironbackpacks.barToPack.tooltip"),
    SORT_BACKPACK(11, 11, 12, 64, 48, true, "button.ironbackpacks.sort.tooltip"),
    MOVE_LEFT(12, 4, 9, 18, 57, true, "button.ironbackpacks.left.tooltip"),
    MOVE_RIGHT(13, 4, 9, 18, 37, true, "button.ironbackpacks.right.tooltip");

    public static ButtonTypes[] buttonTypesArray = {EXACT, FUZZY, ORE_DICT, MOD_SPECIFIC}; //for use in the alternate gui
    protected int id; //the internal id
    protected int sizeX; //the width of the button
    protected int sizeY; //the height of the button
    protected int iconOffsetX; //the icon's x texture position
    protected int iconOffsetY; //the icon's y texture position
    protected boolean delay; //to delay the tooltip appearing or not
    protected String[] tooltip; //the tooltip to display. Each index is a nut line.

    ButtonTypes(int id, int sizeX, int sizeY, int offsetX, int offsetY, boolean delay, String tooltip) {
        this.id = id;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.iconOffsetX = offsetX;
        this.iconOffsetY = offsetY;
        this.delay = delay;
        this.tooltip = TextUtils.cutLongString(TextUtils.localizeEffect(tooltip));
    }

    public int getID() {
        return id;
    }

}
