package gr8pefish.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterAdvancedUpgrade extends ItemUpgradeBase {

    public ItemFilterAdvancedUpgrade(){
        super("filterAdvancedUpgrade", IronBackpacksConstants.Upgrades.FILTER_ADVANCED_UPGRADE_ID, ConfigHandler.filterAdvancedUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_ADVANCED_DESCRIPTION);
    }

}
