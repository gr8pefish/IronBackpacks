package gr8pefish.ironbackpacks.api.item.upgrades;

import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.upgrades.interfaces.IConflictingUpgrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemConflictingUpgrade implements IConflictingUpgrade {

    private String name;
    private int upgradeCost;
    private String[] description;
    private String[] recipe;
    private List<ItemConflictingUpgrade> conflictingUpgrades;

    public ItemConflictingUpgrade(String name, int upgradeCost, String[] description, String[] recipe, List<ItemConflictingUpgrade> conflictingUpgrades){
        this.name = name;
        this.upgradeCost = upgradeCost;
        this.description = description;
        this.recipe = recipe;
        this.conflictingUpgrades = conflictingUpgrades;
    }

    public Item getItem(){
        return IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_CRAFTING_BASE);
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
    public List<ItemConflictingUpgrade> getConflictingUpgrades(ItemStack upgrade) {
        return conflictingUpgrades;
    }
}
