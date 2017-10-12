package gr8pefish.ironbackpacks.integration.jei.increaseTier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import gr8pefish.ironbackpacks.api.recipes.IIncreaseBackpackTierRecipe;
import gr8pefish.ironbackpacks.util.TextUtils;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class IncreaseTierRecipeWrapper implements IRecipeWrapper {

    private IIncreaseBackpackTierRecipe recipe;
    private final String[] description;
    private final String craftingType;

    public IncreaseTierRecipeWrapper(IIncreaseBackpackTierRecipe recipe){
        this.craftingType = TextUtils.localize("jei.description.shapedCrafting"); //type of recipes
        this.description = TextUtils.cutLongString(TextUtils.localize("jei.description.increaseTier")); //description
        this.recipe = recipe;

    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
        List<List<ItemStack>> l = new ArrayList<>();
        for(Ingredient i : recipe.getIngredients())l.add(Arrays.asList(i.getMatchingStacks()));
        ingredients.setInputLists(ItemStack.class, l);
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

        //add the recipes type necessary at the top of the screen
        minecraft.fontRenderer.drawString(craftingType, 43, 0, Color.darkGray.getRGB());

        //add the description below the images
        for (int i = 0; i < description.length; i++)
            minecraft.fontRenderer.drawString(description[i], 10, 69 + (i*8), Color.black.getRGB());

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        //if mouse is over the word to have a tooltip (hardcoded for english 'crafting recipe')
        if (mouseX >= 52 && mouseX <= 130 && mouseY >= 85 && mouseY <= 91)
            return Arrays.asList(TextUtils.cutLongString(TextUtils.localize("jei.description.increaseTier.tooltip")));
        return null;
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
