package main.ironbackpacks.client.gui.buttons;

import java.util.ArrayList;

/**
 * Interface for buttons that contain a tooltip when hovered over
 */
public interface ITooltipButton {

    //Is the mouse over the button?
    public boolean mouseInButton(int mouseX, int mouseY);

    //Get the tooltip
    public ArrayList<String> getTooltip();

    //Get the length of time the mouse has been hovering over the button
    public int getHoverTime();
}
