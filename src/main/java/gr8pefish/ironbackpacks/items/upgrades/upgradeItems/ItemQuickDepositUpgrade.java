package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemQuickDepositUpgrade extends ItemUpgradeBase{

    public ItemQuickDepositUpgrade(){
        super("quickDepositUpgrade", IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_UPGRADE_ID, ConfigHandler.quickDepositUpgradeCost, IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_DESCRIPTION);
    }
}
