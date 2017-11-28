package gr8pefish.ironbackpacks.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRequestAction implements IMessage {

    private RequestAction action;

    public MessageRequestAction() {
    }

    public MessageRequestAction(RequestAction action) {
        this.action = action;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.action = RequestAction.getAction(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(action.ordinal());
    }

    public static class Handler implements IMessageHandler<MessageRequestAction, IMessage> {
        @Override
        public IMessage onMessage(MessageRequestAction message, MessageContext ctx) {
            if (message.action == null)
                return null;

            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> message.action.handle(ctx.getServerHandler()));
            return null;
        }
    }
}
