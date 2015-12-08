package main.ironbackpacks.network;

import io.netty.buffer.ByteBuf;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.entity.PlayerBackpackProperties;
import main.ironbackpacks.items.backpacks.IBackpack;
import main.ironbackpacks.proxies.CommonProxy;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.Logger;
import main.ironbackpacks.util.NBTHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * A message that contains a single byte as the data sent
 */
public class SingleByteMessage implements IMessage {

    //the data sent
    private byte action;

    public SingleByteMessage() {
    } //default constructor is necessary

    public SingleByteMessage(byte action) {
        this.action = action;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        action = (byte) ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
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
                case IronBackpacksConstants.Messages.SingleByte.EQUIP_BACKPACK:
                    player = ctx.getServerHandler().playerEntity;

                    if (PlayerBackpackProperties.getEquippedBackpack(player) == null) {
                        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IBackpack) {
                            PlayerBackpackProperties.setEquippedBackpack(player, player.getHeldItem());
                            player.inventory.decrStackSize(player.inventory.currentItem, 1);
                        }
                    } else {
                        player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, PlayerBackpackProperties.getEquippedBackpack(player)));
                        PlayerBackpackProperties.reset(player);
                    }
                    break;
                case IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK:
                    player = ctx.getServerHandler().playerEntity;

                    ItemStack backpack = PlayerBackpackProperties.get(player).getEquippedBackpack();

                    if (backpack != null) {
                        NBTHelper.setUUID(backpack);
                        CommonProxy.updateCurrBackpack(player, backpack);
                        backpack.useItemRightClick(player.worldObj, player);
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
