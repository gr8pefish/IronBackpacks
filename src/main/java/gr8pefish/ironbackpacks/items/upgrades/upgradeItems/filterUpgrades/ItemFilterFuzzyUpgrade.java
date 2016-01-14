package gr8pefish.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterFuzzyUpgrade extends ItemUpgradeBase{

    public ItemFilterFuzzyUpgrade(){
        super("filterFuzzyUpgrade", IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID, ConfigHandler.filterFuzzyUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_FUZZY_DESCRIPTION);
    }

}
