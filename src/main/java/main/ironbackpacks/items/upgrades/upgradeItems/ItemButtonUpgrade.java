package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemButtonUpgrade extends ItemUpgradeBase {

    public ItemButtonUpgrade(){
        super("buttonUpgrade", "card_arrow", IronBackpacksConstants.Upgrades.BUTTON_UPGRADE_ID, "Adds buttons to quickly move items to and from your backpack.");
    }
}
