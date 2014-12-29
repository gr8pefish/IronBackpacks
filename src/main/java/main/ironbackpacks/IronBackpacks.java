package main.ironbackpacks;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import main.ironbackpacks.items.ItemBasicBackpack;
import main.ironbackpacks.util.GuiHandler;
import main.ironbackpacks.items.ItemRecipeRegistry;
import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.DEPEND, guiFactory = ModInformation.GUIFACTORY)
public class IronBackpacks {

	public static final CreativeTabs creativeTab = new CreativeTabs(ModInformation.ID) {
		@Override
		public Item getTabIconItem() {
			return ItemRegistry.basicBackpack; //TODO change icon for creative tab
		}
	};

	@SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
	public static CommonProxy proxy;

	@Mod.Instance
	public static IronBackpacks instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ItemRegistry.registerItems();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		ItemRecipeRegistry.registerItemRecipes();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
}
