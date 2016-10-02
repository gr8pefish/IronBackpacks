package gr8pefish.ironbackpacks.events;

import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.client.KeyHandler;
import gr8pefish.ironbackpacks.client.renderer.LayerBackpack;
import gr8pefish.ironbackpacks.entity.EntityBackpack;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.server.SingleByteMessage;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Events that occur solely on the client side
 */
public class ClientEventHandler {

//    /**
//     * Handle the rendering of things (int his case entities)
//     * @param event - the renderTick event
//     */
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public void onRenderTickStart(TickEvent.RenderTickEvent event) {
//        if (event.phase == TickEvent.Phase.START && Minecraft.getMinecraft().theWorld != null) {
//            preRenderTick(Minecraft.getMinecraft(), Minecraft.getMinecraft().theWorld, event.renderTickTime);
//        }
//    }
//
//    /**
//     * Before rendering call this method.
//     * @param mc - minecraft
//     * @param world - the world
//     * @param renderTick - renderTick
//     */
//    @SideOnly(Side.CLIENT)
//    private void preRenderTick(Minecraft mc, World world, float renderTick) {
//        //make sure all backpacks to be rendered are valid
//
//        //add a scheduled task so there is no concurrency modification exception due to the client and server both trying to modify the entityBackpack map
//        mc.addScheduledTask(() -> {
//            EntityPlayer player1 = mc.thePlayer;
//            ItemStack backpack = PlayerWearingBackpackCapabilities.getEquippedBackpack(player1);
//            if (backpack != null){
//                EntityBackpack pack = EntityBackpack.getEntityBackpack(backpack);
//                if (pack != null) {
//                    if (EntityBackpack.isBackpackValid(player1, pack)) pack.fixPositions(player1, true);
//                    else pack.setDead();
//                }
//            }
//        });
//    }

//    @SubscribeEvent
//    public void renderPlayer(RenderPlayerEvent.Pre event) {
//
//        EntityPlayer player = event.getEntityPlayer();
//        ItemStack backpack = PlayerWearingBackpackCapabilities.getEquippedBackpack(player);
//        if (backpack != null) {
////            renderBackpack(player, backpack);
//            event.getRenderer().addLayer(new LayerBackpack(event.getRenderer()));
//        }
//    }

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

}
