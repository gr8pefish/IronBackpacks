package gr8pefish.ironbackpacks.integration.jei.increaseTier;

import gr8pefish.ironbackpacks.crafting.BackpackIncreaseTierRecipe;
import gr8pefish.ironbackpacks.crafting.BackpackRemoveUpgradeRecipe;
import gr8pefish.ironbackpacks.util.TextUtils;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IncreaseTierRecipeWrapper implements IRecipeWrapper {

    private BackpackIncreaseTierRecipe increaseTierRecipe;
    private final String[] description;
    private final String descriptionContinues;
    private final String craftingType;

    public IncreaseTierRecipeWrapper(BackpackIncreaseTierRecipe recipe){
        this.craftingType = TextUtils.localize("jei.description.shapedCrafting");
        this.description = TextUtils.cutLongString(TextUtils.localize("jei.description.increaseTier"));
        this.descriptionContinues = TextUtils.localize("jei.description.continuedNextPage");
        increaseTierRecipe = recipe;

    }

    @Override
    public List getInputs() {
        return Arrays.asList(increaseTierRecipe.getInput());
    }

    @Override
    public List getOutputs() {
        return Collections.singletonList(increaseTierRecipe.getRecipeOutput());
    }

    @Override
    public List<FluidStack> getFluidInputs() {
        return null;
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return null;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

        minecraft.fontRendererObj.drawString(craftingType, 43, -8, Color.darkGray.getRGB());

        for (int i = 0; i < description.length; i++)
            minecraft.fontRendererObj.drawString(description[i], 10, 64 + (i*8), Color.black.getRGB());

    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
