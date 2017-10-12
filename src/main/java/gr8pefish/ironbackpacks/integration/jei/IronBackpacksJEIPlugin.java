package gr8pefish.ironbackpacks.integration.jei;

import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.core.RegistrarIronBackpacks;
import gr8pefish.ironbackpacks.core.recipe.BackpackTierRecipe;
import gr8pefish.ironbackpacks.integration.jei.recipe.upgrade.RecipeCategoryTier;
import gr8pefish.ironbackpacks.integration.jei.recipe.upgrade.RecipeWrapperTier;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

// TODO - Figure out why recipes are merged with normal crafting category
@JEIPlugin
public class IronBackpacksJEIPlugin implements IModPlugin {

    public static IJeiRuntime runtime;
    public static IJeiHelpers helpers;

    @Override
    public void register(IModRegistry registry) {
        helpers = registry.getJeiHelpers();

        registry.handleRecipes(BackpackTierRecipe.class, RecipeWrapperTier::new, RecipeCategoryTier.ID);
        registry.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), RecipeCategoryTier.ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new RecipeCategoryTier(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.useNbtForSubtypes(RegistrarIronBackpacks.UPGRADE);
        subtypeRegistry.registerSubtypeInterpreter(RegistrarIronBackpacks.BACKPACK, s -> {
            if (!(s.getItem() instanceof IBackpack))
                return ISubtypeRegistry.ISubtypeInterpreter.NONE;

            BackpackInfo backpackInfo = ((IBackpack) s.getItem()).getBackpackInfo(s);
            return backpackInfo.getVariant().getBackpackType().getIdentifier().toString() + "|" + backpackInfo.getVariant().getBackpackSpecialty();
        });
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }
}
