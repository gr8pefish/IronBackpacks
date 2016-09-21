//package gr8pefish.ironbackpacks.network.server;
//
//import io.netty.buffer.ByteBuf;
//import net.minecraft.client.Minecraft;
//import net.minecraft.server.MinecraftServer;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class BackpackCapabilityMessage implements IMessage
//{
//    public int side;
//
//    public BackpackCapabilityMessage() {}
//
//    public BackpackCapabilityMessage(int side)
//    {
//        this.side = side;
//    }
//
//    @Override
//    public void fromBytes(ByteBuf buf)
//    {
//        this.side = buf.readInt();
//    }
//
//    @Override
//    public void toBytes(ByteBuf buf)
//    {
//        buf.writeInt(this.side);
//    }
//
//    @SideOnly(Side.SERVER)
//    public static class ServerHandler implements IMessageHandler<BackpackCapabilityMessage, IMessage>
//    {
//
//        @Override
//        public IMessage onMessage(BackpackCapabilityMessage message, MessageContext ctx) {
//            //MinecraftServer.getServer().addScheduledTask(new ScheduledPacketTask(ctx.getServerHandler().playerEntity, message));
//            return null;
//        }
//
//    }
//
//    @SideOnly(Side.CLIENT)
//    public static class ClientHandler implements IMessageHandler<BackpackCapabilityMessage, IMessage>
//    {
//
//        @Override
//        public IMessage onMessage(BackpackCapabilityMessage message, MessageContext ctx)
//        {
//            //Minecraft.getMinecraft().addScheduledTask(new ScheduledPacketTask(null, message));
//            return null;
//        }
//
//    }
//
//}
