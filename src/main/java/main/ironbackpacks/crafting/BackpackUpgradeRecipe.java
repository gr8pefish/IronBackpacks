package main.ironbackpacks.crafting;

import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BackpackUpgradeRecipe extends ShapelessOreRecipe { //TODO: refactor

    /** Is the ItemStack that you get when craft the recipe. */
    private final ItemStack recipeOutput;

    public BackpackUpgradeRecipe(ItemStack recipeOutput, Object... items){
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

    public static ItemStack getFirstUpgrade(InventoryCrafting inventoryCrafting){
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);

                if (itemstack != null && (itemstack.getItem() instanceof ItemUpgradeBase))
                    return itemstack;
            }
        }

        return null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

        ItemStack backpack = getFirstBackpack(inventoryCrafting);
        ItemStack result = backpack.copy();

        int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(result); //ex 1,2
        int totalUpgradePoints = IronBackpacksHelper.getTotalUpgradePointsFromNBT(result); //ex: 3
        ItemStack upgradeToApply = getFirstUpgrade(inventoryCrafting);
        ItemUpgradeBase upgradeToApplyBase = null;
        if (upgradeToApply != null) {
            upgradeToApplyBase = (ItemUpgradeBase) upgradeToApply.getItem();
        }

        NBTTagCompound nbtTagCompound = result.getTagCompound();
        if (nbtTagCompound == null){
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ITEMS, new NBTTagList());
            result.setTagCompound(nbtTagCompound);
        }

        boolean upgradeFound = false; //will put upgrade in first valid slot
        NBTTagList tagList = new NBTTagList();

        if (totalUpgradePoints != 0 && upgradeToApplyBase != null) { //if have more than zero upgrade slots
            if (upgrades.length == 0){ //if no upgrades applied, apply upgrade
                if (upgradeToApplyBase.getTypeID() == IronBackpacksConstants.Upgrades.ADDITIONAL_UPGRADE_SLOTS_UPGRADE_ID){
                    upgradeFound = applyAdditional(nbtTagCompound, result);
                }else {
                    if (IronBackpacksHelper.getUpgradePointsUsed(upgrades) + upgradeToApplyBase.getUpgradePoints() <= totalUpgradePoints) {
                        if (IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.contains(upgradeToApplyBase.getTypeID()))
                            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDED, new NBTTagInt(IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.indexOf(upgradeToApplyBase.getTypeID()))); //int value of upgrade added
                        NBTTagCompound tagCompound = new NBTTagCompound();
                        tagCompound.setByte(IronBackpacksConstants.NBTKeys.UPGRADE, (byte) upgradeToApplyBase.getTypeID());
                        tagList.appendTag(tagCompound);
                        upgradeFound = true;
                    }
                }
            }else { //upgrades have been applied
                if (upgradeToApplyBase.getTypeID() == IronBackpacksConstants.Upgrades.ADDITIONAL_UPGRADE_SLOTS_UPGRADE_ID) {
                    upgradeFound = applyAdditional(nbtTagCompound, result);
                }
                for (int upgrade : upgrades) { //for each slot in possible upgrades
//                    if (!upgradeFound && shouldOverride(upgradeToApplyBase, upgrade, totalUpgradePoints, upgrades)) { //if should override the upgrade in this slot, do so
//                        NBTTagCompound tagCompound = new NBTTagCompound();
//                        tagCompound.setByte("Upgrade", (byte) upgradeToApplyBase.getTypeID());
//                        tagList.appendTag(tagCompound);
//                        upgradeFound = true;
                    if (!upgradeFound && shouldRemove(upgradeToApplyBase, upgrade)){
                        //not adding the old recipe is the same outcome as removing the recipe, so no code needed here
                        upgradeFound = true;
                        if (IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.contains(upgradeToApplyBase.getTypeID()))
                            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.REMOVED, new NBTTagInt(IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.indexOf(upgradeToApplyBase.getTypeID()))); //int value of upgrade removed
                    } else { //save old contents to new tag
                        NBTTagCompound tagCompound = new NBTTagCompound();
                        tagCompound.setByte(IronBackpacksConstants.NBTKeys.UPGRADE, (byte) upgrade);
                        tagList.appendTag(tagCompound);
                    }
                }
                if (!upgradeFound && !(upgradeToApplyBase.getTypeID() == IronBackpacksConstants.Upgrades.ADDITIONAL_UPGRADE_SLOTS_UPGRADE_ID)){ //if not already applied
                    if (canApplyUpgrade(upgradeToApplyBase, upgrades, totalUpgradePoints)){
                        if (IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.contains(upgradeToApplyBase.getTypeID()))
                            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDED, new NBTTagInt(IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.indexOf(upgradeToApplyBase.getTypeID()))); //int value of upgrade added
                        NBTTagCompound tagCompound = new NBTTagCompound();
                        tagCompound.setByte(IronBackpacksConstants.NBTKeys.UPGRADE, (byte) upgradeToApplyBase.getTypeID());
                        tagList.appendTag(tagCompound);
                        upgradeFound = true;
                    }
                }
            }
        }else if (upgradeToApplyBase != null && upgradeToApplyBase.getTypeID() == IronBackpacksConstants.Upgrades.ADDITIONAL_UPGRADE_SLOTS_UPGRADE_ID){
            upgradeFound = applyAdditional(nbtTagCompound, result);
        }

        nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.UPGRADES, tagList);
        if (upgradeFound) {
            return result;
        }else{
            return null;
        }

    }

    private boolean canApplyUpgrade(ItemUpgradeBase upgradeToApplyBase, int[] upgrades, int totalUpgradePoints){
        if (IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADE_IDS.contains(upgradeToApplyBase.getTypeID())){ //alt gui upgrade
            if (UpgradeMethods.getAltGuiUpgradesUsed(upgrades)+1 <= IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED){ //alt gui in general
//                if (UpgradeMethods.getFilterUpgradesUsed(upgrades)+1 <= IronBackpacksConstants.Upgrades.FILTER_UPGRADES_ALLOWED){ //filter upgrades specifically
//                    return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + upgradeToApplyBase.getUpgradePoints() <= totalUpgradePoints; //return if it can accept that many more upgrade points
//                }
                return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + upgradeToApplyBase.getUpgradePoints() <= totalUpgradePoints;
            }
            return false;
        }else{ //other upgrade (additional upgrade points already taken care of)
            return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + upgradeToApplyBase.getUpgradePoints() <= totalUpgradePoints;
        }

    }

    private boolean applyAdditional(NBTTagCompound nbtTagCompound, ItemStack backpack){ //nbt out of scope, will changes apply?
        ItemBaseBackpack backpackBase = (ItemBaseBackpack) backpack.getItem();
        if (backpackBase == null) return false;
        if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS)){
            int[] oldValuesArray = nbtTagCompound.getIntArray(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS);
            if (oldValuesArray[1] < ConfigHandler.additionalUpgradesLimit + backpackBase.getGuiId()){
                nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS, new NBTTagIntArray(new int[]{ConfigHandler.additionalUpgradesIncrease + oldValuesArray[0], ++oldValuesArray[1]})); //[pointsAdded, upgradesApplied]
                return true;
            }

        }else{
            if (ConfigHandler.additionalUpgradesLimit + backpackBase.getGuiId() > 0) {
                nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ADDITIONAL_POINTS, new NBTTagIntArray(new int[]{ConfigHandler.additionalUpgradesIncrease, 1})); //[pointsAdded, upgradesApplied]
                return true;
            }
        }
        return false;
    }

//    private boolean shouldOverride(ItemUpgradeBase upgradeToApplyBase, int currUpgrade, int totalUpgradePoints, int[] upgrades){ //only use was with filters
//        //only overriding with filter upgrades
//        if (upgradeToApplyBase.getTypeID() == IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_UPGRADE_ID) { //try to override basic filter first, if not apply to blank;
//            if (currUpgrade == IronBackpacksConstants.Upgrades.FILTER_UPGRADE_ID) {
//                return IronBackpacksHelper.getUpgradePointsUsed(upgrades) + upgradeToApplyBase.getUpgradePoints() - IronBackpacksConstants.Upgrades.UPGRADE_POINTS[currUpgrade] <= totalUpgradePoints;
//            }
//        }
//        return false;
//    }

    private boolean shouldRemove(ItemUpgradeBase upgradeToApplyBase, int currUpgrade){
        if (upgradeToApplyBase.getTypeID() == currUpgrade){ //removing if the same upgrade is applied twice
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }
}
