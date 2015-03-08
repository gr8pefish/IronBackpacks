package main.ironbackpacks.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;

public class AdvFilterTypesMessage implements IMessage {
    //Messages sent from the gui that are only used if the backpack has the button upgrade

    private byte slot;
    private byte changeTo;

    public AdvFilterTypesMessage() {} //default constructor is necessary

    public AdvFilterTypesMessage(byte slot, byte changeTo) {
        this.slot = slot;
        this.changeTo = changeTo;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        slot = (byte)ByteBufUtils.readVarShort(buf);
        changeTo = (byte)ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeVarShort(buf, slot);
        ByteBufUtils.writeVarShort(buf, changeTo);
    }

    public static class Handler implements IMessageHandler<AdvFilterTypesMessage, IMessage> {

        @Override
        public IMessage onMessage(AdvFilterTypesMessage message, MessageContext ctx) {
            ContainerAlternateGui altContainer = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
//            System.out.println("LIST ENTRIES: "+list.get(0) + ", "+list.get(1));
            altContainer.setAdvFilterButtonType(message.slot, message.changeTo);
            return null; //no return message necessary
        }

    }
}

