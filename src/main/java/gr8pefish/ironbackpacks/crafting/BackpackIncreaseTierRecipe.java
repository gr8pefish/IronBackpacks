package gr8pefish.ironbackpacks.crafting;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.register.ItemBackpackRegistry;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.libs.IronBackpacksConstants;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;

/**
 * The recipe to upgrade a backpack to it's next tier (ex: iron -&gt; gold)
 */
public class BackpackIncreaseTierRecipe extends ShapedOreRecipe {

    private final ItemStack recipeOutput;

    public BackpackIncreaseTierRecipe(ItemStack recipeOutput, Object... items){
        super(recipeOutput, items);
        this.recipeOutput = recipeOutput;
    }

    /**
     * Simply gets the next tier backpack with the NBT data from the backpack in the crafting grid (so it keeps it's inventory/upgrades/etc.)
     * @param inventoryCrafting - the crafting inventory
     * @return - the itemstack result
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

        ItemStack result;
        ItemStack backpack = getFirstTieredBackpack(inventoryCrafting);
        if (backpack == null) return null;

        NBTTagCompound nbtTagCompound = backpack.getTagCompound();
        if (nbtTagCompound == null){
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ITEMS, new NBTTagList());
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, new NBTTagList());
            backpack.setTagCompound(nbtTagCompound);
        }

        //get the higher tier backpack if it exists
        List<ITieredBackpack> backpacksAbove = ItemBackpackRegistry.getBackpacksAbove(backpack);
        if (backpacksAbove != null && backpacksAbove.size() > 0) {
            result = new ItemStack((ItemBackpack)backpacksAbove.get(0)); //hardcoded, get the next backpack above it and typecast to ItemBackpack so it can make an itemstack
            result.setTagCompound(backpack.getTagCompound());
            return result;
        } else {
            return null;
        }

    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }

    /**
     * Helper method for getting the first backpack in the crafting grid (which will be the one used)
     * @param inventoryCrafting - the inventory to search
     * @return - the backpack to be crafted
     */
    private static ItemStack getFirstTieredBackpack(InventoryCrafting inventoryCrafting){
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);
                if (itemstack != null && (itemstack.getItem() instanceof ITieredBackpack))
                    return itemstack;
            }
        }
        return null;
    }
}
