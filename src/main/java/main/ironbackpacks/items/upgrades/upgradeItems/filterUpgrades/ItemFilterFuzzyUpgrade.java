package main.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterFuzzyUpgrade extends ItemUpgradeBase {

    public ItemFilterFuzzyUpgrade() {
        super("filterFuzzyUpgrade", "filterFuzzyUpgrade", IronBackpacksConstants.Upgrades.FILTER_FUZZY_UPGRADE_ID, ConfigHandler.filterFuzzyUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_FUZZY_DESCRIPTION);
    }
}
