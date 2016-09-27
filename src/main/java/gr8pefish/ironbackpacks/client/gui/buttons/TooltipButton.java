package gr8pefish.ironbackpacks.client.gui.buttons;

import gr8pefish.ironbackpacks.api.client.gui.button.ButtonNames;
import gr8pefish.ironbackpacks.api.client.gui.button.IGuiButton;
import gr8pefish.ironbackpacks.api.client.gui.button.ITooltipButton;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.registry.GuiButtonRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
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

    //Constructor which uses an IGuiButton
    public TooltipButton(IGuiButton guiButton, int xPos, int yPos){
        super(guiButton.getId(), xPos, yPos, guiButton.getSizeX(), guiButton.getSizeY(), ""); //empty string displays no text on the button
        this.buttonID = guiButton.getId();

        hoverTime = guiButton.shouldDelay() ? ConfigHandler.tooltipDelay : 0;
        tooltips = new ArrayList<String>();
        for (String string: guiButton.getTooltip()){
            tooltips.add(string);
        }

        iconOffsetX = guiButton.getIconOffsetX();
        iconOffsetY = guiButton.getIconOffsetY();
    }

    //Overloaded constructor for more flexibility when initializing (used for the clear lines buttons in the alternate gui)
    public TooltipButton(int id, IGuiButton guiButton, int xPos, int yPos, String... tooltipLines){
        super(id, xPos, yPos, guiButton.getSizeX(), guiButton.getSizeY(), "");
        this.buttonID = id;

        hoverTime = guiButton.shouldDelay() ? ConfigHandler.tooltipDelay : 0;
        tooltips = new ArrayList<String>();
        for (String string: ((tooltipLines.length == 0) ? guiButton.getTooltip() : tooltipLines)){
            tooltips.add(string);
        }

        iconOffsetX = guiButton.getIconOffsetX();
        iconOffsetY = guiButton.getIconOffsetY();
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

    public void setTooltip(ArrayList<String> newTooltips) {
        tooltips = newTooltips;
    }

    @Override
    public int getHoverTime(){
        return hoverTime;
    }


    /**
     * Rotates through the 4 filter type buttons by getting the next one. TODO: hardcoded
     * @param button - the button to increment
     * @return - int id of the new button
     */
    public static int incrementType(GuiButton button){
        int buttonID = ((TooltipButton)button).buttonID;
        if (buttonID < GuiButtonRegistry.getButton(ButtonNames.VOID).getId()){ //increment
            return ++buttonID;
        }else{
            return GuiButtonRegistry.getButton(ButtonNames.EXACT).getId(); //wraparound
        }
    }
}
