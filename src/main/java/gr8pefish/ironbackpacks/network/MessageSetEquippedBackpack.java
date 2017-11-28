package gr8pefish.ironbackpacks.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSetEquippedBackpack implements IMessage {

    private ItemStack backpack;

    public MessageSetEquippedBackpack() {

    }

    public MessageSetEquippedBackpack(ItemStack backpack) {
        this.backpack = backpack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        backpack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, backpack);
    }

    public static class Handler implements IMessageHandler<MessageSetEquippedBackpack, IMessage> {
        @Override
        public IMessage onMessage(MessageSetEquippedBackpack message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> GuiHandler.equipped = message.backpack);
            return null;
        }
    }
}
