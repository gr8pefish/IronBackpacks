package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemHopperUpgrade extends ItemUpgradeBase {

    public ItemHopperUpgrade(){
        super("hopperUpgrade", IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID, ConfigHandler.hopperUpgradeCost, IronBackpacksConstants.Upgrades.HOPPER_DESCRIPTION);
    }
}
