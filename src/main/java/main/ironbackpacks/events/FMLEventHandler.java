package main.ironbackpacks.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.network.ClientPackMessage;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FMLEventHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderTickStart(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START && Minecraft.getMinecraft().theWorld != null) {
            preRenderTick(Minecraft.getMinecraft(), Minecraft.getMinecraft().theWorld, event.renderTickTime);
        }
    }

    @SideOnly(Side.CLIENT)
    private void preRenderTick(Minecraft mc, World world, float renderTick) {
        EntityBackpack.updateBackpacks(world);
    }

    /**
     * Used to make sure the player's equipped backpack is shown correctly
     * @param event - the player logged in event
     */
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        ItemStack backpack = IronBackpacks.proxy.getEquippedBackpack(event.player);
        if (!EntityBackpack.backpacksSpawnedMap.containsKey(event.player) && backpack != null) {

            NetworkingHandler.network.sendTo(new ClientPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
            IronBackpacks.proxy.updateEquippedBackpack(event.player, backpack); //update server on correct pack

            if (!ConfigHandler.disableRendering)
                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
        }
    }

    /**
     * Used to make sure the player respawns with an equipped backpack if they should
     * @param event - the player respawn event
     */
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event){
        ItemStack backpack = IronBackpacks.proxy.getEquippedBackpack(event.player);
        if (!EntityBackpack.backpacksSpawnedMap.containsKey(event.player) && backpack != null) {

            NetworkingHandler.network.sendTo(new ClientPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
            IronBackpacks.proxy.updateEquippedBackpack(event.player, backpack); //update server on correct pack

            if (!ConfigHandler.disableRendering)
                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
        }
    }

    /**
     * Used to make sure the equipped backpack transfers over correctly between dimensions
     * @param event - the change dimension event
     */
    @SubscribeEvent
    public void onPlayerDimChange(PlayerEvent.PlayerChangedDimensionEvent event){ //TODO: test more
        ItemStack backpack = IronBackpacks.proxy.getEquippedBackpack(event.player);
        if (backpack != null) {
            if (EntityBackpack.backpacksSpawnedMap.containsKey(event.player)) {//if has old dimension backpack
                if (EntityBackpack.backpacksSpawnedMap.get(event.player) != null) //possible if config option disabled rendering
                    EntityBackpack.backpacksSpawnedMap.get(event.player).setDead(); //kill old backpack
            }

            NetworkingHandler.network.sendTo(new ClientPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
            IronBackpacks.proxy.updateEquippedBackpack(event.player, backpack); //update server on correct pack

            if (!ConfigHandler.disableRendering)
                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player); //spawn new pack
        }
    }

}
