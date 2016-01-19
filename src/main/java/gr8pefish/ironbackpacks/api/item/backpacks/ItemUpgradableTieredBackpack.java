package gr8pefish.ironbackpacks.api.item.backpacks;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.backpacks.abstractClasses.AbstractUpgradableTieredBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.register.ItemBackpackRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ItemUpgradableTieredBackpack extends AbstractUpgradableTieredBackpack {

    private final String name; //display name
    private final int rowLength; //length of each row
    private final int rowCount; //number of rows
    private final int size; //size of the backpack
    private final int upgradePoints; //number of upgradePoints

    private final int guiId; //the id of the gui (must only be unique to the mod)

    private final ResourceLocation guiResourceLocation; //the resource location of the gui to display
    private final int guiXSize; //the width of the gui
    private final int guiYSize; //the height of the gui

    private List<ITieredBackpack> backpacksBelow; //the backpacks lower in tier (in order)
    private List<ITieredBackpack> backpacksAbove; //the backpacks higher in tier (in order)

    /**
     * The Item that represents an AbstractUpgradableTieredBackpack
     */
    public ItemUpgradableTieredBackpack(String name, int rowLength, int rowCount, int upgradePoints, ResourceLocation guiResourceLocation, int guiXSize, int guiYSize, List<ITieredBackpack> backpacksBelow, List<ITieredBackpack> backpacksAbove){
        setMaxStackSize(1);
        setNoRepair();

        setUnlocalizedName(Constants.MODID + "." + IronBackpacksAPI.ITEM_BACKPACK_BASE + "." + name);

        this.name = name;
        this.rowLength = rowLength;
        this.rowCount = rowCount;
        this.size = rowCount * rowLength;
        this.upgradePoints = upgradePoints;

        this.guiId = 0;//backpackEnum.guiId; //TODO, use registry for this

        this.guiResourceLocation = guiResourceLocation;
        this.guiXSize = guiXSize;
        this.guiYSize = guiYSize;

        this.backpacksBelow = backpacksBelow;
        this.backpacksAbove = backpacksAbove;
    }

    //================================================Override Vanilla Item Methods=========================================

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false; //no more item backpack bobbing hopefully
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }


    //=====================================================IBackpack=========================================================

    @Override
    public String getName(ItemStack backpack) {
        return name;
    }

    @Override
    public int getRowCount(ItemStack backpack) {
        return rowCount;
    }

    @Override
    public int getRowLength(ItemStack backpack) {
        return rowLength;
    }

    @Override
    public ResourceLocation getGuiResourceLocation(ItemStack backpack) {
        return guiResourceLocation;
    }

    @Override
    public int getGuiXSize(ItemStack backpack) {
        return guiXSize;
    }

    @Override
    public int getGuiYSize(ItemStack backpack) {
        return guiYSize;
    }

    //TODO: fix with dynamic
    public int getGuiId(ItemStack stack) {
        return ItemBackpackRegistry.getIndexOf((IBackpack)stack.getItem());
    }

    public int getSize(ItemStack backpack) {
        return size;
    }

    //====================================================Upgrades======================================================

    @Override
    public int getUpgradePoints(ItemStack backpack) {
        return upgradePoints;
    }

    //====================================================Tiering=====================================================


    @Override
    public List<ITieredBackpack> getBackpacksAbove(ItemStack backpack) {
        return backpacksAbove;
    }

    @Override
    public boolean hasBackpacksAbove(ItemStack backpack) {
        return (!backpacksAbove.isEmpty());
    }

    @Override
    public void setBackpacksAbove(ItemStack baseBackpack, List<ITieredBackpack> aboveBackpacks) {
        backpacksAbove = aboveBackpacks;
    }


    @Override
    public List<ITieredBackpack> getBackpacksBelow(ItemStack backpack) {
        return backpacksBelow;
    }

    @Override
    public boolean hasBackpacksBelow(ItemStack backpack) {
        return (!backpacksBelow.isEmpty());
    }

    @Override
    public void setBackpacksBelow(ItemStack baseBackpack, List<ITieredBackpack> belowBackpacks) {
        backpacksBelow = belowBackpacks;
    }


}
