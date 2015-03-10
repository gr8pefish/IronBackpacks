package main.ironbackpacks.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import main.ironbackpacks.proxies.CommonProxy;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class UpdateBackpackMessage implements IMessage {

    //Message for the renaming upgrade

    private ItemStack newPack;

    public UpdateBackpackMessage() {}

    public UpdateBackpackMessage(ItemStack newPack) {
        this.newPack = newPack;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        newPack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeItemStack(buf, newPack);
    }

    public static class Handler implements IMessageHandler<UpdateBackpackMessage, IMessage> {

        @Override
        public IMessage onMessage(UpdateBackpackMessage message, MessageContext ctx) {
            CommonProxy.updateCurrBackpack(ctx.getServerHandler().playerEntity, message.newPack);
            return null; //no return message necessary
        }

    }

}
