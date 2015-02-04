package main.ironbackpacks.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import main.ironbackpacks.container.backpack.ContainerBackpack;

public class AdvancedFilterMessage implements IMessage{

    //Messages sent from the gui that are only used if the backpack has the advanced filter upgrade

    private int action;

    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;


    public AdvancedFilterMessage() {} //default constructor is necessary

    public AdvancedFilterMessage(int action) {
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

    public static class Handler implements IMessageHandler<AdvancedFilterMessage, IMessage> {

        @Override
        public IMessage onMessage(AdvancedFilterMessage message, MessageContext ctx) {
            ContainerAlternateGui container;
            switch (message.action) {
                case MOVE_LEFT:
                    container = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
                    container.changeAdvFilterSlots("left");
                    break;
                case MOVE_RIGHT:
                    container = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
                    container.changeAdvFilterSlots("right");
                    break;
//                case HOTBAR_TO_BACKPACK:
//                    container = (ContainerBackpack) ctx.getServerHandler().playerEntity.openContainer;
//                    container.hotbarToBackpack();
//                    break;
//                case CONDENSE_BACKPACK:
//                    container = (ContainerBackpack) ctx.getServerHandler().playerEntity.openContainer;
//                    container.sort();
//                    break;
//                default: //1,2,3,4 for button in alt gui remove slots in row
//                    altContainer = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
//                    altContainer.removeSlotsInRow(message.action);
//                    break;
            }
            return null; //no return message necessary
        }

    }

}
