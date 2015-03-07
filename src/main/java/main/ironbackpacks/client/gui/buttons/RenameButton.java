package main.ironbackpacks.client.gui.buttons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenameButton extends GuiButton {

    //A class for the button used ot rename the backpack

    public static final int RENAME_BUTTON = 0;


    protected String[] backpackTooltip = {"Renames the backpack", "to whatever is written", "in the text box."};
    private int tooltipWidth = -1;
    private int tooltipDelay = ConfigHandler.tooltipDelay;
    private int hoverTime = 0;
    private long prevSystemTime = 0;

    public int xPos;
    public int yPos;
    public int width;
    public int height;

    public static final ResourceLocation widgetTextures = IronBackpacksConstants.Resources.WIDGETS;

    private int iconOffsetX = 0;
    private int iconOffsetY = 0;

    public RenameButton(int id, int xPos, int yPos, int width, int height, int type) {
        super(id, xPos, yPos, width, height, "");

        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;

        if (type == RENAME_BUTTON) {
            iconOffsetX = 1;
            iconOffsetY = 1;
        }
    }

    @Override
    public void drawButton(Minecraft minecraft, int mPosX, int mPosY) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(widgetTextures);

            //hover is used to change the color of the button by altering the texture location when a player's mouse if over the button
            boolean hover = mPosX >= this.xPosition && mPosY >= this.yPosition && mPosX < this.xPosition + this.width && mPosY < this.yPosition + this.height;
            int h = getHoverState(hover);

            int fromLeft = iconOffsetX + (h-1) * 28;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, fromLeft, iconOffsetY, 25, 10);

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
                int x = mPosX + 12, y = mPosY - 11 * backpackTooltip.length;
                if(tooltipWidth == -1) {
                    for(String line : backpackTooltip) {
                        tooltipWidth = Math.max(fontRenderer.getStringWidth(line), tooltipWidth);
                    }
                }
                if(x + tooltipWidth > minecraft.currentScreen.width) {
                    x = minecraft.currentScreen.width - tooltipWidth;
                }

                // Draw background
                drawGradientRect(x - 3, y - 3, x + tooltipWidth + 3, y + 11 * backpackTooltip.length, 0xc0000000,
                        0xc0000000);

                // Draw lines
                int lineCount = 0;
                for(String line : backpackTooltip) {
                    int j1 = y + (lineCount++) * 11;
                    int k = -1;
                    fontRenderer.drawStringWithShadow(line, x, j1, k);
                }
            }
        }
    }

}
