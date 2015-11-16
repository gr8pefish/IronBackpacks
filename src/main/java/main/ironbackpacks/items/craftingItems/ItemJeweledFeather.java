package main.ironbackpacks.items.craftingItems;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import net.minecraft.item.Item;

public class ItemJeweledFeather extends Item {

    public ItemJeweledFeather() {
        super();
        setUnlocalizedName(ModInformation.ID + ":jeweledFeather");
        setCreativeTab(IronBackpacks.creativeTab);
    }
}
