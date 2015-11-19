package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemQuickDepositUpgrade extends ItemUpgradeBase{

    public ItemQuickDepositUpgrade(){
        super("quickDepositUpgrade", IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_UPGRADE_ID, ConfigHandler.quickDepositUpgradeCost, IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_DESCRIPTION);
    }
}
