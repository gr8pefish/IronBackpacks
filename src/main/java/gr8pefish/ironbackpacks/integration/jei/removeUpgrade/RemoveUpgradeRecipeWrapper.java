package gr8pefish.ironbackpacks.integration.jei.removeUpgrade;

import gr8pefish.ironbackpacks.crafting.BackpackRemoveUpgradeRecipe;
import gr8pefish.ironbackpacks.util.TextUtils;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RemoveUpgradeRecipeWrapper implements IRecipeWrapper {

    private BackpackRemoveUpgradeRecipe removeUpgradeRecipe;
    private final String[] description;
    private final String descriptionContinues;
    private final String[] descriptionAdditional;
    private final String[] descriptionAdditionalMore;
    private final String craftingType;
    private final String removeSlot1;
    private final String removeSlot2;

    public RemoveUpgradeRecipeWrapper(BackpackRemoveUpgradeRecipe recipe){
        this.craftingType = TextUtils.localize("jei.description.shapedCrafting");
        this.removeSlot1 = TextUtils.localize("jei.description.removeUpgrade.slot1");
        this.removeSlot2 = TextUtils.localize("jei.description.removeUpgrade.slot2");
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

        minecraft.fontRendererObj.drawString(craftingType, 43, -4, Color.darkGray.getRGB());

        minecraft.fontRendererObj.drawString(removeSlot1, 8, 15, Color.darkGray.getRGB());
        minecraft.fontRendererObj.drawString(removeSlot2, 4, 23, Color.darkGray.getRGB());

        for (int i = 0; i < description.length; i++)
            minecraft.fontRendererObj.drawString(description[i], 11, 40 + (i*8), Color.black.getRGB());
        for (int i = 0; i < descriptionAdditional.length; i++)
            minecraft.fontRendererObj.drawString(descriptionAdditional[i], 11, 80 + (i*8), Color.black.getRGB());

    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 4 && mouseX <= 50 && mouseY <= 31 && mouseY >= 15)
            return Arrays.asList(TextUtils.cutLongString(TextUtils.localize("jei.description.removeUpgrade.locationDependent")));
        return null;
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
