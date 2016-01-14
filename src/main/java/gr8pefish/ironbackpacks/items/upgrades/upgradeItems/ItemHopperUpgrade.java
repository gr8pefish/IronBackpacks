package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemHopperUpgrade extends ItemUpgradeBase {

    public ItemHopperUpgrade(){
        super("hopperUpgrade", IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID, ConfigHandler.hopperUpgradeCost, IronBackpacksConstants.Upgrades.HOPPER_DESCRIPTION);
    }
}
