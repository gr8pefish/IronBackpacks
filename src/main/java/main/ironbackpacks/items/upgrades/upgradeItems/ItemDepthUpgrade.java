package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemDepthUpgrade extends ItemUpgradeBase {

    public ItemDepthUpgrade(){
        super("depthUpgrade", "depthUpgrade", IronBackpacksConstants.Upgrades.DEPTH_UPGRADE_ID, ConfigHandler.depthUpgradeCost, IronBackpacksConstants.Upgrades.DEPTH_UPGRADE_DESCRIPTION);
    }
}
