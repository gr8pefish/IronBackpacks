package gr8pefish.ironbackpacks.items.backpacks;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.items.backpacks.ItemIUpgradableITieredBackpack;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.register.ItemIBackpackRegistry;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.container.backpack.ContainerBackpack;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.entity.extendedProperties.PlayerBackpackProperties;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.NBTUtils;
import gr8pefish.ironbackpacks.util.TextUtils;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
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


public class ItemBackpack extends ItemIUpgradableITieredBackpack {

    private boolean openAltGui = true; //to track which gui to open

    private String emphasis; //unlocalized string to show if the backpack has a specialty, could be null if none

    public ItemBackpack(String name, int rowLength, int rowCount, int upgradePoints, int additionalPoints, ResourceLocation guiResourceLocation, int guiXSize, int guiYSize, ResourceLocation modelTexture, String specialty){
        super(name, rowLength, rowCount, upgradePoints, additionalPoints, guiResourceLocation, guiXSize, guiYSize, modelTexture); //null is for the recipe (set after items init)
        setCreativeTab(IronBackpacks.creativeTab);
        this.emphasis = specialty;

    }

    //=================================================================Overriden Vanilla Methods=============================================================

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
            if (!(player.isSneaking())) { //only do it when player is sneaking
                return false;
            }
            ArrayList<ItemStack> upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(itemstack);
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
                        ArrayList<ItemStack> nestedUpgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(nestedBackpack);
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
            NBTUtils.setUUID(itemStack);
            PlayerBackpackProperties.setCurrentBackpack(player, itemStack); //need to update on client side so has access to backpack for GUI's backpack stack's display name //TODO: client side
            return itemStack;
        } else {
            NBTUtils.setUUID(itemStack);
            PlayerBackpackProperties.setCurrentBackpack(player, itemStack);
            if (!(player.isSneaking())){
                int guiId = ItemIBackpackRegistry.getIndexOf((IBackpack)itemStack.getItem());
                player.openGui(IronBackpacks.instance, guiId, world, (int) player.posX, (int) player.posY, (int) player.posZ); //"Normal usage"
                return itemStack;
            } else { //if sneaking
                if (openAltGui) {
                    int guiId = ItemIBackpackRegistry.getIndexOf((IBackpack) itemStack.getItem());
                    player.openGui(IronBackpacks.instance, (guiId * -1) - 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
                } else
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
        ArrayList<ItemStack> upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(stack);
        int totalUpgradePoints = IronBackpacksHelper.getTotalUpgradePointsFromNBT(stack);

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            int upgradesUsed = 0;

            for (ItemStack upgradeStack : upgrades) {
//                if (!ItemIUpgradeRegistry.getItemUpgrade(upgradeStack).equals(ItemRegistry.additionalUpgradePointsUpgrade))
                list.add(TextUtils.localizeEffect("items.ironbackpacks.upgrade."+ ItemIUpgradeRegistry.getItemUpgrade(upgradeStack).getName(upgradeStack)+".name"));
                upgradesUsed += ItemIUpgradeRegistry.getItemUpgrade(upgradeStack).getUpgradeCost(upgradeStack);
            }

            if (upgrades.size() > 0)
                list.add("");

            if (emphasis != null)
                list.add(TextUtils.localizeEffect(emphasis));

            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.upgrade.used", upgradesUsed, totalUpgradePoints));
            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.upgrade.used.alt", UpgradeMethods.getAltGuiUpgradesApplied(upgrades), IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED));

            if (ConfigHandler.renamingUpgradeRequired)
                list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.upgrade.rename", IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED));


            int additionalPossiblePoints = this.getAdditionalUpgradePoints(null);

            if (additionalPossiblePoints > 0) {
                int used = IronBackpacksHelper.getAdditionalUpgradesTimesApplied(stack) * ConfigHandler.additionalUpgradePointsIncrease;
                list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.upgrade.used.additionalPoints", used, additionalPossiblePoints));
            }
        } else {
            if (totalUpgradePoints > 0)
                list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.shift"));
        }

        if (advanced && NBTUtils.hasUUID(stack))
            list.add(TextUtils.localize("tooltip.ironbackpacks.uuid", NBTUtils.getUUID(stack)));
    }

    //=============================================================================Helper Methods===================================================================================


    private double getFullness(ItemStack stack) {
        ItemStack[] inventory;
        int total = 0;
        int full = 0;

        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey("Items")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Items", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
                    inventory = new ItemStack[((ItemIUpgradableITieredBackpack)stack.getItem()).getSize(stack)]; //new ItemStack[BackpackNames.values()[getGuiId(stack)].getSize()]; //TODO: test
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
}
