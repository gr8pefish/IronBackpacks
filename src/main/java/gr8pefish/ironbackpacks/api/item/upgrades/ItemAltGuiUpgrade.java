package gr8pefish.ironbackpacks.api.item.upgrades;

import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.upgrades.interfaces.IAltGuiUpgrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemAltGuiUpgrade implements IAltGuiUpgrade {

    private String name;
    private int upgradeCost;
    private String[] description;
    private String[] recipe;

    public ItemAltGuiUpgrade(String name, int upgradeCost, String[] description, String... recipe){
        this.name = name;
        this.upgradeCost = upgradeCost;
        this.description = description;
        this.recipe = recipe;
    }

    public Item getItem(){
        return IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_CRAFTING_BASE);
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
        return Arrays.asList(description);
    }
}
