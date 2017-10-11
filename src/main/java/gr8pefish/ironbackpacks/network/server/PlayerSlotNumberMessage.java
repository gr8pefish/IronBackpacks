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

public class PlayerSlotNumberMessage implements IMessage {

    //the data sent
    private int slotNumber;
    private int isSneaking;

    //for sneaking or not, sending as an integer rather than a bool
    public static int SNEAKING = 0;
    public static int NOT_SNEAKING = 1;

    public PlayerSlotNumberMessage() {} //default constructor is necessary

    public PlayerSlotNumberMessage(int slotNumber, int isSneaking) {
        this.slotNumber = slotNumber;
        this.isSneaking = isSneaking;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        slotNumber = ByteBufUtils.readVarShort(buf);
        isSneaking = ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeVarShort(buf, slotNumber);
        ByteBufUtils.writeVarShort(buf, isSneaking);
    }

    public static class Handler implements IMessageHandler<PlayerSlotNumberMessage, IMessage> {

        @Override
        public IMessage onMessage(PlayerSlotNumberMessage message, MessageContext ctx) {

            //set current backpack and right click it
            EntityPlayer player = ctx.getServerHandler().player;
            ItemStack backpackStack = player.inventory.getStackInSlot(message.slotNumber);
            if (backpackStack != null) {
                NBTUtils.setUUID(backpackStack);
                PlayerWearingBackpackCapabilities.setCurrentBackpack(player, backpackStack);
                NetworkingHandler.network.sendTo(new ClientCurrentPackMessage(backpackStack), (EntityPlayerMP)player);
                if (message.isSneaking == NOT_SNEAKING)
                    backpackStack.useItemRightClick(player.world, player, EnumHand.MAIN_HAND); //normal right click open
                else
                    ((ItemBackpack)backpackStack.getItem()).handleBackpackOpening(backpackStack, ((EntityPlayerMP) player).world, player, EnumHand.MAIN_HAND, true); //special sneak right click open
            }

            return null; //no return message
        }
    }
}
