package main.ironbackpacks.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.client.Minecraft;
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

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        ItemStack backpack = IronBackpacks.proxy.getEquippedBackpack(event.player);
        if (!EntityBackpack.backpacksSpawnedMap.containsKey(event.player) && backpack != null) {
            IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event){
        ItemStack backpack = IronBackpacks.proxy.getEquippedBackpack(event.player);
        if (!EntityBackpack.backpacksSpawnedMap.containsKey(event.player) && backpack != null) {
            IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
        }
    }

    @SubscribeEvent
    public void onPlayerDimChange(PlayerEvent.PlayerChangedDimensionEvent event){
        ItemStack backpack = IronBackpacks.proxy.getEquippedBackpack(event.player);
        if (!EntityBackpack.backpacksSpawnedMap.containsKey(event.player) && backpack != null) {
            IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
        }
    }

}
