package gr8pefish.ironbackpacks.api.item.upgrades.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;

/**
 * The base interface for all upgrades that can be applied to a backpack.
 */
public interface IPackUpgrade {

    /**
     * The unlocalized name (i.e. buttonUpgrade)
     * @param upgrade - the upgrade as an item stack (needed for subitems)
     * @return - the string name
     */
    String getName(ItemStack upgrade);

    /**
     * The cost (in upgrade points) to apply this upgrade
     * @param upgrade - the upgrade as an item stack (needed for subitems)
     * @return - the integer number of points
     */
    int getUpgradeCost(ItemStack upgrade);

    /**
     * The tooltip, each element is a new line
     * @param upgrade - the upgrade as an item stack (needed for subitems)
     * @return - the tooltip
     */
    List<String> getTooltip(ItemStack upgrade);

    /**
     * Get the minimum tier of the backpack necessary for the upgrade to be applied. 0 is any backpack, each number after that is one more tier of backpack (e.g. 2 = gold backpack or higher).
     * @param upgrade - the upgrade as an item stack (needed for subitems)
     * @return - the tier
     */
    int getTier(ItemStack upgrade);

    /**
     * Get the recipe to make the upgrade.
     * @param upgrade - the upgrade in question
     * @return - an IRecipe (usually Shaped/ShapelessOreRecipe)
     */
    IRecipe getItemRecipe(ItemStack upgrade);

    void setItemRecipe(IRecipe recipe);

}
