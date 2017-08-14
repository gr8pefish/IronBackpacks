package gr8pefish.ironbackpacks.proxy;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.capabilities.BackpackInvImpl;
import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import gr8pefish.ironbackpacks.core.RecipesIronBackpacks;
import gr8pefish.ironbackpacks.network.GuiHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        IronBackpacksCapabilities.registerAllCapabilities();
        BackpackInvImpl.init();
    }

    public void init(FMLInitializationEvent event) {
        RecipesIronBackpacks.init();
        IronBackpacksAPI.initVariantList();
        IronBackpacksAPI.initVariantEnumList();
        NetworkRegistry.INSTANCE.registerGuiHandler(IronBackpacks.INSTANCE, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
