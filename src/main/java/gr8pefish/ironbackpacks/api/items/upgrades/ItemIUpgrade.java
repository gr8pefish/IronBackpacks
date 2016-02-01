package gr8pefish.ironbackpacks.api.items.upgrades;

import gr8pefish.ironbackpacks.api.items.upgrades.interfaces.IUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.Arrays;
import java.util.List;

/**
 * Class for items that extend IUpgrade
 */
public class ItemIUpgrade implements IUpgrade {

    private String name;
    private int upgradeCost;
    private int tier;
    private String[] description;
    private IRecipe itemRecipe;

    public ItemIUpgrade(String name, int upgradeCost, int tier, String[] description){
        this.name = name;
        this.upgradeCost = upgradeCost;
        this.tier = tier;
        this.description = description;
    }

    @Override
    public String getName(ItemStack upgrade) {
        return name;
    }

    @Override
    public int getUpgradeCost(ItemStack upgrade) {
        return upgradeCost;
    }

    @Override
    public List<String> getTooltip(ItemStack upgrade) {
        return Arrays.asList(description);
    }

    @Override
    public int getTier(ItemStack upgrade) {
        return tier;
    }

    @Override
    public IRecipe getItemRecipe(ItemStack upgrade) {
        return itemRecipe;
    }

    @Override
    public void setItemRecipe(IRecipe recipe) {
        itemRecipe = recipe;
    }
}
