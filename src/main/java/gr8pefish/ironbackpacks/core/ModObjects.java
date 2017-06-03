package gr8pefish.ironbackpacks.core;

import gr8pefish.ironbackpacks.ConfigHandler;
import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.BackpackType;
import gr8pefish.ironbackpacks.api.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.core.recipe.BackpackTierRecipe;
import gr8pefish.ironbackpacks.item.ItemBackpack;
import gr8pefish.ironbackpacks.item.ItemUpgrade;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModObjects {

    public static final ItemBackpack BACKPACK = new ItemBackpack();
    public static final ItemUpgrade UPGRADE = new ItemUpgrade();

    public static final SoundEvent BACKPACK_OPEN = new SoundEvent(new ResourceLocation(IronBackpacks.MODID, "open_backpack"));
    public static final SoundEvent BACKPACK_CLOSE = new SoundEvent(new ResourceLocation(IronBackpacks.MODID, "close_backpack"));

    public static final BackpackType PACK_BASIC = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "basic"), 0, 4, false);
    public static final BackpackType PACK_IRON = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "iron"), 1, 7, true);
    public static final BackpackType PACK_GOLD = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "gold"), 2, 12, true);
    public static final BackpackType PACK_DIAMOND = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "diamond"), 3, 18, true);

    public static final BackpackUpgrade UPGRADE_DAMAGE_BAR = new BackpackUpgrade(new ResourceLocation(IronBackpacks.MODID, "damage_bar"), 1, 0);
    public static final BackpackUpgrade UPGRADE_LOCK = new BackpackUpgrade(new ResourceLocation(IronBackpacks.MODID, "lock"), 1, 0); // TODO - Add texture

    public static void preInit() {
        RecipeSorter.register(IronBackpacks.MODID + ":upgrade", BackpackTierRecipe.class, RecipeSorter.Category.SHAPED, "");

        register(BACKPACK_OPEN, "open_backpack");
        register(BACKPACK_CLOSE, "close_backpack");

        register(BACKPACK, "backpack");
        register(UPGRADE, "upgrade");

        IronBackpacksAPI.registerBackpackType(PACK_BASIC);
        IronBackpacksAPI.registerBackpackType(PACK_IRON);
        IronBackpacksAPI.registerBackpackType(PACK_GOLD);
        IronBackpacksAPI.registerBackpackType(PACK_DIAMOND);

        if (ConfigHandler.upgrades.enableDamageBar)
            IronBackpacksAPI.registerUpgrade(UPGRADE_DAMAGE_BAR);
        if (ConfigHandler.upgrades.enablePackLatch)
            IronBackpacksAPI.registerUpgrade(UPGRADE_LOCK);
    }

    public static void init() {
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
