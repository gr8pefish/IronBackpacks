package main.ironbackpacks.items;

/*
 * Class to register your blocks in.
 * The order that you list them here is the order they are registered.
 * Keep that in mind if you like nicely organized creative tabs.
 */

import cpw.mods.fml.common.registry.GameRegistry;
import main.ironbackpacks.items.backpacks.ItemDoubleBackpack;
import main.ironbackpacks.items.backpacks.ItemSingleBackpack;
import main.ironbackpacks.items.upgrades.ItemButtonUpgrade;
import main.ironbackpacks.items.upgrades.ItemNestingUpgrade;
import net.minecraft.item.Item;

public class ItemRegistry {

	//backpacks
    public static Item singleBackpack;
    public static Item doubleBackpack;

    //upgrades
    public static Item buttonUpgrade;
    public static Item nestingUpgrade;

    public static Item[] getBackpacks(){
        Item[] backpacks = new Item[2]; //TODO - change from hardcoded to dynamic
        backpacks[0] = singleBackpack;
        backpacks[1] = doubleBackpack;
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
        singleBackpack = new ItemSingleBackpack();
        GameRegistry.registerItem(singleBackpack, "Basic Backpack");
        doubleBackpack = new ItemDoubleBackpack();
        GameRegistry.registerItem(doubleBackpack, "Advanced Backpack");

        //upgrades
        buttonUpgrade = new ItemButtonUpgrade();
        GameRegistry.registerItem(buttonUpgrade, "Button Upgrade");
        nestingUpgrade = new ItemNestingUpgrade();
        GameRegistry.registerItem(nestingUpgrade, "Nesting Upgrade");


	}
}
