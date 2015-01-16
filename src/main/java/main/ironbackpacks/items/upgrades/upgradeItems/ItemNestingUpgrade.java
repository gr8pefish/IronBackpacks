package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemNestingUpgrade extends ItemUpgradeBase {

    public ItemNestingUpgrade(){
        super("nestingUpgrade", "nestingUpgrade", IronBackpacksConstants.Upgrades.NESTING_UPGRADE_ID, "Allows you to place any backpack","of a previous tier inside the backpack.");
    }

}
