package main.ironbackpacks.items.backpacks.backpackItems;

import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemBasicBackpack extends ItemBaseBackpack {

    public ItemBasicBackpack(){
        super("basicBackpack", "backpack_basic", 0, ConfigHandler.enumBasicBackpack.upgradePoints.getValue(), IronBackpacksConstants.Backpacks.BASIC_ID);
    }
}
