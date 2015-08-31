package main.ironbackpacks.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.IronBackpacks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ClientPackMessage implements IMessage {

    //the data sent
    private ItemStack stack;

    public ClientPackMessage() {} //default constructor is necessary

    public ClientPackMessage(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeItemStack(buf, stack);
    }

    public static class Handler implements IMessageHandler<ClientPackMessage, IMessage> {

        @Override
        public IMessage onMessage(ClientPackMessage message, MessageContext ctx) {
            //somehow get the sent pack to the GuiHandler
//            IronBackpacks.proxy.updateEquippedBackpack(Minecraft.getMinecraft().thePlayer, message.stack);
            System.out.println(ctx.side.toString());
            return null;
        }
    }
}
