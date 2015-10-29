package main.ironbackpacks.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.util.Logger;
import main.ironbackpacks.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * A message that contains a single byte as the data sent
 */
public class SingleByteMessage implements IMessage{

    //the data sent
    private byte action;

    public SingleByteMessage() {} //default constructor is necessary

    public SingleByteMessage(byte action) {
        this.action = action;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        action = (byte) ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeVarShort(buf, action);
    }

    public static class Handler implements IMessageHandler<SingleByteMessage, IMessage> {

        @Override
        public IMessage onMessage(SingleByteMessage message, MessageContext ctx) {

            ContainerBackpack container;
            ContainerAlternateGui altContainer;
            EntityPlayer player;

            switch (message.action) {
                case IronBackpacksConstants.Messages.SingleByte.BACKPACK_TO_INVENTORY:
                    container = (ContainerBackpack) ctx.getServerHandler().playerEntity.openContainer;
                    container.backpackToInventory();
                    break;
                case IronBackpacksConstants.Messages.SingleByte.INVENTORY_TO_BACKPACK:
                    container = (ContainerBackpack) ctx.getServerHandler().playerEntity.openContainer;
                    container.inventoryToBackpack();
                    break;
                case IronBackpacksConstants.Messages.SingleByte.HOTBAR_TO_BACKPACK:
                    container = (ContainerBackpack) ctx.getServerHandler().playerEntity.openContainer;
                    container.hotbarToBackpack();
                    break;
                case IronBackpacksConstants.Messages.SingleByte.SORT_BACKPACK:
                    container = (ContainerBackpack) ctx.getServerHandler().playerEntity.openContainer;
                    container.sort();
                    break;
                case IronBackpacksConstants.Messages.SingleByte.MOVE_LEFT:
                    altContainer = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
                    altContainer.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_LEFT);
                    break;
                case IronBackpacksConstants.Messages.SingleByte.MOVE_RIGHT:
                    altContainer = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
                    altContainer.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_RIGHT);
                    break;
                case IronBackpacksConstants.Messages.SingleByte.CLEAR_ROW_1:
                    altContainer = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
                    altContainer.removeSlotsInRow(1);
                    break;
                case IronBackpacksConstants.Messages.SingleByte.CLEAR_ROW_2:
                    altContainer = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
                    altContainer.removeSlotsInRow(2);
                    break;
                case IronBackpacksConstants.Messages.SingleByte.CLEAR_ROW_3:
                    altContainer = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
                    altContainer.removeSlotsInRow(3);
                    break;
                case IronBackpacksConstants.Messages.SingleByte.EQUIP_BACKPACK_KEYBINDING:
                    IronBackpacksHelper.equipBackpackFromKeybinding(ctx.getServerHandler().playerEntity);
                    break;
                case IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK_KEYBINDING:
                    player = ctx.getServerHandler().playerEntity;
                    ItemStack backpackStack = IronBackpacks.proxy.getEquippedBackpack(player);
//                    IronBackpacks.proxy.updateCurrBackpack(Minecraft.getMinecraft().thePlayer, backpackStack); //TODO: error here because doesn't update currPack right? can't update client on server packet
                    if (backpackStack != null) {
                        NBTHelper.setUUID(backpackStack);
                        IronBackpacks.proxy.updateCurrBackpack(player, backpackStack);
//                        System.out.println(player.isSneaking());
//                        System.out.println(Minecraft.getMinecraft().thePlayer.isSneaking());
                        backpackStack.useItemRightClick(player.worldObj, player);
                    }
                    break;
                default:
                    Logger.error("Error in sending message for Iron Backpacks in SingleByteMessage");
                    break;
            }
            return null; //no return message necessary
        }

    }
}
