package gr8pefish.ironbackpacks.proxy;

import gr8pefish.ironbackpacks.core.RegistrarIronBackpacks;
import gr8pefish.ironbackpacks.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        // Register colors for backpacks
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ColorUtil::getBackpackColor, RegistrarIronBackpacks.BACKPACK);
    }

}
