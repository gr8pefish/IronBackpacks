package main.ironbackpacks.items.backpacks.backpackItems;

import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemGoldBackpack extends ItemBaseBackpack {

    public ItemGoldBackpack() {
        super("goldBackpack", "backpack_gold", 2, ConfigHandler.enumGoldBackpack.upgradePoints.getValue(), IronBackpacksConstants.Backpacks.GOLD_ID);
    }
}
