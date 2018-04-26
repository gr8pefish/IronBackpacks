package gr8pefish.ironbackpacks.network;

import gr8pefish.ironbackpacks.client.gui.GuiBackpack;
import gr8pefish.ironbackpacks.container.ContainerBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int OPEN_GUI_BACKPACK_ID = 0;
    public static final int OPEN_GUI_BACKPACK_EQUIPPED_ID = 1;

    static ItemStack equipped = ItemStack.EMPTY;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;

        switch (ID) {
            case OPEN_GUI_BACKPACK_ID: {
                ItemStack stack = player.getHeldItem(hand); //ToDo: Helper method (not necessarily held item)
                return new ContainerBackpack(player.inventory, hand, stack);
            }
            case OPEN_GUI_BACKPACK_EQUIPPED_ID: {
                if (equipped.isEmpty())
                    return null;

                return new ContainerBackpack(player.inventory, hand, equipped);
            }
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;

        switch (ID) {
            case OPEN_GUI_BACKPACK_ID: {
                ItemStack stack = player.getHeldItem(hand); //ToDo: Helper method (not necessarily held item)
                return new GuiBackpack(new ContainerBackpack(player.inventory, hand, stack));
            }
            case OPEN_GUI_BACKPACK_EQUIPPED_ID: {
                if (equipped.isEmpty())
                    return null;

                return new GuiBackpack(new ContainerBackpack(player.inventory, hand, equipped));
            }
        }

        return null;
    }

}