package gr8pefish.ironbackpacks.registry.recipes;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.register.ItemBackpackRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemBackpackRecipes {

    public static ShapedOreRecipe basicBackpackRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.basicBackpack),
            "lll",
            "lcl",
            "lll",
            'l', Items.leather, 'c', "chestWood");

    public static void registerItemBackpackRecipes(){
        for (int i = 0; i < ItemBackpackRegistry.getSize(); i++){
            IBackpack backpack = ItemBackpackRegistry.getBackpackAtIndex(i);
            if (backpack != null) {
                if(backpack.getItemRecipe(null) != null) {
                    GameRegistry.addRecipe(backpack.getItemRecipe(null)); //hardcoded to ItemBackpack
                }
            }
        }
    }
}
