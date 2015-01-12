package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterUpgrade extends ItemUpgradeBase{

    public ItemFilterUpgrade(){
        super("filterUpgrade", "filterUpgrade", IronBackpacksConstants.Upgrades.FILTER_UPGRADE_ID, "Adds a configurable filter of items","that the backpack will automatically collect.");
    }
}
