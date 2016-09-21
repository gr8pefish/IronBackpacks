package gr8pefish.ironbackpacks.integration.jei.addUpgrade;

import gr8pefish.ironbackpacks.crafting.BackpackAddUpgradeRecipe;
import gr8pefish.ironbackpacks.util.TextUtils;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddUpgradeRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {

    private BackpackAddUpgradeRecipe addUpgradeRecipe;
    private final String[] description;
    private final String[] descriptionAdditional;
    private final String craftingType;

    public AddUpgradeRecipeWrapper(BackpackAddUpgradeRecipe recipe){
        this.craftingType = TextUtils.localize("jei.description.shapelessCrafting"); //to indicate the recipes style
        this.description = TextUtils.cutLongString(TextUtils.localize("jei.description.addUpgrade")); //the main description
        this.descriptionAdditional = TextUtils.cutLongString(TextUtils.localize("jei.description.addUpgrade.additional")); //the secondary description
        addUpgradeRecipe = recipe; //the recipe
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
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

        //add the recipes type necessary at the top of the screen
        minecraft.fontRendererObj.drawString(craftingType, 29, -4, Color.darkGray.getRGB());

        //add the descriptions below the images (hardcoded for english length)
        for (int i = 0; i < description.length; i++)
            minecraft.fontRendererObj.drawString(description[i], 11, 40 + (i*8), Color.black.getRGB());
        for (int i = 0; i < descriptionAdditional.length; i++)
            minecraft.fontRendererObj.drawString(descriptionAdditional[i], 11, 72 + (i*8), Color.black.getRGB());
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        //if mouse is over the word to have a tooltip (hardcoded for english 'special conditions')
        if (mouseX >= 60 && mouseX <= 124 && mouseY >= 96 && mouseY <= 102)
            return Arrays.asList(TextUtils.cutLongString(TextUtils.localize("jei.description.addUpgrade.specialConditions")));
        return null;
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    @Override
    public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {

        //input backpack
        if (slotIndex == 0) {
            tooltip.remove(tooltip.size()-1); //remove 'shift for more info'
            tooltip.add("Without the upgrade."); //add my tooltip
        }

        //output backpack
        if (slotIndex == 2) {
            tooltip.remove(tooltip.size()-1); //remove 'shift for more info'
            tooltip.add("With the upgrade."); //add my tooltip
        }

    }
}
