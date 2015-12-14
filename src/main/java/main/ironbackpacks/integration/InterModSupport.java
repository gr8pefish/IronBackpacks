package main.ironbackpacks.integration;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import main.ironbackpacks.ModInformation;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Class for registering all compatibility modules for interacting with other mods
 */
public class InterModSupport {

    public static boolean isEnderStorageLoaded = false;
    public static boolean isOpenBlocksLoaded = false;
    public static OpenBlocksNoSupport gliderClass;

    //Initializes each mod in the pre-init phase
    public static void preinit() {
        initVersionChecker();
    }

    //Initializes each mod in the init phase
    public static void init() {
        initEnderStorage();
        initOpenBlocks();
    }

    //Initializes each mod in the post-init phase
    public static void postinit() {

    }

    public static void initVersionChecker(){
        if (Loader.isModLoaded("VersionChecker")) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setString("curseProjectName", "227049-iron-backpacks");
            tagCompound.setString("curseFilenameParser", "IronBackpacks-1.7.10-[].jar");
            FMLInterModComms.sendRuntimeMessage(ModInformation.ID, "VersionChecker", "addCurseCheck", tagCompound);
        }
    }

    public static void initEnderStorage(){
        if (Loader.isModLoaded("EnderStorage")) {
            isEnderStorageLoaded = true;
        }
    }

    public static void initOpenBlocks(){
        if (Loader.isModLoaded("OpenBlocks")) {
            isOpenBlocksLoaded = true;
            gliderClass = new OpenBlocksGliderSupport();
        }else{
            gliderClass = new OpenBlocksNoSupport();
        }
    }

}
