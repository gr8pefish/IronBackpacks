package main.ironbackpacks.proxies;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.client.KeyHandler;
import main.ironbackpacks.client.renderer.RenderBackpack;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.events.ClientEventHandler;
import main.ironbackpacks.handlers.ConfigAdaptor;
import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
    }

    private void initItemRenderers(){
        ItemRegistry.registerItemRenders();
    }


//    private static String equippedBackpack = ModInformation.ID+"EquippedPack";
//
//    /**
//     * Updates the data stored as the equipped backpack.
//     * @param player - the player with the backpack
//     * @param stack - the backpack
//     */
//    public void updateEquippedBackpack(EntityPlayer player, ItemStack stack){
//        if (player.worldObj.isRemote) { //if client
//            System.out.println("client side");
//        }else{
//            System.out.println("server side");
//        }
//        if (player.getEntityData() != null){
//            System.out.println("non-null entity data");
//            System.out.println("Update curr has key: "+player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG));
//        }else{
//            System.out.println("null entity data");
//        }
//
//
//        NBTTagCompound rootPersistentCompound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
//        if (stack != null) {
//            NBTTagCompound tagCompound = new NBTTagCompound();
//            stack.writeToNBT(tagCompound);
//
//            rootPersistentCompound.setTag(equippedBackpack, tagCompound);
//            if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
//                player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, rootPersistentCompound);
//        }else{
//            rootPersistentCompound.removeTag(equippedBackpack);
//            if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
//                player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, rootPersistentCompound);
//        }
//    }

}
