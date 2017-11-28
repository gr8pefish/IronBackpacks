package gr8pefish.ironbackpacks.proxy;

import gr8pefish.ironbackpacks.client.ClientEventHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ClientRegistry.registerKeyBinding(ClientEventHandler.KEY_EQUIP);
        ClientRegistry.registerKeyBinding(ClientEventHandler.KEY_OPEN);
    }
}
