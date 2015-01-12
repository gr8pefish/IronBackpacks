package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemHopperUpgrade extends ItemUpgradeBase {

    public ItemHopperUpgrade(){
        super("hopperUpgrade", "hopperUpgrade", IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID, "Allows you to specify an item to keep stocked","in your inventory/hotbar","which is supplied from your backpack.");
    }
}
