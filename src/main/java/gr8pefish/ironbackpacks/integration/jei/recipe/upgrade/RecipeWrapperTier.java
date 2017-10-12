package gr8pefish.ironbackpacks.integration.jei.recipe.upgrade;

import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.core.recipe.BackpackTierRecipe;
import gr8pefish.ironbackpacks.integration.jei.IronBackpacksJEIPlugin;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class RecipeWrapperTier implements IRecipeWrapper {

    private final BackpackTierRecipe backpackTierRecipe;

    public RecipeWrapperTier(BackpackTierRecipe backpackTierRecipe) {
        this.backpackTierRecipe = backpackTierRecipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        IStackHelper stackHelper = IronBackpacksJEIPlugin.helpers.getStackHelper();
        ItemStack recipeOutput = backpackTierRecipe.getRecipeOutput();

        List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(backpackTierRecipe.getIngredients());
        ingredients.setInputLists(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, recipeOutput);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        // Adds the recipes type necessary at the top of the screen
        minecraft.fontRenderer.drawString(I18n.format("jei.description.shapedCrafting"), 43, 0, 0x424242);

        // Adds the tier description
        List<String> description = minecraft.fontRenderer.listFormattedStringToWidth(I18n.format("jei.ironbackpacks.increaseTier.desc"), 160);
        for (int i = 0; i < description.size(); i++)
            minecraft.fontRenderer.drawString(description.get(i), 10, 69 + (i * 8), 0);
    }


    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> tooltip = Lists.newArrayList();

        if (mouseX >= 52 && mouseX <= 130 && mouseY >= 85 && mouseY <= 91) {
            tooltip.add(I18n.format("jei.ironbackpacks.increaseTier.name"));
            tooltip.add(TextFormatting.GRAY + I18n.format("jei.ironbackpacks.increaseTier.desc2"));
        }

        return tooltip;
    }
}
