package gr8pefish.ironbackpacks.crafting;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IUpgradableBackpack;
import gr8pefish.ironbackpacks.api.register.ItemUpgradeRegistry;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

/**
 * Deals with the cases when a backpack is shapelessly crafted alone to remove an upgrade.
 */
public class BackpackRemoveUpgradeRecipe extends ShapelessOreRecipe {

    private final ItemStack recipeOutput; //The outputted item after crafting

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
                    nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.REMOVED, upgradeInQuestion.writeToNBT(new NBTTagCompound())); //add item stack to nbt key
            } else { //save old contents to new tag
                tagList.appendTag(upgrade.writeToNBT(new NBTTagCompound()));
            }
        }

        //set the new tag compound and return the new stack if it has changed
        nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, tagList);
        if (upgradeRemoved) {
            return result;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }

    //=============================================================================Helper Methods====================================================================

    /**
     * Helper method for getting the first backpack in the crafting grid (which will be the one used)
     * @param inventoryCrafting - the inventory to search
     * @return - the integer of the slot number that the backpack is in
     */
    private int getFirstUpgradableBackpackSlotNumber(InventoryCrafting inventoryCrafting) {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemstack = inventoryCrafting.getStackInSlot(i);
            if (itemstack != null && (itemstack.getItem() instanceof IUpgradableBackpack))
                return i;
        }
        return -1;
    }

}
