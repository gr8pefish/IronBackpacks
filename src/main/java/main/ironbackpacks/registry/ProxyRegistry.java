package main.ironbackpacks.registry;

public class ProxyRegistry {

    //=========================================================================Client Registry==================================================================

    public static void preInitClient(){
        ItemRegistry.registerItemRenders();
    }

    public static void initClient(){
        //nothing
    }

    public static void postInitClient(){
        //nothing
    }

    //==========================================================================Server Registry===================================================================

    public static void preInitServer(){
        //nothing
    }

    public static void initServer(){
        //nothing
    }

    public static void postInitServer(){
        //nothing
    }
}
