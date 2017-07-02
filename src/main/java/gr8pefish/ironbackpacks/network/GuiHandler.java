package gr8pefish.ironbackpacks.network;

import gr8pefish.ironbackpacks.api.BackpackInfo;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.container.BackpackContainer;
import gr8pefish.ironbackpacks.container.GuiBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class GuiHandler implements IGuiHandler {

    public static final int OPEN_GUI_BACKPACK_ID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;

        switch(ID) {
            case OPEN_GUI_BACKPACK_ID:
                ItemStack stack = player.getHeldItem(hand); //ToDo: Helper method (not necessarily held item)
                IItemHandlerModifiable inventory = (IItemHandlerModifiable) stack.getCapability(IronBackpacksAPI.BACKPACK_INV_CAPABILITY, null).getInventory(BackpackInfo.fromStack(stack).getVariant());
                return new BackpackContainer(player.inventory, hand, inventory);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;

        switch(ID) {
            case OPEN_GUI_BACKPACK_ID:
                ItemStack stack = player.getHeldItem(hand); //ToDo: Helper method (not necessarily held item)
                IItemHandlerModifiable inventory = (IItemHandlerModifiable) stack.getCapability(IronBackpacksAPI.BACKPACK_INV_CAPABILITY, null).getInventory(BackpackInfo.fromStack(stack).getVariant());
                return new GuiBackpack(player.inventory, hand, inventory);

        }

        return null;
    }

}