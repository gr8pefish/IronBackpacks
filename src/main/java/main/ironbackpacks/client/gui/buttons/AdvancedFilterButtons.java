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
public class AdvancedFilterButtons extends GuiButton {

    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int EXACT = 3;
    public static final int FUZZY = 4;
    public static final int ORE_DICT = 5;
    public static final int MOD_SPECIFIC = 6;

    public int buttonType;

    public String[] moveRightTooltip = {"Moves right"}; //TODO - tooltip shows # of slots left in direction if I don't wraparound
    public String[] moveLeftTooltip = {"Moves left"};
    public String[] exactTooltip = {"Exact Matching"};
    public String[] fuzzyTooltip = {"Fuzzy Matching"};
    public String[] oreDictTooltip = {"Ore Dictionary Matching"};
    public String[] modSpecificTooltip = {"Mod Specific Matching"};

    public String[] tooltipInstance;
    public int tooltipWidth = -1;
    private int tooltipDelay = ConfigHandler.tooltipDelay;
    private int hoverTime = 0;
    private long prevSystemTime = 0;

    public static final ResourceLocation widgetTextures = IronBackpacksConstants.Resources.WIDGETS;

    private int iconOffsetX = 0;
    private int iconOffsetY = 0;

    public AdvancedFilterButtons(int id, int xPos, int yPos, int width, int height, int type) {
        super(id, xPos, yPos, width, height, "");
        this.width = width;
        this.height = height;
        buttonType = type;
        switch (type){
            case MOVE_LEFT:
                iconOffsetX = 18;
                iconOffsetY = 57;
                this.tooltipInstance = moveLeftTooltip;
                break;
            case MOVE_RIGHT:
                iconOffsetX = 18;
                iconOffsetY = 37;
                this.tooltipInstance = moveRightTooltip;
                break;
            case EXACT:
                iconOffsetX = 0;
                iconOffsetY = 13;
                this.tooltipInstance = exactTooltip;
                break;
            case FUZZY:
                iconOffsetX = 0;
                iconOffsetY = 19;
                this.tooltipInstance = fuzzyTooltip;
                break;
            case ORE_DICT:
                iconOffsetX = 0;
                iconOffsetY = 25;
                this.tooltipInstance = oreDictTooltip;
                break;
            case MOD_SPECIFIC:
                iconOffsetX = 0;
                iconOffsetY = 31;
                this.tooltipInstance = modSpecificTooltip;
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

            int fromLeft = iconOffsetX + (h-1) * (this.width+1);

            this.drawTexturedModalRect(this.xPosition, this.yPosition, fromLeft, iconOffsetY, this.width, this.height);

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
