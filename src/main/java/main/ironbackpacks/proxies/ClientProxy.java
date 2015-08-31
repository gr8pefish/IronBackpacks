package main.ironbackpacks.proxies;

import cpw.mods.fml.client.registry.RenderingRegistry;
import main.ironbackpacks.client.renderer.RenderBackpack;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.util.KeybindingHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * The client proxy
 */
public class ClientProxy extends CommonProxy {

    public static KeybindingHandler keybindingHandler;

    public EntityPlayer getClientPlayer(){
        return Minecraft.getMinecraft().thePlayer;
    }

    public void init(){
        initKeybindings();
        initRenderers();
    }

    public void initKeybindings() {
        keybindingHandler = new KeybindingHandler();
        keybindingHandler.init();
    }

    public void initRenderers(){
        RenderingRegistry.registerEntityRenderingHandler(EntityBackpack.class, new RenderBackpack());
    }

}
