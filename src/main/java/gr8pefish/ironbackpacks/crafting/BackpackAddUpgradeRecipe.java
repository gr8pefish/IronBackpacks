package gr8pefish.ironbackpacks.crafting;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.item.backpacks.abstractClasses.AbstractUpgradablePack;
import gr8pefish.ironbackpacks.api.item.backpacks.abstractClasses.AbstractUpgradableTieredBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IUpgradableBackpack;
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
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

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
     * You can't have more than the config amount of 'additional upgrade' upgrades applied.
     * You can't have multiple upgrades of the same type. If you try to apply the upgrade twice it will return the backpack with teh upgrade removed instead (which is how you remove upgrades).
     * You can't have both an advanced nesting upgrade and a nesting upgrade on the same backpack.
     *
     * @param inventoryCrafting - the inventory crafting to check
     * @return - the resulting itemstack
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

        ItemStack backpack = getFirstUpgradableBackpack(inventoryCrafting);
        if (backpack == null) return null; //if no valid backpack (i.e. an upgradable one, return nothing)
        ItemStack result = backpack.copy();

        ArrayList<ItemStack> upgrades = ItemBackpack.getUpgrades(result);
//        int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(result); //ex 1,2
        int totalUpgradePoints = IronBackpacksHelper.getTotalUpgradePointsFromNBT(result); //ex: 3

        ItemStack upgradeToApply = getFirstUpgrade(inventoryCrafting);
