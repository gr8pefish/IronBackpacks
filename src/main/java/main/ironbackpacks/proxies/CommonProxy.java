package main.ironbackpacks.proxies;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.entity.extendedProperties.PlayerBackpackProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

/**
 * Class for saving data common to the client and the server
 */
public class CommonProxy {

    public void preInit(){
        //nothing to see here
    }

    public void init(){
        //nothing here either
    }

    public String getModVersion(){
        return null;
    }

    public String getRemoteUpdatedVersion(){
        return null;
    }

    public EntityPlayer getClientPlayer(){
        throw new RuntimeException("Can't get client player from server side");
    }

}
