package gr8pefish.ironbackpacks.proxies;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.client.KeyHandler;
import gr8pefish.ironbackpacks.client.renderer.EntityBackpackRenderFactory;
import gr8pefish.ironbackpacks.config.ConfigAdaptor;
import gr8pefish.ironbackpacks.entity.EntityBackpack;
import gr8pefish.ironbackpacks.events.ClientEventHandler;
import gr8pefish.ironbackpacks.registry.ProxyRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * All the specifics that need to be called on the client side
 */
public class ClientProxy extends CommonProxy {

    public void preInit(){
        KeyHandler.init();
        initClientEventHandlers();

        //register render backpack entity
        RenderingRegistry.registerEntityRenderingHandler(EntityBackpack.class, new EntityBackpackRenderFactory());

        ProxyRegistry.preInitClient();
    }

    public void init(){
        ProxyRegistry.initClient();
    }

    public void postInit(){
        ProxyRegistry.postInitClient();
    }

    //============================================================Helper Methods===================================================================

    //helper init methods

    private void initClientEventHandlers(){
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
    }

    //random helper methods

    public String getModVersion(){
        return Constants.VERSION;
    }

    public String getRemoteUpdatedVersion(){
        return ConfigAdaptor.getLatestFilenameFromCurse(IronBackpacksConstants.Miscellaneous.URL_UPDATED_VERSION);
    }

    public EntityPlayer getClientPlayer(){ //TODO: remove eventually
        return Minecraft.getMinecraft().thePlayer;
    }

}
