package gr8pefish.ironbackpacks.core.recipe;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackVariant;
import gr8pefish.ironbackpacks.util.Utils;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

/**
 * Class for defining how the recipes work for coloring backpacks.
 *
 * Currently:
 * - Backpack + Dye = Colored Backpack, to the color of the dye
 * - Colored Backpack + Water Bucket = Uncolored Backpack
 */
public class ColorBackpackRecipe extends ShapedOreRecipe {

    public ColorBackpackRecipe(@Nonnull BackpackVariant backpackVariant, Object... recipe) {
        super(new ResourceLocation(IronBackpacks.MODID, "color"), IronBackpacksAPI.getStack(backpackVariant), recipe);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting matrix) {
        ItemStack backpack = RecipeUtil.getFirstBackpackInGrid(matrix);
        if (backpack.isEmpty())
            return super.getCraftingResult(matrix);

        ItemStack upgraded = getRecipeOutput().copy();
        BackpackInfo upgradedInfo = Utils.getBackpackInfoFromStack(backpack);
        upgradedInfo.setRGBColor(getDyeColor(matrix));

        return IronBackpacksAPI.applyPackInfo(upgraded, upgradedInfo);
    }

    // Helper

    /**
     * Gets the color to dye the backpack from a crafting grid.
     *
     * @param matrix - the crafting inventory to search
     * @return - a RGB color as an {@link int}
     */
    private int getDyeColor(@Nonnull InventoryCrafting matrix) {
        ItemStack stack = getFirstDyeOrWaterBucketInGrid(matrix);
        if (stack.isEmpty()) return BackpackInfo.NO_COLOR; //Error, return no color
        if (stack.getItem().equals(Items.WATER_BUCKET)) return BackpackInfo.NO_COLOR; //Wash away color
        return DyeUtils.colorFromStack(stack).map(EnumDyeColor::getColorValue).orElse(BackpackInfo.NO_COLOR); //Get dye RGB color and return it if possible, otherwise return no color
    }

    /**
     * Gets the first {@link net.minecraftforge.oredict.OreDictionary} dye or Item.WATER_BUCKET ItemStack in a crafting grid.
     *
     * @param matrix - the crafting inventory to search
     * @return - a ItemStack which is known to be a backpack
     */
    @Nonnull
    private static ItemStack getFirstDyeOrWaterBucketInGrid(@Nonnull InventoryCrafting matrix) {
        ItemStack stack = ItemStack.EMPTY;
        for (int i=0; i < matrix.getSizeInventory(); i++ ) {
            stack = matrix.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (DyeUtils.isDye(stack))  {
                    return stack;
                }
                if (stack.getItem().equals(Items.WATER_BUCKET)) {
                    return stack;
                }
            }
        }
        return stack;
    }



}
