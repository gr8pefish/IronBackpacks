package gr8pefish.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterBasicUpgrade extends ItemUpgradeBase{

    public ItemFilterBasicUpgrade(){
        super("filterBasicUpgrade", IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID, ConfigHandler.filterBasicUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_BASIC_DESCRIPTION);
    }

}
