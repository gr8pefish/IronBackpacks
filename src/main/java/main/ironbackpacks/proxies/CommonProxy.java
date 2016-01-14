package main.ironbackpacks.proxies;

import main.ironbackpacks.registry.ProxyRegistry;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Class for saving data common to the client and the server
 */
public class CommonProxy {

    public void preInit(){
        ProxyRegistry.preInitServer();
    }

    public void init(){
        ProxyRegistry.initServer();
    }

    public void postInit(){
        ProxyRegistry.postInitServer();
    }

    //Helper methods

    public String getModVersion(){
        return null;
    }

    public String getRemoteUpdatedVersion(){
        return null;
    }

    public EntityPlayer getClientPlayer(){
        throw new RuntimeException("Can't get client player from server side");
    }

}
