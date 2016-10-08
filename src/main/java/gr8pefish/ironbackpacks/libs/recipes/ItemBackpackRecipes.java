package gr8pefish.ironbackpacks.libs.recipes;

import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.register.ItemIBackpackRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemBackpackRecipes {

    public static ShapedOreRecipe basicBackpackRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.basicBackpack),
            "lll",
            "lcl",
            "lll",
            'l', "leather", 'c', "chestWood");

    public static void registerItemBackpackRecipes(){
        for (int i = 0; i < ItemIBackpackRegistry.getSize(); i++){
            IBackpack backpack = ItemIBackpackRegistry.getBackpackAtIndex(i);
            if (backpack != null) {
                if(backpack.getItemRecipe(null) != null) {
                    GameRegistry.addRecipe(backpack.getItemRecipe(null)); //hardcoded to ItemBackpack
                }
            }
        }
    }
}
