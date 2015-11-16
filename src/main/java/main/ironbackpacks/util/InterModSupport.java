package main.ironbackpacks.util;

import main.ironbackpacks.ModInformation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * Class for registering all compatibility modules for interacting with other mods
 */
public class InterModSupport {

    //Initializes each mod in the pre-init phase
    public static void preinit() {
        initVersionChecker();
    }

    //Initializes each mod in the init phase
    public static void init() {

    }

    //Initializes each mod in the post-init phase
    public static void postinit() {

    }

    public static void initVersionChecker() {
        if (Loader.isModLoaded("VersionChecker")) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setString("curseProjectName", "227049-iron-backpacks");
            tagCompound.setString("curseFilnameParser", "Iron Backpacks-1.7.10-[]-universal.jar");
            FMLInterModComms.sendRuntimeMessage(ModInformation.ID, "VersionChecker", "addCurseCheck", tagCompound);
        }
    }
}
