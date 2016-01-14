package gr8pefish.ironbackpacks.items.upgrades.upgradeItems;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public class ItemDamageBarUpgrade extends ItemUpgradeBase {

    public ItemDamageBarUpgrade(){
        super("damageBarUpgrade", IronBackpacksConstants.Upgrades.DAMAGE_BAR_UPGRADE_ID, ConfigHandler.damageBarUpgradeCost, IronBackpacksConstants.Upgrades.DAMAGE_BAR_DESCRIPTION);
    }
}
