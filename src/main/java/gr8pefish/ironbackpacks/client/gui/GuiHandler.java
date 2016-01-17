package gr8pefish.ironbackpacks.client.gui;

import gr8pefish.ironbackpacks.client.gui.inventory.GUIBackpack;
import gr8pefish.ironbackpacks.client.gui.inventory.GUIBackpackAlternate;
import gr8pefish.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import gr8pefish.ironbackpacks.container.alternateGui.InventoryAlternateGui;
import gr8pefish.ironbackpacks.container.backpack.ContainerBackpack;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * The GuiHandler handles the opening of GUIs. Called from onItemRightClick() in ItemBaseBackpack.
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID >=0){ //normal gui
            return new ContainerBackpack(new InventoryBackpack(player, IronBackpacksHelper.getBackpack(player))); //TODO: check okay once done refactoring
        }else if (ID < 0){ //alternate gui
            return new ContainerAlternateGui(new InventoryAlternateGui(player, IronBackpacksHelper.getBackpack(player)));
        }
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID >= 0) { //normal gui
            return GUIBackpack.buildGUI(player, new InventoryBackpack(player, IronBackpacksHelper.getBackpack(player)));
        }else if (ID < 0){ //alternate gui
            return GUIBackpackAlternate.GUI.buildGUIAlternate(new InventoryAlternateGui(player, IronBackpacksHelper.getBackpack(player))); //TODO: THE BUG IS HERE?
        }
		return null;
	}
}
