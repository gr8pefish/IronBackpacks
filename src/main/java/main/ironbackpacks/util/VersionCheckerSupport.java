package main.ironbackpacks.util;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import main.ironbackpacks.ModInformation;
import net.minecraft.nbt.NBTTagCompound;

public class VersionCheckerSupport {

    public static void init(){
        if (Loader.isModLoaded("VersionChecker")) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setString("curseProjectName", "227049-iron-backpacks");
            tagCompound.setString("curseFilnameParser", "Iron Backpacks-1.7.10-[]-0-universal.jar");
            FMLInterModComms.sendRuntimeMessage(ModInformation.ID, "VersionChecker", "addCurseCheck", tagCompound);
        }
    }
}
