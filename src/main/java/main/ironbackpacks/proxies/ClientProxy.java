package main.ironbackpacks.proxies;

import main.ironbackpacks.items.ItemRegistry;

/**
 * The client proxy
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void postInit() {
        ItemRegistry.registerRenders();
    }
}
