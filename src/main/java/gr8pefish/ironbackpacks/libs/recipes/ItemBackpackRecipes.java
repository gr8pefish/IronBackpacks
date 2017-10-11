package gr8pefish.ironbackpacks.libs.recipes;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.register.ItemIBackpackRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemBackpackRecipes {

    public static IRecipe basicBackpackRecipe = new ShapedOreRecipe(null, new ItemStack(ItemRegistry.basicBackpack),
            "wlw",
            "lcl",
            "wlw",
            'l', "leather", 'c', "chestWood", 'w', Blocks.WOOL).setRegistryName(Constants.MODID, "basic_backpack");

    public static void registerItemBackpackRecipes(){
        for (int i = 0; i < ItemIBackpackRegistry.getSize(); i++){
            IBackpack backpack = ItemIBackpackRegistry.getBackpackAtIndex(i);
            if (backpack != null) {
                if(backpack.getItemRecipe(null) != null) {
                    ForgeRegistries.RECIPES.register(backpack.getItemRecipe(null)); //hardcoded to ItemBackpack
                }
            }
        }
    }
}
