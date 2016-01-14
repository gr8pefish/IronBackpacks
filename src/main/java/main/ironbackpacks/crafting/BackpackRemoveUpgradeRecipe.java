package main.ironbackpacks.crafting;

import main.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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

        int slotOfBackpack = getFirstBackpackSlotNumber(inventoryCrafting);
        if (slotOfBackpack == -1) //if no backpack
            return null; //return no output

        //get the backpack
        ItemStack backpack = inventoryCrafting.getStackInSlot(slotOfBackpack);
        ItemStack result = backpack.copy();

        //get the upgrades
        int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(result);
        if (upgrades.length == 0) //no upgrades
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
        int upgradeInQuestion = -1;
        if ((slotOfBackpack <= (upgrades.length - 1)) && (slotOfBackpack >= 0) && (upgrades[slotOfBackpack] > 0)) {
            nullChecksPassed = true;
            upgradeInQuestion = upgrades[slotOfBackpack];
        }

        //init variables for the return stack
        boolean upgradeRemoved = false;
        NBTTagList tagList = new NBTTagList();

        for (int upgrade : upgrades) { //for each slot in possible upgrades
            if (nullChecksPassed && (upgrade == upgradeInQuestion)) { //same upgrade, remove it
                upgradeRemoved = true;
                //not adding the old recipe is the same outcome as removing the recipe, so no code needed here
                if (IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.contains(upgradeInQuestion)) //if in alt gui need to remove the stored items there
                    nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.REMOVED, new NBTTagInt(IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.indexOf(upgradeInQuestion))); //int value of upgrade removed
            } else { //save old contents to new tag
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte(IronBackpacksConstants.NBTKeys.UPGRADE, (byte) upgrade);
                tagList.appendTag(tagCompound);
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
    private int getFirstBackpackSlotNumber(InventoryCrafting inventoryCrafting) {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemstack = inventoryCrafting.getStackInSlot(i);
            if (itemstack != null && (itemstack.getItem() instanceof IBackpack))
                return i;
        }
        return -1;
    }

}
