package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemKeepOnDeathUpgrade extends ItemUpgradeBase {

    public ItemKeepOnDeathUpgrade(){
        super("keepOnDeathUpgrade", IronBackpacksConstants.Upgrades.KEEP_ON_DEATH_UPGRADE_ID, ConfigHandler.keepOnDeathUpgradeCost, IronBackpacksConstants.Upgrades.KEEP_ON_DEATH_DESCRIPTION);
    }
}
