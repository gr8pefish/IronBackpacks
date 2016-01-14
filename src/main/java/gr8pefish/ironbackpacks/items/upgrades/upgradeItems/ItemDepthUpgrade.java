package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemDepthUpgrade extends ItemUpgradeBase {

    public ItemDepthUpgrade(){
        super("depthUpgrade", IronBackpacksConstants.Upgrades.DEPTH_UPGRADE_ID, ConfigHandler.depthUpgradeCost, IronBackpacksConstants.Upgrades.DEPTH_UPGRADE_DESCRIPTION);
    }
}
