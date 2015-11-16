package main.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterModSpecificUpgrade extends ItemUpgradeBase {

    public ItemFilterModSpecificUpgrade() {
        super("filterModSpecificUpgrade", "filterModSpecificUpgrade", IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID, ConfigHandler.filterModSpecificUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_DESCRIPTION);
    }
}
