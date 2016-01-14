package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemKeepOnDeathUpgrade extends ItemUpgradeBase {

    public ItemKeepOnDeathUpgrade(){
        super("keepOnDeathUpgrade", IronBackpacksConstants.Upgrades.KEEP_ON_DEATH_UPGRADE_ID, ConfigHandler.keepOnDeathUpgradeCost, IronBackpacksConstants.Upgrades.KEEP_ON_DEATH_DESCRIPTION);
    }
}
