package gr8pefish.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterModSpecificUpgrade extends ItemUpgradeBase {

    public ItemFilterModSpecificUpgrade(){
        super("filterModSpecificUpgrade", IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID, ConfigHandler.filterModSpecificUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_DESCRIPTION);
    }

}
