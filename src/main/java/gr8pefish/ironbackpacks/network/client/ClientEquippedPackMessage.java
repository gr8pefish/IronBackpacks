package gr8pefish.ironbackpacks.network.client;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.entity.extendedProperties.PlayerBackpackProperties;
import gr8pefish.ironbackpacks.util.Logger;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientEquippedPackMessage implements IMessage {

    //the data sent
    private ItemStack stack;

    public ClientEquippedPackMessage() {} //default constructor is necessary

    public ClientEquippedPackMessage(ItemStack stack) {
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

    public static class Handler implements IMessageHandler<ClientEquippedPackMessage, IMessage> {

        @Override
        public IMessage onMessage(ClientEquippedPackMessage message, MessageContext ctx) {

            EntityPlayer player = IronBackpacks.proxy.getClientPlayer();
            Logger.info("getting player on client side?");
//            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                if (message.stack != null) Logger.info("setting client equipped "+message.stack.toString()); else Logger.info("setting client null");
                PlayerBackpackProperties.setEquippedBackpack(player, message.stack); //update the backpack //TODO: client
            }

            return null; //no return message
        }
    }
}
