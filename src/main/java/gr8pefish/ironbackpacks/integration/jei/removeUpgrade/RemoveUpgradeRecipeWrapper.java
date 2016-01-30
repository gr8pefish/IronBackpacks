package gr8pefish.ironbackpacks.integration.jei.removeUpgrade;

import gr8pefish.ironbackpacks.crafting.BackpackRemoveUpgradeRecipe;
import gr8pefish.ironbackpacks.util.TextUtils;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class RemoveUpgradeRecipeWrapper implements IRecipeWrapper {

    private BackpackRemoveUpgradeRecipe removeUpgradeRecipe;
    private final String[] description;
    private final String descriptionContinues;
    private final String[] descriptionAdditional;
    private final String[] descriptionAdditionalMore;

    public RemoveUpgradeRecipeWrapper(BackpackRemoveUpgradeRecipe recipe){
        this.description = TextUtils.cutLongString(TextUtils.localize("jei.description.removeUpgrade"));
        this.descriptionContinues = TextUtils.localize("jei.description.continuedNextPage");
        this.descriptionAdditional = TextUtils.cutLongString(TextUtils.localize("jei.description.removeUpgrade.additional"));
        this.descriptionAdditionalMore = TextUtils.cutLongString(TextUtils.localize("jei.description.removeUpgrade.additional.more"));
        removeUpgradeRecipe = recipe;
    }

    @Override
    public List getInputs() {
        return removeUpgradeRecipe.getInput();
    }

    @Override
    public List getOutputs() {
        return Collections.singletonList(removeUpgradeRecipe.getRecipeOutput());
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
            minecraft.fontRendererObj.drawString(description[i], -15, 66 + (i*8), Color.darkGray.getRGB());

        minecraft.fontRendererObj.drawString(descriptionContinues, -15, 107, Color.gray.getRGB());

        for (int i = 0; i < descriptionAdditional.length; i++)
            minecraft.fontRendererObj.drawString(descriptionAdditional[i], -15, 150 + (i*8), Color.darkGray.getRGB());

        for (int i = 0; i < descriptionAdditionalMore.length; i++)
            minecraft.fontRendererObj.drawString(descriptionAdditionalMore[i], -15, 150 + (i*8), Color.darkGray.getRGB());
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
