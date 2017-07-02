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
//                EnumDyeColor color = EnumDyeColor.byMetadata(player.getHeldItem(hand).getItemDamage());
//                IItemHandlerModifiable inventory = (IItemHandlerModifiable) player.getCapability(ProjectEAPI.ALCH_BAG_CAPABILITY, null).getBag(color);
//                return new AlchBagContainer(player.inventory, hand, inventory);
//            }
                ItemStack stack = player.getHeldItem(hand);
                IItemHandlerModifiable inventory = (IItemHandlerModifiable) stack.getCapability(IronBackpacksAPI.BACKPACK_INV_CAPABILITY, null).getInventory(BackpackInfo.fromStack(stack).getVariant());
                return new BackpackContainer(player.inventory, hand, inventory); //ToDo: Helper method (not necessarily held item)
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;

        switch(ID) {
            case OPEN_GUI_BACKPACK_ID:
//                EnumDyeColor color = EnumDyeColor.byMetadata(player.getHeldItem(hand).getItemDamage());
//                IItemHandlerModifiable inventory = (IItemHandlerModifiable) player.getCapability(ProjectEAPI.ALCH_BAG_CAPABILITY, null).getBag(color);
//                return new GUIAlchChest(player.inventory, hand, inventory);
//            }
                ItemStack stack = player.getHeldItem(hand);
                IItemHandlerModifiable inventory = (IItemHandlerModifiable) stack.getCapability(IronBackpacksAPI.BACKPACK_INV_CAPABILITY, null).getInventory(BackpackInfo.fromStack(stack).getVariant());
                return new GuiBackpack(player.inventory, hand, inventory); //ToDo: Helper method (not necessarily held item)
//                return new GuiBackpack(player.inventory, new InventoryBackpack(player.getHeldItem(hand)));
        }

        return null;
    }

}