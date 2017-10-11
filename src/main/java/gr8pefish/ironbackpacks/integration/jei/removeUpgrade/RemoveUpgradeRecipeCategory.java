package gr8pefish.ironbackpacks.integration.jei.removeUpgrade;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import gr8pefish.ironbackpacks.util.TextUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RemoveUpgradeRecipeCategory implements IRecipeCategory<RemoveUpgradeRecipeWrapper> {

    private final String title;
    private final IDrawable background;
    private final IDrawable icon;

    public RemoveUpgradeRecipeCategory(IGuiHelper guiHelper) {
        title = TextUtils.localize("jei.ironbackpacks.removeUpgradeRecipe.name");
        ResourceLocation RL = new ResourceLocation("ironbackpacks", "textures/jei/removeUpgradeRecipeJEI.png");
        background = guiHelper.createDrawable(RL, 0, 0, 166, 130);
        icon = guiHelper.createDrawable(RL, 168, 0, 16, 16);
    }

    @Nonnull
    @Override
    public String getUid() {
        return "ironbackpacks.removeUpgrade";
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
        return icon;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull RemoveUpgradeRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {

        //add custom tooltips
        recipeLayout.getItemStacks().addTooltipCallback((RemoveUpgradeRecipeWrapper)recipeWrapper);

        //backpack input
        recipeLayout.getItemStacks().init(0, true, 59, 14);
        recipeLayout.getItemStacks().set(0, ingredients.getInputs(ItemStack.class).get(0));

        //backpack output
        recipeLayout.getItemStacks().init(1, false, 119, 14);
        recipeLayout.getItemStacks().set(1, ingredients.getOutputs(ItemStack.class).get(0));
    }

	@Override
	public String getModName() {
		// TODO Auto-generated method stub
		return null;
	}
}
