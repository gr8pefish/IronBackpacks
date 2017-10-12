package gr8pefish.ironbackpacks.core;

import gr8pefish.ironbackpacks.ConfigHandler;
import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.core.recipe.BackpackTierRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

import static gr8pefish.ironbackpacks.core.RegistrarIronBackpacks.*;

@SuppressWarnings("ConstantConditions")
public class RecipesIronBackpacks {

    public static void init() {
        RecipeSorter.register(IronBackpacks.MODID + ":upgrade", BackpackTierRecipe.class, RecipeSorter.Category.SHAPED, "");

        // Backpacks
        GameRegistry.addRecipe(new BackpackTierRecipe(PACK_BASIC, BackpackSpecialty.NONE, "WLW", "LCL", "WLW", 'W', Blocks.WOOL, 'L', "leather", 'C', "chestWood"));

        GameRegistry.addRecipe(new BackpackTierRecipe(PACK_IRON, BackpackSpecialty.STORAGE, "ICI", "IBI", "III", 'I', "ingotIron", 'B', IronBackpacksAPI.getStack(PACK_BASIC, BackpackSpecialty.NONE), 'C', "chestWood"));
        GameRegistry.addRecipe(new BackpackTierRecipe(PACK_IRON, BackpackSpecialty.UPGRADE, "ICI", "IBI", "III", 'I', "ingotIron", 'B', IronBackpacksAPI.getStack(PACK_BASIC, BackpackSpecialty.NONE), 'C', new ItemStack(UPGRADE)));

        GameRegistry.addRecipe(new BackpackTierRecipe(PACK_GOLD, BackpackSpecialty.STORAGE, "ICI", "IBI", "III", 'I', "ingotGold", 'B', IronBackpacksAPI.getStack(PACK_IRON, BackpackSpecialty.STORAGE), 'C', "chestWood"));
        GameRegistry.addRecipe(new BackpackTierRecipe(PACK_GOLD, BackpackSpecialty.UPGRADE, "ICI", "IBI", "III", 'I', "ingotGold", 'B', IronBackpacksAPI.getStack(PACK_IRON, BackpackSpecialty.UPGRADE), 'C', new ItemStack(UPGRADE)));

        GameRegistry.addRecipe(new BackpackTierRecipe(PACK_DIAMOND, BackpackSpecialty.STORAGE, "DDD", "CBC", "DDD", 'D', "gemDiamond", 'B', IronBackpacksAPI.getStack(PACK_GOLD, BackpackSpecialty.STORAGE), 'C', "chestWood"));
        GameRegistry.addRecipe(new BackpackTierRecipe(PACK_DIAMOND, BackpackSpecialty.UPGRADE, "DDD", "CBC", "DDD", 'D', "gemDiamond", 'B', IronBackpacksAPI.getStack(PACK_GOLD, BackpackSpecialty.UPGRADE), 'C', new ItemStack(UPGRADE)));

        // Upgrades
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(UPGRADE), "SPS", "PWP", "SPS", 'S', "string", 'W', "stickWood", 'P', "paper"));

        if (ConfigHandler.upgrades.enableDamageBar)
            GameRegistry.addRecipe(new ShapedOreRecipe(IronBackpacksAPI.getStack(UPGRADE_DAMAGE_BAR), "MSM", "SCS", "MSM", 'M', Items.BOWL, 'S', "string", 'C', new ItemStack(UPGRADE)));
        if (ConfigHandler.upgrades.enablePackLatch)
            GameRegistry.addRecipe(new ShapedOreRecipe(IronBackpacksAPI.getStack(UPGRADE_LOCK), "MSM", "SCS", "MSM", 'M', "ingotGold", 'S', "string", 'C', new ItemStack(UPGRADE)));
    }

    public static <T extends IForgeRegistryEntry<T>> T register(T type, String name) {
        type.setRegistryName(new ResourceLocation(IronBackpacks.MODID, name));
        GameRegistry.register(type);
        return type;
    }
}
