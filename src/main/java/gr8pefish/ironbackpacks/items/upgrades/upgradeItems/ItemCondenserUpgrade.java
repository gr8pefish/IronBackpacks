package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;

public class ItemCondenserUpgrade extends ItemUpgradeBase {

    public ItemCondenserUpgrade(){
        super("condenserUpgrade", IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID, ConfigHandler.condenserUpgradeCost, IronBackpacksConstants.Upgrades.CONDENSER_DESCRIPTION);
    }
}
