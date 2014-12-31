package main.ironbackpacks.items;

/*
 * Class to register your blocks in.
 * The order that you list them here is the order they are registered.
 * Keep that in mind if you like nicely organized creative tabs.
 */

import cpw.mods.fml.common.registry.GameRegistry;
import main.ironbackpacks.items.backpacks.ItemDoubleBackpack;
import main.ironbackpacks.items.backpacks.ItemSingleBackpack;
import net.minecraft.item.Item;

public class ItemRegistry {

	//items
    public static Item singleBackpack;
    public static Item doubleBackpack;


    //Register all items here
	public static void registerItems() {

        singleBackpack = new ItemSingleBackpack();
        GameRegistry.registerItem(singleBackpack, "Basic Backpack");

        doubleBackpack = new ItemDoubleBackpack();
        GameRegistry.registerItem(doubleBackpack, "Advanced Backpack");


	}
}
