package gr8pefish.ironbackpacks.integration.jei.addUpgrade;

import gr8pefish.ironbackpacks.util.TextUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AddUpgradeRecipeCategory implements IRecipeCategory {

    private String title;
    private IDrawable background;

    public AddUpgradeRecipeCategory(IGuiHelper guiHelper) {
        title = TextUtils.localize("jei.ironbackpacks.addUpgradeRecipe.name");
        this.background = guiHelper.createDrawable(new ResourceLocation("ironbackpacks", "textures/jei/addUpgradeRecipeJEI.png"), 0, 0, 166, 108);
    }

    @Nonnull
    @Override
    public String getUid() {
        return "ironbackpacks.addUpgrade";
    }

    @Nonnull
    @Override
    public String getTitle() {
        return title;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        //deprecated
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {

        //add custom tooltips
        recipeLayout.getItemStacks().addTooltipCallback((AddUpgradeRecipeWrapper)recipeWrapper);

        //backpack input
        recipeLayout.getItemStacks().init(0, true, 23, 14);
        recipeLayout.getItemStacks().set(0, (ItemStack)recipeWrapper.getInputs().get(1));

        //upgrade input
        recipeLayout.getItemStacks().init(1, true, 59, 14);
        recipeLayout.getItemStacks().set(1, (ItemStack)recipeWrapper.getInputs().get(0));

        //backpack output
        recipeLayout.getItemStacks().init(2, false, 119, 14);
        recipeLayout.getItemStacks().set(2, (ItemStack)recipeWrapper.getOutputs().get(0));

    }

}
