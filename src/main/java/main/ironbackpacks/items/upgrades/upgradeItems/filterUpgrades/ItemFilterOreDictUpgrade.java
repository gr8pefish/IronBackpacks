package main.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

public class ItemFilterOreDictUpgrade extends ItemUpgradeBase{

    public ItemFilterOreDictUpgrade(){
        super("filterOreDictUpgrade", IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID, ConfigHandler.filterOreDictUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_OREDICT_DESCRIPTION);
    }
}
