package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemCondenserSmallUpgrade extends ItemUpgradeBase {

    public ItemCondenserSmallUpgrade(){
        super("condenserSmallUpgrade", IronBackpacksConstants.Upgrades.CONDENSER_SMALL_UPGRADE_ID, ConfigHandler.condenserSmallUpgradeCost, IronBackpacksConstants.Upgrades.CONDENSER_SMALL_DESCRIPTION);
    }
}
