package gr8pefish.ironbackpacks.proxy;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import gr8pefish.ironbackpacks.network.GuiHandler;
import gr8pefish.ironbackpacks.util.InventoryBlacklist;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        IronBackpacksCapabilities.registerAllCapabilities();
    }

    public void init(FMLInitializationEvent event) {
        IronBackpacksAPI.initBackpackVariantList();
        NetworkRegistry.INSTANCE.registerGuiHandler(IronBackpacks.INSTANCE, new GuiHandler());
        InventoryBlacklist.initBlacklist();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
