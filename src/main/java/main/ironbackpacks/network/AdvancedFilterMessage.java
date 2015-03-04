package main.ironbackpacks.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.util.IronBackpacksHelper;

import java.util.ArrayList;

public class AdvancedFilterMessage implements IMessage{

    //Messages sent from the gui that are only used if the backpack has the advanced filter upgrade

    private int action;

    public static final int MOVE_LEFT = 5;
    public static final int MOVE_RIGHT = 6;


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
                default: //to change button status with it's index
                    ArrayList<Integer> list = IronBackpacksHelper.getNumbersFromOneNumber(message.action);
                    container = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
                    System.out.println("LIST ENTRIES: "+list.get(0) + ", "+list.get(1));
                    container.setAdvFilterButtonType(list.get(0), list.get(1));
//                    IronBackpacksHelper.setAdvFilterTypeToNBT();

            }
            return null; //no return message necessary
        }

    }

}
