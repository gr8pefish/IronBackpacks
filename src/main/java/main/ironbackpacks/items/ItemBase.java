package main.ironbackpacks.items;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import net.minecraft.item.Item;

/**
 * A very basic item class that every item on this mod extends
 */
public class ItemBase extends Item {

	public ItemBase(String unlocName, String textureName) { //TODO: remove texture name
		super();
		setUnlocalizedName(ModInformation.ID + ":" + unlocName);
		setCreativeTab(IronBackpacks.creativeTab);
	}

}
