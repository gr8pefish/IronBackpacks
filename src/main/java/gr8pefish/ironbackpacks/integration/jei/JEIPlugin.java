package gr8pefish.ironbackpacks.integration.jei;

import gr8pefish.ironbackpacks.integration.jei.addUpgrade.AddUpgradeRecipeCategory;
import mezz.jei.api.*;

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
                new AddUpgradeRecipeCategory(jeiHelpers.getGuiHelper())
        );
//        registry.addRecipeHandlers(
//                new AddUpgradeRecipeHandler();
//        );
//        registry.addRecipes(); //TODO: register recipes dynamically in registry
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {}

}
