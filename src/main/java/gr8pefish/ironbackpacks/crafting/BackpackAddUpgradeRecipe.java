package gr8pefish.ironbackpacks.crafting;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IUpgradableBackpack;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemConflictingUpgrade;
import gr8pefish.ironbackpacks.api.item.upgrades.interfaces.IPackUpgrade;
import gr8pefish.ironbackpacks.api.register.ItemUpgradeRegistry;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgrade;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.IronBackpacksHelper;
import gr8pefish.ironbackpacks.util.Logger;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Deals with the cases when a backpack is shapelessly crafted with an upgrade.
 */
public class BackpackAddUpgradeRecipe extends ShapelessOreRecipe {

    private final ItemStack recipeOutput; //The outputted item after crafting

    public BackpackAddUpgradeRecipe(ItemStack recipeOutput, Object... items){
        super(recipeOutput, items);
        this.recipeOutput = recipeOutput;
    }

    /**
     * Crafts the backpack with the upgrade, with some special cases recognized.
     * First it checks if the backpack has enough upgrade points available to apply said upgrade to the backpack.
     * If it has enough points available it progresses, otherwise it returns null;
     *
     * Then it checks for special cases, listed below:
     * You can't have more than the config amount of 'additional upgrade points' upgrades applied.
     * You can't have conflicting upgrades (as defined by each IConflictingUpgrade).
     * You can only have a certain amount of alternate gui upgrades. Currently 'hardcoded' as 4, see IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED
     *
     * @param inventoryCrafting - the inventory crafting to check
     * @return - the resulting itemstack
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

        ItemStack backpack = getFirstUpgradableBackpack(inventoryCrafting); //get the upgradable backpack in the crafting grid
        if (backpack == null) return null; //if no valid backpack return nothing
        ItemStack result = backpack.copy(); //the resulting backpack, copied so it's data can be more easily manipulated

        ArrayList<ItemStack> upgrades = ItemBackpack.getUpgrades(result); //get the upgrades
        int totalUpgradePoints = IronBackpacksHelper.getTotalUpgradePointsFromNBT(result); //get the total upgrade points available to the backpack

        ItemStack upgradeToApply = getFirstUpgrade(inventoryCrafting); //get the upgrade the player is attempting to apply to the backpack

        //save all the items from the old pack
        NBTTagCompound nbtTagCompound = result.getTagCompound();
        if (nbtTagCompound == null){
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ITEMS, new NBTTagList());
            result.setTagCompound(nbtTagCompound);
        }

        boolean upgradeFound = false; //too determine if you need to return a new backpack in the output slot
        NBTTagList tagList = new NBTTagList(); //the upgrade data base tag

        if (totalUpgradePoints != 0 && upgradeToApply != null) { //if have more than zero upgrade slots
            if (upgrades.size() == 0){ //if no upgrades applied
                if (ItemUpgrade.areUpgradesEqual(upgradeToApply, ItemRegistry.additionalUpgradePointsUpgrade)){ //if the upgrade is an additional upgrade points upgrade
                    upgradeFound = applyAdditional(nbtTagCompound, result); //if you can apply more upgrade points, do it
                } else { //some other upgrade (i.e. not additional upgrade points)
                    if (IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints) { //if you have enough upgrade points to apply it
                        if (ItemUpgradeRegistry.isInstanceOfAltGuiUpgrade(upgradeToApply)) //if it is an alt gui upgrade
                            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDED_ALT_GUI, upgradeToApply.writeToNBT(new NBTTagCompound())); //make sure to update so the alt gui can change when it is opened
                        tagList.appendTag(upgradeToApply.writeToNBT(new NBTTagCompound())); //save the new upgrade
                        upgradeFound = true; //you applied an upgrade, congratulations
                    }
                }
            } else { //upgrades have been applied
                if (ItemUpgrade.areUpgradesEqual(upgradeToApply, ItemRegistry.additionalUpgradePointsUpgrade)) { //if the upgrade is an additional upgrade points upgrade
                    upgradeFound = applyAdditional(nbtTagCompound, result); //if you can apply more upgrade points, do it
                }
                for (ItemStack upgrade : upgrades) { //for each upgrade in possible upgrades
                    tagList.appendTag(upgrade.writeToNBT(new NBTTagCompound())); //save old contents to new tag (transfer over the data, essentially)
                }
                if (!upgradeFound && !(ItemUpgrade.areUpgradesEqual(upgradeToApply, ItemRegistry.additionalUpgradePointsUpgrade))){ //if not already applied
                    if (canApplyUpgrade(upgrades, totalUpgradePoints, upgradeToApply)){ //if you can apply the upgrade (this checks special conditions (i.e. IConflictingUpgrades) too)
                        if (ItemUpgradeRegistry.isInstanceOfAltGuiUpgrade(upgradeToApply)) //if it is an alt gui upgrade
                            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDED_ALT_GUI, upgradeToApply.writeToNBT(new NBTTagCompound())); //make sure to update so the alt gui can change when it is opened
                        tagList.appendTag(upgradeToApply.writeToNBT(new NBTTagCompound())); //save the new upgrade
                        upgradeFound = true; //you applied an upgrade, congratulations
                    }
                }
            }
        } else if (upgradeToApply != null && ItemUpgrade.areUpgradesEqual(upgradeToApply, ItemRegistry.additionalUpgradePointsUpgrade)){ //if no upgrade points you could apply to get more upgrade points (corner case)
            upgradeFound = applyAdditional(nbtTagCompound, result); //if you can apply more upgrade points, do it
        }

        nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, tagList); //set the tag with all the upgrade that were just updated
        if (upgradeFound) { //if you applied an upgrade
            return result; //return the new backpack
        } else { //otherwise
            return null; //return nothing
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
     * @return - the backpack to be crafted
     */
    private static ItemStack getFirstUpgradableBackpack(InventoryCrafting inventoryCrafting) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);
                if (itemstack != null && (itemstack.getItem() instanceof IUpgradableBackpack)) {
                    return itemstack;
                }
            }
        }
        return null;
    }

    /**
     * Helper method for getting the first upgrade in the crafting grid (which will be the one used)
     * @param inventoryCrafting - the inventory to search
     * @return - the upgrade to be used
     */
    private static ItemStack getFirstUpgrade(InventoryCrafting inventoryCrafting){
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);
                if (itemstack != null && itemstack.getItem() != null)
                    if (itemstack.getItem() instanceof ItemUpgrade)  //hardcoded for ItemUpgrade
                        if (ItemUpgradeRegistry.isInstanceOfAnyUpgrade(itemstack)) //any upgrade is fine here
                            return itemstack;
            }
        }
        return null;
    }

    /**
     * Checks the special conditions to see if the upgrade can be applied. Also checks if the backpack has sufficient available upgrade points to apply the upgrade.
     * @param upgrades - the upgrades already on the backpack
     * @param totalUpgradePoints - the total upgrade points on the backpack
     * @param upgradeToApply - the upgradeToApply as an itemstack
     * @return - true if it can be applied, false otherwise
     */
    private boolean canApplyUpgrade(ArrayList<ItemStack> upgrades, int totalUpgradePoints, ItemStack upgradeToApply){
        if (ItemUpgradeRegistry.isInstanceOfConflictingUpgrade(upgradeToApply) || ItemUpgradeRegistry.isInstanceOfAltGuiUpgrade(upgradeToApply)){
            if (ItemUpgradeRegistry.isInstanceOfConflictingUpgrade(upgradeToApply)){ //conflicting
                if (hasConflictingUpgradeInUpgrades(upgradeToApply, upgrades)){ //if has the conflicting upgrade
                    return false; //can't apply conflicting
                } else { //no conflicting one tried to be applied
                    return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints; //if you have the upgrade points
                }
            } else{ //alt gui upgrade
                if (UpgradeMethods.getAltGuiUpgradesUsed(upgrades) + 1 <= IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED){ //if you can fit the alt gui
                    return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints; //if you have the upgrade points
                } else { //you can't fit another alternate gui upgrade
                    return false; //can't apply it
                }
            }
        } else { //normal upgrade
            for (ItemStack upgrade : upgrades) { //check for duplicate
                if ((upgrade.getItem().equals(upgradeToApply.getItem()) && (upgrade.getItemDamage() == upgradeToApply.getItemDamage()))) //if duplicate upgrade
                    return false; //can't apply
            }
            return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints; //if you have the upgrade points
        }
    }

    /**
     * Check if the backpack's upgrades contain a conflicting upgrade (relative to the upgradeToApply itemStack).
     * @param upgradeToApply - the upgrade that is attempted to be applied
     * @param upgrades - the current upgrades on the pack
     * @return - true if it has a conflicting upgrade, false otherwise
     */
    private boolean hasConflictingUpgradeInUpgrades(ItemStack upgradeToApply, ArrayList<ItemStack> upgrades) {
        List<ItemConflictingUpgrade> conflictingUpgrades = ItemUpgradeRegistry.getItemConflictingUpgrade(upgradeToApply).getConflictingUpgrades(upgradeToApply);
        for (ItemStack stack : upgrades){ //for every upgrade
            if (ItemUpgradeRegistry.isInstanceOfConflictingUpgrade(stack)){ //if it is an instance of a conflicting upgrade
                if (conflictingUpgrades.contains(ItemUpgradeRegistry.getItemConflictingUpgrade(stack))){ //if it specifically conflicts with this upgrade applied
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Applies the upgrade to the backpack by adding it's NBT data.
     * @param nbtTagCompound - the tag compound of the resulting itemstack
     * @param backpack - the backpack in the crafting grid
     * @return - true if it can be applied, false otherwise
     */
    //TODO: I have no idea what this does, and I wrote it O_o That may cause some issues, as this is confusing NBT saving.
    private boolean applyAdditional(NBTTagCompound nbtTagCompound, ItemStack backpack){
        ItemBackpack backpackBase = (ItemBackpack) backpack.getItem();
        if (backpackBase == null) return false;
        if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS)){
            int[] oldValuesArray = nbtTagCompound.getIntArray(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS);
            if (oldValuesArray[1] < ConfigHandler.additionalUpgradePointsLimit + backpackBase.getGuiId(backpack)){
                nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS, new NBTTagIntArray(new int[]{ConfigHandler.additionalUpgradePointsIncrease + oldValuesArray[0], ++oldValuesArray[1]})); //[pointsAdded, upgradesApplied]
                return true;
            }

        } else {
            if (ConfigHandler.additionalUpgradePointsLimit + backpackBase.getGuiId(backpack) > 0) {
                nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS, new NBTTagIntArray(new int[]{ConfigHandler.additionalUpgradePointsIncrease, 1})); //[pointsAdded, upgradesApplied]
                return true;
            }
        }
        return false;
    }

}
