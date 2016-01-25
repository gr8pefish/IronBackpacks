package gr8pefish.ironbackpacks.crafting;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IUpgradableBackpack;
import gr8pefish.ironbackpacks.api.register.ItemUpgradeRegistry;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.Logger;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Deals with the cases when a backpack is shapelessly crafted alone to remove an upgrade.
 */
public class BackpackRemoveUpgradeRecipe extends ShapelessOreRecipe {

    private final ItemStack recipeOutput; //The outputted item after crafting

    private ItemStack upgradeRemovedStack;

    public BackpackRemoveUpgradeRecipe(ItemStack recipeOutput, Object... items) {
        super(recipeOutput, items);
        this.recipeOutput = recipeOutput;
    }

    /**
     * Crafts the backpack by itself to remove an upgrade;
     * First it checks if the backpack has any upgrades.
     * If it does it progresses, otherwise it returns null;
     * <p/>
     * Then it checks for where the backpack is located in the crafting grid.
     * It then removes the upgrade in said slot. So if it is in the 2nd slot then it removes the 2nd upgrade on the backpack.
     *
     * @param inventoryCrafting - the inventory crafting to check
     * @return - the resulting itemstack
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

        int slotOfBackpack = getFirstUpgradableBackpackSlotNumber(inventoryCrafting);
        if (slotOfBackpack == -1) //if no backpack
            return null; //return no output

        //get the backpack
        ItemStack backpack = inventoryCrafting.getStackInSlot(slotOfBackpack);
        ItemStack result = backpack.copy();

        //get the upgrades
        ArrayList<ItemStack> upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(result);
        if (upgrades.isEmpty()) //no upgrades
            return null; //no output itemStack, i.e. no crafting result

        //get the old tag compound
        NBTTagCompound nbtTagCompound = result.getTagCompound();
        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ITEMS, new NBTTagList());
            result.setTagCompound(nbtTagCompound);
        }

        //make sure that we can check for an upgrade to remove
        boolean nullChecksPassed = false;
        ItemStack upgradeInQuestion = null;
        if ((slotOfBackpack <= (upgrades.size() - 1)) && (slotOfBackpack >= 0) && (upgrades.get(slotOfBackpack) != null)) {
            nullChecksPassed = true;
            upgradeInQuestion = upgrades.get(slotOfBackpack);
        }

        //init variables for the return stack
        boolean upgradeRemoved = false;
        NBTTagList tagList = new NBTTagList();

        for (ItemStack upgrade : upgrades) { //for each slot in possible upgrades
            if (nullChecksPassed && (UpgradeMethods.areUpgradesFunctionallyEquivalent(upgrade, upgradeInQuestion))) { //same upgrade, remove it
                upgradeRemoved = true;
                //not adding the old recipe is the same outcome as removing the recipe, so no code needed here
                if (ItemUpgradeRegistry.isInstanceOfAltGuiUpgrade(upgradeInQuestion)) //if in alt gui need to remove the stored items there
                    nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.REMOVED_ALT_GUI, upgradeInQuestion.writeToNBT(new NBTTagCompound())); //add item stack to nbt key
            } else { //save old contents to new tag
                tagList.appendTag(upgrade.writeToNBT(new NBTTagCompound()));
            }
        }

        //set the new tag compound and return the new stack if it has changed
        nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, tagList);
        if (upgradeRemoved) {
            upgradeRemovedStack = upgradeInQuestion;
            return result;
        } else {
            upgradeRemovedStack = null;
            return null;
        }
    }

    @Override //copied directly from ShapelessOreRecipe
    public boolean matches(InventoryCrafting var1, World world)
    {
        ArrayList<Object> required = new ArrayList<Object>(input);

        for (int x = 0; x < var1.getSizeInventory(); x++)
        {
            ItemStack slot = var1.getStackInSlot(x);

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
                        match = OreDictionary.itemMatches((ItemStack)next, slot, false);
                    }
                    else if (next instanceof List)
                    {
                        Iterator<ItemStack> itr = ((List<ItemStack>)next).iterator();
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
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv){ //needs matches overridden due to (Forge?) bug
        if (upgradeRemovedStack != null){
            ItemStack[] ret = new ItemStack[inv.getSizeInventory()];
            ret[0] = upgradeRemovedStack.copy();
            for (int i = 1; i < ret.length; i++) {
                ret[i] = null; //remove everything else (i.e can't leave backpack)
            }
            return ret;
        }else{
            return super.getRemainingItems(inv);
        }
    }

    //=============================================================================Helper Methods====================================================================

    /**
     * Helper method for getting the first backpack in the crafting grid (which will be the one used)
     * @param inventoryCrafting - the inventory to search
     * @return - the integer of the slot number that the backpack is in (-1 if no slot found)
     */
    private int getFirstUpgradableBackpackSlotNumber(InventoryCrafting inventoryCrafting) {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemstack = inventoryCrafting.getStackInSlot(i);
            if (itemstack != null && (itemstack.getItem() instanceof IUpgradableBackpack))
                return i;
        }
        return -1;
    }

    /**
     * Find an empty slot in the crafting grid to put the removed upgrade in.
     * @param inventoryCrafting - the inventory to search
     * @return - the integer of the slot number of the empty slot (-1 if no slot found)
     */
    public static int findEmptySlot(IInventory inventoryCrafting) {
        for (int i = 0; i < 9; i++) {
            ItemStack itemstack = inventoryCrafting.getStackInSlot(i);
            if (itemstack == null)
                return i;
        }
        return -1;
    }

}
