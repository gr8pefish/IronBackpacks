package gr8pefish.ironbackpacks.client.gui;

import gr8pefish.ironbackpacks.client.gui.inventory.GUIBackpack;
import gr8pefish.ironbackpacks.client.gui.inventory.GUIBackpackAlternate;
import gr8pefish.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import gr8pefish.ironbackpacks.container.alternateGui.InventoryAlternateGui;
import gr8pefish.ironbackpacks.container.backpack.ContainerBackpack;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * The GuiHandler handles the opening of GUIs. Called from onItemRightClick() in ItemBaseBackpack.
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID >=0){ //normal gui
            return new ContainerBackpack(player, new InventoryBackpack(player, IronBackpacksHelper.getBackpack(player)), IronBackpacksHelper.getBackpack(player)); //TODO: check okay once refactoring
        }else if (ID < 0){ //alternate gui
            return new ContainerAlternateGui(player, new InventoryAlternateGui(player, IronBackpacksHelper.getBackpack(player)));
        }
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID >= 0) { //normal gui
            ItemStack backpack = IronBackpacksHelper.getBackpack(player);
            int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack);
            return GUIBackpack.GUI.buildGUI(player, new InventoryBackpack(player, backpack), upgrades, backpack);
        }else if (ID < 0){ //alternate gui
            ItemStack backpack = IronBackpacksHelper.getBackpack(player);
            int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack);
            return GUIBackpackAlternate.GUI.buildGUIAlternate(player, new InventoryAlternateGui(player, backpack), upgrades, backpack);
        }
		return null;
	}
}
