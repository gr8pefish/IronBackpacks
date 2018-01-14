package gr8pefish.ironbackpacks.core.recipe;

import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Helper methods for recipes
 */
public class RecipeUtil {

    /**
     * Gets the first backpack ItemStack in a crafting grid.
     *
     * @param matrix - the crafting inventory to search
     * @return - a ItemStack which is known to be a backpack
     */
    @Nonnull
    public static ItemStack getFirstBackpackInGrid(@Nonnull InventoryCrafting matrix) {
        ItemStack stack = ItemStack.EMPTY;
        for (int i=0; i < matrix.getSizeInventory(); i++ ) {
            stack = matrix.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof IBackpack) {
                return stack;
            }
        }
        return stack;
    }

}
