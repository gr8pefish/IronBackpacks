package main.ironbackpacks.util;

import cpw.mods.fml.common.network.IGuiHandler;
import main.ironbackpacks.client.gui.GUIBackpack;
import main.ironbackpacks.container.ContainerBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.inventory.InventoryBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID >=0){
            IronBackpackType type = IronBackpackType.values()[ID];
            return new ContainerBackpack(player, new InventoryBackpack(player, IronBackpacksHelper.getBackpack(player), type), type);
        }
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID >= 0) {
            return GUIBackpack.GUI.buildGUI(player, new InventoryBackpack(player, IronBackpacksHelper.getBackpack(player), IronBackpackType.values()[ID]), x, y, z);
        }
		return null;
	}
}
