package main.ironbackpacks.proxies;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.client.renderer.RenderBackpack;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.handlers.ConfigAdaptor;
import main.ironbackpacks.handlers.KeybindingHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

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

    public String getModVersion(){
        return ModInformation.VERSION;
    }

    public String getRemoteUpdatedVersion(){
        return ConfigAdaptor.getLatestFilenameFromCurse(IronBackpacksConstants.Miscellaneous.URL_UPDATED_VERSION);
    }

    public void initKeybindings() {
        keybindingHandler = new KeybindingHandler();
        keybindingHandler.init();
    }

    //TODO: rendering model
    public void initRenderers(){
        RenderingRegistry.registerEntityRenderingHandler(EntityBackpack.class, new RenderBackpack());
    }

}
