package gr8pefish.ironbackpacks.client;

import gr8pefish.ironbackpacks.client.gui.inventory.GUIBackpack;
import gr8pefish.ironbackpacks.client.gui.inventory.GUIBackpackAlternate;
import gr8pefish.ironbackpacks.container.slot.GhostSlot;
import gr8pefish.ironbackpacks.integration.InterModSupport;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.server.PlayerSlotNumberMessage;
import gr8pefish.ironbackpacks.network.server.SingleByteMessage;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.Logger;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
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
    public void onGuiMouse(GuiScreenEvent.MouseInputEvent.Pre event){ //breaks it, but pre enable right click persisting through packs
        GuiScreen guiScreen = event.getGui();
        if (Mouse.getEventButtonState()) { //mouse clicked (i.e. not released)
            if (Mouse.getEventButton() == 1) { //right click only
                if (guiScreen instanceof GuiContainer) { //containers only
                    GuiContainer container = (GuiContainer) guiScreen;
                    if ( (!(container instanceof GuiContainerCreative)) && (!(container instanceof GUIBackpack)) ) { //exclude JEI and creative inventories //ToDo: Possibly remove compat from clientClickEvent in favor of containerBackpack
                        if (InterModSupport.isJEILoaded && (InterModSupport.isGuiContainerInstanceOfIGuiHelper(container))) return;
                        if (container instanceof GuiInventory) { //ToDo: Right now it's only 'e' inventory allowed
                            Slot slot = container.getSlotUnderMouse();
                            if (slot != null && slot.getHasStack()) { //needs an item
                                ItemStack stack = slot.getStack();
                                if (stack.getItem() instanceof ItemBackpack) { //needs to be a backpack
                                    NetworkingHandler.network.sendToServer(new PlayerSlotNumberMessage(slot.getSlotIndex(), Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? PlayerSlotNumberMessage.SNEAKING : PlayerSlotNumberMessage.NOT_SNEAKING)); //open the backpack via pseudo-right click on server
                                    event.setCanceled(true); //cancel pickup/further processing
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Cancel number keys moving a backpack into itself
     *
     * @param event - keyboard gui event
     */
    @SubscribeEvent
    public void onGuiKeyboard(GuiScreenEvent.KeyboardInputEvent.Pre event) {

        //fix key numbers to swap ruining things
        if (event.getGui() instanceof GUIBackpack) {
            KeyBinding[] hotbarBindings = Minecraft.getMinecraft().gameSettings.keyBindsHotbar;
            ItemStack openedPack = ((GUIBackpack) event.getGui()).container.getInventoryBackpack().getBackpackStack(); //access once here instead of in the loop
            if (!(hotbarBindings != null && hotbarBindings.length > 0)) return;
            try { //try catch because some setups can result in the IndexOOB crash
                for (int i = 0; i < hotbarBindings.length - 1; i++) { //-1 for more safety
                    if (Keyboard.isKeyDown(hotbarBindings[i].getKeyCode())) { //have to use Keyboard directly, instead of .isPressed()
                        if (IronBackpacksHelper.areItemStacksTheSame(Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i), openedPack)) { //can't move the same backpack you have open
                            event.setCanceled(true);
                            return;
                        }
                    }
                }
            } catch (IndexOutOfBoundsException exception) {
                Logger.error("Keybinds for hotbar not playing nicely with Iron Backpacks (disabled direct swapping of open bag to prevent a bug). Shouldn't be a big deal though.");
            }
        }

    }

    /**
     * Cancel scroll wheel when ghost slot inventory open to deal with InventorySorter.
     * Also cancel scrolling open backpack into itself
     *
     * @param event - the mouse input event
     */
    @SubscribeEvent
    public void onMouse(GuiScreenEvent.MouseInputEvent.Pre event) {
        if (event.getGui() instanceof GUIBackpackAlternate) { //only alternate backpack gui

            int wheelState = Mouse.getEventDWheel();
            boolean canceled = (wheelState != 0); //if mouse wheel value here isn't 0, then the mouse wheel was scrolled (-120 for down and 120 for up for me)

            //ToDo: occasionally dupes ghost items
            //The following allows you to rotate the advanced filter with the scroll wheel
            GUIBackpackAlternate gui = (GUIBackpackAlternate) event.getGui();
            if (gui.hasFilterAdvancedUpgrade && wheelState != 0){
                int x = Mouse.getEventX() * gui.width / gui.mc.displayWidth;
                int y = gui.height - Mouse.getEventY() * gui.height / gui.mc.displayHeight - 1;

                for (int i = 0; i < 9; i++) {
                    GhostSlot slot = (GhostSlot) gui.container.getSlot(gui.container.getFilterAdvSlotIdStart() + i);
                    if (gui.isMouseOverSlot(slot, x, y)) {
                        canceled = false; //could just cancel always, but seems to mess up filter items more often
                        if ((wheelState / 120) == 1) { //scroll up
                            gui.container.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_RIGHT);
                            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.MOVE_RIGHT));
                            gui.drawButtons();
                        } else { //scroll down
                            if (gui.container.getSlot(gui.container.getFilterAdvSlotIdStart() + i - 1).getHasStack()) canceled = true; //slot to the left has stack, so it will rotate then pull out with InvSorter
                            gui.container.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_LEFT);
                            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.MOVE_LEFT));
                            gui.drawButtons();
                        }
                    }
                }
            }
            event.setCanceled(canceled);

        // Cancel if trying to scroll wheel move the open backpack into itself
        } else if (event.getGui() instanceof GUIBackpack) {
            int wheelState = Mouse.getEventDWheel();
            if (wheelState != 0) { //if mouse wheel value here isn't 0, then the mouse wheel was scrolled (-120 for down and 120 for up for me)
                GUIBackpack gui = (GUIBackpack) event.getGui();
                if (wheelState != 0) {
                    int x = Mouse.getEventX() * gui.width / gui.mc.displayWidth;
                    int y = gui.height - Mouse.getEventY() * gui.height / gui.mc.displayHeight - 1;

                    for (int i = 0; i < 27; i++) {
                        Slot slot = gui.getSlotUnderMouse();
                        if (slot != null) {
                            if ((wheelState / 120) == 1) { //scroll up
                                if (slot.getHasStack() && IronBackpacksHelper.areItemStacksTheSame(slot.getStack(), gui.container.getInventoryBackpack().getBackpackStack())) { //if moving open backpack
                                    event.setCanceled(true); //cancel the move
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

}
