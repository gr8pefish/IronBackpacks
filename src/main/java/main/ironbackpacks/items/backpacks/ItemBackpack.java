package main.ironbackpacks.items.backpacks;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.proxies.CommonProxy;
import main.ironbackpacks.util.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

/**
 * Base class for all the backpack items to extend
 */
public class ItemBackpack extends Item implements IBackpack {

    private final int id;
    private final int size;
    private final int rowLength;
    private final int upgradePoints;
    private final String fancyName;
    private boolean openAltGui = true;

    public ItemBackpack(int id, int size, int rowLength, String fancyName, int upgradePoints) {
        setCreativeTab(IronBackpacks.creativeTab);
        setUnlocalizedName(ModInformation.ID + ":" + fancyName);
        setMaxStackSize(1);

        this.id = id;
        this.size = size;
        this.rowLength = rowLength;
        this.upgradePoints = upgradePoints;
        this.fancyName = fancyName;
    }

    public ItemBackpack(BackpackTypes type) {
        this(type.getId(), type.getSize(), type.getRowLength(), type.getName(), type.getUpgradePoints());
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return UpgradeMethods.hasDamageBarUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(stack));
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getFullness(stack);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (!player.isSneaking())
                return false;

            if (UpgradeMethods.hasQuickDepositUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(stack))) {
                openAltGui = !UpgradeMethods.transferFromBackpackToInventory(player, stack, world, pos);
                return !openAltGui;
            }
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (world.isRemote) {
            CommonProxy.updateCurrBackpack(player, stack);
            return stack;
        } else {
            NBTHelper.setUUID(stack);
            CommonProxy.updateCurrBackpack(player, stack);
            if (!player.isSneaking()) {
                player.openGui(IronBackpacks.instance, getGuiId(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
                return stack;
            } else {
                if (openAltGui)
                    player.openGui(IronBackpacks.instance, (getGuiId() * -1) - 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
                else
                    openAltGui = true;

                return stack;
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(stack);
        int totalUpgradePoints = IronBackpacksHelper.getTotalUpgradePointsFromNBT(stack);

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            int upgradesUsed = 0;
            for (int upgrade : upgrades) {
                list.add(IronBackpacksConstants.Upgrades.LOCALIZED_NAMES[upgrade]);
                upgradesUsed += IronBackpacksConstants.Upgrades.UPGRADE_POINTS[upgrade];
            }

            if (upgrades.length > 0)
                list.add("");

            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.upgrade.used", upgradesUsed, totalUpgradePoints));
            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.upgrade.used.alt", UpgradeMethods.getAltGuiUpgradesUsed(upgrades), IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED));

            if (ConfigHandler.renamingUpgradeRequired)
                list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.upgrade.rename", IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED));

            int tierUpgradeCount = ConfigHandler.additionalUpgradesLimit + getGuiId();

            if (tierUpgradeCount > 0) {
                int used = IronBackpacksHelper.getAdditionalUpgradesTimesApplied(stack) * ConfigHandler.additionalUpgradesIncrease;
                int have = tierUpgradeCount * ConfigHandler.additionalUpgradesIncrease;
                list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.upgrade.used.tier", used, have));
            }
        } else {
            if (totalUpgradePoints > 0)
                list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.shift"));
        }

        if (advanced && NBTHelper.hasUUID(stack))
            list.add(TextUtils.localize("tooltip.ironbackpacks.uuid", NBTHelper.getUUID(stack)));
    }

    // IBackpack

    public double getFullness(ItemStack stack) {
        ItemStack[] inventory;
        int total = 0;
        int full = 0;

        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey("Items")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
                    inventory = new ItemStack[BackpackTypes.values()[getGuiId()].getSize()];
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

    @Override
    public int getUpgradePoints() {
        return upgradePoints;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getRowLength() {
        return rowLength;
    }

    @Override
    public String getName() {
        return fancyName;
    }

    @Override
    public int getGuiId() {
        return getId() - 1;
    }
}
