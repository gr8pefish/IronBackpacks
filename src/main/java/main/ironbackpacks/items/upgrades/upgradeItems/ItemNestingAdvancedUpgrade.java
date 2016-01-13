package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.config.ConfigHandler;
import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemNestingAdvancedUpgrade extends ItemUpgradeBase {

    public ItemNestingAdvancedUpgrade(){
        super("nestingAdvancedUpgrade", IronBackpacksConstants.Upgrades.ADVANCED_NESTING_UPGRADE_ID, ConfigHandler.nestingAdvancedUpgradeCost, IronBackpacksConstants.Upgrades.ADVANCED_NESTING_DESCRIPTION);
    }
}