//        ItemUpgrade upgradeToApplyBase = null;
//        if (upgradeToApply != null) {
//            System.out.println(upgradeToApply.getDisplayName());
//            upgradeToApplyBase = (ItemUpgrade) upgradeToApply.getItem();
//        }

        NBTTagCompound nbtTagCompound = result.getTagCompound();
        if (nbtTagCompound == null){
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ITEMS, new NBTTagList());
            result.setTagCompound(nbtTagCompound);
        }

        boolean upgradeFound = false; //will put upgrade in first valid slot
        NBTTagList tagList = new NBTTagList();

        if (totalUpgradePoints != 0 && upgradeToApply != null) { //if have more than zero upgrade slots
            if (upgrades.size() == 0){ //if no upgrades applied, apply upgrade
                if (ItemUpgrade.areUpgradesEqual(upgradeToApply, ItemRegistry.additionalUpgradePointsUpgrade)){ //ItemUpgrade.getId(upgradeToApply) == IronBackpacksConstants.Upgrades.ADDITIONAL_UPGRADE_POINTS_UPGRADE_ID){
                    upgradeFound = applyAdditional(nbtTagCompound, result);
                }else {
                    if (IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints) {
//                        if (IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.contains(ItemUpgrade.getId(upgradeToApply)))
                        if (ItemUpgradeRegistry.isInstanceOfAltGuiUpgrade(upgradeToApply)){
                            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDED_ALT_GUI, upgradeToApply.writeToNBT(new NBTTagCompound()));
//                            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDED, new NBTTagCompound(); //int value of upgrade added
                        }
//                        NBTTagCompound tagCompound = new NBTTagCompound();
//                        tagCompound.setByte(IronBackpacksConstants.NBTKeys.UPGRADE, (byte) ItemUpgrade.getId(upgradeToApply));
//                        tagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADE, upgradeToApply.writeToNBT(new NBTTagCompound()));
//                        upgradeToApply.writeToNBT(tagCompound);
                        tagList.appendTag(upgradeToApply.writeToNBT(new NBTTagCompound()));
                        upgradeFound = true;
                    }
                }
            }else { //upgrades have been applied
                if (ItemUpgrade.areUpgradesEqual(upgradeToApply, ItemRegistry.additionalUpgradePointsUpgrade)) {
                    upgradeFound = applyAdditional(nbtTagCompound, result);
                }
                for (ItemStack upgrade : upgrades) { //for each upgrade in possible upgrades
//                    if (!upgradeFound && shouldRemove(upgrade, upgradeToApply)){
//                        //not adding the old recipe is the same outcome as removing the recipe, so no code needed here
//                        upgradeFound = true;
//                        if (IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.contains(ItemUpgrade.getId(upgradeToApply)))
//                            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.REMOVED, new NBTTagInt(IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.indexOf(ItemUpgrade.getId(upgradeToApply)))); //int value of upgrade removed
                    //save old contents to new tag
//                    NBTTagCompound tagCompound = new NBTTagCompound();
//                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.UPGRADE, (byte) upgrade);
                    tagList.appendTag(upgrade.writeToNBT(new NBTTagCompound())); //save old contents to new tag (transfer over the data, essentially)
                }
                if (!upgradeFound && !(ItemUpgrade.areUpgradesEqual(upgradeToApply, ItemRegistry.additionalUpgradePointsUpgrade))){ //if not already applied
                    if (canApplyUpgrade(upgrades, totalUpgradePoints, upgradeToApply)){
                        if (ItemUpgradeRegistry.isInstanceOfAltGuiUpgrade(upgradeToApply))
                            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDED_ALT_GUI, upgradeToApply.writeToNBT(new NBTTagCompound()));
//                        NBTTagCompound tagCompound = new NBTTagCompound();
//                        tagCompound.setByte(IronBackpacksConstants.NBTKeys.UPGRADE, (byte) ItemUpgrade.getId(upgradeToApply));
//                        tagList.appendTag(tagCompound);
                        tagList.appendTag(upgradeToApply.writeToNBT(new NBTTagCompound()));
                        upgradeFound = true;
                    }
                }
            }
        }else if (upgradeToApply != null && ItemUpgrade.areUpgradesEqual(upgradeToApply, ItemRegistry.additionalUpgradePointsUpgrade)){
            upgradeFound = applyAdditional(nbtTagCompound, result);
        }

        nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, tagList);
        if (upgradeFound) {
            return result;
        }else{
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
                if (itemstack != null && (ItemUpgradeRegistry.isInstanceOfAnyUpgrade(itemstack))) {
                    return itemstack;
                }
            }
        }
        return null;
    }

    private boolean canApplyUpgrade(ArrayList<ItemStack> upgrades, int totalUpgradePoints, ItemStack upgradeToApply){
        if (ItemUpgradeRegistry.isInstanceOfConflictingUpgrade(upgradeToApply) || ItemUpgradeRegistry.isInstanceOfAltGuiUpgrade(upgradeToApply)){
            if (ItemUpgradeRegistry.isInstanceOfConflictingUpgrade(upgradeToApply)){ //conflicting
                if (UpgradeMethods.hasConflictingUpgradeInUpgrades(upgradeToApply, upgrades)){ //if has the conflicting upgrade
                    return false; //can't apply conflicting
                } else { //no conflicting one tried to be applied
                    return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints; //if you have the upgrade points
                }
            }else{ //alt gui upgrade
                if (UpgradeMethods.getAltGuiUpgradesUsed(upgrades) + 1 <= IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED){ //if you can fit the alt gui
                    return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints; //if you have the upgrade points
                } else { //you can't fit another alternate gui upgrade
                    return false; //can't apply it
                }
            }
        } else { //normal upgrade
            return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints; //if you have the upgrade points
        }
    }

    /**
     * Checks the special conditions to see if the upgrade can be applied. Also checks if the backpack has sufficient available upgrade points to apply the upgrade.
     * @param upgrades - the upgrades already on the backpack
     * @param totalUpgradePoints - the total upgrade points on the backpack
     * @param upgradeToApply - the upgradeToApply as an itemstack
     * @return - true if it can be applied, false otherwise
     */
