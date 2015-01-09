package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemRenamingUpgrade extends ItemUpgradeBase {

    public ItemRenamingUpgrade(){
        super("renamingUpgrade", "card_box", IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID, "Allows you to rename the backpack.");
    }
}
