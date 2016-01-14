package main.ironbackpacks.client.renderer;

import main.ironbackpacks.entity.EntityBackpack;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityBackpackRenderFactory implements IRenderFactory<EntityBackpack>{

    @Override
    public Render<? super EntityBackpack> createRenderFor(RenderManager manager) {
        return new RenderEntityBackpack(manager);
    }
}
