package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;


public class ItemCondenserTinyUpgrade extends ItemUpgradeBase {

    public ItemCondenserTinyUpgrade(){
        super("condenserTinyUpgrade", IronBackpacksConstants.Upgrades.CONDENSER_TINY_UPGRADE_ID, ConfigHandler.condenserTinyUpgradeCost, IronBackpacksConstants.Upgrades.CONDENSER_TINY_DESCRIPTION);
    }
}
