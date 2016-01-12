package main.ironbackpacks.network;

import io.netty.buffer.ByteBuf;
import main.ironbackpacks.IronBackpacks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
//            System.out.println("player: "+player.getName());
            if (player != null) {
                IronBackpacks.proxy.updateEquippedBackpack(player, message.stack); //update the backpack
            }else{
                System.out.println("NULL PLAYER");
            }

            return null; //no return message
        }
    }
}
