package main.ironbackpacks.crafting;

import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.ItemUpgradeBase;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BackpackUpgradeRecipe extends ShapelessOreRecipe {

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
        int[] upgrades = IronBackpacksHelper.getUpgradesFromNBT(result);
        NBTTagCompound nbtTagCompound = result.getTagCompound();

        if (nbtTagCompound == null){
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag("Items", new NBTTagList());
            result.setTagCompound(nbtTagCompound);
        }

        boolean upgradeFound = false; //will put upgrade in first valid slot
        NBTTagList tagList = new NBTTagList();
        int currentSlot = 0;
        for (int integer : upgrades){
            if (integer == 0 && currentSlot <= ((ItemBaseBackpack)result.getItem()).getUpgradeIndex() && !hasUpgradeAlready(upgrades, inventoryCrafting)){ //If an empty upgrade slot and backpack can support upgrade
                if (!upgradeFound){
                    ItemStack upgrade = getFirstUpgrade(inventoryCrafting);
                    ItemUpgradeBase upgradeBase = (ItemUpgradeBase) upgrade.getItem();
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Upgrade", (byte) upgradeBase.getTypeID());
                    tagList.appendTag(tagCompound);
                    upgradeFound = true;
                }else{
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Upgrade", (byte) integer);
                    tagList.appendTag(tagCompound);
                }
            }else{
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Upgrade", (byte) integer);
                tagList.appendTag(tagCompound);
            }
            currentSlot++;
        }

        nbtTagCompound.setTag("Upgrades", tagList);

        if (upgradeFound) {
            return result;
        }else{
            return null;
        }
    }

    protected boolean hasUpgradeAlready(int[] upgrades, InventoryCrafting inventoryCrafting){
        ItemStack upgrade = getFirstUpgrade(inventoryCrafting);
        ItemUpgradeBase upgradeBase = (ItemUpgradeBase) upgrade.getItem();
        for (int integer: upgrades) {
            if (integer != 0) {
                if (upgradeBase.getTypeID() == integer){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }
}
