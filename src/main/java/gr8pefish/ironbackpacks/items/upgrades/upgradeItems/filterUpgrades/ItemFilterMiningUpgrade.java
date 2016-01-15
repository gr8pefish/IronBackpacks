package gr8pefish.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterMiningUpgrade extends ItemUpgradeBase {

    public ItemFilterMiningUpgrade(){
        super("filterMiningUpgrade", IronBackpacksConstants.Upgrades.FILTER_MINING_UPGRADE_ID, ConfigHandler.filterMiningUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_MINING_DESCRIPTION);
    }

}
