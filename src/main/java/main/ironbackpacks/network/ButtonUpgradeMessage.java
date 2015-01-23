package main.ironbackpacks.network;


import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import main.ironbackpacks.container.backpack.ContainerBackpack;

public class ButtonUpgradeMessage implements IMessage {

    //Messages sent from the gui that are only used if the backpack has the button upgrade

    private int action;

    public static final int BACKPACK_TO_INVENTORY = 11;
    public static final int INVENTORY_TO_BACKPACK = 12;
    public static final int HOTBAR_TO_BACKPACK = 13;
    public static final int CONDENSE_BACKPACK = 14;

//    public static final int CLEAR_ROW = 5;

    public ButtonUpgradeMessage() {} //default constructor is necessary

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
            ContainerAlternateGui altContainer;
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
                    container.sort();
                    break;
                default: //1,2,3,4 for button in alt gui remove slots in row
                    altContainer = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
                    altContainer.removeSlotsInRow(message.action);
                    break;
            }
            return null; //no return message necessary
        }

    }

}


