package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemButtonUpgrade extends ItemUpgradeBase {

    public ItemButtonUpgrade(){
        super("buttonUpgrade", IronBackpacksConstants.Upgrades.BUTTON_UPGRADE_ID, ConfigHandler.buttonUpgradeCost , IronBackpacksConstants.Upgrades.BUTTON_DESCRIPTION);
    }
}
