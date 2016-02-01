package gr8pefish.ironbackpacks.api.client.gui.button;

import java.util.ArrayList;

/**
 * Interface for buttons that contain a tooltip when hovered over. Can be used in conjunction with IGuiButton.
 */
public interface ITooltipButton {


    /**
     * Return if the mouse is over the button or not
     * @param mouseX - the x coordinates of the mouse
     * @param mouseY - the y coordinates of the mouse
     * @return - true if over the button, false otherwise
     */
    boolean mouseInButton(int mouseX, int mouseY);


    /**
     * Get the tooltip. Each element in the array list is a new line.
     * @return - the tooltip
     */
    ArrayList<String> getTooltip();


    /**
     * Get the length of time the mouse has been hovering over the button
     * @return - integer of time
     */
    int getHoverTime();
}

