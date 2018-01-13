package gr8pefish.ironbackpacks.util;

import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import net.minecraft.item.ItemStack;

public class ColorUtil {

    /**
     * Gets the color for the specified backpack
     *
     * @param backpackStack - the backpack to target
     * @param tintindex - the tintindex, which refers to the layer# in the model json
     * @return - RGB value as an {@link int}
     */
    public static int getBackpackColor(ItemStack backpackStack, int tintindex) {

        BackpackInfo backpackInfo = Utils.getBackpackInfoFromStack(backpackStack);
        boolean isColored = backpackInfo.getIsColored();
        int color = backpackInfo.getRGBColor();

        if (tintindex < 1 || !isColored) return -1; //only color latch and border layer, leave center alone
        return color;
    }

}
