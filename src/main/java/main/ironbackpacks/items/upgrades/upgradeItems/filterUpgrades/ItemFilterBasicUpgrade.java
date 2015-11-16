package main.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterBasicUpgrade extends ItemUpgradeBase {

    public ItemFilterBasicUpgrade() {
        super("filterBasicUpgrade", "filterBasicUpgrade", IronBackpacksConstants.Upgrades.FILTER_BASIC_UPGRADE_ID, ConfigHandler.filterBasicUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_BASIC_DESCRIPTION);
    }
}
