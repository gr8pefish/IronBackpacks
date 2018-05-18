package gr8pefish.ironbackpacks.network;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import gr8pefish.ironbackpacks.capabilities.PlayerBackpackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public enum RequestAction {

    EQUIP_BACKPACK {
        @Override
        public void handle(NetHandlerPlayServer serverContext) {
            EntityPlayer player = serverContext.player;
            if (!player.hasCapability(IronBackpacksCapabilities.PLAYER_BACKPACK_HANDLER_CAPABILITY, null))
                return;

            PlayerBackpackHandler backpackHandler = player.getCapability(IronBackpacksCapabilities.PLAYER_BACKPACK_HANDLER_CAPABILITY, null);
            ItemStack equipped = backpackHandler.getEquippedBackpack();
            ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
            if (!equipped.isEmpty()) {
                ItemStack remainder = ItemHandlerHelper.insertItem(player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), equipped, false);
                if (remainder.isEmpty()) {
                    IronBackpacks.NETWORK.sendTo(new MessageSetEquippedBackpack(ItemStack.EMPTY), (EntityPlayerMP) player);
                    backpackHandler.setEquippedBackpack(ItemStack.EMPTY);
                }
            }

            if (!held.isEmpty() && held.getItem() instanceof IBackpack) {
                backpackHandler.setEquippedBackpack(held);
                IronBackpacks.NETWORK.sendTo(new MessageSetEquippedBackpack(backpackHandler.getEquippedBackpack()), (EntityPlayerMP) player);
                player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
            }
        }
    },
    OPEN_BACKPACK {
        @Override
        public void handle(NetHandlerPlayServer serverContext) {
            EntityPlayer player = serverContext.player;

            //return if no equipped pack possible (should never be the case)
            if (!player.hasCapability(IronBackpacksCapabilities.PLAYER_BACKPACK_HANDLER_CAPABILITY, null))
                return;

            //get the equipped backpack handler
            PlayerBackpackHandler backpackHandler = player.getCapability(IronBackpacksCapabilities.PLAYER_BACKPACK_HANDLER_CAPABILITY, null);

            //has a backpack equipped, open that (and update elsewhere)
            if (!backpackHandler.getEquippedBackpack().isEmpty()) {
                IronBackpacks.NETWORK.sendTo(new MessageSetEquippedBackpack(backpackHandler.getEquippedBackpack()), (EntityPlayerMP) player);
                GuiHandler.equipped = backpackHandler.getEquippedBackpack();
                player.openGui(IronBackpacks.MODID, GuiHandler.OPEN_GUI_BACKPACK_EQUIPPED_ID, player.getEntityWorld(), 1, 0, 0);

            //no backpack equipped, open any pack in the inventory
            } else {
                player.openGui(IronBackpacks.MODID, GuiHandler.OPEN_GUI_BACKPACK_INVENTORY_ID, player.getEntityWorld(), 1, 0, 0);
            }
        }
    },
    ;

    private static final RequestAction[] VALUES = values();

    public abstract void handle(NetHandlerPlayServer serverContext);

    @Nullable
    public static RequestAction getAction(int index) {
        if (index < 0 || index >= VALUES.length)
            return null;

        return VALUES[index];
    }
}
