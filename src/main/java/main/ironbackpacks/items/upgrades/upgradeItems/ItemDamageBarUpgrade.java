package main.ironbackpacks.items.upgrades.upgradeItems;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemDamageBarUpgrade extends ItemUpgradeBase {

    public ItemDamageBarUpgrade(){
        super("damageBarUpgrade", "damageBarUpgrade", IronBackpacksConstants.Upgrades.DAMAGE_BAR_UPGRADE_ID, ConfigHandler.damageBarUpgradeCost, IronBackpacksConstants.Upgrades.DAMAGE_BAR_DESCRIPTION);
    }
}
