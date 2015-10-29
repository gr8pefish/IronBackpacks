
package main.ironbackpacks.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.client.gui.inventory.GUIBackpack;
import main.ironbackpacks.client.gui.inventory.GUIBackpackAlternate;
import main.ironbackpacks.container.alternateGui.InventoryAlternateGui;
import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class OpenGuiClientElement implements IMessage {

    private int ID;
    private ItemStack stack;

    public OpenGuiClientElement() {}

    public OpenGuiClientElement(int ID, ItemStack stack) {
        this.ID = ID;
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        ID = ByteBufUtils.readVarShort(buf);
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeVarShort(buf, ID);
        ByteBufUtils.writeItemStack(buf, stack);
    }

    public static class Handler implements IMessageHandler<OpenGuiClientElement, IMessage> {

        @Override
        public IMessage onMessage(OpenGuiClientElement message, MessageContext ctx) {

//            EntityPlayer player = Minecraft.getMinecraft().thePlayer; //this error: Attempted to load class net/minecraft/client/entity/EntityClientPlayerMP for invalid side SERVER
            System.out.println("opening pack on client via message"); //TODO: debug this, need to get the server side right too
            EntityPlayer player = IronBackpacks.proxy.getClientPlayer();

            if (message.ID >= 0) { //normal gui
                ItemStack backpack = message.stack; //need it from server side, not the client player
                int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack);
                Object guiContainer = GUIBackpack.GUI.buildGUI(player, new InventoryBackpack(player, backpack, IronBackpackType.values()[message.ID]), upgrades, backpack);
                FMLCommonHandler.instance().showGuiScreen(guiContainer);
            }else if (message.ID < 0){ //alternate gui
                ItemStack backpack = message.stack;
                int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack);
                Object guiContainer = GUIBackpackAlternate.GUI.buildGUIAlternate(player, new InventoryAlternateGui(player, backpack, IronBackpackType.values()[Math.abs((message.ID + 1))]), upgrades, IronBackpackType.values()[Math.abs((message.ID + 1))], backpack);
                FMLCommonHandler.instance().showGuiScreen(guiContainer);
            }
            return null;
        }

    }

}

//public class GuiHandler implements IGuiHandler {
//
//    @Override
//    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//
//    }
//
//    @Override
//    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//
//    }
//}
