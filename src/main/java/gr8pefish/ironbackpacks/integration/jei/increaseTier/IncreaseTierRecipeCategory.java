package gr8pefish.ironbackpacks.integration.jei.increaseTier;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import javax.annotation.Nonnull;

public class IncreaseTierRecipeCategory implements IRecipeCategory {

    private String title;
    private IDrawable background;

    private final ICraftingGridHelper craftingGridHelper;

    public IncreaseTierRecipeCategory(IGuiHelper guiHelper) {

        title = StatCollector.translateToLocal("jei.ironbackpacks.increaseTierRecipe.name");

        this.background = guiHelper.createDrawable(new ResourceLocation("ironbackpacks", "textures/jei/craftingGridRecipeJEI.png"), 0, 0, 166, 101);

        craftingGridHelper = guiHelper.createCraftingGridHelper(1, 0);
    }

    @Nonnull
    @Override
    public String getUid() {
        return "ironbackpacks.increaseTier";
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

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        int craftInputSlot1 = 1;
        guiItemStacks.init(0, false, 119, 22);

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = craftInputSlot1 + x + (y * 3);
                guiItemStacks.init(index, true, (x * 18) + 23, (y * 18) + 6);
            }
        }

        craftingGridHelper.setInput(guiItemStacks, recipeWrapper.getInputs());
        craftingGridHelper.setOutput(guiItemStacks, recipeWrapper.getOutputs());

    }
}
