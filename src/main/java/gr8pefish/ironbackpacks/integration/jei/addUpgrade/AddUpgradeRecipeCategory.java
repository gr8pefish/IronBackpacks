package gr8pefish.ironbackpacks.integration.jei.addUpgrade;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.util.TextUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AddUpgradeRecipeCategory implements IRecipeCategory<AddUpgradeRecipeWrapper> {

    private final String title;
    private final IDrawable background;
    private final IDrawable icon;

    public AddUpgradeRecipeCategory(IGuiHelper guiHelper) {
        title = TextUtils.localize("jei.ironbackpacks.addUpgradeRecipe.name");
        ResourceLocation RL = new ResourceLocation("ironbackpacks", "textures/jei/addUpgradeRecipeJEI.png");
        background = guiHelper.createDrawable(RL, 0, 0, 166, 130);
        icon = guiHelper.createDrawable(RL, 168, 0, 16, 16);
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
        return icon;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull AddUpgradeRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {

        //add custom tooltips
        recipeLayout.getItemStacks().addTooltipCallback((AddUpgradeRecipeWrapper)recipeWrapper);

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        
        //backpack input
        recipeLayout.getItemStacks().init(0, true, 23, 14);
        recipeLayout.getItemStacks().set(0, inputs.get(1));

        //upgrade input
        recipeLayout.getItemStacks().init(1, true, 59, 14);
        recipeLayout.getItemStacks().set(1, inputs.get(0));

        //backpack output
        recipeLayout.getItemStacks().init(2, false, 119, 14);
        recipeLayout.getItemStacks().set(2, ingredients.getOutputs(ItemStack.class).get(0));

    }

	@Override
	public String getModName() {
		return Constants.MOD_NAME;
	}

}
