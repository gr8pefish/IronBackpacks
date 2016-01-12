package main.ironbackpacks;

import main.ironbackpacks.client.gui.GuiHandler;
import main.ironbackpacks.crafting.ItemRecipeRegistry;
import main.ironbackpacks.entity.Entities;
import main.ironbackpacks.events.IronBackpacksEventHandler;
import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.DEPEND)//, guiFactory = ModInformation.GUIFACTORY)
public class IronBackpacks {

	//Make a custom creative tab with the iron backpack as the logo
	public static final CreativeTabs creativeTab = new CreativeTabs(ModInformation.ID) {
		@Override
		public Item getTabIconItem() {
			return ItemRegistry.ironBackpack;
		}
	};

	//The proxies for siding
	@SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
	public static CommonProxy proxy;

	//The instance of this mod
	@Mod.Instance
	public static IronBackpacks instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		//compatibility
//		InterModSupport.preinit(); //Nothing yet in 1.8

		//config file
		File config = event.getSuggestedConfigurationFile();
		ConfigHandler.init(config);

		//networking
		NetworkingHandler.initPackets();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		//items
		ItemRegistry.registerItems();

		//entity registering
		Entities.init();

		//Keybindings and Rendering
		proxy.preInit();

		System.out.println("end of preinit");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		//compatibility
//		InterModSupport.init();

		//event handlers
		IronBackpacksEventHandler handler = new IronBackpacksEventHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);

		System.out.println("middle of init");

		//recipes
		ItemRecipeRegistry.registerItemRecipes();

		//entity rendering
		proxy.init();

		System.out.println("end of init");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		//compatibility
//		InterModSupport.postinit();

		System.out.println("end of post init");

	}
}
