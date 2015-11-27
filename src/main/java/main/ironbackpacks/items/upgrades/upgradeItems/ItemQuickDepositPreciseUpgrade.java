package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemQuickDepositPreciseUpgrade extends ItemUpgradeBase {

    public ItemQuickDepositPreciseUpgrade(){
        super("quickDepositPreciseUpgrade", IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_UPGRADE_ID, ConfigHandler.quickDepositPreciseUpgradeCost, IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_DESCRIPTION);
    }
}