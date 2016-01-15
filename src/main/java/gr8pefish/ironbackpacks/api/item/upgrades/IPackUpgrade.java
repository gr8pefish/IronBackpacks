package gr8pefish.ironbackpacks.api.item.upgrades;

import net.minecraft.item.ItemStack;

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
     * The internal id of the upgrade
     * @param upgrade - the upgrade as an item stack (needed for subitems)
     * @return - the id of the item
     */
    int getId(ItemStack upgrade);

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

}
