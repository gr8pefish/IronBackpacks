package main.ironbackpacks.handlers;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.network.SingleByteMessage;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
//            if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed()){ //TODO: implement shift right click to open altGui of packs directly - doesn't work with alt gui upgrades
//                NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK_ALT_KEYBINDING));
//            }
        }else if(equipBackpack.isPressed()) {

            //TODO: try updating client pack directly here

            //updates the server's saved data of which pack is equipped
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.EQUIP_BACKPACK_KEYBINDING));
        }
    }


}