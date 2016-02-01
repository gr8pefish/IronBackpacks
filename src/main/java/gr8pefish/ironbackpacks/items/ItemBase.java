package gr8pefish.ironbackpacks.items;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.Constants;
import net.minecraft.item.Item;

/**
 * A very basic items class that every items on this mod extends
 */
public class ItemBase extends Item {

	public ItemBase(String unlocName, String unneeded) {
		super();
		setUnlocalizedName(Constants.MODID + ":" + unlocName);
		setCreativeTab(IronBackpacks.creativeTab);
	}

}
