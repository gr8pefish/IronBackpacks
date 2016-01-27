package gr8pefish.ironbackpacks.integration.jei.addUpgrade;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

import javax.annotation.Nonnull;

public class AddUpgradeRecipeCategory implements IRecipeCategory {

    private String title;
    private IDrawable background;

    public AddUpgradeRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(168, 64);
        title = StatCollector.translateToLocal("jei.ironbackpacks.addUpgradeRecipe.name");
//        overlay = guiHelper.createDrawable(new ResourceLocation("botania", "textures/gui/pureDaisyOverlay.png"),
//                0, 0, 64, 46);
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

    }
}
