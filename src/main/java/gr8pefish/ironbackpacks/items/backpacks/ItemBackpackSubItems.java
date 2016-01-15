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

    private final int id; //internal id //TODO: can get rid of this, I think, if I use other methods elsewhere
    private final String name; //display name
    private final int size; //size of the backpack
    private final int rowCount; //number of rows
    private final int rowLength; //length of each row
    private final int upgradePoints; //number of upgradePoints

    public ItemBackpackSubItems(int id, String name, int rowCount, int rowLength, int upgradePoints){
        setCreativeTab(IronBackpacks.creativeTab);
        setMaxStackSize(1);
        setNoRepair();

        setUnlocalizedName(Constants.MODID + ":" + name);

        this.id = id;
        this.name = name;
        this.size = rowCount * rowLength;
        this.rowCount = rowCount;
        this.rowLength = rowLength;
        this.upgradePoints = upgradePoints;
    }

    public NBTTagCompound toNbt(int id, String name, int size, int rowCount, int rowLength, int upgradePoints) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("id", id);
        tagCompound.setString("name", name);
        tagCompound.setInteger("size", size);
        tagCompound.setInteger("rowCount", rowCount);
        tagCompound.setInteger("rowLength", rowLength);
        tagCompound.setInteger("upgradePoints", upgradePoints);
        return tagCompound;
    }

    public static ItemBackpackSubItems fromNbt(NBTTagCompound tagCompound) {
        if (tagCompound == null)
            tagCompound = new NBTTagCompound();

        if (!tagCompound.hasKey(IronBackpacksConstants.NBTKeys.BACKPACK_FIELDS)){ //The NBT tag compound to store this field data at
            tagCompound.setTag(IronBackpacksConstants.NBTKeys.BACKPACK_FIELDS, new NBTTagCompound());
            Logger.error("IronBackpacks: Item has no BACKPACK_FIELDS tag, and fromNBT is being called");
            return null;
        } else {
            NBTTagCompound fieldCompound = tagCompound.getCompoundTag(IronBackpacksConstants.NBTKeys.BACKPACK_FIELDS);

            int id = fieldCompound.getInteger("id");
            String name = fieldCompound.getString("name");
            int size = fieldCompound.getInteger("size");
            int rowCount = fieldCompound.getInteger("rowCount");
            int rowLength = fieldCompound.getInteger("rowLength");
            int upgradePoints = fieldCompound.getInteger("upgradePoints");

            return new ItemBackpackSubItems(id, name, size, rowCount, rowLength, upgradePoints);
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
        return fromNbt(backpack.getTagCompound()).id;
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
