package main.ironbackpacks.items;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.items.backpacks.BackpackTypes;
import main.ironbackpacks.items.backpacks.ItemBackpack;
import main.ironbackpacks.items.backpacks.ItemEnderBackpack;
import main.ironbackpacks.items.craftingItems.ItemJeweledFeather;
import main.ironbackpacks.items.craftingItems.ItemNest;
import main.ironbackpacks.items.craftingItems.ItemTreatedLeather;
import main.ironbackpacks.items.craftingItems.ItemUpgradeCore;
import main.ironbackpacks.items.upgrades.upgradeItems.*;
import main.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades.*;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.InventoryRenderHelper;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

/**
 * Registers all the items in this mod.
 */
public class ItemRegistry {

    //backpacks
    public static Item basicBackpack;
    public static Item ironBackpack;
    public static Item goldBackpack;
    public static Item diamondBackpack;
    public static Item enderBackpack;

    //upgrades
    public static Item buttonUpgrade;
    public static Item nestingUpgrade;
    public static Item renamingUpgrade;
    public static Item damageBarUpgrade;
    public static Item filterBasicUpgrade;
    public static Item filterFuzzyUpgrade;
    public static Item filterOreDictUpgrade;
    public static Item filterModSpecificUpgrade;
    public static Item hopperUpgrade;
    public static Item condenserUpgrade;
    public static Item keepOnDeathUpgrade;
    public static Item additionalUpgradesUpgrade;
    public static Item quickDepositUpgrade;
    public static Item filterAdvancedUpgrade;
    public static Item nestingAdvancedUpgrade;

    //misc
    public static Item nest;
    public static Item upgradeCore;
    public static Item jeweledFeather;
    public static Item treatedLeather;

    /**
     * Gets every backpack item.
     *
     * @return - backpacks as an ArrayList of Items
     */
    public static ArrayList<Item> getBackpacks() {
        ArrayList<Item> backpacks = new ArrayList<Item>();
        backpacks.add(basicBackpack);
        backpacks.add(ironBackpack);
        backpacks.add(goldBackpack);
        backpacks.add(diamondBackpack);
        return backpacks;
    }

    /**
     * Gets every upgrade item.
     *
     * @return - upgrades as an ArrayList of Items
     */
    public static ArrayList<Item> getUpgrades() {
        ArrayList<Item> upgrades = new ArrayList<Item>();
        upgrades.add(buttonUpgrade);
        upgrades.add(nestingUpgrade);
        upgrades.add(damageBarUpgrade);
        if (ConfigHandler.renamingUpgradeRequired) upgrades.add(renamingUpgrade);
        upgrades.add(filterBasicUpgrade);
        upgrades.add(filterFuzzyUpgrade);
        upgrades.add(filterOreDictUpgrade);
        upgrades.add(filterModSpecificUpgrade);
        upgrades.add(hopperUpgrade);
        upgrades.add(condenserUpgrade);
        upgrades.add(keepOnDeathUpgrade);
        upgrades.add(additionalUpgradesUpgrade);
        upgrades.add(quickDepositUpgrade);
        upgrades.add(filterAdvancedUpgrade);
        upgrades.add(nestingAdvancedUpgrade);
        return upgrades;
    }


