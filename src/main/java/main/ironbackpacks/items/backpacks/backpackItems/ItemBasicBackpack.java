package main.ironbackpacks.items.backpacks.backpackItems;

import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemBasicBackpack extends ItemBaseBackpack {

    public ItemBasicBackpack(){
        super("basicBackpack", ConfigHandler.useAlternateBackpackTextures ? "backpack_basic_alt" : "backpack_basic", ConfigHandler.enumBasicBackpack.upgradePoints.getValue(), IronBackpacksConstants.Backpacks.BASIC_ID);
    }
}
