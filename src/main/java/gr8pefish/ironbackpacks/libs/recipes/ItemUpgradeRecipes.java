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
            'w', Blocks.wooden_button, 's', Blocks.stone_button, 'p', Items.paper);

    public static ShapedOreRecipe damageBarUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.damageBarUpgrade)),
            "bsb",
            "sps",
            "bsb",
            's', Items.string, 'p', Items.paper, 'b', Items.bowl);

    public static ShapedOreRecipe depthUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.depthUpgrade)),
            "lcl",
            "cnc",
            "lcl",
            'l', Items.leather,
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
            'f', Items.feather, 's', "slabWood", 'p', Items.paper, 'i', "dyeBlack", 'g', Items.glass_bottle);

    public static ShapedOreRecipe nestingUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)),
            "rer",
            "ece",
            "rer",
            'r', "dustRedstone",
            'e', Items.egg,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe nestingAdvancedUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingAdvancedUpgrade)),
            "rer",
            "ene",
            "rer",
            'r', "dustRedstone",
            'e', Items.egg,
            'n', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)));

    public static ShapedOreRecipe quickDepositUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositUpgrade)),
            "psp",
            "srs",
            "psp",
            'p', Blocks.piston, 'r', Items.paper, 's', Items.string);

    public static ShapedOreRecipe quickDepositPreciseUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositPreciseUpgrade)),
            "psp",
            "sds",
            "psp",
            'p', Blocks.sticky_piston, 's', Items.slime_ball,
            'd', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositUpgrade)));

    public static ShapedOreRecipe craftingUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingUpgrade)),
            "rpb",
            "tct",
            "ttt",
            'r', "dustRedstone", 'b', "blockRedstone", 'p', Blocks.piston, 't', Blocks.crafting_table,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe craftingSmallUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingSmallUpgrade)),
            "spn",
            "tct",
            "ttt",
            's', "sand", 'n', "sandstone", 'p', Blocks.piston, 't', Blocks.crafting_table,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe craftingTinyUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingTinyUpgrade)),
            "bpr",
            "tct",
            "ttt",
            'b', "blockRedstone", 'r', "dustRedstone", 'p', Blocks.piston, 't', Blocks.crafting_table,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterBasicUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterBasicUpgrade)),
            " p ",
            "pcp",
            " p ",
            'p', Items.paper,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterFuzzyUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterFuzzyUpgrade)),
            " f ",
            "pcp",
            " p ",
            'p', Items.paper,
            'f', Blocks.red_flower,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterOreDictUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterOreDictUpgrade)),
            " t ",
            "pcp",
            " p ",
            'p', Items.paper,
            't', "treeSapling",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterModSpecificUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterModSpecificUpgrade)),
            " m ",
            "pcp",
            " p ",
            'p', Items.paper,
            'm', Items.compass,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterVoidUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterVoidUpgrade)),
            " i ",
            "pcp",
            " p ",
            'p', Items.paper,
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
            'p', Items.paper,
            'i', Items.iron_pickaxe,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe restockingUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.restockingUpgrade)),
            "rbr",
            "pcp",
            "rbr",
            'r', "dustRedstone",
            'p', Blocks.sticky_piston,
            'b', Blocks.piston,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));


    /**
     * Actually registers the recipes.
     */
    public static void registerItemUpgradeRecipes() {
        for (int i = 0; i < ItemIUpgradeRegistry.getTotalSize(); i++)
            GameRegistry.addRecipe(ItemIUpgradeRegistry.getItemRecipe(i));
    }

}
