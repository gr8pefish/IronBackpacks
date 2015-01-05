package main.ironbackpacks.crafting;

import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BackpackTierRecipe extends ShapedOreRecipe {

    /** Is the ItemStack that you get when craft the recipe. */
    private final ItemStack recipeOutput;

    public BackpackTierRecipe(ItemStack recipeOutput, Object... items){
        super(recipeOutput, items);
        this.recipeOutput = recipeOutput;
    }

    public static ItemStack getFirstBackpack(InventoryCrafting inventoryCrafting)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);

                if (itemstack != null && (itemstack.getItem() instanceof ItemBaseBackpack))
                    return itemstack;
            }
        }

        return null;
    }


    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

        ItemStack result;
        ItemStack backpack = getFirstBackpack(inventoryCrafting);
        NBTTagCompound nbtTagCompound = backpack.getTagCompound();

        if (nbtTagCompound == null){
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag("Items", new NBTTagList());
            nbtTagCompound.setTag("Upgrades", new NBTTagList());
            backpack.setTagCompound(nbtTagCompound);
        }

        ItemBaseBackpack backpackItem = (ItemBaseBackpack)backpack.getItem();
        Item[] backpacks = ItemRegistry.getBackpacks();
        result = new ItemStack(backpacks[backpackItem.getTypeId()]);
        result.setTagCompound(backpack.getTagCompound());

        return result;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }
}
