package main.ironbackpacks.proxies;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.client.KeyHandler;
import main.ironbackpacks.client.renderer.RenderBackpack;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.events.IronBackpacksClientEventHandler;
import main.ironbackpacks.handlers.ConfigAdaptor;
import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * The client proxy
 */
public class ClientProxy extends CommonProxy {

    public EntityPlayer getClientPlayer(){
        return Minecraft.getMinecraft().thePlayer;
    }

    public void preInit(){
        initKeybindings();
        initItemRenderers();
        initEntityRenderers(); //for 1.9
        initClientEventHandler();
    }

    private void initEntityRenderers(){
//        RenderingRegistry.registerEntityRenderingHandler(EntityBackpack.class, //some IRenderFactory) //deprecated past 1.8
    }

    public void init(){
        RenderingRegistry.registerEntityRenderingHandler(EntityBackpack.class, new RenderBackpack(Minecraft.getMinecraft().getRenderManager()));
    }

    public String getModVersion(){
        return ModInformation.VERSION;
    }

    public String getRemoteUpdatedVersion(){
        return ConfigAdaptor.getLatestFilenameFromCurse(IronBackpacksConstants.Miscellaneous.URL_UPDATED_VERSION);
    }

    private void initKeybindings() {
//        keyHandler = new KeyHandler();
        KeyHandler.init();
    }

    private void initClientEventHandler(){
        FMLCommonHandler.instance().bus().register(new IronBackpacksClientEventHandler());
    }

    private void initItemRenderers(){
        ItemRegistry.registerItemRenders();
    }

}
