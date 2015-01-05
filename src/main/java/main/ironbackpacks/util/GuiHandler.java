package main.ironbackpacks.util;

import cpw.mods.fml.common.network.IGuiHandler;
import main.ironbackpacks.client.gui.inventory.GUIBackpack;
import main.ironbackpacks.container.ContainerBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.container.InventoryBackpack;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
            ItemStack backpack = IronBackpacksHelper.getBackpack(player);
            int[] upgrades = ((ItemBaseBackpack)backpack.getItem()).getUpgradesFromNBT(backpack);
            return GUIBackpack.GUI.buildGUI(player, new InventoryBackpack(player, IronBackpacksHelper.getBackpack(player), IronBackpackType.values()[ID]), upgrades);
        }
		return null;
	}
}
