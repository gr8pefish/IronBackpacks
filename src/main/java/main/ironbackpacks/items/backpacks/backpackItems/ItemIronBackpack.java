package main.ironbackpacks.items.backpacks.backpackItems;

import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemIronBackpack extends ItemBaseBackpack {

    public ItemIronBackpack() {
        super("ironBackpack", "backpack_iron", 1, ConfigHandler.enumIronBackpack.upgradePoints.getValue(), IronBackpacksConstants.Backpacks.IRON_ID);
    }
}