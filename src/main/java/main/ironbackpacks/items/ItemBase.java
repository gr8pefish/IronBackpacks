package main.ironbackpacks.items;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    //Base item class for getting standard things done with quickly.
    //Extend this for pretty much every item you make.

	// If you aren't setting multiple textures for your item. IE: Non-Metadata items.
	public ItemBase(String unlocName, String textureName) {
		super();

		setUnlocalizedName(ModInformation.ID + ":" + unlocName);
		setTextureName(ModInformation.ID + ":" + textureName);
		setCreativeTab(IronBackpacks.creativeTab);
	}
}
