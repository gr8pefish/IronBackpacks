package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemCondenserSmallUpgrade extends ItemUpgradeBase {

    public ItemCondenserSmallUpgrade(){
        super("condenserSmallUpgrade", IronBackpacksConstants.Upgrades.CONDENSER_SMALL_UPGRADE_ID, ConfigHandler.condenserSmallUpgradeCost, IronBackpacksConstants.Upgrades.CONDENSER_SMALL_DESCRIPTION);
    }
}