//    @Deprecated
//    private boolean canApplyUpgrade(int[] upgrades, int totalUpgradePoints, ItemStack upgradeToApply){
//        if (IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.contains(ItemUpgrade.getId(upgradeToApply))) { //alt gui upgrade
//            if (UpgradeMethods.getAltGuiUpgradesUsed(upgrades) + 1 <= IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED) { //alt gui in general
//                return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints;
//            }
//            return false;
//        }else if(ItemUpgrade.getId(upgradeToApply) == IronBackpacksConstants.Upgrades.ADVANCED_NESTING_UPGRADE_ID || ItemUpgrade.getId(upgradeToApply) == IronBackpacksConstants.Upgrades.NESTING_UPGRADE_ID) { //have to choose between nesting upgrade and advanced nesting upgrade
//            if (ItemUpgrade.getId(upgradeToApply) == IronBackpacksConstants.Upgrades.ADVANCED_NESTING_UPGRADE_ID) {
//                for (int upgrade : upgrades) {
//                    if (upgrade == IronBackpacksConstants.Upgrades.NESTING_UPGRADE_ID) {
//                        return false;
//                    }
//                }
//                return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints;
//            } else {
//                for (int upgrade : upgrades) {
//                    if (upgrade == IronBackpacksConstants.Upgrades.ADVANCED_NESTING_UPGRADE_ID) {
//                        return false;
//                    }
//                }
//                return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints;
//            }
//        }else if(ItemUpgrade.getId(upgradeToApply) == IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_UPGRADE_ID || ItemUpgrade.getId(upgradeToApply) == IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_UPGRADE_ID) { //have to choose between quick deposit normal and precise
//            if (ItemUpgrade.getId(upgradeToApply) == IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_UPGRADE_ID) {
//                for (int upgrade : upgrades) {
//                    if (upgrade == IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_UPGRADE_ID) {
//                        return false;
//                    }
//                }
//                return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints;
//            } else {
//                for (int upgrade : upgrades) {
//                    if (upgrade == IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_UPGRADE_ID) {
//                        return false;
//                    }
//                }
//                return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints;
//            }
//        }else if(ItemUpgrade.getId(upgradeToApply) == IronBackpacksConstants.Upgrades.DEPTH_UPGRADE_ID){ //If trying to apply the depth upgrade you need a nesting upgrade applied already
//            for (int upgrade : upgrades){
//                if (upgrade == IronBackpacksConstants.Upgrades.NESTING_UPGRADE_ID || upgrade == IronBackpacksConstants.Upgrades.ADVANCED_NESTING_UPGRADE_ID){
//                    return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints;
//                }
//            }
//            return false;
//        }else{ //other upgrade (additional upgrade points already taken care of)
//            return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + ItemUpgrade.getUpgradeCost(upgradeToApply) <= totalUpgradePoints;
//        }
//
//    }

    /**
     * Applies the upgrade to the backpack by adding it's NBT data.
     * @param nbtTagCompound - the tag compound of the resulting itemstack
     * @param backpack - the backpack in the crafting grid
     * @return - true if it can be applied, false otherwise
     */
    //TODO: I have no idea what this does, and I wrote it O_o
    private boolean applyAdditional(NBTTagCompound nbtTagCompound, ItemStack backpack){
        ItemBackpack backpackBase = (ItemBackpack) backpack.getItem();
        if (backpackBase == null) return false;
        if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS)){
            int[] oldValuesArray = nbtTagCompound.getIntArray(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS);
            if (oldValuesArray[1] < ConfigHandler.additionalUpgradePointsLimit + backpackBase.getGuiId(backpack)){
                nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS, new NBTTagIntArray(new int[]{ConfigHandler.additionalUpgradePointsIncrease + oldValuesArray[0], ++oldValuesArray[1]})); //[pointsAdded, upgradesApplied]
                return true;
            }

        }else{
            if (ConfigHandler.additionalUpgradePointsLimit + backpackBase.getGuiId(backpack) > 0) {
                nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS, new NBTTagIntArray(new int[]{ConfigHandler.additionalUpgradePointsIncrease, 1})); //[pointsAdded, upgradesApplied]
                return true;
            }
        }
        return false;
    }

}
