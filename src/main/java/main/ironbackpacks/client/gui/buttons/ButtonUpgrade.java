package main.ironbackpacks.client.gui.buttons;

import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class ButtonUpgrade extends GuiButton {

    public static final int BACKPACK_TO_INVENTORY = 0;
    public static final int INVENTORY_TO_BACKPACK = 1;
    public static final int HOTBAR_TO_BACKPACK = 2;

    public static final int CONDENSE_BACKPACK = 3;

    public static final ResourceLocation widgetTextures = IronBackpacksConstants.Resources.WIDGETS;

    private int iconOffsetX = 0;
    private int iconOffsetY = 0;

    public ButtonUpgrade(int id, int xPos, int yPos, int width, int height, int type) {
        super(id, xPos, yPos, width, height, "");

        if (type == HOTBAR_TO_BACKPACK){
            iconOffsetX = 64;
            iconOffsetY = 32;
        } else if (type == INVENTORY_TO_BACKPACK) {
            iconOffsetX = 64;
            iconOffsetY = 16;
        } else if (type == BACKPACK_TO_INVENTORY) {
            iconOffsetX = 64;
            iconOffsetY = 0;
        } else if (type == CONDENSE_BACKPACK) {
            iconOffsetX = 64;
            iconOffsetY = 48;
        }
    }

    @Override
    public void drawButton(Minecraft minecraft, int mPosX, int mPosY) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(widgetTextures);

            boolean hover = mPosX >= this.xPosition && mPosY >= this.yPosition && mPosX < this.xPosition + this.width && mPosY < this.yPosition + this.height;
            int h = getHoverState(hover);

            int fromLeft = iconOffsetX + (h-1) * 16;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, fromLeft, iconOffsetY, 16, 16);
        }
    }

}
