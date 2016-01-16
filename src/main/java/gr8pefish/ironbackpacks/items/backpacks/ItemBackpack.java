package gr8pefish.ironbackpacks.items.backpacks;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.item.backpacks.abstractClasses.AbstractUpgradableTieredBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.container.backpack.ContainerBackpack;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.entity.extendedProperties.PlayerBackpackProperties;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.IronBackpacksHelper;
import gr8pefish.ironbackpacks.util.NBTHelper;
import gr8pefish.ironbackpacks.util.TextUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ItemBackpack extends AbstractUpgradableTieredBackpack {

    private boolean openAltGui = true; //to track which gui to open

    private final String name; //display name
    private final int rowLength; //length of each row
    private final int rowCount; //number of rows
    private final int size; //size of the backpack
    private final int upgradePoints; //number of upgradePoints

    private final int guiId; //the id of the gui (must only be unique to the mod)

    private final ResourceLocation guiResourceLocation; //the resource location of the gui to display
    private final int guiXSize; //the width of the gui
    private final int guiYSize; //the height of the gui

    /**
     * The Item that represents an AbstractUpgradableTieredBackpack
     * @param enumName - the name to access the enumeration of backpacks which stores all the other data (ex: GOLD)
     */
    public ItemBackpack(String enumName){
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

        this.guiId = backpackEnum.guiId;

        this.guiResourceLocation = backpackEnum.getGuiResourceLocation();
        this.guiXSize = backpackEnum.getGuiXSize();
        this.guiYSize = backpackEnum.getGuiYSize();
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

    //Called before anything else
    //returning true will stop the alt. gui from opening
    //returning false will let it continue as normal (i.e. it can open)
    @Override
    public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) { //server side
            if (!player.isSneaking()) { //only do it when player is sneaking
                return false;
            }
            int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(itemstack);
            boolean hasDepthUpgrade = UpgradeMethods.hasDepthUpgrade(upgrades);
            if (UpgradeMethods.hasQuickDepositUpgrade(upgrades)) {
                openAltGui = !UpgradeMethods.transferFromBackpackToInventory(player, itemstack, world, pos, false);
                if (!hasDepthUpgrade)
                    return !openAltGui;
            }else if (UpgradeMethods.hasQuickDepositPreciseUpgrade(upgrades)) {
                openAltGui = !UpgradeMethods.transferFromBackpackToInventory(player, itemstack, world, pos, true);
                if (!hasDepthUpgrade)
                    return !openAltGui;
            }
            boolean openAltGuiDepth;
            if (hasDepthUpgrade) {
                ContainerBackpack container = new ContainerBackpack(new InventoryBackpack(player, itemstack));
                for (int j = 0; j < container.getInventoryBackpack().getSizeInventory(); j++) {
                    ItemStack nestedBackpack = container.getInventoryBackpack().getStackInSlot(j);
                    if (nestedBackpack != null && nestedBackpack.getItem() != null && nestedBackpack.getItem() instanceof IBackpack) {
                        int[] nestedUpgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(nestedBackpack);
                        if (UpgradeMethods.hasQuickDepositUpgrade(nestedUpgrades)) {
                            openAltGuiDepth = !UpgradeMethods.transferFromBackpackToInventory(player, nestedBackpack, world, pos, false);
                            if (!openAltGuiDepth) openAltGui = false;
                        }else if (UpgradeMethods.hasQuickDepositPreciseUpgrade(nestedUpgrades)) {
                            openAltGuiDepth = !UpgradeMethods.transferFromBackpackToInventory(player, nestedBackpack, world, pos, true);
                            if (!openAltGuiDepth) openAltGui = false;
                        }
                    }
                }
                return !openAltGui;
            }
        }
        return false;
    }

    //to open the guis
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote){ //client side
            PlayerBackpackProperties.setCurrentBackpack(player, itemStack); //need to update on client side so has access to backpack for GUI's backpack stack's display name //TODO: client side
            return itemStack;
        } else {
            PlayerBackpackProperties.setCurrentBackpack(player, itemStack);
            if (!player.isSneaking()){
                player.openGui(IronBackpacks.instance, getGuiId(itemStack), world, (int) player.posX, (int) player.posY, (int) player.posZ); //"Normal usage"
                return itemStack;
            }else { //if sneaking
                if (openAltGui)
                    player.openGui(IronBackpacks.instance, (getGuiId(itemStack) * -1) - 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
                else
                    openAltGui = true;
                return itemStack;
            }
        }
    }

    //adds a fancy tooltip
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

            int tierUpgradeCount = ConfigHandler.additionalUpgradePointsLimit + getGuiId(stack);

            if (tierUpgradeCount > 0) {
                int used = IronBackpacksHelper.getAdditionalUpgradesTimesApplied(stack) * ConfigHandler.additionalUpgradePointsIncrease;
                int have = tierUpgradeCount * ConfigHandler.additionalUpgradePointsIncrease;
                list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.upgrade.used.tier", used, have));
            }
        } else {
            if (totalUpgradePoints > 0)
                list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.shift"));
        }

        if (advanced && NBTHelper.hasUUID(stack))
            list.add(TextUtils.localize("tooltip.ironbackpacks.uuid", NBTHelper.getUUID(stack)));
    }

    //=====================================================IBackpack=========================================================

    @Override
    public String getName(ItemStack backpack) {
        return this.name;
    }

    @Override
    public int getRowCount(ItemStack backpack) {
        return this.rowCount;
    }

    @Override
    public int getRowLength(ItemStack backpack) {
        return this.rowLength;
    }

    @Override
    public ResourceLocation getGuiResourceLocation(ItemStack backpack) {
        return this.guiResourceLocation;
    }

    @Override
    public int getGuiXSize(ItemStack backpack) {
        return this.guiXSize;
    }

    @Override
    public int getGuiYSize(ItemStack backpack) {
        return this.guiYSize;
    }

    //TODO: fix with dynamic
    public int getGuiId(ItemStack stack) {
        return this.guiId;
    }

    public int getSize(ItemStack backpack) {
        return this.size;
    }

    //====================================================Upgrades======================================================

    @Override
    public int getUpgradePoints(ItemStack backpack) {
        return this.upgradePoints;
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
                    inventory = new ItemStack[((ItemBackpack)stack.getItem()).getSize(stack)]; //new ItemStack[BackpackNames.values()[getGuiId(stack)].getSize()]; //TODO: test
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

    //====================================================Tiering=====================================================


    //TODO: add fields to enum for this?

    @Override
    public ArrayList<ItemStack> getBackpacksAbove(ItemStack backpack) {
        return null;
    }

    @Override
    public boolean hasBackpacksAbove(ItemStack backpack) {
        return false;
    }

    @Override
    public void setBackpacksAbove(ItemStack baseBackpack, ArrayList<ItemStack> aboveBackpacks) {

    }

    @Override
    public ArrayList<ItemStack> getBackpacksBelow(ItemStack backpack) {
        return null;
    }

    @Override
    public boolean hasBackpacksBelow(ItemStack backpack) {
        return false;
    }

    @Override
    public void setBackpacksBelow(ItemStack baseBackpack, ArrayList<ItemStack> belowBackpacks) {

    }


}

