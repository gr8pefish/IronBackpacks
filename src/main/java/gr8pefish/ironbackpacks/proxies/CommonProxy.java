package gr8pefish.ironbackpacks.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Class for saving data common to the client and the server
 */
public class CommonProxy {

    public void preInit(){

    }

    public void init(){

    }

    public void postInit(){

    }
    
    @SuppressWarnings("deprecation")
    public String translate(String langkey, Object... pars) {
    	return net.minecraft.util.text.translation.I18n.translateToLocalFormatted(langkey, pars);
    }

    //Helper methods

    public String getModVersion(){
        return null;
    }

    public String getRemoteUpdatedVersion(){
        return null;
    }

    public World getClientWorld() {
        throw new RuntimeException("Can't get client world from server side");
    }

    public EntityPlayer getClientPlayer(){
        throw new RuntimeException("Can't get client player from server side");
    }

}
