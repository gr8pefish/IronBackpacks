package gr8pefish.ironbackpacks.crafting;

import java.util.List;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.recipes.IIncreaseBackpackTierRecipe;
import gr8pefish.ironbackpacks.registry.RecipeRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

/**
 * The recipe to upgrade a backpack to it's next tier (ex: iron -&gt; gold)
 */
public class BackpackIncreaseTierRecipe extends Impl<IRecipe> implements IIncreaseBackpackTierRecipe {

    private final ItemStack recipeOutput;
    private final ShapedOreRecipe internal;

    public BackpackIncreaseTierRecipe(ItemStack recipeOutput, Object... items){
        internal = new ShapedOreRecipe(null, recipeOutput, items);
        this.recipeOutput = recipeOutput;
        this.setRegistryName(Constants.MODID, "recipe"+Constants.j++);
        RecipeRegistry.INCREASE.add(this);
    }

    /**
     * Simply gets the next tier backpack with the NBT data from the backpack in the recipes grid (so it keeps it's inventory/upgrades/etc.)
     * @param inventoryCrafting - the recipes inventory
     * @return - the itemstack result
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

        ItemStack result;
        ItemStack backpack = getFirstTieredBackpack(inventoryCrafting);
        if (backpack.isEmpty()) return ItemStack.EMPTY;

        NBTTagCompound nbtTagCompound = backpack.getTagCompound();
        if (nbtTagCompound == null){
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ITEMS, new NBTTagList());
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, new NBTTagList());
            backpack.setTagCompound(nbtTagCompound);
        }

        //get the higher tier backpack if it exists
        List<ITieredBackpack> backpacksAbove = null;
        if (backpack.getItem() instanceof ITieredBackpack && ((ITieredBackpack)backpack.getItem()).hasBackpacksAbove(backpack))
            backpacksAbove = ((ITieredBackpack)backpack.getItem()).getBackpacksAbove(backpack);

        if (backpacksAbove != null && backpacksAbove.size() > 0) {
            result = new ItemStack(recipeOutput.getItem());
            result.setTagCompound(backpack.getTagCompound());
            return result;
        } else {
            return ItemStack.EMPTY;
        }

    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }

    /**
     * Helper method for getting the first backpack in the recipes grid (which will be the one used)
     * @param inventoryCrafting - the inventory to search
     * @return - the backpack to be crafted
     */
    private static ItemStack getFirstTieredBackpack(InventoryCrafting inventoryCrafting){
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);
                if (!itemstack.isEmpty() && (itemstack.getItem() instanceof ITieredBackpack))
                    return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		return internal.matches(inv, world);
	}

	@Override
	public boolean canFit(int width, int height) {
		return internal.canFit(width, height);
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients(){
        return internal.getIngredients();
    }
}
