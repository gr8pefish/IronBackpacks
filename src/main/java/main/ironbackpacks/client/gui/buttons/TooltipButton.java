package main.ironbackpacks.client.gui.buttons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class TooltipButton extends GuiButton implements ITooltipButton {

    //TODO: ButtonTypes.class so can just pass in class object with reference to button type's specific data

    //rename button
    public static final int RENAME = 0;

    //button upgrade buttons
    public static final int CLEAR_ROW = 11; //can be 11-13
    public static final int BACKPACK_TO_INVENTORY = 5;
    public static final int INVENTORY_TO_BACKPACK = 6;
    public static final int HOTBAR_TO_BACKPACK = 7;
    public static final int SORT_BACKPACK = 8;

    //adv filter buttons
    public static final int MOVE_LEFT = 9;
    public static final int MOVE_RIGHT = 10;
    public static final int EXACT = 1;
    public static final int FUZZY = 2;
    public static final int ORE_DICT = 3;
    public static final int MOD_SPECIFIC = 4;

    public static final String[] exactTooltip = {"Exact Matching"};
    public static final String[] fuzzyTooltip = {"Fuzzy Matching"};
    public static final String[] oreDictTooltip = {"Ore Dictionary Matching"};
    public static final String[] modSpecificTooltip = {"Mod Specific Matching"};
    public static final String[][] advTooltipsArray = {exactTooltip, fuzzyTooltip, oreDictTooltip, modSpecificTooltip};

    private int type;

    public static final ResourceLocation widgetTextures = IronBackpacksConstants.Resources.WIDGETS;
    private int iconOffsetX;
    private int iconOffsetY;

    private ArrayList<String> tooltips;
    private int hoverTime;

    public TooltipButton(int id, int xPos, int yPos, int width, int height, int type, boolean delay, String text, String... tooltipLines) {
        super(id, xPos, yPos, width, height, text); // "" for empty text

        this.type = type;

        hoverTime = delay ? ConfigHandler.tooltipDelay : 0;
        tooltips = new ArrayList<String>();
        for (String string: tooltipLines){
            tooltips.add(string);
        }

        switch (type){
            case RENAME:
                iconOffsetX = 1;
                iconOffsetY = 1;
                break;
            case HOTBAR_TO_BACKPACK:
                iconOffsetX = 64;
                iconOffsetY = 32;
                break;
            case INVENTORY_TO_BACKPACK:
                iconOffsetX = 64;
                iconOffsetY = 16;
                break;
            case BACKPACK_TO_INVENTORY:
                iconOffsetX = 64;
                iconOffsetY = 0;
                break;
            case SORT_BACKPACK:
                iconOffsetX = 64;
                iconOffsetY = 48;
                break;
            case MOVE_LEFT:
                iconOffsetX = 18;
                iconOffsetY = 57;
                break;
            case MOVE_RIGHT:
                iconOffsetX = 18;
                iconOffsetY = 37;
                break;
            case EXACT:
                iconOffsetX = 0;
                iconOffsetY = 13;
                break;
            case FUZZY:
                iconOffsetX = 0;
                iconOffsetY = 19;
                break;
            case ORE_DICT:
                iconOffsetX = 0;
                iconOffsetY = 25;
                break;
            case MOD_SPECIFIC:
                iconOffsetX = 0;
                iconOffsetY = 31;
                break;
            default: //clear tooltip
                iconOffsetX = 64;
                iconOffsetY = 64;
                break;
        }
    }

    @Override
    public void drawButton(Minecraft minecraft, int mPosX, int mPosY) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(widgetTextures);

            //hover is used to change the color of the button by altering the texture location when a player's mouse if over the button
            boolean hover = mouseInButton(mPosX, mPosY);
            int h = getHoverState(hover);

            int fromLeft = iconOffsetX + (h - 1) * (this.width + 1);

            int heightToUse = ((type <= SORT_BACKPACK && type >= BACKPACK_TO_INVENTORY) || (type == CLEAR_ROW)) ? this.height + 1 : this.height;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, fromLeft, iconOffsetY, this.width, heightToUse);
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

    public static int incrementType(GuiButton button){
        int type = ((TooltipButton)button).type;
        if (type < TooltipButton.MOD_SPECIFIC){
            return ++type;
        }else{
            return TooltipButton.EXACT;
        }
    }
}
