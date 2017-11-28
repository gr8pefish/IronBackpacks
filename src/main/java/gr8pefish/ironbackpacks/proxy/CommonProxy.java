package gr8pefish.ironbackpacks.proxy;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import gr8pefish.ironbackpacks.network.GuiHandler;
import gr8pefish.ironbackpacks.network.MessageRequestAction;
import gr8pefish.ironbackpacks.network.MessageSetEquippedBackpack;
import gr8pefish.ironbackpacks.util.InventoryBlacklist;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        IronBackpacksCapabilities.registerAllCapabilities();
    }

    public void init(FMLInitializationEvent event) {
        IronBackpacksAPI.initBackpackVariantList();
        InventoryBlacklist.initBlacklist();

        NetworkRegistry.INSTANCE.registerGuiHandler(IronBackpacks.INSTANCE, new GuiHandler());
        IronBackpacks.NETWORK.registerMessage(MessageRequestAction.Handler.class, MessageRequestAction.class, 0, Side.SERVER);
        IronBackpacks.NETWORK.registerMessage(MessageSetEquippedBackpack.Handler.class, MessageSetEquippedBackpack.class, 1, Side.CLIENT);
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
