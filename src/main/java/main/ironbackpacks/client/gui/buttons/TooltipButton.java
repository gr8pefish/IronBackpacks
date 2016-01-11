package main.ironbackpacks.client.gui.buttons;

import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

/**
 * Class for drawing and creating all the buttons.
 */
@SideOnly(Side.CLIENT)
public class TooltipButton extends GuiButton implements ITooltipButton {

    public static final ResourceLocation widgetTextures = IronBackpacksConstants.Resources.WIDGETS; //the texture location

    private int buttonID; //the internal id
    private int iconOffsetX; //the icon x location
    private int iconOffsetY; //the icon y location
    private ArrayList<String> tooltips; //the tooltip
    private int hoverTime; //the hover time (for displaying tooltips)

    //Constructor which uses the ButtonTypes enum
    public TooltipButton(ButtonTypes buttonType, int xPos, int yPos){
        super(buttonType.id, xPos, yPos, buttonType.sizeX, buttonType.sizeY, ""); //empty string displays no text on the button
        this.buttonID = buttonType.id;

        hoverTime = buttonType.delay ? ConfigHandler.tooltipDelay : 0;
        tooltips = new ArrayList<String>();
        for (String string: buttonType.tooltip){
            tooltips.add(string);
        }

        iconOffsetX = buttonType.iconOffsetX;
        iconOffsetY = buttonType.iconOffsetY;
    }

    //Overloaded constructor for more flexibility when initializing (used for the clear lines buttons in the alternate gui)
    public TooltipButton(int id, ButtonTypes buttonType, int xPos, int yPos, String... tooltipLines){
        super(id, xPos, yPos, buttonType.sizeX, buttonType.sizeY, "");
        this.buttonID = id;

        hoverTime = buttonType.delay ? ConfigHandler.tooltipDelay : 0;
        tooltips = new ArrayList<String>();
        for (String string: ((tooltipLines.length == 0) ? buttonType.tooltip : tooltipLines)){
            tooltips.add(string);
        }

        iconOffsetX = buttonType.iconOffsetX;
        iconOffsetY = buttonType.iconOffsetY;
    }


    @Override
    public void drawButton(Minecraft minecraft, int mPosX, int mPosY) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(widgetTextures);

            //hover is used to change the color of the button by altering the texture location when a player's mouse if over the button
            boolean hover = mouseInButton(mPosX, mPosY);
            int h = getHoverState(hover);
            int fromLeft = iconOffsetX + (h - 1) * (this.width + 1);

            this.drawTexturedModalRect(this.xPosition, this.yPosition, fromLeft, iconOffsetY, this.width, this.height);
        }
    }

    @Override
    public boolean mouseInButton(int mPosX, int mPosY) {
        return (mPosX >= this.xPosition && mPosY >= this.yPosition && mPosX < this.xPosition + this.width && mPosY < this.yPosition + this.height);
    }

    @Override
    public ArrayList<String> getTooltip() {
        return tooltips;
    }

    @Override
    public int getHoverTime(){
        return hoverTime;
    }

    /**
     * Rotates through the 4 filter type buttons by getting teh next one.
     * @param button - the button to increment
     * @return - int id of the new button
     */
    public static int incrementType(GuiButton button){
        int buttonID = ((TooltipButton)button).buttonID;
        if (buttonID < ButtonTypes.MOD_SPECIFIC.id){ //increment
            return ++buttonID;
        }else{
            return ButtonTypes.EXACT.id; //wraparound
        }
    }
}
