package main.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterVoidUpgrade extends ItemUpgradeBase {

    public ItemFilterVoidUpgrade(){
        super("filterVoidUpgrade", IronBackpacksConstants.Upgrades.FILTER_VOID_UPGRADE_ID, ConfigHandler.filterVoidUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_VOID_DESCRIPTION);
    }

}

