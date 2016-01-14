package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;

public class ItemRenamingUpgrade extends ItemUpgradeBase {

    public ItemRenamingUpgrade(){
        super("renamingUpgrade", IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID, ConfigHandler.renamingUpgradeCost, IronBackpacksConstants.Upgrades.RENAMING_DESCRIPTION);
    }
}
