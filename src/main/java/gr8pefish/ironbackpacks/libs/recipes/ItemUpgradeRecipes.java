package gr8pefish.ironbackpacks.libs.recipes;

import gr8pefish.ironbackpacks.api.register.ItemICraftingRegistry;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
            'w', Blocks.WOODEN_BUTTON, 's', Blocks.STONE_BUTTON, 'p', "paper");

    public static ShapedOreRecipe damageBarUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.damageBarUpgrade)),
            "bsb",
            "sps",
            "bsb",
            's', "string", 'p', "paper", 'b', Items.BOWL);

    public static ShapedOreRecipe depthUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.depthUpgrade)),
            "lcl",
            "cnc",
            "lcl",
            'l', "leather",
            'c', "chestWood",
            'n', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)));

    public static ShapedOreRecipe eternityUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.eternityUpgrade)),
            "d d",
            " c ",
            "d d",
            'd', "gemDiamond",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe renamingUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.renamingUpgrade)),
            "fgi",
            "sps",
            "sss",
            'f', "feather", 's', "slabWood", 'p', "paper", 'i', "dyeBlack", 'g', Items.GLASS_BOTTLE);

    public static ShapedOreRecipe nestingUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)),
            "rer",
            "ece",
            "rer",
            'r', "dustRedstone",
            'e', "egg",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe nestingAdvancedUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingAdvancedUpgrade)),
            "rer",
            "ene",
            "rer",
            'r', "dustRedstone",
            'e', "egg",
            'n', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)));

    public static ShapedOreRecipe quickDepositUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositUpgrade)),
            " s ",
            "srs",
            " s ",
            's', Blocks.PISTON, 'r', "paper");

    public static ShapedOreRecipe quickDepositPreciseUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositPreciseUpgrade)),
            " s ",
            "sds",
            " s ",
            's', Blocks.STICKY_PISTON,
            'd', new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositUpgrade)));

    public static ShapedOreRecipe craftingUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingUpgrade)),
            "rpb",
            "tct",
            "ttt",
            'r', "dustRedstone", 'b', "blockRedstone", 'p', Blocks.PISTON, 't', "workbench", //could do bone meal -> bone block
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe craftingSmallUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingSmallUpgrade)),
            "spn",
            "tct",
            "ttt",
            's', "sand", 'n', "sandstone", 'p', Blocks.PISTON, 't', "workbench",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe craftingTinyUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingTinyUpgrade)),
            "bpr",
            "tct",
            "ttt",
            'b', "blockRedstone", 'r', "dustRedstone", 'p', Blocks.PISTON, 't', "workbench",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterBasicUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterBasicUpgrade)),
            " p ",
            "pcp",
            " p ",
            'p', "paper",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterFuzzyUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterFuzzyUpgrade)),
            " f ",
            "pcp",
            " p ",
            'p', "paper",
            'f', Blocks.WOOL,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterOreDictUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterOreDictUpgrade)),
            " t ",
            "pcp",
            " p ",
            'p', "paper",
            't', "treeSapling",
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterModSpecificUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterModSpecificUpgrade)),
            " m ",
            "pcp",
            " p ",
            'p', "paper",
            'm', Items.COMPASS,
            'c', new ItemStack(ItemRegistry.craftingItem, 1, ItemICraftingRegistry.getIndexOf(ItemRegistry.upgradeCore)));

    public static ShapedOreRecipe filterVoidUpgradeRecipe = new ShapedOreRecipe(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterVoidUpgrade)),
            " i ",
            "pcp",
            " p ",
            'p', "paper",
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
            'p', "paper",
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
        for (int i = 0; i < ItemIUpgradeRegistry.getTotalSize(); i++) {
            IRecipe recipe = ItemIUpgradeRegistry.getItemRecipe(i);
            if (recipe != null)
                GameRegistry.addRecipe(recipe);
        }
    }

}
