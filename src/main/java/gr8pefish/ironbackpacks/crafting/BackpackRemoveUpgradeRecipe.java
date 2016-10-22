package gr8pefish.ironbackpacks.crafting;

import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IUpgradableBackpack;
import gr8pefish.ironbackpacks.api.items.upgrades.ItemIUpgrade;
import gr8pefish.ironbackpacks.api.recipes.IRemoveUpgradeRecipe;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.container.backpack.ContainerBackpack;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Deals with the cases when a backpack is shapelessly crafted alone to remove an upgrade.
 */
public class BackpackRemoveUpgradeRecipe extends ShapelessOreRecipe implements IRemoveUpgradeRecipe {

    private ItemStack recipeOutput; //The outputted items after recipes

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
     * Then it checks for where the backpack is located in the recipes grid.
     * It then removes the upgrade in said slot. So if it is in the 2nd slot then it removes the 2nd upgrade on the backpack.
     *
     * @param inventoryCrafting - the inventory recipes to check
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
            return null; //no output itemStack, i.e. no recipes result

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
            upgradeInQuestion = upgrades.get(slotOfBackpack);

            //can't remove it if is a nesting upgrade and there are nested backpacks inside
            //ToDo: Give descriptive error message to player
            if (!canRemoveNestingUpgrade(backpack, upgradeInQuestion)) {
                upgradeInQuestion = null;
            } else {
                nullChecksPassed = true;
            }

        }

        //init variables for the return stack
        boolean upgradeRemoved = false;
        NBTTagList tagList = new NBTTagList();

        for (ItemStack upgrade : upgrades) { //for each slot in possible upgrades
            if (nullChecksPassed && (UpgradeMethods.areUpgradesFunctionallyEquivalent(upgrade, upgradeInQuestion))) { //same upgrade, remove it
                upgradeRemoved = true;
                //not adding the old recipe is the same outcome as removing the recipe, so no code needed here
                if (ItemIUpgradeRegistry.isInstanceOfIConfigurableUpgrade(upgradeInQuestion)) //if in alt gui need to remove the stored items there
                    nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.REMOVED_ALT_GUI, upgradeInQuestion.writeToNBT(new NBTTagCompound())); //add items stack to nbt key
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
     * Helper method for getting the first backpack in the recipes grid (which will be the one used)
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
     * Find an empty slot in the recipes grid to put the removed upgrade in.
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

    /**
     * Checks if the upgrade is a nesting or advanced nesting, and then checks if the backpack has any nested inside of it, because if it does the upgrade should be impossible to remove.
     * @param backpack - the backpack to remove the upgrade from
     * @param upgrade - the upgrade in question
     * @return true if it can be removed, false otherwise
     */
    private boolean canRemoveNestingUpgrade(ItemStack backpack, ItemStack upgrade) {
        if (ItemIUpgradeRegistry.isInstanceOfIConflictingUpgrade(upgrade)) {
            if (ItemIUpgradeRegistry.getItemIConflictingUpgrade(upgrade.getItemDamage()).equals(ItemRegistry.nestingUpgrade) || ItemIUpgradeRegistry.getItemIConflictingUpgrade(upgrade.getItemDamage()).equals(ItemRegistry.nestingAdvancedUpgrade)) {
                //check if has backpack of any tier inside
                InventoryBackpack inventoryBackpack = new InventoryBackpack(backpack, true);
                for (int i = 0; i < inventoryBackpack.getSizeInventory(); i++){
                    if (inventoryBackpack.getStackInSlot(i) != null && inventoryBackpack.getStackInSlot(i).getItem() instanceof ItemBackpack) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
