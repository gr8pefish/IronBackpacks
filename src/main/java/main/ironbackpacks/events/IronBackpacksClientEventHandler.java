package main.ironbackpacks.events;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.client.KeyHandler;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.network.SingleByteMessage;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IronBackpacksClientEventHandler {

    /**
     * Handle the rendering of things (int his case entities)
     * @param event - the renderTick event
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderTickStart(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START && Minecraft.getMinecraft().theWorld != null) {
            preRenderTick(Minecraft.getMinecraft(), Minecraft.getMinecraft().theWorld, event.renderTickTime);
        }
    }

    /**
     * Before rendering call this method.
     * @param mc - minecraft
     * @param world - the world
     * @param renderTick - renderTick
     */
    @SideOnly(Side.CLIENT)
    private void preRenderTick(Minecraft mc, World world, float renderTick) {
        //make sure all backpacks to be rendered are valid
        EntityBackpack.updateBackpacks(world);
    }

    /**
     * Handle the keybindings from my mod. These can be found at the KeyHandler class (client package).
     * @param event - the key pressed
     */
    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event) {

        //open the equipped pack
        if (KeyHandler.OPEN_PACK.isPressed()) {

            //update the equipped backpack
            ItemStack backpackStack = IronBackpacks.proxy.getEquippedBackpack(Minecraft.getMinecraft().thePlayer);
            IronBackpacks.proxy.updateCurrBackpack(Minecraft.getMinecraft().thePlayer, backpackStack);

            //if sneaking only relevant once I get altGui keybindings working, hence why the if block has teh same code in each right now
            if (!(Minecraft.getMinecraft().thePlayer.isSneaking()))
                NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK_KEYBINDING));
            else
                NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK_KEYBINDING));

            //TODO: implement shift right click to open altGui of packs directly - doesn't work with alt gui upgrades
            //means you can alter this if(sneaking) block
//            if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed()){
//                NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK_ALT_KEYBINDING));
//            }

        // try to equip a pack
        }else if(KeyHandler.EQUIP_PACK.isPressed()) {
            //updates the server's saved data of which pack is equipped
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.EQUIP_BACKPACK_KEYBINDING));
        }
    }

}
