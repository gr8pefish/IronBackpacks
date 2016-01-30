package gr8pefish.ironbackpacks.integration.jei;

import gr8pefish.ironbackpacks.integration.jei.addUpgrade.AddUpgradeRecipeCategory;
import gr8pefish.ironbackpacks.integration.jei.addUpgrade.AddUpgradeRecipeHandler;
import gr8pefish.ironbackpacks.integration.jei.removeUpgrade.RemoveUpgradeRecipeCategory;
import gr8pefish.ironbackpacks.integration.jei.removeUpgrade.RemoveUpgradeRecipeHandler;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import mezz.jei.api.*;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelpers;

    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
        JEIPlugin.jeiHelpers = jeiHelpers;
    }

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {}

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeCategories(
                new AddUpgradeRecipeCategory(jeiHelpers.getGuiHelper()),
                new RemoveUpgradeRecipeCategory(jeiHelpers.getGuiHelper())
        );

        registry.addRecipeHandlers(
                new AddUpgradeRecipeHandler(),
                new RemoveUpgradeRecipeHandler()
        );

        //TODO, ask for 'u' for description //TODO: do it dynamically for upgrade ,ask for \p\ or something for new page

        registry.addDescription(new ItemStack(ItemRegistry.basicBackpack), "jei.description.backpack.basic", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.ironBackpackStorageEmphasis), "jei.description.backpack.iron", "jei.description.backpack.storage", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.ironBackpackUpgradeEmphasis), "jei.description.backpack.iron", "jei.description.backpack.upgrade", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.goldBackpackStorageEmphasis), "jei.description.backpack.gold", "jei.description.backpack.storage", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.goldBackpackUpgradeEmphasis), "jei.description.backpack.gold", "jei.description.backpack.upgrade", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.diamondBackpackStorageEmphasis), "jei.description.backpack.diamond", "jei.description.backpack.storage", "jei.description.backpack.generic");
        registry.addDescription(new ItemStack(ItemRegistry.diamondBackpackUpgradeEmphasis), "jei.description.backpack.diamond", "jei.description.backpack.upgrade", "jei.description.backpack.generic");


    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {}


}
