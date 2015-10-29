package main.ironbackpacks.util;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.network.SingleByteMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeybindingHandler {

    public static KeyBinding openBackpack = new KeyBinding("key_ironbackpacks_openbackpack", Keyboard.KEY_R, "Iron Backpacks");
    public static KeyBinding equipBackpack = new KeyBinding("key_ironbackpacks_equipbackpack", Keyboard.KEY_B, "Iron Backpacks");

    public void init() {
        ClientRegistry.registerKeyBinding(openBackpack);
        ClientRegistry.registerKeyBinding(equipBackpack);
        FMLCommonHandler.instance().bus().register(this);
    }


    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event) {
        if (openBackpack.isPressed()) {
            ItemStack backpackStack = IronBackpacks.proxy.getEquippedBackpack(Minecraft.getMinecraft().thePlayer);
            IronBackpacks.proxy.updateCurrBackpack(Minecraft.getMinecraft().thePlayer, backpackStack);
            if (!(Minecraft.getMinecraft().thePlayer.isSneaking())) //TODO: remove once i can open alt gui from right click
                NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK_KEYBINDING));
            else
                NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK_KEYBINDING));
//            if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed()){
//                NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK_ALT_KEYBINDING));
//            }
        }else if(equipBackpack.isPressed()) {

//            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
//            ItemStack backpack = IronBackpacks.proxy.getEquippedBackpack(player);
//
//            if (backpack != null) {
//
//                boolean hasEmptySlot = false;
//                for (int i = 0; i < player.inventory.getSizeInventory() - 4; i++) { //don't care about armor slots
//                    if (player.inventory.getStackInSlot(i) == null) hasEmptySlot = true;
//                }
//
//                if (hasEmptySlot) {
//                    //update equipped backpack to null
//                    IronBackpacks.proxy.updateEquippedBackpack(player, null);
//                }
//
//            } else if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBaseBackpack) {
//
//                ItemStack backpackStack = player.getHeldItem();
//                NBTHelper.setUUID(backpackStack);
//
//                //equip backpack from the backpack the player is holding
//                IronBackpacks.proxy.updateEquippedBackpack(player, backpackStack);
//            }

            //TODO: tell client which backpack is the current one / change it so client doesn't care about current one, just gets it from server
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.EQUIP_BACKPACK_KEYBINDING));
        }
    }


}