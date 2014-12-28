package main.ironbackpacks.items;

/*
 * Class to register your blocks in.
 * The order that you list them here is the order they are registered.
 * Keep that in mind if you like nicely organized creative tabs.
 */

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemRegistry {

	//items
    public static Item basicBackpack;


    //Register all items here
	public static void registerItems() {

        basicBackpack = new ItemBasicBackpack();
        GameRegistry.registerItem(basicBackpack, "Basic Backpack");
	}
}
