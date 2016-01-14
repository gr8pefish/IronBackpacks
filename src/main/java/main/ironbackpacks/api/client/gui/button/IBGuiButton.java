package main.ironbackpacks.api.client.gui.button;

public class IBGuiButton {

    int id; //the internal id
    private int sizeX; //the width of the button
    private int sizeY; //the height of the button
    private int iconOffsetX; //the icon's x texture position
    private int iconOffsetY; //the icon's y texture position
    private boolean delay; //to delay the tooltip appearing or not
    private String[] tooltip; //the tooltip to display. Each index is a new line.

    public IBGuiButton(int id, int sizeX, int sizeY, int offsetX, int offsetY, boolean delay, String[] tooltip){
        this.id = id;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.iconOffsetX = offsetX;
        this.iconOffsetY = offsetY;
        this.delay = delay;
        this.tooltip = tooltip;
    }

//    public static ButtonTypes[] buttonTypesArray = {EXACT, FUZZY, ORE_DICT, MOD_SPECIFIC}; //for use in the alternate gui

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getIconOffsetX() {
        return iconOffsetX;
    }

    public void setIconOffsetX(int iconOffsetX) {
        this.iconOffsetX = iconOffsetX;
    }

    public int getIconOffsetY() {
        return iconOffsetY;
    }

    public void setIconOffsetY(int iconOffsetY) {
        this.iconOffsetY = iconOffsetY;
    }

    public boolean shouldDelay() {
        return delay;
    }

    public void setShouldDelay(boolean delay) {
        this.delay = delay;
    }

    public String[] getTooltip() {
        return tooltip;
    }

    public void setTooltip(String[] tooltip) {
        this.tooltip = tooltip;
    }

}
