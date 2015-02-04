package main.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterAdvancedUpgrade extends ItemUpgradeBase {

    public ItemFilterAdvancedUpgrade(){
        super("filterAdvancedUpgrade", "filterAdvancedUpgrade", IronBackpacksConstants.Upgrades.FILTER_ADVANCED_UPGRADE_ID, ConfigHandler.filterAdvancedUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_ADVANCED_DESCRIPTION);
    }
}
