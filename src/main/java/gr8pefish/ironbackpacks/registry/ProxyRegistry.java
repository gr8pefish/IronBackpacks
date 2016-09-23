package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;

public class ProxyRegistry {

    //=========================================================================Client Registry==================================================================

    public static void preInitClient(){
        ItemRegistry.registerItemRenders();
        IronBackpacksCapabilities.registerAllCapabilities();
    }

    public static void initClient(){
        //nothing
    }

    public static void postInitClient(){
        //nothing
    }

    //==========================================================================Server Registry===================================================================

    public static void preInitServer(){
        IronBackpacksCapabilities.registerAllCapabilities();
    }

    public static void initServer(){
        //nothing
    }

    public static void postInitServer(){
        //nothing
    }
}
