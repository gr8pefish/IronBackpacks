package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemNestingUpgrade extends ItemUpgradeBase {

    public ItemNestingUpgrade() {
        super("nestingUpgrade", "nestingUpgrade", IronBackpacksConstants.Upgrades.NESTING_UPGRADE_ID, ConfigHandler.nestingUpgradeCost, IronBackpacksConstants.Upgrades.NESTING_DESCRIPTION);
    }

}
