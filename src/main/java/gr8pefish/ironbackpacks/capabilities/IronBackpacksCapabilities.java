package gr8pefish.ironbackpacks.capabilities;

import gr8pefish.ironbackpacks.capabilities.player.PlayerDeathBackpackCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class IronBackpacksCapabilities {

    //TODO: Refactor to API eventually?

    //Inject capabilities
    @CapabilityInject(PlayerWearingBackpackCapabilities.class)
    public static final Capability<PlayerWearingBackpackCapabilities> WEARING_BACKPACK_CAPABILITY = null;

    @CapabilityInject(PlayerDeathBackpackCapabilities.class)
    public static final Capability<PlayerDeathBackpackCapabilities> DEATH_BACKPACK_CAPABILITY = null;

    //Get capabilities
    public static PlayerWearingBackpackCapabilities getWearingBackpackCapability(EntityPlayer player) {
        return player.getCapability(WEARING_BACKPACK_CAPABILITY, null);
    }

    public static PlayerDeathBackpackCapabilities getDeathBackpackCapability(EntityPlayer player) {
        return player.getCapability(DEATH_BACKPACK_CAPABILITY, null);
    }

    //Registration
    public static void registerAllCapabilities(){
        PlayerWearingBackpackCapabilities.register();
        PlayerDeathBackpackCapabilities.register();
    }

    //Links:
    //https://github.com/McJty/RFTools/tree/1.10/src/main/java/mcjty/rftools/playerprops
    //https://github.com/DiabolicaTrix/MinecraftLifeRPG/tree/master/src/main/java/me/diabolicatrix/other
    //https://mcforge.readthedocs.io/en/latest/datastorage/capabilities/
}
