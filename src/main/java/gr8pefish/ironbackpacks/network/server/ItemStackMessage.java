package gr8pefish.ironbackpacks.network.server;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.client.ClientCurrentPackMessage;
import gr8pefish.ironbackpacks.util.NBTUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ItemStackMessage implements IMessage {

    //the data sent
    private ItemStack stack;

    public ItemStackMessage() {} //default constructor is necessary

    public ItemStackMessage(ItemStack stack) {
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

    public static class Handler implements IMessageHandler<ItemStackMessage, IMessage> {

        @Override
        public IMessage onMessage(ItemStackMessage message, MessageContext ctx) {

            //set current backpack and right click it
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ItemStack backpackStack = message.stack;
            if (backpackStack != null) {
                NBTUtils.setUUID(backpackStack);
                PlayerWearingBackpackCapabilities.setCurrentBackpack(player, backpackStack);
                NetworkingHandler.network.sendTo(new ClientCurrentPackMessage(backpackStack), (EntityPlayerMP)player);
                backpackStack.useItemRightClick(player.worldObj, player, EnumHand.MAIN_HAND);
            }

            return null; //no return message
        }
    }
}
