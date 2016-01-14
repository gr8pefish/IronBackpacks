package main.ironbackpacks.proxies;

import main.ironbackpacks.registry.IronBackpacksRegistry;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Class for saving data common to the client and the server
 */
public class CommonProxy {

    public void preInit(){
        //nothing to see here
        IronBackpacksRegistry.preInitServer();
    }

    public void init(){
        //nothing here either
    }

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
