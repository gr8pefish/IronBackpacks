package main.ironbackpacks.items.upgrades;

import net.minecraft.util.StatCollector;

public enum UpgradeNames {

    NONE(StatCollector.translateToLocal("emptyUpgradeSlot")),
    BUTTON(StatCollector.translateToLocal("item.ironbackpacks:buttonUpgrade.name")),
    NESTING(StatCollector.translateToLocal("item.ironbackpacks:nestingUpgrade.name")),
    DAMAGE_BAR(StatCollector.translateToLocal("item.ironbackpacks:damageBarUpgrade.name")),
    RENAMING(StatCollector.translateToLocal("item.ironbackpacks:renamingUpgrade.name")),
    FILTER(StatCollector.translateToLocal("item.ironbackpacks:filterUpgrade.name")),
    HOPPER(StatCollector.translateToLocal("item.ironbackpacks:hopperUpgrade.name")),
    CONDENSER(StatCollector.translateToLocal("item.ironbackpacks:condenserUpgrade.name"));

    public String fancyName;

    UpgradeNames(String fancyName){
        this.fancyName = fancyName;
    }

    public String getFancyName(){
        return this.fancyName;
    }

}
