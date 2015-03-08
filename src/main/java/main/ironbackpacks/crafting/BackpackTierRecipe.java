package main.ironbackpacks.crafting;

import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;

public class BackpackTierRecipe extends ShapedOreRecipe {

    //The Recipe to upgrade a backpack to it's next tier

    private final ItemStack recipeOutput;

    public BackpackTierRecipe(ItemStack recipeOutput, Object... items){
        super(recipeOutput, items);
        this.recipeOutput = recipeOutput;
    }

    public static ItemStack getFirstBackpack(InventoryCrafting inventoryCrafting){ // helper method for getting the first backpack in the crafting grid (which will be the output)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);
                if (itemstack != null && (itemstack.getItem() instanceof ItemBaseBackpack))
                    return itemstack;
            }
        }
        return null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

        //simply gets the next tier backpack with the NBT data from the backpack in the crafting grid (so it keeps it's inventory/upgrades/etc.)

        ItemStack result;
        ItemStack backpack = getFirstBackpack(inventoryCrafting);
        NBTTagCompound nbtTagCompound = backpack.getTagCompound();

        if (nbtTagCompound == null){
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ITEMS, new NBTTagList());
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, new NBTTagList());
            backpack.setTagCompound(nbtTagCompound);
        }

        ItemBaseBackpack backpackItem = (ItemBaseBackpack)backpack.getItem();
        ArrayList<Item> backpacks = ItemRegistry.getBackpacks();
        result = new ItemStack(backpacks.get(backpackItem.getTypeId()));
        result.setTagCompound(backpack.getTagCompound());

        return result;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }
}
