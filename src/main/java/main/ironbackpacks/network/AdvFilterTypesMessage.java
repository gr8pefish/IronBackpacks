package main.ironbackpacks.network;

import io.netty.buffer.ByteBuf;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Message used for the advanced filter upgrade to rotate through its shown slots
 */
public class AdvFilterTypesMessage implements IMessage {

    private byte slot;
    private byte changeTo;

    public AdvFilterTypesMessage() {
    } //default constructor is necessary

    public AdvFilterTypesMessage(byte slot, byte changeTo) {
        this.slot = slot;
        this.changeTo = changeTo;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        slot = (byte) ByteBufUtils.readVarShort(buf);
        changeTo = (byte) ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarShort(buf, slot);
        ByteBufUtils.writeVarShort(buf, changeTo);
    }

    public static class Handler implements IMessageHandler<AdvFilterTypesMessage, IMessage> {

        @Override
        public IMessage onMessage(AdvFilterTypesMessage message, MessageContext ctx) {
            ContainerAlternateGui altContainer = (ContainerAlternateGui) ctx.getServerHandler().playerEntity.openContainer;
            altContainer.setAdvFilterButtonType(message.slot, message.changeTo);
            return null; //no return message necessary
        }

    }
}

