package main.ironbackpacks.items.backpacks.backpackItems;


import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemDiamondBackpack extends ItemBaseBackpack{

    public ItemDiamondBackpack() {
        super("diamondBackpack", "backpack_diamond", 3, ConfigHandler.enumDiamondBackpack.upgradePoints.getValue(), IronBackpacksConstants.Backpacks.DIAMOND_ID);
    }


}
