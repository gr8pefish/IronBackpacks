package main.ironbackpacks;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import main.ironbackpacks.network.PacketHandler;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.GuiHandler;
import main.ironbackpacks.crafting.ItemRecipeRegistry;
import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.DEPEND, guiFactory = ModInformation.GUIFACTORY)
public class IronBackpacks {

	public static int[] configUpgradeSlots;

	public static final CreativeTabs creativeTab = new CreativeTabs(ModInformation.ID) {
		@Override
		public Item getTabIconItem() {
			return ItemRegistry.ironBackpack; //TODO change icon for creative tab
		}
	};

	@SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
	public static CommonProxy proxy;

	@Mod.Instance
	public static IronBackpacks instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		File config = event.getSuggestedConfigurationFile();
		ConfigHandler.init(config);

		PacketHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		ItemRegistry.registerItems();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		ItemRecipeRegistry.registerItemRecipes();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
}
