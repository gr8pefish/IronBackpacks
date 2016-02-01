package gr8pefish.ironbackpacks.api.client.gui.button;

/**
 * This class should be extended for every button. Naturally ties in to ITooltipButton.
 */
public class IGuiButton {

    int id; //the internal id
    private int sizeX; //the width of the button
    private int sizeY; //the height of the button
    private int iconOffsetX; //the icon's x texture position
    private int iconOffsetY; //the icon's y texture position
    private boolean delay; //to delay the tooltip appearing or not
    private String[] tooltip; //the tooltip to display. Each index is a new line.

    public IGuiButton(int id, int sizeX, int sizeY, int offsetX, int offsetY, boolean delay, String[] tooltip){
        this.id = id;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.iconOffsetX = offsetX;
        this.iconOffsetY = offsetY;
        this.delay = delay;
        this.tooltip = tooltip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getIconOffsetX() {
        return iconOffsetX;
    }

    public int getIconOffsetY() {
        return iconOffsetY;
    }

    public boolean shouldDelay() {
        return delay;
    }

    public String[] getTooltip() {
        return tooltip;
    }

}
