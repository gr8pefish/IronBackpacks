package gr8pefish.ironbackpacks.network;

import gr8pefish.ironbackpacks.client.gui.GuiBackpack;
import gr8pefish.ironbackpacks.container.ContainerBackpack;
import gr8pefish.ironbackpacks.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int OPEN_GUI_BACKPACK_ID = 0;
    public static final int OPEN_GUI_BACKPACK_INVENTORY_ID = 1;
    public static final int OPEN_GUI_BACKPACK_EQUIPPED_ID = 2;

    static ItemStack equipped = ItemStack.EMPTY;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;

        switch (ID) {
            case OPEN_GUI_BACKPACK_ID: {
                //get which backpack to open (details in method called)
                ItemStack selected = Utils.getNonequippedBackpackFromInventory(player, hand);
                if (selected.isEmpty())
                    return null;

                return new ContainerBackpack(selected, player.inventory, hand);
            }
            case OPEN_GUI_BACKPACK_INVENTORY_ID: {
                //get which backpack to open (details in method called)
                ItemStack selected = Utils.getNonequippedBackpackFromInventory(player, hand);
                if (selected.isEmpty())
                    return null;

                return new ContainerBackpack(selected, player.inventory, null).setBlockedStack(selected);
            }
            case OPEN_GUI_BACKPACK_EQUIPPED_ID: {
                if (equipped.isEmpty())
                    return null;

                return new ContainerBackpack(equipped, player.inventory, hand);
            }
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;

        switch (ID) {
            case OPEN_GUI_BACKPACK_ID: {
                //get which backpack to open (details in method called)
                ItemStack selected = Utils.getNonequippedBackpackFromInventory(player, hand);
                if (selected.isEmpty())
                    return null;

                return new GuiBackpack(new ContainerBackpack(selected, player.inventory, hand));
            }
            case OPEN_GUI_BACKPACK_INVENTORY_ID: {
                //get which backpack to open (details in method called)
                ItemStack selected = Utils.getNonequippedBackpackFromInventory(player, hand);
                if (selected.isEmpty())
                    return null;

                return new GuiBackpack(new ContainerBackpack(selected, player.inventory, null).setBlockedStack(selected));
            }
            case OPEN_GUI_BACKPACK_EQUIPPED_ID: {
                if (equipped.isEmpty())
                    return null;

                return new GuiBackpack(new ContainerBackpack(equipped, player.inventory, hand));
            }
        }

        return null;
    }

}