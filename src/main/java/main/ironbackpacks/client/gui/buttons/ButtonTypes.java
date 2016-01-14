package main.ironbackpacks.client.gui.buttons;

import main.ironbackpacks.util.TextUtils;

/**
 * The different buttons used in this mod, in an enumeration for ease of access
 */
//public enum ButtonTypes {
//
//    RENAME(0, 25, 10, 1, 1, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.rename.tooltip"))), //id, width, height, iconOffsetX, iconOffsetY, tooltip delay, tooltip
//    EXACT(1, 16, 5, 0, 13, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.exact.tooltip"))),
//    FUZZY(2, 16, 5, 0, 19, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.fuzzy.tooltip"))),
//    ORE_DICT(3, 16, 5, 0, 25, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.oreDict.tooltip"))),
//    MOD_SPECIFIC(4, 16, 5, 0, 31, false, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.modSpecific.tooltip"))),
//    CLEAR_ROW(5, 11, 12, 64, 64, true, TextUtils.cutLongString(TextUtils.localizeEffect(""))), //5,6,7 because can clear 3 rows
//    BACKPACK_TO_INVENTORY(8, 11, 12, 64, 0, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.packToInv.tooltip"))),
//    INVENTORY_TO_BACKPACK(9, 11, 12, 64, 16, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.invToPack.tooltip"))),
//    HOTBAR_TO_BACKPACK(10, 11, 12, 64, 32, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.barToPack.tooltip"))),
//    SORT_BACKPACK(11, 11, 12, 64, 48, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.sort.tooltip"))),
//    MOVE_LEFT(12, 4, 9, 18, 57, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.left.tooltip"))),
//    MOVE_RIGHT(13, 4, 9, 18, 37, true, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.right.tooltip")));
//
//    private int id; //the internal id
//    private int sizeX; //the width of the button
//    private int sizeY; //the height of the button
//    private int iconOffsetX; //the icon's x texture position
//    private int iconOffsetY; //the icon's y texture position
//    private boolean delay; //to delay the tooltip appearing or not
//    private String[] tooltip; //the tooltip to display. Each index is a new line.
//
//    ButtonTypes(int id, int sizeX, int sizeY, int offsetX, int offsetY, boolean delay, String[] tooltip){
//        this.id = id;
//        this.sizeX = sizeX;
//        this.sizeY = sizeY;
//        this.iconOffsetX = offsetX;
//        this.iconOffsetY = offsetY;
//        this.delay = delay;
//        this.tooltip = tooltip;
//    }
//
//    public static ButtonTypes[] buttonTypesArray = {EXACT, FUZZY, ORE_DICT, MOD_SPECIFIC}; //for use in the alternate gui
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getSizeX() {
//        return sizeX;
//    }
//
//    public void setSizeX(int sizeX) {
//        this.sizeX = sizeX;
//    }
//
//    public int getSizeY() {
//        return sizeY;
//    }
//
//    public void setSizeY(int sizeY) {
//        this.sizeY = sizeY;
//    }
//
//    public int getIconOffsetX() {
//        return iconOffsetX;
//    }
//
//    public void setIconOffsetX(int iconOffsetX) {
//        this.iconOffsetX = iconOffsetX;
//    }
//
//    public int getIconOffsetY() {
//        return iconOffsetY;
//    }
//
//    public void setIconOffsetY(int iconOffsetY) {
//        this.iconOffsetY = iconOffsetY;
//    }
//
//    public boolean shouldDelay() {
//        return delay;
//    }
//
//    public void setShouldDelay(boolean delay) {
//        this.delay = delay;
//    }
//
//    public String[] getTooltip() {
//        return tooltip;
//    }
//
//    public void setTooltip(String[] tooltip) {
//        this.tooltip = tooltip;
//    }
//
//}
