package gr8pefish.ironbackpacks.api.item.backpacks.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

/**
 * This interface should be used for all backpacks.
 * Note that tiered backpacks and upgradable backpacks have separate interfaces.
 */
public interface IBackpack {

    /**
     * The internal name of the backpack (i.e. "ironBackpack")
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the name
     */
    String getName(ItemStack backpack);

    /**
     * The number of the rows in the backpack
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - integer of the row count
     */
    int getRowCount(ItemStack backpack);

    /**
     * The length of each row in the backpack
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - integer of the row length
     */
    int getRowLength(ItemStack backpack);

    /**
     * Get the location of the texture for the GUI.
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the resource location
     */
    ResourceLocation getGuiResourceLocation(ItemStack backpack);

    /**
     * Get the width of the GUI.
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the size in pixels in the x-direction
     */
    int getGuiXSize(ItemStack backpack);

    /**
     * Get the height of the GUI.
     * @param backpack - the backpack as an item stack (needed for sub items)
     * @return - the size in pixels in the y-direction
     */
    int getGuiYSize(ItemStack backpack);

    /**
     * Get the recipe to get the backpack directly. If you can only get this backpack from upgrading another, simply put in null.
     * @param backpack - the backpack as an item stack (needed for sub items) to get the recipe for
     * @return - the IRecipe to get the backpack item
     */
    IRecipe getItemRecipe(ItemStack backpack);

    void setItemRecipe(IRecipe recipe);

}
