package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemNestingUpgrade extends ItemUpgradeBase {

    public ItemNestingUpgrade(){
        super("nestingUpgrade", IronBackpacksConstants.Upgrades.NESTING_UPGRADE_ID, ConfigHandler.nestingUpgradeCost, IronBackpacksConstants.Upgrades.NESTING_DESCRIPTION);
    }

}
