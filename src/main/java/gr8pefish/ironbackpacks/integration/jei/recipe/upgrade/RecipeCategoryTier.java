package gr8pefish.ironbackpacks.integration.jei.recipe.upgrade;

import gr8pefish.ironbackpacks.IronBackpacks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RecipeCategoryTier implements IRecipeCategory<RecipeWrapperTier> {

    public static final String ID = IronBackpacks.MODID + ":tier";

    private final String title;
    private final IDrawable background;
    private final IDrawable icon;

    private final ICraftingGridHelper craftingGridHelper;

    public RecipeCategoryTier(IGuiHelper guiHelper) {
        title = I18n.format("jei.ironbackpacks.increaseTier.name");
        ResourceLocation backgroundLocation = new ResourceLocation(IronBackpacks.MODID, "textures/jei/crafting_grid.png");
        background = guiHelper.createDrawable(backgroundLocation, 0, 0, 166, 130);
        icon = guiHelper.createDrawable(backgroundLocation, 168, 0, 16, 16);
        craftingGridHelper = guiHelper.createCraftingGridHelper(1, 0);
    }

    @Override
    public String getModName() {
        return IronBackpacks.NAME;
    }

    @Override
    public String getUid() {
        return ID;
    }

    @Override
    public String getTitle() {
        return title;
    }

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
    public void setRecipe(IRecipeLayout recipeLayout, RecipeWrapperTier recipeWrapper, IIngredients ingredients) {
        //get the items to display
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        //init the output slot
        guiItemStacks.init(0, false, 118, 29);

        //init the input slots
        int craftInputSlot1 = 1;
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = craftInputSlot1 + x + (y * 3);
                guiItemStacks.init(index, true, (x * 18) + 23, (y * 18) + 11);
            }
        }

        //set the slots with the correct items
        craftingGridHelper.setInputs(guiItemStacks, ingredients.getInputs(ItemStack.class));
        guiItemStacks.set(0, ingredients.getOutputs(ItemStack.class).get(0));
    }
}
