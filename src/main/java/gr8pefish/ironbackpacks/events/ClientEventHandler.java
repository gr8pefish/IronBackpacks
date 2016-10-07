package gr8pefish.ironbackpacks.events;

import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.client.KeyHandler;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.server.ItemStackMessage;
import gr8pefish.ironbackpacks.network.server.SingleByteMessage;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

/**
 * Events that occur solely on the client side
 */
public class ClientEventHandler {


    /**
     * To handle keybindings
     * @param event - the key input event
     */
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) { //TODO: old keybinding event better so I can get player, then set stuff
        if (KeyHandler.OPEN_PACK.isPressed()) {
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK_KEYBINDING));
            return;
        }

        if (KeyHandler.EQUIP_PACK.isPressed())
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.EQUIP_BACKPACK_KEYBINDING));
    }

    /**
     * To handle right clicking backpacks to open them from any inventory
     * @param event - mouse event (filtered for right click)
     */
    @SubscribeEvent
    public void onGuiMouse(GuiScreenEvent.MouseInputEvent.Pre event){
        GuiScreen guiScreen = event.getGui();
        if (Mouse.getEventButtonState()) { //mouse clicked (i.e. not released)
            if (Mouse.getEventButton() == 1) { //right click only
                if (guiScreen instanceof GuiContainer) { //containers only
                    GuiContainer container = (GuiContainer) guiScreen;
                    Slot slot = container.getSlotUnderMouse();
                    if (slot != null && slot.getHasStack()) { //needs an item
                        ItemStack stack = slot.getStack();
                        if (stack.getItem() instanceof ItemBackpack) { //needs to be a backpack
                            NetworkingHandler.network.sendToServer(new ItemStackMessage(stack)); //open the backpack via pseudo-right click on server
                            event.setCanceled(true); //cancel pickup/further processing
                        }
                    }
                }
            }
        }
    }

}
