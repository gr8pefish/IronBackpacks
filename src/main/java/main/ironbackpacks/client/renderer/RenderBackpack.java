package main.ironbackpacks.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;


@SideOnly(Side.CLIENT)
public class RenderBackpack extends RenderPlayerEvent{

    public RenderBackpack(EntityPlayer player, RenderPlayer renderer, float partialRenderTick) {
        super(player, renderer, partialRenderTick);
    }

}