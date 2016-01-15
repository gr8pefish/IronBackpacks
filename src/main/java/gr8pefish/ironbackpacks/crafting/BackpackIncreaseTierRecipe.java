package gr8pefish.ironbackpacks.crafting;

import gr8pefish.ironbackpacks.items.backpacks.ItemBackpackSubItems;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;

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
        ItemStack backpack = getFirstBackpack(inventoryCrafting);
        NBTTagCompound nbtTagCompound = backpack.getTagCompound();

        if (nbtTagCompound == null){
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ITEMS, new NBTTagList());
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, new NBTTagList());
            backpack.setTagCompound(nbtTagCompound);
        }

        ItemBackpackSubItems backpackItem = (ItemBackpackSubItems)backpack.getItem();
        ArrayList<Item> backpacks = ItemRegistry.getBackpacks();
        result = new ItemStack(backpacks.get(backpackItem.getId()));
        result.setTagCompound(backpack.getTagCompound());

        return result;
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
    private static ItemStack getFirstBackpack(InventoryCrafting inventoryCrafting){
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);
                if (itemstack != null && (itemstack.getItem() instanceof IBackpack))
                    return itemstack;
            }
        }
        return null;
    }
}
