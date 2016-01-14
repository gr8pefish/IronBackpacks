package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemNestingAdvancedUpgrade extends ItemUpgradeBase {

    public ItemNestingAdvancedUpgrade(){
        super("nestingAdvancedUpgrade", IronBackpacksConstants.Upgrades.ADVANCED_NESTING_UPGRADE_ID, ConfigHandler.nestingAdvancedUpgradeCost, IronBackpacksConstants.Upgrades.ADVANCED_NESTING_DESCRIPTION);
    }
}
