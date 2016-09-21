package gr8pefish.ironbackpacks.integration;

import gr8pefish.ironbackpacks.api.Constants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * Class for registering all compatibility modules for interacting with other mods
 */
public class InterModSupport {

    //as of writing this, it's basically non-existent
    public static boolean isEnderStorageLoaded = false;
    public static boolean isOpenBlocksLoaded = false;

    //Initializes each mod in the pre-init phase
    public static void preinit() {
        initVersionChecker();
    }

    //Initializes each mod in the init phase
    public static void init() {
//        ToDo: EnderStorage is updated, add support for it again
//        initEnderStorage(); //not updated
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


}
