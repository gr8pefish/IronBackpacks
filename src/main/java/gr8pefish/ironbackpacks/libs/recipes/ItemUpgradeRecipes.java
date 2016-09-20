package gr8pefish.ironbackpacks.libs.recipes;

import gr8pefish.ironbackpacks.api.register.ItemICraftingRegistry;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;


public class ItemUpgradeRecipes {

    //TODO: actual Todo: Have to make the recipes not be initialized when you make the items, it just makes it too messy since indices are not set and whatnot.
    //TODO What I should do is something similar to setting the backpack tiering, a method after registering that sets the recipes. (has to be after the ItemIUpgradeRegistry is set)

    public static ShapedOreRecipe additionalUpgradePointsRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.additionalUpgradePointsUpgrade)),
            "ere",
            "rcr",
            "ere",
            'e', "gemEmerald", 'r', "record", 'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe buttonUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.buttonUpgrade)),
            " w ",
            "sps",
            " w ",
            'w', Blocks.WOODEN_BUTTON, 's', Blocks.STONE_BUTTON, 'p', Items.PAPER);

    public static ShapedOreRecipe damageBarUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.damageBarUpgrade)),
            "bsb",
            "sps",
            "bsb",
            's', Items.STRING, 'p', Items.PAPER, 'b', Items.BOWL);

    public static ShapedOreRecipe depthUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.depthUpgrade)),
            "lcl",
            "cnc",
            "lcl",
            'l', Items.LEATHER,
            'c', "chestWood",
            'n', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)));

    public static ShapedOreRecipe eternityUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.eternityUpgrade)),
            "ded",
            "ece",
            "ded",
            'd', "gemDiamond",
            'e', "gemEmerald",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe renamingUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.renamingUpgrade)),
            "fgi",
            "sps",
            "sss",
            'f', Items.FEATHER, 's', "slabWood", 'p', Items.PAPER, 'i', "dyeBlack", 'g', Items.GLASS_BOTTLE);

    public static ShapedOreRecipe nestingUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)),
            "rer",
            "ece",
            "rer",
            'r', "dustRedstone",
            'e', Items.EGG,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe nestingAdvancedUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingAdvancedUpgrade)),
            "rer",
            "ene",
            "rer",
            'r', "dustRedstone",
            'e', Items.EGG,
            'n', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)));

    public static ShapedOreRecipe quickDepositUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositUpgrade)),
            "psp",
            "srs",
            "psp",
            'p', Blocks.PISTON, 'r', Items.PAPER, 's', Items.STRING);

    public static ShapedOreRecipe quickDepositPreciseUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositPreciseUpgrade)),
            "psp",
            "sds",
            "psp",
            'p', Blocks.STICKY_PISTON, 's', Items.SLIME_BALL,
            'd', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositUpgrade)));

    public static ShapedOreRecipe craftingUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingUpgrade)),
            "rpb",
            "tct",
            "ttt",
            'r', "dustRedstone", 'b', "blockRedstone", 'p', Blocks.PISTON, 't', Blocks.CRAFTING_TABLE,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe craftingSmallUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingSmallUpgrade)),
            "spn",
            "tct",
            "ttt",
            's', "sand", 'n', "sandstone", 'p', Blocks.PISTON, 't', Blocks.CRAFTING_TABLE,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe craftingTinyUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingTinyUpgrade)),
            "bpr",
            "tct",
            "ttt",
            'b', "blockRedstone", 'r', "dustRedstone", 'p', Blocks.PISTON, 't', Blocks.CRAFTING_TABLE,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterBasicUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterBasicUpgrade)),
            " p ",
            "pcp",
            " p ",
            'p', Items.PAPER,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterFuzzyUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterFuzzyUpgrade)),
            " f ",
            "pcp",
            " p ",
            'p', Items.PAPER,
            'f', Blocks.RED_FLOWER,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterOreDictUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterOreDictUpgrade)),
            " t ",
            "pcp",
            " p ",
            'p', Items.PAPER,
            't', "treeSapling",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterModSpecificUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterModSpecificUpgrade)),
            " m ",
            "pcp",
            " p ",
            'p', Items.PAPER,
            'm', Items.COMPASS,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterVoidUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterVoidUpgrade)),
            " i ",
            "pcp",
            " p ",
            'p', Items.PAPER,
            'i', "dyeBlack",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterAdvancedUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterAdvancedUpgrade)),
            "bwv",
            "xcy",
            "vzb",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)),
            'v', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterVoidUpgrade)),
            'b', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.buttonUpgrade)),
            'w', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterBasicUpgrade)),
            'x', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterFuzzyUpgrade)),
            'y', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterOreDictUpgrade)),
            'z', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterModSpecificUpgrade)));

    public static ShapedOreRecipe filterMiningUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterMiningUpgrade)),
            "rir",
            "pcp",
            "rpr",
            'r', "dustRedstone",
            'p', Items.PAPER,
            'i', Items.IRON_PICKAXE,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe restockingUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.restockingUpgrade)),
            "rbr",
            "pcp",
            "rbr",
            'r', "dustRedstone",
            'p', Blocks.STICKY_PISTON,
            'b', Blocks.PISTON,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));


    /**
     * Actually registers the recipes.
     */
    public static void registerItemUpgradeRecipes() {
        for (int i = 0; i < ItemIUpgradeRegistry.getTotalSize(); i++)
            GameRegistry.addRecipe(ItemIUpgradeRegistry.getItemRecipe(i));
    }

}
