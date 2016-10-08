package gr8pefish.ironbackpacks.network.server;

import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.client.ClientCurrentPackMessage;
import gr8pefish.ironbackpacks.util.NBTUtils;
import io.netty.buffer.ByteBuf;
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
    private int isSneaking;

    //for sneaking or not, sending as an integer rather than a bool
    public static int SNEAKING = 0;
    public static int NOT_SNEAKING = 1;

    public ItemStackMessage() {} //default constructor is necessary

    public ItemStackMessage(ItemStack stack, int isSneaking) {
        this.stack = stack;
        this.isSneaking = isSneaking;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        stack = ByteBufUtils.readItemStack(buf);
        isSneaking = ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeItemStack(buf, stack);
        ByteBufUtils.writeVarShort(buf, isSneaking);
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
                if (message.isSneaking == NOT_SNEAKING)
                    backpackStack.useItemRightClick(player.worldObj, player, EnumHand.MAIN_HAND); //normal right click open
                else
                    ((ItemBackpack)backpackStack.getItem()).handleBackpackOpening(backpackStack, ((EntityPlayerMP) player).worldObj, player, EnumHand.MAIN_HAND, true); //special sneak right click open
            }

            return null; //no return message
        }
    }
}
