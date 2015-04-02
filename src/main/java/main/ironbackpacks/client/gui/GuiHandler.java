package main.ironbackpacks.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import main.ironbackpacks.client.gui.inventory.GUIBackpack;
import main.ironbackpacks.client.gui.inventory.GUIBackpackAlternate;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import main.ironbackpacks.container.alternateGui.InventoryAlternateGui;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    //called from onItemRightClick in ItemBaseBackpack

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID >=0){ //normal gui
            IronBackpackType type = IronBackpackType.values()[ID];
//            System.out.println("SERVER PACK");
            return new ContainerBackpack(player, new InventoryBackpack(player, IronBackpacksHelper.getBackpack(player), type), type);
        }else if (ID < 0){ //alternate gui
            IronBackpackType type = IronBackpackType.values()[Math.abs(ID + 1)];
            return new ContainerAlternateGui(player, new InventoryAlternateGui(player, IronBackpacksHelper.getBackpack(player), type));
        }
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID >= 0) { //normal gui
//            System.out.println("CLIENT PACK");
            ItemStack backpack = IronBackpacksHelper.getBackpack(player); //need it from server side
            int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack);
            return GUIBackpack.GUI.buildGUI(player, new InventoryBackpack(player, backpack, IronBackpackType.values()[ID]), upgrades, backpack);
        }else if (ID < 0){ //alternate gui
            ItemStack backpack = IronBackpacksHelper.getBackpack(player);
            int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack);
            return GUIBackpackAlternate.GUI.buildGUIAlternate(player, new InventoryAlternateGui(player, backpack, IronBackpackType.values()[Math.abs((ID + 1))]), upgrades, IronBackpackType.values()[Math.abs((ID + 1))]);
        }
		return null;
	}
}
