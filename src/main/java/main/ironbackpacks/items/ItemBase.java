package main.ironbackpacks.items;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import net.minecraft.item.Item;

/**
 * A very basic item class that every item on this mod extends
 */
public class ItemBase extends Item {

	// For sub-items
	public ItemBase(){
		super();
		setCreativeTab(IronBackpacks.creativeTab);
	}

	// If you aren't setting multiple textures for your item. IE: Non-Metadata items.
	public ItemBase(String unlocName, String textureName) {
		super();
		setUnlocalizedName(ModInformation.ID + ":" + unlocName);
		setTextureName(ModInformation.ID + ":" + textureName);
		setCreativeTab(IronBackpacks.creativeTab);
	}

}
