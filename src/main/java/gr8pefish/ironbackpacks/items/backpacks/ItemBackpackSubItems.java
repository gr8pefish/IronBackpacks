package gr8pefish.ironbackpacks.items.backpacks;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.item.backpacks.BackpackNames;
import gr8pefish.ironbackpacks.api.item.backpacks.abstractClasses.AbstractUpgradableTieredBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.registry.BackpackItemRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.IronBackpacksHelper;
import gr8pefish.ironbackpacks.util.Logger;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

public class ItemBackpackSubItems extends AbstractUpgradableTieredBackpack {

    private boolean openAltGui = true; //to track which gui to open

    private final String name; //display name
    private final int rowLength; //length of each row
    private final int rowCount; //number of rows
    private final int size; //size of the backpack
    private final int upgradePoints; //number of upgradePoints

    /**
     * The Item that stores all the backpacks in one item ID by using a reference to an enum and NBT data to differentiate.
     * @param enumName - the name to access the enumeration of backpacks which stores all the other data (ex: GOLD)
     */
    public ItemBackpackSubItems(String enumName){
        setCreativeTab(IronBackpacks.creativeTab);
        setMaxStackSize(1);
        setNoRepair();

        BackpackEnum backpackEnum = BackpackEnum.valueOf(enumName); //the backpack itself in the enum (accessed by the name)

        setUnlocalizedName(Constants.MODID + ":" + backpackEnum.getName());

        this.name = backpackEnum.getName();
        this.rowLength = backpackEnum.getRowLength();
        this.rowCount = backpackEnum.getRowCount();
        this.size = rowCount * rowLength;
        this.upgradePoints = backpackEnum.getUpgradePoints();
    }

    public NBTTagCompound toNbt(String enumName) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString(IronBackpacksConstants.NBTKeys.ENUM_NAME, enumName);
        return tagCompound;
    }

    public static ItemBackpackSubItems fromNbt(NBTTagCompound tagCompound) {
        if (tagCompound == null)
            tagCompound = new NBTTagCompound();

        if (tagCompound.hasKey(IronBackpacksConstants.NBTKeys.ENUM_NAME)){ //The NBT tag compound to store this field data at
            String enumName = tagCompound.getString(IronBackpacksConstants.NBTKeys.ENUM_NAME);
            return new ItemBackpackSubItems(enumName);
        } else {
            tagCompound.setTag(IronBackpacksConstants.NBTKeys.ENUM_NAME, new NBTTagCompound());
            Logger.error("IronBackpacks: Item has no ENUM_ID tag, and fromNBT is being called");
            return null;
        }
    }

    //================================================Override Vanilla Item Methods=========================================

    @Override //TODO: test
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false; //no more item backpack bobbing hopefully
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return UpgradeMethods.hasDamageBarUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(stack));
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getFullness(stack);
    }

    //=====================================================IBackpack=========================================================

    @Override
    public int getId(ItemStack backpack) {
//        return fromNbt(backpack.getTagCompound()).id;
        return 1; //TODO: remove
//        return this.id;
    }

    @Override
    public String getName(ItemStack backpack) {
        return fromNbt(backpack.getTagCompound()).name;
//        return this.name;
    }

    @Override
    public int getSize(ItemStack backpack) {
        return fromNbt(backpack.getTagCompound()).size;
//        return this.size;
    }

    @Override
    public int getRowCount(ItemStack backpack) {
        return fromNbt(backpack.getTagCompound()).rowCount;
//        return this.rowCount;
    }

    @Override
    public int getRowLength(ItemStack backpack) {
        return fromNbt(backpack.getTagCompound()).rowLength;
//        return this.rowLength;
    }

    @Override
    public double getFullness(ItemStack stack) {
        ItemStack[] inventory;
        int total = 0;
        int full = 0;

        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey("Items")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Items", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
                    inventory = new ItemStack[BackpackItemRegistry.getBackpack(BackpackNames.BASIC).getSize(stack)]; //new ItemStack[BackpackNames.values()[getGuiId(stack)].getSize()];
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int slot = stackTag.getByte("Slot");
                        if (i >= 0 && i <= inventory.length)
                            inventory[slot] = ItemStack.loadItemStackFromNBT(stackTag);
                    }
                    for (ItemStack tempStack : inventory) {
                        if (tempStack != null) {
                            full += tempStack.stackSize;
                            total += tempStack.getMaxStackSize();
                        } else {
                            total += 64;
                        }
                    }
                }
            }
        }

        return 1 - ((double) full / total);
    }

    //TODO: refactor
    public int getGuiId(ItemStack stack) {
        return getId(stack) - 1;
    }

    //====================================================Upgrades======================================================

    @Override
    public int getUpgradePoints(ItemStack backpack) {
        return fromNbt(backpack.getTagCompound()).upgradePoints;
//        return this.upgradePoints;
    }

    @Override
    public ArrayList<Integer> getUpgrades(ItemStack backpack) {
        ArrayList<Integer> upgradesArrayList = new ArrayList<>();
        if (backpack != null) {
            NBTTagCompound nbtTagCompound = backpack.getTagCompound();
            if (nbtTagCompound != null) {
                if(nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.UPGRADES)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.UPGRADES, net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int hasUpgrade = stackTag.getByte(IronBackpacksConstants.NBTKeys.UPGRADE);
                        if (hasUpgrade != 0){ //if has an upgrade
                            upgradesArrayList.add(hasUpgrade);
                        }
                    }
                }
            }
        }
        return upgradesArrayList;
    }

    //====================================================Tiering=====================================================


    //TODO: add fields to enum for this?


    @Override
    public IBackpack getBackpackAbove(ItemStack backpack) {
        return null;
    }

    @Override
    public boolean hasBackpackAbove(ItemStack backpack) {
        return false;
    }

    @Override
    public void setBackpackAbove(ItemStack baseBackpack, ItemStack aboveBackpack) {

    }

    @Override
    public IBackpack getBackpackBelow(ItemStack backpack) {
        return null;
    }

    @Override
    public boolean hasBackpackBelow(ItemStack backpack) {
        return false;
    }

    @Override
    public void setBackpackBelow(ItemStack baseBackpack, ItemStack belowBackpack) {

    }

}
