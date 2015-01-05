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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BackpackTierRecipe implements IRecipe {

    /** Is the ItemStack that you get when craft the recipe. */
    private final ItemStack recipeOutput;
    /** Is a List of ItemStack that composes the recipe. */
    public final List recipeItems;

    public BackpackTierRecipe(ItemStack recipeOutput, List recipeItems){
        this.recipeItems = recipeItems;
        this.recipeOutput = recipeOutput;
    }

    public BackpackTierRecipe(ItemStack recipeOutput, Object... items){
        this(recipeOutput, getItemStacks(items));
    }

    public static List<Object> getItemStacks(Object[] items)
    {
        List<Object> recipeItems = new ArrayList<Object>();
        for (Object in : items)
        {
            System.out.println("GOT AN ITEM: "+in.toString());
            if (in instanceof ItemStack)
                recipeItems.add(((ItemStack) in).copy());
            else if (in instanceof Item)
                recipeItems.add(new ItemStack((Item) in));
            else if (in instanceof Block)
                recipeItems.add(new ItemStack((Block) in));
            else if (in instanceof String)
                recipeItems.add(OreDictionary.getOres((String) in));
            else
            {
                System.out.println("THE CULPRIT: "+in.toString());
                String ret = "Invalid shaped ore recipe: ";
                for (Object tmp : items)
                    ret += tmp + ", ";
                throw new RuntimeException(ret);
            }
        }
        return recipeItems;
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
    public boolean matches(InventoryCrafting inventoryCrafting, World world)
    {
        ArrayList<Object> required = new ArrayList<Object>(this.recipeItems);

        ItemStack backpack = getFirstBackpack(inventoryCrafting);
        if (backpack == null)
            return false;

        for (int x = 0; x < inventoryCrafting.getSizeInventory(); x++)
        {
            ItemStack slot = inventoryCrafting.getStackInSlot(x);

            if (slot != null)
            {
                boolean inRecipe = false;
                Iterator<Object> req = required.iterator();

                while (req.hasNext())
                {
                    boolean match = false;

                    Object next = req.next();

                    if (next instanceof ItemStack)
                    {
                        match = OreDictionary.itemMatches((ItemStack) next, slot, false);
                    }
                    else if (next instanceof ArrayList)
                    {
                        Iterator<ItemStack> itr = ((ArrayList<ItemStack>) next).iterator();
                        while (itr.hasNext() && !match)
                        {
                            match = OreDictionary.itemMatches(itr.next(), slot, false);
                        }
                    }

                    if (match)
                    {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe)
                {
                    return false;
                }
            }
        }

        return required.isEmpty();
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
    public int getRecipeSize() {
        return this.recipeItems.size();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }
}
