package gr8pefish.ironbackpacks.integration.jei;

import javax.annotation.Nonnull;

import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.integration.jei.addUpgrade.AddUpgradeRecipeCategory;
import gr8pefish.ironbackpacks.integration.jei.addUpgrade.AddUpgradeRecipeHandler;
import gr8pefish.ironbackpacks.integration.jei.increaseTier.IncreaseTierRecipeCategory;
import gr8pefish.ironbackpacks.integration.jei.increaseTier.IncreaseTierRecipeHandler;
import gr8pefish.ironbackpacks.integration.jei.removeUpgrade.RemoveUpgradeRecipeCategory;
import gr8pefish.ironbackpacks.integration.jei.removeUpgrade.RemoveUpgradeRecipeHandler;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@JEIPlugin
@SuppressWarnings("deprecation")
public class JEIIronBackpacksPlugin implements IModPlugin {

    @Override
    public void register(@Nonnull IModRegistry registry) {

        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(
                new AddUpgradeRecipeCategory(guiHelper),
                new RemoveUpgradeRecipeCategory(guiHelper),
                new IncreaseTierRecipeCategory(guiHelper)
        );

        registry.addRecipeHandlers(
                new AddUpgradeRecipeHandler(),
                new RemoveUpgradeRecipeHandler(),
                new IncreaseTierRecipeHandler()
        );
        registry.addRecipeCategoryCraftingItem(new ItemStack(Blocks.CRAFTING_TABLE), "ironbackpacks.addUpgrade", "ironbackpacks.removeUpgrade", "ironbackpacks.increaseTier");

        //Add backpack descriptions
        registry.addDescription(new ItemStack(ItemRegistry.basicBackpack), "jei.description.backpack.basic", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.ironBackpackStorageEmphasis), "jei.description.backpack.iron", "jei.description.backpack.storage", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.ironBackpackUpgradeEmphasis), "jei.description.backpack.iron", "jei.description.backpack.upgrade", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.goldBackpackStorageEmphasis), "jei.description.backpack.gold", "jei.description.backpack.storage", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.goldBackpackUpgradeEmphasis), "jei.description.backpack.gold", "jei.description.backpack.upgrade", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.diamondBackpackStorageEmphasis), "jei.description.backpack.diamond", "jei.description.backpack.storage", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.diamondBackpackUpgradeEmphasis), "jei.description.backpack.diamond", "jei.description.backpack.upgrade", "jei.description.backpack.generic");

        //Add upgrade descriptions
        //basic
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.additionalUpgradePointsUpgrade)), "jei.description.upgrade.additionalUpgradePoints");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.buttonUpgrade)), "jei.description.upgrade.button");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.damageBarUpgrade)), "jei.description.upgrade.damageBar");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.depthUpgrade)), "jei.description.upgrade.depth");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.eternityUpgrade)), "jei.description.upgrade.eternity");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIUpgrade(ItemRegistry.renamingUpgrade)), "jei.description.upgrade.renaming");
        //conflicting
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingUpgrade)), "jei.description.upgrade.nesting", "jei.description.upgrade.conflicting");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.nestingAdvancedUpgrade)), "jei.description.upgrade.nestingAdvanced", "jei.description.upgrade.conflicting");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositUpgrade)), "jei.description.upgrade.quickDeposit", "jei.description.upgrade.conflicting");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConflictingUpgrade(ItemRegistry.quickDepositPreciseUpgrade)), "jei.description.upgrade.quickDepositPrecise", "jei.description.upgrade.conflicting");
        //configurable
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingUpgrade)), "jei.description.upgrade.crafting", "jei.description.upgrade.configurable");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingSmallUpgrade)), "jei.description.upgrade.craftingSmall", "jei.description.upgrade.configurable");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.craftingTinyUpgrade)), "jei.description.upgrade.craftingTiny", "jei.description.upgrade.configurable");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterBasicUpgrade)), "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.basic", "jei.description.upgrade.configurable");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterFuzzyUpgrade)), "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.fuzzy", "jei.description.upgrade.configurable"); //ToDo: replace ordering of filters so specifics first? Do it just for non-basic ones?
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterOreDictUpgrade)), "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.oreDict", "jei.description.upgrade.configurable");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterModSpecificUpgrade)), "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.modSpecific", "jei.description.upgrade.configurable");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterVoidUpgrade)), "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.void", "jei.description.upgrade.configurable");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterAdvancedUpgrade)), "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.advanced", "jei.description.upgrade.configurable");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.filterMiningUpgrade)), "jei.description.upgrade.filter.generic", "jei.description.upgrade.filter.mining", "jei.description.upgrade.configurable");
        registry.addDescription(new ItemStack(ItemRegistry.upgradeItem, 1, ItemIUpgradeRegistry.getIndexOfIConfigurableUpgrade(ItemRegistry.restockingUpgrade)), "jei.description.upgrade.restocking", "jei.description.upgrade.configurable");


//        IItemBlacklist itemBlacklist = registry.getJeiHelpers().getItemBlacklist();
//        itemBlacklist.addItemToBlacklist(); //ToDo
    }

}
