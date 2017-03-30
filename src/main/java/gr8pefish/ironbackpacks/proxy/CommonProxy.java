package gr8pefish.ironbackpacks.proxy;

import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import gr8pefish.ironbackpacks.core.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        IronBackpacksCapabilities.registerAllCapabilities();
        ModObjects.preInit();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void handleInventoryModel(Item item) {

    }

    public void handleInventoryModel(Block block) {

    }
}
