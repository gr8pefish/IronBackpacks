package main.ironbackpacks.client.gui.buttons;

/**
 * The different buttons used in this mod, in an enumeration for ease of access
 */
public enum ButtonTypes {

    RENAME(0, 25,10, 1,1, true, "Renames the backpack"), //id, width, height, iconOffsetX, iconOffsetY, tooltip delay, tooltip
    EXACT(1, 16,5, 0,13, false, "Exact Matching"),
    FUZZY(2, 16,5, 0,19, false, "Fuzzy Matching"),
    ORE_DICT(3, 16,5, 0,25, false, "Ore Dictionary Matching"),
    MOD_SPECIFIC(4, 16,5, 0,31, false, "Mod Specific Matching"),
    CLEAR_ROW(5, 11,12, 64,64, true), //5,6,7 because can clear 3 rows
    BACKPACK_TO_INVENTORY(8, 11,12, 64,0, true, "Moves items from the", "backpack to your inventory."),
    INVENTORY_TO_BACKPACK(9, 11,12, 64,16, true, "Moves items from your", "inventory to the backpack"),
    HOTBAR_TO_BACKPACK(10, 11,12, 64,32, true, "Moves items from your", "hotbar to the backpack."),
    SORT_BACKPACK(11, 11,12, 64,48, true, "Sorts and condenses the","items in the backpack", "(by localized, alphabetical name)"),
    MOVE_LEFT(12, 4,9, 18,57, true, "Shift to the left."),
    MOVE_RIGHT(13, 4,9, 18,37, true, "Shift to the right.");

    protected int id; //the internal id
    protected int sizeX; //the width of the button
    protected int sizeY; //the height of the button
    protected int iconOffsetX; //the icon's x texture position
    protected int iconOffsetY; //the icon's y texture position
    protected boolean delay; //to delay the tooltip appearing or not
    protected String[] tooltip; //the tooltip to display. Each index is a new line.

    ButtonTypes(int id, int sizeX, int sizeY, int offsetX, int offsetY, boolean delay, String... tooltip){
        this.id = id;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.iconOffsetX = offsetX;
        this.iconOffsetY = offsetY;
        this.delay = delay;
        this.tooltip = tooltip;
    }

    public static ButtonTypes[] buttonTypesArray = {EXACT, FUZZY, ORE_DICT, MOD_SPECIFIC}; //for use in the alternate gui

    public int getID(){
        return id;
    }

}
