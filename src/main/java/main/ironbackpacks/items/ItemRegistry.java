package main.ironbackpacks.items;

/*
 * Class to register your blocks in.
 * The order that you list them here is the order they are registered.
 * Keep that in mind if you like nicely organized creative tabs.
 */

import cpw.mods.fml.common.registry.GameRegistry;
import main.ironbackpacks.items.backpacks.backpackItems.ItemDiamondBackpack;
import main.ironbackpacks.items.backpacks.backpackItems.ItemIronBackpack;
import main.ironbackpacks.items.backpacks.backpackItems.ItemBasicBackpack;
import main.ironbackpacks.items.backpacks.backpackItems.ItemGoldBackpack;
import main.ironbackpacks.items.upgrades.upgradeItems.ItemButtonUpgrade;
import main.ironbackpacks.items.upgrades.upgradeItems.ItemNestingUpgrade;
import net.minecraft.item.Item;

public class ItemRegistry {

	//backpacks
    public static Item basicBackpack;
    public static Item ironBackpack;
    public static Item goldBackpack;
    public static Item diamondBackpack;

    //upgrades
    public static Item buttonUpgrade;
    public static Item nestingUpgrade;

    public static Item[] getBackpacks(){
        Item[] backpacks = new Item[4]; //TODO - change from hardcoded to dynamic
        backpacks[0] = basicBackpack;
        backpacks[1] = ironBackpack;
        backpacks[2] = goldBackpack;
        backpacks[3] = diamondBackpack;
        return backpacks;
    }

    public static Item[] getUpgrades(){
        Item[] upgrades = new Item[2];
        upgrades[0] = buttonUpgrade;
        upgrades[1] = nestingUpgrade;
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
        GameRegistry.registerItem(buttonUpgrade, "Button Upgrade");
        nestingUpgrade = new ItemNestingUpgrade();
        GameRegistry.registerItem(nestingUpgrade, "Nesting Upgrade");


	}
}
