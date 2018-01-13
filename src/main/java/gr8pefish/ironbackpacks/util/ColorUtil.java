package gr8pefish.ironbackpacks.util;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class ColorUtil {

    /**
     * Gets the color for the specified itemstack
     *
     * @param stack - the itemstack to target. Damage values on the item in the range of 0-15 are valid colors.
     * @param tintindex - the tintindex, which refers to the layer# in the model json, as it is an item
     * @return - RGB value
     */
    public static int getColor(ItemStack stack, int tintindex) {
        if (tintindex < 1 || !isColored(stack)) return -1; //only color latch and border layer, leave center alone; only do this when the item is colored
        return EnumDyeColor.byMetadata(stack.getItemDamage()).getColorValue();
    }

    /**
     * Helper method to determine if an item is colored.
     * Makes sure damage value is in the valid range of 0-15 (EnumDyeColor metas).
     *
     * @param stack - the stack to check
     * @return - if the item is colored or not
     */
    private static boolean isColored(ItemStack stack) {
        return (!stack.isEmpty() && stack.getItemDamage() < EnumDyeColor.WHITE.getMetadata() || stack.getItemDamage() > EnumDyeColor.BLACK.getMetadata());
    }


}
