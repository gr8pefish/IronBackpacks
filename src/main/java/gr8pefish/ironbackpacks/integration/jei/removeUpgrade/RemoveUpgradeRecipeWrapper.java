package gr8pefish.ironbackpacks.integration.jei.removeUpgrade;

import gr8pefish.ironbackpacks.crafting.BackpackRemoveUpgradeRecipe;
import gr8pefish.ironbackpacks.util.TextUtils;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;
import java.util.List;

public class RemoveUpgradeRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {

    private BackpackRemoveUpgradeRecipe removeUpgradeRecipe;
    private final String[] description;
    private final String[] descriptionAdditional;
    private final String craftingType;
    private final String removeSlot1;
    private final String removeSlot2;

    public RemoveUpgradeRecipeWrapper(BackpackRemoveUpgradeRecipe recipe){
        this.craftingType = TextUtils.localize("jei.description.shapedCrafting");
        this.removeSlot1 = TextUtils.localize("jei.description.removeUpgrade.slot1");
        this.removeSlot2 = TextUtils.localize("jei.description.removeUpgrade.slot2");
        this.description = TextUtils.cutLongString(TextUtils.localize("jei.description.removeUpgrade"));
        this.descriptionAdditional = TextUtils.cutLongString(TextUtils.localize("jei.description.removeUpgrade.additional"));
        removeUpgradeRecipe = recipe;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        //ToDo
//        ingredients.setInput(ItemStack.class, );
//        ingredients.setOutputs(ItemStack.class, new ArrayList<>(Collections.singletonList(removeUpgradeRecipe.getRecipeOutput())));
    }

    @Nonnull
    @Override
    public List getInputs() {
        //ToDo: move to ingredients
        return removeUpgradeRecipe.getInput();
    }

    @Nonnull
    @Override
    public List getOutputs() {
        //ToDo: move to ingredients
        return Collections.singletonList(removeUpgradeRecipe.getRecipeOutput());
    }

    @Nonnull
    @Override
    public List<FluidStack> getFluidInputs() {
        return null;
    }

    @Nonnull
    @Override
    public List<FluidStack> getFluidOutputs() {
        return null;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

        //draw the type of recipes
        minecraft.fontRendererObj.drawString(craftingType, 43, -4, Color.darkGray.getRGB());

        //draw the special text so the user knows it is location dependent
        minecraft.fontRendererObj.drawString(removeSlot1, 8, 15, Color.darkGray.getRGB()); //Location
        minecraft.fontRendererObj.drawString(removeSlot2, 4, 23, Color.darkGray.getRGB()); //Dependent

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
        //if mouse is over the 'Location Dependent' string, display this tooltip
        if (mouseX >= 4 && mouseX <= 50 && mouseY <= 31 && mouseY >= 15)
            return Arrays.asList(TextUtils.cutLongString(TextUtils.localize("jei.description.removeUpgrade.locationDependent")));
        return null;
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    @Override
    public void onTooltip(int slotIndex, boolean input, @Nonnull ItemStack ingredient, @Nonnull List<String> tooltip) {

        //input backpack
        if (slotIndex == 0) {
            tooltip.remove(tooltip.size()-1); //remove 'shift for more info'
            tooltip.add("With the upgrade."); //add my tooltip
        }

        //output backpack
        if (slotIndex == 1) {
            tooltip.remove(tooltip.size()-1); //remove 'shift for more info'
            tooltip.add("Without the upgrade."); //add my tooltip
        }
    }
}
