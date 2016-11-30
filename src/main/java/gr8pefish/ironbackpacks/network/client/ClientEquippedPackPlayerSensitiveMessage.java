package gr8pefish.ironbackpacks.network.client;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.capabilities.player.PlayerDeathBackpackCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientEquippedPackPlayerSensitiveMessage implements IMessage {

    //the data sent
    private ItemStack stack;
    private int entityID;

    public ClientEquippedPackPlayerSensitiveMessage() {} //default constructor is necessary

    public ClientEquippedPackPlayerSensitiveMessage(int entityID, ItemStack stack) {
        this.stack = stack;
        this.entityID = entityID;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        stack = ByteBufUtils.readItemStack(buf);
        entityID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeInt(entityID);
    }

    public static class Handler implements IMessageHandler<ClientEquippedPackPlayerSensitiveMessage, IMessage> {

        @Override
        public IMessage onMessage(ClientEquippedPackPlayerSensitiveMessage message, MessageContext ctx) {

            //have to use threading system since 1.8
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = IronBackpacks.proxy.getClientWorld();
                EntityPlayer targetEntity = (EntityPlayer) world.getEntityByID(message.entityID);
                if (targetEntity != null) {
                    PlayerWearingBackpackCapabilities.setEquippedBackpack(targetEntity, message.stack); //update the backpack
                    PlayerDeathBackpackCapabilities.setEquippedBackpack(targetEntity, null); //update death equipped to null in case too
                }
            });

            return null; //no return message
        }
    }
}
