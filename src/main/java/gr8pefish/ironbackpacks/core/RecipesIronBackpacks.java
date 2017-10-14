package gr8pefish.ironbackpacks.core;

import gr8pefish.ironbackpacks.ConfigHandler;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.core.recipe.BackpackTierRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;

import static gr8pefish.ironbackpacks.core.RegistrarIronBackpacks.*;

// TODO - Replace with json recipes?
@Mod.EventBusSubscriber
@SuppressWarnings("ConstantConditions")
public class RecipesIronBackpacks {

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        // Backpacks
        event.getRegistry().register(new BackpackTierRecipe(PACK_BASIC, BackpackSpecialty.NONE, "WLW", "LCL", "WLW", 'W', Blocks.WOOL, 'L', "leather", 'C', "chestWood").setRegistryName("pack_basic"));

        event.getRegistry().register(new BackpackTierRecipe(PACK_IRON, BackpackSpecialty.STORAGE, "ICI", "IBI", "III", 'I', "ingotIron", 'B', IronBackpacksAPI.getStack(PACK_BASIC, BackpackSpecialty.NONE), 'C', "chestWood").setRegistryName("pack_iron_storage"));
        event.getRegistry().register(new BackpackTierRecipe(PACK_IRON, BackpackSpecialty.UPGRADE, "ICI", "IBI", "III", 'I', "ingotIron", 'B', IronBackpacksAPI.getStack(PACK_BASIC, BackpackSpecialty.NONE), 'C', new ItemStack(UPGRADE)).setRegistryName("pack_iron_upgrade"));

        event.getRegistry().register(new BackpackTierRecipe(PACK_GOLD, BackpackSpecialty.STORAGE, "ICI", "IBI", "III", 'I', "ingotGold", 'B', IronBackpacksAPI.getStack(PACK_IRON, BackpackSpecialty.STORAGE), 'C', "chestWood").setRegistryName("pack_gold_storage"));
        event.getRegistry().register(new BackpackTierRecipe(PACK_GOLD, BackpackSpecialty.UPGRADE, "ICI", "IBI", "III", 'I', "ingotGold", 'B', IronBackpacksAPI.getStack(PACK_IRON, BackpackSpecialty.UPGRADE), 'C', new ItemStack(UPGRADE)).setRegistryName("pack_gold_upgrade"));

        event.getRegistry().register(new BackpackTierRecipe(PACK_DIAMOND, BackpackSpecialty.STORAGE, "DDD", "CBC", "DDD", 'D', "gemDiamond", 'B', IronBackpacksAPI.getStack(PACK_GOLD, BackpackSpecialty.STORAGE), 'C', "chestWood").setRegistryName("pack_diamond_storage"));
        event.getRegistry().register(new BackpackTierRecipe(PACK_DIAMOND, BackpackSpecialty.UPGRADE, "DDD", "CBC", "DDD", 'D', "gemDiamond", 'B', IronBackpacksAPI.getStack(PACK_GOLD, BackpackSpecialty.UPGRADE), 'C', new ItemStack(UPGRADE)).setRegistryName("pack_diamond_upgrade"));

        // Upgrades
        event.getRegistry().register(new ShapedOreRecipe(UPGRADE.getRegistryName(), new ItemStack(UPGRADE), "SPS", "PWP", "SPS", 'S', "string", 'W', "stickWood", 'P', "paper").setRegistryName("upgrade_blank"));

        if (ConfigHandler.upgrades.enableDamageBar)
            event.getRegistry().register(new ShapedOreRecipe(UPGRADE.getRegistryName(), IronBackpacksAPI.getStack(UPGRADE_DAMAGE_BAR), "MSM", "SCS", "MSM", 'M', Items.BOWL, 'S', "string", 'C', new ItemStack(UPGRADE)).setRegistryName("upgrade_damage_bar"));
        if (ConfigHandler.upgrades.enablePackLatch)
            event.getRegistry().register(new ShapedOreRecipe(UPGRADE.getRegistryName(), IronBackpacksAPI.getStack(UPGRADE_LOCK), "MSM", "SCS", "MSM", 'M', "ingotGold", 'S', "string", 'C', new ItemStack(UPGRADE)).setRegistryName("upgrade_latch"));
        if (ConfigHandler.upgrades.enableExtraUpgrade)
            event.getRegistry().register(new ShapedOreRecipe(UPGRADE.getRegistryName(), IronBackpacksAPI.getStack(UPGRADE_EXTRA_UPGRADE), "MSM", "SCS", "MSM", 'M', "leather", 'S', "string", 'C', new ItemStack(UPGRADE)).setRegistryName("upgrade_extra_upgrade"));
    }
}
