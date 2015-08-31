package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemNestingAdvancedUpgrade extends ItemUpgradeBase {

    public ItemNestingAdvancedUpgrade(){
        super("nestingAdvancedUpgrade", "nestingAdvancedUpgrade", IronBackpacksConstants.Upgrades.ADVANCED_NESTING_UPGRADE_ID, ConfigHandler.nestingAdvancedUpgradeCost, IronBackpacksConstants.Upgrades.ADVANCED_NESTING_DESCRIPTION);
    }
}
