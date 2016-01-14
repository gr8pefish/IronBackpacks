package gr8pefish.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterVoidUpgrade extends ItemUpgradeBase {

    public ItemFilterVoidUpgrade(){
        super("filterVoidUpgrade", IronBackpacksConstants.Upgrades.FILTER_VOID_UPGRADE_ID, ConfigHandler.filterVoidUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_VOID_DESCRIPTION);
    }

}

