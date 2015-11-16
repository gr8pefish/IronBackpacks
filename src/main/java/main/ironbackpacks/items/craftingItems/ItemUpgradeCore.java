package main.ironbackpacks.items.craftingItems;


import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import net.minecraft.item.Item;

public class ItemUpgradeCore extends Item {

    public ItemUpgradeCore() {
        super();
        setUnlocalizedName(ModInformation.ID + ":upgradeCore");
        setCreativeTab(IronBackpacks.creativeTab);
    }
}
