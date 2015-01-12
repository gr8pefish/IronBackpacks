package main.ironbackpacks.network;


import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import main.ironbackpacks.container.backpack.ContainerBackpack;

public class ButtonUpgradeMessage implements IMessage {

    private int action;

    public static final int BACKPACK_TO_INVENTORY = 1;
    public static final int INVENTORY_TO_BACKPACK = 2;
    public static final int HOTBAR_TO_BACKPACK = 3;
    public static final int CONDENSE_BACKPACK = 4;

    public static final int RENAME = 4;

    public ButtonUpgradeMessage() {}

    public ButtonUpgradeMessage(int action) {
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

    public static class Handler implements IMessageHandler<ButtonUpgradeMessage, IMessage> {

        @Override
        public IMessage onMessage(ButtonUpgradeMessage message, MessageContext ctx) {
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
                case CONDENSE_BACKPACK:
                    container = (ContainerBackpack) ctx.getServerHandler().playerEntity.openContainer;
                    System.out.println("SERVER SIDE");
                    container.condenseBackpack(ctx.getServerHandler().playerEntity);
                    break;
            }
            return null; //no return message necessary
        }

    }

}


