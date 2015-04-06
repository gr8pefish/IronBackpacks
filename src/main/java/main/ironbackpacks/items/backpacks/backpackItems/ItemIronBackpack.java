package main.ironbackpacks.items.backpacks.backpackItems;

import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemIronBackpack extends ItemBaseBackpack {

    public ItemIronBackpack() {
        super("ironBackpack", ConfigHandler.useAlternateHDBackpackTextures ? "backpack_iron_alternate" : "backpack_iron", ConfigHandler.enumIronBackpack.upgradePoints.getValue(), IronBackpacksConstants.Backpacks.IRON_ID);
    }
}