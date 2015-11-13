package main.ironbackpacks.items.craftingItems;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import net.minecraft.item.Item;

public class ItemTreatedLeather extends Item {

    public ItemTreatedLeather() {
        super();
        setUnlocalizedName(ModInformation.ID + ":treatedLeather");
        setCreativeTab(IronBackpacks.creativeTab);
    }
}
