package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemDamageBarUpgrade extends ItemUpgradeBase {

    public ItemDamageBarUpgrade(){
        super("damageBarUpgrade", "damageBarUpgrade", IronBackpacksConstants.Upgrades.DAMAGE_BAR_UPGRADE_ID, "Adds a damage bar to visually represent how full the backpack is at a glance.");
    }
}
