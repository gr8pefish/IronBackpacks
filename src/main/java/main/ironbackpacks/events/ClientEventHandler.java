package main.ironbackpacks.events;

import main.ironbackpacks.client.KeyHandler;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.network.server.SingleByteMessage;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Events that occur solely on the client side
 */
public class ClientEventHandler {

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
     * To handle keybindings
     * @param event - the key input event
     */
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (KeyHandler.OPEN_PACK.isPressed()) {
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK_KEYBINDING));
            return;
        }

        if (KeyHandler.EQUIP_PACK.isPressed())
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.EQUIP_BACKPACK_KEYBINDING));
    }

}
