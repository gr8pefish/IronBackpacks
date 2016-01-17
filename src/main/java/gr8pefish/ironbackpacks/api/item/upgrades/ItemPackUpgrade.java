package gr8pefish.ironbackpacks.api.item.upgrades;

import gr8pefish.ironbackpacks.api.item.upgrades.interfaces.IPackUpgrade;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemPackUpgrade implements IPackUpgrade {

    private String name;
    private int upgradeCost;
    private List<String> tooltip;

    public ItemPackUpgrade(String name, int upgradeCost, List<String> tooltip){
        this.name = name;
        this.upgradeCost = upgradeCost;
        this.tooltip = tooltip;
    }

    public String getName(){
        return name;
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
        return tooltip;
    }
}