    /**
     * Registers all the items with the GameRegistry
     */
    public static void registerItems() {

        basicBackpack = new ItemBackpack(BackpackTypes.BASIC);
        GameRegistry.registerItem(basicBackpack, BackpackTypes.BASIC.getName());
        ironBackpack = new ItemBackpack(BackpackTypes.IRON);
        GameRegistry.registerItem(ironBackpack, BackpackTypes.IRON.getName());
        goldBackpack = new ItemBackpack(BackpackTypes.GOLD);
        GameRegistry.registerItem(goldBackpack, BackpackTypes.GOLD.getName());
        diamondBackpack = new ItemBackpack(BackpackTypes.DIAMOND);
        GameRegistry.registerItem(diamondBackpack, BackpackTypes.DIAMOND.getName());
        if (ConfigHandler.enableEnderBackpack) {
            enderBackpack = new ItemEnderBackpack();
            GameRegistry.registerItem(enderBackpack, ((ItemEnderBackpack) enderBackpack).getName());
        }

        //upgrades
        buttonUpgrade = new ItemButtonUpgrade();
        GameRegistry.registerItem(buttonUpgrade, "buttonUpgrade");
        nestingUpgrade = new ItemNestingUpgrade();
        GameRegistry.registerItem(nestingUpgrade, "nestingUpgrade");
        if (ConfigHandler.renamingUpgradeRequired) {
            renamingUpgrade = new ItemRenamingUpgrade();
            GameRegistry.registerItem(renamingUpgrade, "renamingUpgrade");
        }
        damageBarUpgrade = new ItemDamageBarUpgrade();
        GameRegistry.registerItem(damageBarUpgrade, "damageBarUpgrade");
        filterBasicUpgrade = new ItemFilterBasicUpgrade();
        GameRegistry.registerItem(filterBasicUpgrade, "filterBasicUpgrade");
        hopperUpgrade = new ItemHopperUpgrade();
        GameRegistry.registerItem(hopperUpgrade, "hopperUpgrade");
        condenserUpgrade = new ItemCondenserUpgrade();
        GameRegistry.registerItem(condenserUpgrade, "condenserUpgrade");
        keepOnDeathUpgrade = new ItemKeepOnDeathUpgrade();
        GameRegistry.registerItem(keepOnDeathUpgrade, "keepOnDeathUpgrade");
        filterModSpecificUpgrade = new ItemFilterModSpecificUpgrade();
        GameRegistry.registerItem(filterModSpecificUpgrade, "filterModSpecificUpgrade");
        additionalUpgradesUpgrade = new ItemAdditionalUpgradePointsUpgrade();
        GameRegistry.registerItem(additionalUpgradesUpgrade, "additionalUpgradeSlotsUpgrade");
        filterFuzzyUpgrade = new ItemFilterFuzzyUpgrade();
        GameRegistry.registerItem(filterFuzzyUpgrade, "filterFuzzyUpgrade");
        filterOreDictUpgrade = new ItemFilterOreDictUpgrade();
        GameRegistry.registerItem(filterOreDictUpgrade, "filterOreDictUpgrade");
        quickDepositUpgrade = new ItemQuickDepositUpgrade();
        GameRegistry.registerItem(quickDepositUpgrade, "quickDepositUpgrade");
        filterAdvancedUpgrade = new ItemFilterAdvancedUpgrade();
        GameRegistry.registerItem(filterAdvancedUpgrade, "filterAdvancedUpgrade");
        nestingAdvancedUpgrade = new ItemNestingAdvancedUpgrade();
        GameRegistry.registerItem(nestingAdvancedUpgrade, "nestingAdvancedUpgrade");

        //misc
        nest = new ItemNest();
        GameRegistry.registerItem(nest, "nest");
        upgradeCore = new ItemUpgradeCore();
        GameRegistry.registerItem(upgradeCore, "upgradeCore");
        jeweledFeather = new ItemJeweledFeather();
        GameRegistry.registerItem(jeweledFeather, "jeweledFeather");
        treatedLeather = new ItemTreatedLeather();
        GameRegistry.registerItem(treatedLeather, "treatedLeather");
    }

    public static void registerRenders() {
        InventoryRenderHelper helper = new InventoryRenderHelper(ModInformation.ID + ":");

        String alternate = ConfigHandler.useAlternateHDBackpackTextures ? "_alternate" : "";
        helper.itemRender(basicBackpack, BackpackTypes.BASIC.getName() + alternate);
        helper.itemRender(ironBackpack, BackpackTypes.IRON.getName() + alternate);
        helper.itemRender(goldBackpack, BackpackTypes.GOLD.getName() + alternate);
        helper.itemRender(diamondBackpack, BackpackTypes.DIAMOND.getName() + alternate);
        helper.itemRender(enderBackpack, ((ItemEnderBackpack) enderBackpack).getName());

        helper.itemRender(buttonUpgrade, "buttonUpgrade");
        helper.itemRender(nestingUpgrade, "nestingUpgrade");
        if (ConfigHandler.renamingUpgradeRequired)
            helper.itemRender(renamingUpgrade, "renamingUpgrade");
        helper.itemRender(damageBarUpgrade, "damageBarUpgrade");
        helper.itemRender(filterBasicUpgrade, "filterBasicUpgrade");
        helper.itemRender(hopperUpgrade, "hopperUpgrade");
        helper.itemRender(condenserUpgrade, "condenserUpgrade");
        helper.itemRender(keepOnDeathUpgrade, "keepOnDeathUpgrade");
        helper.itemRender(filterModSpecificUpgrade, "filterModSpecificUpgrade");
        helper.itemRender(additionalUpgradesUpgrade, "additionalUpgradesUpgrade");
        helper.itemRender(filterFuzzyUpgrade, "filterFuzzyUpgrade");
        helper.itemRender(filterOreDictUpgrade, "filterOreDictUpgrade");
        helper.itemRender(quickDepositUpgrade, "quickDepositUpgrade");
        helper.itemRender(filterAdvancedUpgrade, "filterAdvancedUpgrade");
        helper.itemRender(nestingAdvancedUpgrade, "nestingAdvancedUpgrade");

        helper.itemRender(nest, "nest");
        helper.itemRender(upgradeCore, "upgradeCore");
        helper.itemRender(jeweledFeather, "jeweledFeather");
        helper.itemRender(treatedLeather, "treatedLeather");
    }
}
