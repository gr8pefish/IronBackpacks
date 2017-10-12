package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.sounds.IronBackpacksSounds;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegistryEvents {

	public static final NonNullList<Item> ITEMS = NonNullList.create();
	public static final NonNullList<IRecipe> RECIPES = NonNullList.create();
	
	@SubscribeEvent
	public void items(Register<Item> e) {
		ItemRegistry.registerItems();
		e.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public void recipes(Register<IRecipe> e) {
		RecipeRegistry.registerAllRecipes(e.getRegistry());
	}
	
	@SubscribeEvent
	public void sounds(Register<SoundEvent> e) {
		IronBackpacksSounds.registerSounds(e.getRegistry());
	}
	
}
