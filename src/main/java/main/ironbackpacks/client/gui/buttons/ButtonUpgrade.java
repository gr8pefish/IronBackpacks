package main.ironbackpacks.client.gui.buttons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class ButtonUpgrade extends GuiButton {

    public static final int BACKPACK_TO_INVENTORY = 10;
    public static final int INVENTORY_TO_BACKPACK = 11;
    public static final int HOTBAR_TO_BACKPACK = 12;
    public static final int SORT_BACKPACK = 13;

    public static final int CLEAR_ROW = 1;

    public String[] clearTooltip = {"Clears the items", "in this category."};
    public String[] backpackTooltip = {"Moves items from the", "backpack to your inventory."};
    public String[] inventoryTooltip = {"Moves items from your", "inventory to the backpack."};
    public String[] hotbarTooltip = {"Moves items from your", "hotbar to the backpack."};
    public String[] sortTooltip = {"Sorts the backpack."};
    public String[] tooltipInstance;
    public int tooltipWidth = -1;
    private int tooltipDelay = ConfigHandler.tooltipDelay;
    private int hoverTime = 0;
    private long prevSystemTime = 0;

    public static final ResourceLocation widgetTextures = IronBackpacksConstants.Resources.WIDGETS;

    private int iconOffsetX = 0;
    private int iconOffsetY = 0;

    public ButtonUpgrade(int id, int xPos, int yPos, int width, int height, int type) {
        super(id, xPos, yPos, width, height, "");
        switch (type){
            case HOTBAR_TO_BACKPACK:
                iconOffsetX = 64;
                iconOffsetY = 32;
                this.tooltipInstance = hotbarTooltip;
                break;
            case INVENTORY_TO_BACKPACK:
                iconOffsetX = 64;
                iconOffsetY = 16;
                this.tooltipInstance = inventoryTooltip;
                break;
            case BACKPACK_TO_INVENTORY:
                iconOffsetX = 64;
                iconOffsetY = 0;
                this.tooltipInstance = backpackTooltip;
                break;
            case SORT_BACKPACK:
                iconOffsetX = 64;
                iconOffsetY = 48;
                this.tooltipInstance = sortTooltip;
                break;
            default: //clear tooltip
                iconOffsetX = 64;
                iconOffsetY = 64;
                this.tooltipInstance = clearTooltip;
                break;
        }
    }

//    @SideOnly(Side.CLIENT);
    @Override
    public void drawButton(Minecraft minecraft, int mPosX, int mPosY) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(widgetTextures);

            //hover is used to change the color of the button by altering the texture location when a player's mouse if over the button
            boolean hover = mPosX >= this.xPosition && mPosY >= this.yPosition && mPosX < this.xPosition + this.width && mPosY < this.yPosition + this.height;
            int h = getHoverState(hover);

            int fromLeft = iconOffsetX + (h-1) * 16;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, fromLeft, iconOffsetY, 16, 16);

            //Thank you inventory tweaks

            //draws tooltips if you hover over the buttons for long enough
            if(hover) {
                long systemTime = System.currentTimeMillis();
                if(prevSystemTime != 0) {
                    hoverTime += systemTime - prevSystemTime;
                }
                prevSystemTime = systemTime;
            } else {
                hoverTime = 0;
                prevSystemTime = 0;
            }
            if(hoverTime > tooltipDelay) {

                FontRenderer fontRenderer = minecraft.fontRenderer;

                // Compute tooltip params
                int x = mPosX + 12, y = mPosY - 11 * this.tooltipInstance.length;
                if(tooltipWidth == -1) {
                    for(String line : this.tooltipInstance) {
                        tooltipWidth = Math.max(fontRenderer.getStringWidth(line), tooltipWidth);
                    }
                }
                if(x + tooltipWidth > minecraft.currentScreen.width) {
                    x = minecraft.currentScreen.width - tooltipWidth;
                }

                // Draw background
                drawGradientRect(x - 3, y - 3, x + tooltipWidth + 3, y + 11 * this.tooltipInstance.length, 0xc0000000,
                        0xc0000000);

                // Draw lines
                int lineCount = 0;
                for(String line : this.tooltipInstance) {
                    int j1 = y + (lineCount++) * 11;
                    int k = -1;
                    fontRenderer.drawStringWithShadow(line, x, j1, k);
                }
            }
        }
    }

}
