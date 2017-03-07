package gr8pefish.ironbackpacks.api.items.backpacks.interfaces;

import java.awt.*;

public interface IColorable {

    /**
     * Returns false if the item doesn't have an overlay.
     *
     * @return - if the item is colored or not
     */
    boolean isColored();

    /**
     * Gets the color of the item.
     *
     * @return - the color of the item. Null if uncolored.
     */
    Color getColor();

    /**
     * Applies the color to the item.
     *
     * @param color - the color to apply
     */
    void applyColor(Color color);

    /**
     * Removes any color from the backpack.
     * Nothing happens if the backpack isn't colored first.
     */
    void removeColor();

}
