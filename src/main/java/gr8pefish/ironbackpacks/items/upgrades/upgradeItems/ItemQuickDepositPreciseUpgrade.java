package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemQuickDepositPreciseUpgrade extends ItemUpgradeBase {

    public ItemQuickDepositPreciseUpgrade(){
        super("quickDepositPreciseUpgrade", IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_UPGRADE_ID, ConfigHandler.quickDepositPreciseUpgradeCost, IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_DESCRIPTION);
    }
}