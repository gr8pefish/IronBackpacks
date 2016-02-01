package gr8pefish.ironbackpacks.api.items.upgrades;

import gr8pefish.ironbackpacks.api.items.upgrades.interfaces.IConflictingUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for items that extend IConflictingUpgrade
 */
public class ItemIConflictingUpgrade implements IConflictingUpgrade {

    private String name;
    private int upgradeCost;
    private int tier;
    private String[] description;
    private List<ItemIConflictingUpgrade> conflictingUpgrades;
    private IRecipe itemRecipe;

    public ItemIConflictingUpgrade(String name, int upgradeCost, int tier, String[] description){
        this.name = name;
        this.upgradeCost = upgradeCost;
        this.description = description;
        this.tier = tier;
        this.conflictingUpgrades = new ArrayList<>();
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
    public void setConflictingUpgrades(ItemStack stack, List<ItemIConflictingUpgrade> conflictingUpgradeList){
        conflictingUpgrades = conflictingUpgradeList;
    }

    @Override
    public List<ItemIConflictingUpgrade> getConflictingUpgrades(ItemStack upgrade) {
        return conflictingUpgrades;
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
