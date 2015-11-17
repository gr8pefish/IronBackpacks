package main.ironbackpacks.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.IronBackpacks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
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

            EntityPlayer player = IronBackpacks.proxy.getClientPlayer(); //get the player via a safe call
            IronBackpacks.proxy.updateEquippedBackpack(player, message.stack); //update the backpack

            return null; //no return message
        }
    }
}
