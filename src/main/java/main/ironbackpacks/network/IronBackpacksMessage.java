package main.ironbackpacks.network;


import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.container.ContainerBackpack;

public class IronBackpacksMessage implements IMessage {

    int action;

    public static final int BACKPACK_TO_INVENTORY = 1;
    public static final int INVENTORY_TO_BACKPACK = 2;
    public static final int HOTBAR_TO_BACKPACK = 3;

    public IronBackpacksMessage() {}

    public IronBackpacksMessage(int action) {
        this.action = action;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        action = ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeVarShort(buf, action);
    }

    public static class Handler implements IMessageHandler<IronBackpacksMessage, IMessage> {

        @Override
        public IMessage onMessage(IronBackpacksMessage message, MessageContext ctx) {
            ContainerBackpack container;
            switch (message.action) {
                case BACKPACK_TO_INVENTORY:
                    container = (ContainerBackpack) ctx.getServerHandler().playerEntity.openContainer;
                    container.backpackToInventory();
                    break;
                case INVENTORY_TO_BACKPACK:
                    container = (ContainerBackpack) ctx.getServerHandler().playerEntity.openContainer;
                    container.inventoryToBackpack();
                    break;
                case HOTBAR_TO_BACKPACK:
                    container = (ContainerBackpack) ctx.getServerHandler().playerEntity.openContainer;
                    container.hotbarToBackpack();
                    break;
            }
            return null; //no return message necessary
        }

    }

}


