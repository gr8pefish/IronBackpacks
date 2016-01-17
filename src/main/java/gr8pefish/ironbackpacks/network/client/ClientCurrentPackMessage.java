package gr8pefish.ironbackpacks.network.client;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.entity.extendedProperties.PlayerBackpackProperties;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientCurrentPackMessage implements IMessage {

    //the data sent
    private ItemStack stack;

    public ClientCurrentPackMessage() {} //default constructor is necessary

    public ClientCurrentPackMessage(ItemStack stack) {
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

    public static class Handler implements IMessageHandler<ClientCurrentPackMessage, IMessage> {

        @Override
        public IMessage onMessage(ClientCurrentPackMessage message, MessageContext ctx) {

            EntityPlayer player = IronBackpacks.proxy.getClientPlayer();
            if (player != null) {
                PlayerBackpackProperties.setCurrentBackpack(player, message.stack); //update the backpack //TODO: client and this is just bad code
            }

            return null; //no return message
        }
    }
}

