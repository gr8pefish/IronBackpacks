package gr8pefish.ironbackpacks.proxies;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.client.ClientEventHandler;
import gr8pefish.ironbackpacks.client.KeyHandler;
import gr8pefish.ironbackpacks.client.renderer.LayerBackpack;
import gr8pefish.ironbackpacks.config.ConfigAdaptor;
import gr8pefish.ironbackpacks.registry.ProxyRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

/**
 * All the specifics that need to be called on the client side
 */
public class ClientProxy extends CommonProxy {

    public void preInit(){
        KeyHandler.init();
        initClientEventHandlers();

        ProxyRegistry.preInitClient();
    }

    public void init(){

        //initialize extra layer for rendering the backpack on the player
        addBackpackModelLayer();

        ProxyRegistry.initClient();
    }

    public void postInit(){
        ProxyRegistry.postInitClient();
    }
    
    public String translate(String langkey, Object... pars) {
    	return I18n.format(langkey, pars);
    }

    //============================================================Helper Methods===================================================================

    //helper init methods

    private void initClientEventHandlers(){
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    //random helper methods

    public String getModVersion(){
        return Constants.VERSION;
    }

    public String getRemoteUpdatedVersion(){
        return ConfigAdaptor.getLatestFilenameFromCurse(IronBackpacksConstants.Miscellaneous.URL_UPDATED_VERSION);
    }

    public World getClientWorld(){
        return Minecraft.getMinecraft().world;
    }

    public EntityPlayer getClientPlayer(){
        return Minecraft.getMinecraft().player;
    }

    /**
     * Add backpack layer via reflection.
     * Credit to TehNut for the base code.
     */
    private void addBackpackModelLayer() {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        try {
            RenderPlayer renderPlayer = ObfuscationReflectionHelper.getPrivateValue(RenderManager.class, renderManager, "playerRenderer", "field_178637_m");
            renderPlayer.addLayer(new LayerBackpack(renderPlayer));
            Logger.info("Added Layer Backpack");
        } catch (Exception e) {
            Logger.error("Failed to add Layer Backpack!");
            Logger.error(e.getLocalizedMessage());
        }
    }

}
