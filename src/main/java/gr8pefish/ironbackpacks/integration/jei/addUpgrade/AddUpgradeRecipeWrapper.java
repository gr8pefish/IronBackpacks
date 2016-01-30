package gr8pefish.ironbackpacks.integration.jei.addUpgrade;

import gr8pefish.ironbackpacks.crafting.BackpackAddUpgradeRecipe;
import gr8pefish.ironbackpacks.util.TextUtils;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class AddUpgradeRecipeWrapper implements IRecipeWrapper {

    private BackpackAddUpgradeRecipe addUpgradeRecipe;
    private final String[] description;
    private final String[] descriptionAdditional;

    public AddUpgradeRecipeWrapper(BackpackAddUpgradeRecipe recipe){
        this.description = TextUtils.cutLongString(TextUtils.localize("jei.description.addUpgrade"));
        this.descriptionAdditional = TextUtils.cutLongString(TextUtils.localize("jei.description.addUpgrade.additional"));
        addUpgradeRecipe = recipe;
    }

    @Override
    public List getInputs() {
        return addUpgradeRecipe.getInput();
    }

    @Override
    public List getOutputs() {
        return Collections.singletonList(addUpgradeRecipe.getRecipeOutput());
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
        for (int i = 0; i < description.length; i++)
            minecraft.fontRendererObj.drawString(description[i], 14, 40 + (i*8), Color.black.getRGB());
        for (int i = 0; i < descriptionAdditional.length; i++)
            minecraft.fontRendererObj.drawString(descriptionAdditional[i], 14, 72 + (i*8), Color.black.getRGB());
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
