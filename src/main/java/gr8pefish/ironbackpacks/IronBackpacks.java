package gr8pefish.ironbackpacks;

import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.core.RegistrarIronBackpacks;
import gr8pefish.ironbackpacks.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = IronBackpacks.MODID, name = IronBackpacks.NAME, version = IronBackpacks.VERSION, dependencies = IronBackpacks.DEPEND, acceptedMinecraftVersions = "[1.12,1.13)")
public class IronBackpacks {

    public static final String MODID = "ironbackpacks";
    public static final String NAME = "Iron Backpacks";
    public static final String VERSION = "@VERSION@";
    public static final String DEPEND = "";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
	public static final CreativeTabs TAB_IB = new CreativeTabs(MODID) {
		@Override
		public ItemStack getTabIconItem() {
            return IronBackpacksAPI.getStack(RegistrarIronBackpacks.PACK_IRON, BackpackSpecialty.STORAGE);
        }
	};

	@SidedProxy(clientSide = "gr8pefish.ironbackpacks.proxy.ClientProxy", serverSide = "gr8pefish.ironbackpacks.proxy.CommonProxy")
	public static CommonProxy PROXY;

	@Mod.Instance(MODID)
	public static IronBackpacks INSTANCE;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	    PROXY.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
	    PROXY.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	    PROXY.postInit(event);
	}

	@Mod.EventHandler
	public void onServerStart(FMLServerStartingEvent event) {

	}
}
