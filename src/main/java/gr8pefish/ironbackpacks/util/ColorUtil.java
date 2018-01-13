package gr8pefish.ironbackpacks.util;

import net.minecraft.item.ItemStack;

import java.awt.*;

public class ColorUtil {

    /**
     * Return the color for the specified backpack
     */
    public static int getColor(ItemStack stack, int tintindex) {
        if (tintindex < 1) return -1; //only color latch and border layer, leave center alone

        //ToDo with crafting recipes
//        if (!stack.isEmpty() && stack.hasTagCompound()) {
//            NBTTagCompound nbttagcompound = stack.getTagCompound();
//            if (nbttagcompound != null && nbttagcompound.hasKey("color")) {
//                return nbttagcompound.getInteger("color");
//            }
//        }

        //testing code;
        return Color.CYAN.getRGB();

    }


}
