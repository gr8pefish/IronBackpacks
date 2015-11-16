package main.ironbackpacks.items.craftingItems;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import net.minecraft.item.Item;

public class ItemNest extends Item {

    public ItemNest() {
        super();
        setUnlocalizedName(ModInformation.ID + ":nest");
        setCreativeTab(IronBackpacks.creativeTab);
    }
}
