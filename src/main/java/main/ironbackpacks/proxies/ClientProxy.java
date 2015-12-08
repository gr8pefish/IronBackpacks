package main.ironbackpacks.proxies;

import main.ironbackpacks.client.KeyHandler;
import main.ironbackpacks.events.ClientEventHandler;
import main.ironbackpacks.items.ItemRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * The client proxy
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {
        ItemRegistry.registerRenders();
    }

    @Override
    public void registerKeybinds() {
        KeyHandler.init();
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
    }
}
