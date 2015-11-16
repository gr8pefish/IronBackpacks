package main.ironbackpacks.client.renderer;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class RenderBackpack extends RenderPlayerEvent {

    public RenderBackpack(EntityPlayer player, RenderPlayer renderer, float partialRenderTick, double x, double y, double z) {
        super(player, renderer, partialRenderTick, x, y, z);
    }

}