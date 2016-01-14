package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;


public class ItemCondenserTinyUpgrade extends ItemUpgradeBase {

    public ItemCondenserTinyUpgrade(){
        super("condenserTinyUpgrade", IronBackpacksConstants.Upgrades.CONDENSER_TINY_UPGRADE_ID, ConfigHandler.condenserTinyUpgradeCost, IronBackpacksConstants.Upgrades.CONDENSER_TINY_DESCRIPTION);
    }
}
