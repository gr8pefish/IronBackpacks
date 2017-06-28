package gr8pefish.ironbackpacks.network;

import gr8pefish.ironbackpacks.container.ContainerBackpack;
import gr8pefish.ironbackpacks.container.GuiBackpack;
import gr8pefish.ironbackpacks.container.InventoryBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int OPEN_GUI_BACKPACK_ID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;

        switch(ID) {
            case OPEN_GUI_BACKPACK_ID:
                return new ContainerBackpack(player.inventory, new InventoryBackpack(player.getHeldItem(hand))); //ToDo: Helper method (not necessarily held item)
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;

        switch(ID) {
            case OPEN_GUI_BACKPACK_ID:
                return new GuiBackpack(player.inventory, new InventoryBackpack(player.getHeldItem(hand)));
        }

        return null;
    }

}