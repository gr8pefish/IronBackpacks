package main.ironbackpacks.items;

import cpw.mods.fml.common.registry.GameRegistry;
import main.ironbackpacks.items.backpacks.backpackItems.ItemBasicBackpack;
import main.ironbackpacks.items.backpacks.backpackItems.ItemDiamondBackpack;
import main.ironbackpacks.items.backpacks.backpackItems.ItemGoldBackpack;
import main.ironbackpacks.items.backpacks.backpackItems.ItemIronBackpack;
import main.ironbackpacks.items.craftingItems.ItemJeweledFeather;
import main.ironbackpacks.items.craftingItems.ItemNest;
import main.ironbackpacks.items.craftingItems.ItemTreatedLeather;
import main.ironbackpacks.items.craftingItems.ItemUpgradeCore;
import main.ironbackpacks.items.upgrades.upgradeItems.*;
import main.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades.*;
import main.ironbackpacks.util.ConfigHandler;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class ItemRegistry {

    //Class to register your items in.

	//backpacks
    public static Item basicBackpack;
    public static Item ironBackpack;
    public static Item goldBackpack;
    public static Item diamondBackpack;

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

    public static ArrayList<Item> getBackpacks(){
        ArrayList<Item> backpacks = new ArrayList<Item>();
        backpacks.add(basicBackpack);
        backpacks.add(ironBackpack);
        backpacks.add(goldBackpack);
        backpacks.add(diamondBackpack);
        return backpacks;
    }

    public static ArrayList<Item> getUpgrades(){
        ArrayList<Item> upgrades = new ArrayList<Item>();
        upgrades.add(buttonUpgrade);
        upgrades.add(nestingUpgrade);
        upgrades.add(damageBarUpgrade);
        if (ConfigHandler.renamingUpgradeRequired){
            upgrades.add(renamingUpgrade);
        }
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

    //Register all items here
	public static void registerItems() {

        //backpacks
        basicBackpack = new ItemBasicBackpack();
        GameRegistry.registerItem(basicBackpack, "basicBackpack");
        ironBackpack = new ItemIronBackpack();
        GameRegistry.registerItem(ironBackpack, "ironBackpack");
        goldBackpack = new ItemGoldBackpack();
        GameRegistry.registerItem(goldBackpack, "goldBackpack");
        diamondBackpack = new ItemDiamondBackpack();
        GameRegistry.registerItem(diamondBackpack, "diamondBackpack");

        //upgrades
        buttonUpgrade = new ItemButtonUpgrade();
        GameRegistry.registerItem(buttonUpgrade, "buttonUpgrade");
        nestingUpgrade = new ItemNestingUpgrade();
        GameRegistry.registerItem(nestingUpgrade, "nestingUpgrade");
        if (ConfigHandler.renamingUpgradeRequired){
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
}
