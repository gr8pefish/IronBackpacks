package gr8pefish.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgradeBase;

public class ItemFilterOreDictUpgrade extends ItemUpgradeBase{

    public ItemFilterOreDictUpgrade(){
        super("filterOreDictUpgrade", IronBackpacksConstants.Upgrades.FILTER_OREDICT_UPGRADE_ID, ConfigHandler.filterOreDictUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_OREDICT_DESCRIPTION);
    }

}
