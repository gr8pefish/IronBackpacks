package main.ironbackpacks.proxies;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.events.IronBackpacksClientEventHandler;
import main.ironbackpacks.handlers.ConfigAdaptor;
import main.ironbackpacks.handlers.KeybindingHandler;
import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * The client proxy
 */
public class ClientProxy extends CommonProxy {

    public static KeybindingHandler keybindingHandler;

    public EntityPlayer getClientPlayer(){
        return Minecraft.getMinecraft().thePlayer;
    }

    public void preInit(){
        initKeybindings();
        initRenderers();
    }

    public String getModVersion(){
        return ModInformation.VERSION;
    }

    public String getRemoteUpdatedVersion(){
        return ConfigAdaptor.getLatestFilenameFromCurse(IronBackpacksConstants.Miscellaneous.URL_UPDATED_VERSION);
    }

    public void initKeybindings() {
        keybindingHandler = new KeybindingHandler();
        keybindingHandler.init();
        FMLCommonHandler.instance().bus().register(new IronBackpacksClientEventHandler());
    }


    public void initRenderers(){
//        RenderingRegistry.registerEntityRenderingHandler(EntityBackpack.class, new RenderBackpack()); //TODO: rendering model
        ItemRegistry.registerItemRenders();
    }

}
