package gr8pefish.ironbackpacks.integration.jei.addUpgrade;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import javax.annotation.Nonnull;

public class AddUpgradeRecipeCategory implements IRecipeCategory {

    private String title;
    private IDrawable background;

    public AddUpgradeRecipeCategory(IGuiHelper guiHelper) {

        title = StatCollector.translateToLocal("jei.ironbackpacks.addUpgradeRecipe.name");

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

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {

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
