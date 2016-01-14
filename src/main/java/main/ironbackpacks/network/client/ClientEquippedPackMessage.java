package main.ironbackpacks.network.client;

import io.netty.buffer.ByteBuf;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.entity.extendedProperties.PlayerBackpackProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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
            if (player != null) {
                System.out.println("Client player: "+player.toString());
                PlayerBackpackProperties.setEquippedBackpack(player, message.stack); //update the backpack //TODO: client
            }

            return null; //no return message
        }
    }
}
