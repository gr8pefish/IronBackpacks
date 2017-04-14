package gr8pefish.ironbackpacks.integration;

import codechicken.enderstorage.item.ItemEnderPouch;
import com.rwtema.extrautils2.items.ItemBuildersWand;
import gr8pefish.ironbackpacks.api.Constants;
import mezz.jei.api.IGuiHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * Class for registering all compatibility modules for interacting with other mods
 */
public class InterModSupport {

    //as of writing this, it's basically non-existent
    public static boolean isEnderStorageLoaded = false;
    public static boolean isJEILoaded = false;
    public static boolean isExtraUtilsLoaded = false;
    public static boolean isOpenBlocksLoaded = false;

    //Initializes each mod in the pre-init phase
    public static void preinit() {
        initVersionChecker();
    }

    //Initializes each mod in the init phase
    public static void init() {
        initEnderStorage();
        initJEI();
        initExUtils();
//        initOpenBlocks(); //not updated
    }

    //Initializes each mod in the post-init phase
    public static void postinit() {

    }

    public static void initVersionChecker(){
        if (Loader.isModLoaded("VersionChecker")) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setString("curseProjectName", "227049-iron-backpacks");
            tagCompound.setString("curseFilenameParser", "IronBackpacks-1.10.2-[].jar");
            FMLInterModComms.sendRuntimeMessage(Constants.MODID, "VersionChecker", "addCurseCheck", tagCompound);
        }
    }

    public static void initJEI(){
        if (Loader.isModLoaded("JEI")) {
            isJEILoaded = true;
        }
    }

    @Optional.Method(modid = "JEI")
    public static boolean isGuiContainerInstanceOfIGuiHelper(GuiContainer container){
        return container instanceof IGuiHelper;
    }


    public static void initEnderStorage(){
        if (Loader.isModLoaded("EnderStorage")) {
            isEnderStorageLoaded = true;
        }
    }

    @Optional.Method(modid = "EnderStorage")
    public static boolean isEnderPouch(Item item) {
        return item instanceof ItemEnderPouch;
    }

    public static void initExUtils(){
        if (Loader.isModLoaded("extrautils2")) {
            isExtraUtilsLoaded = true;
        }
    }

    @Optional.Method(modid = "extrautils2")
    public static boolean isExUtilsBuildersWand(Item item) {
        return item instanceof ItemBuildersWand;
    }
}
