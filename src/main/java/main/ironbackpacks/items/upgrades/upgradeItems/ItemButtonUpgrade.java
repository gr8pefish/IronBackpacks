package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemButtonUpgrade extends ItemUpgradeBase {

    public ItemButtonUpgrade(){
        super("buttonUpgrade", IronBackpacksConstants.Upgrades.BUTTON_UPGRADE_ID, ConfigHandler.buttonUpgradeCost , IronBackpacksConstants.Upgrades.BUTTON_DESCRIPTION);
    }
}
