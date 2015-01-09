package main.ironbackpacks.client.gui.buttons;

import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class RenameButton extends GuiButton {

    public static final int RENAME_BUTTON = 0;

    public static final ResourceLocation widgetTextures = IronBackpacksConstants.Resources.WIDGETS;

    private int iconOffsetX = 0;
    private int iconOffsetY = 0;

    public RenameButton(int id, int xPos, int yPos, int width, int height, int type) {
        super(id, xPos, yPos, width, height, "");

        if (type == RENAME_BUTTON) {
            iconOffsetX = 1;
            iconOffsetY = 1;
        }
    }

    @Override
    public void drawButton(Minecraft minecraft, int mPosX, int mPosY) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(widgetTextures);

            boolean hover = mPosX >= this.xPosition && mPosY >= this.yPosition && mPosX < this.xPosition + this.width && mPosY < this.yPosition + this.height;
            int h = getHoverState(hover);

            int fromLeft = iconOffsetX + (h-1) * 28;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, fromLeft, iconOffsetY, 25, 10);
        }
    }

}
