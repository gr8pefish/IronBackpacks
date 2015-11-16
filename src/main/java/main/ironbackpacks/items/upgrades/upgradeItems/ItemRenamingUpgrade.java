package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemRenamingUpgrade extends ItemUpgradeBase {

    public ItemRenamingUpgrade() {
        super("renamingUpgrade", "renamingUpgrade", IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID, ConfigHandler.renamingUpgradeCost, IronBackpacksConstants.Upgrades.RENAMING_DESCRIPTION);
    }
}
