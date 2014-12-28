package main.ironbackpacks.util;

import cpw.mods.fml.common.network.IGuiHandler;
import main.ironbackpacks.client.gui.BackpackGui;
import main.ironbackpacks.container.ContainerBackpack;
import main.ironbackpacks.inventory.InventoryBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) { //TODO change IDs to an ENUM with each id linked to each backpack tier's item
            return null;
        }else if (ID == 1){
            return new ContainerBackpack(player, new InventoryBackpack(player, IronBackpacksHelper.getBackpack(player)));
        }
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) {
            return null;
        }else if (ID == 1){
            return new BackpackGui(new ContainerBackpack(player, new InventoryBackpack(player, IronBackpacksHelper.getBackpack(player))));
        }
		return null;
	}
}
