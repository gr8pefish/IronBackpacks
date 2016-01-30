package gr8pefish.ironbackpacks.integration.jei.removeUpgrade;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import javax.annotation.Nonnull;

public class RemoveUpgradeRecipeCategory implements IRecipeCategory {

    private String title;
    private IDrawable background;

    public RemoveUpgradeRecipeCategory(IGuiHelper guiHelper) {

        title = StatCollector.translateToLocal("jei.ironbackpacks.removeUpgradeRecipe.name");

        ResourceLocation location = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");
        background = guiHelper.createDrawable(location, 29, 16, 116, 54);
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

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft) {
        //TODO: make it move through each slot in crafting grid
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {

        int craftInputSlot1 = 1;

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = craftInputSlot1 + x + (y * 3);
                recipeLayout.getItemStacks().init(index, true, x * 18, y * 18);
                recipeLayout.getItemStacks().set(index, (ItemStack)recipeWrapper.getInputs().get(0));
            }
        }

        //backpack input
//        recipeLayout.getItemStacks().init(0, true, 23, 14);
//        recipeLayout.getItemStacks().set(0, (ItemStack)recipeWrapper.getInputs().get(0));

        //backpack output
        recipeLayout.getItemStacks().init(0, false, 94, 18);
        recipeLayout.getItemStacks().set(0, (ItemStack)recipeWrapper.getInputs().get(0));

    }
}
