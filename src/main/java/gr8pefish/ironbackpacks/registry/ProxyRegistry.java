package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.client.modelItem.BackpackModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class ProxyRegistry {

    //=========================================================================Client Registry==================================================================

    public static void preInitClient(){
        ItemRegistry.registerItemRenders();
        ModelLoaderRegistry.registerLoader(new BackpackModelLoader()); //register custom model loader for backpacks to render as a model when not in hand
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
