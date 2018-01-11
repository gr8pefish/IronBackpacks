package gr8pefish.ironbackpacks.items.backpacks;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.achievements.IBackpackCraftAchievement;
import gr8pefish.ironbackpacks.achievements.IronBackpacksAchievements;
import gr8pefish.ironbackpacks.api.items.backpacks.ItemIUpgradableITieredBackpack;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.register.ItemIBackpackRegistry;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.container.backpack.ContainerBackpack;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.sounds.IronBackpacksSounds;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.Logger;
import gr8pefish.ironbackpacks.util.NBTUtils;
import gr8pefish.ironbackpacks.util.TextUtils;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.Achievement;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ItemBackpack extends ItemIUpgradableITieredBackpack implements IBackpackCraftAchievement {

    private boolean openAltGui = true; //to track which gui to open

    public ItemBackpack(String name, int rowLength, int rowCount, int upgradePoints, int additionalPoints, ResourceLocation guiResourceLocation, int guiXSize, int guiYSize, ResourceLocation modelTexture, String specialty){
        super(name, rowLength, rowCount, upgradePoints, additionalPoints, guiResourceLocation, guiXSize, guiYSize, modelTexture, specialty); //null is for the recipe (set after items init)
        setCreativeTab(IronBackpacks.creativeTab);
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
    //returning true=success will stop the alt. gui from opening
    //returning false=pass will let it continue as normal (i.e. it can open)
    @Override
    public EnumActionResult onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        //ToDo: Add specific hand opening only?
//        if (!hand.equals(EnumHand.MAIN_HAND)) {
//            return EnumActionResult.PASS;
//        }
        if (!world.isRemote) { //server side
            if (!(player.isSneaking())) { //only do it when player is sneaking
                return EnumActionResult.PASS;
            }
            ArrayList<ItemStack> upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(itemstack);
            boolean hasDepthUpgrade = UpgradeMethods.hasDepthUpgrade(upgrades);
            if (UpgradeMethods.hasQuickDepositUpgrade(upgrades)) {
                openAltGui = !UpgradeMethods.transferFromBackpackToInventory(player, itemstack, world, pos, side, false);
                if (!hasDepthUpgrade)
                    return !openAltGui ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
            }else if (UpgradeMethods.hasQuickDepositPreciseUpgrade(upgrades)) {
                openAltGui = !UpgradeMethods.transferFromBackpackToInventory(player, itemstack, world, pos, side, true);
                if (!hasDepthUpgrade)
                    return !openAltGui ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
            }
            boolean openAltGuiDepth;
            if (hasDepthUpgrade) {
                ContainerBackpack container = new ContainerBackpack(new InventoryBackpack(player, itemstack));
                for (int j = 0; j < container.getInventoryBackpack().getSizeInventory(); j++) {
                    ItemStack nestedBackpack = container.getInventoryBackpack().getStackInSlot(j);
                    if (nestedBackpack != null && nestedBackpack.getItem() != null && nestedBackpack.getItem() instanceof IBackpack) {
                        ArrayList<ItemStack> nestedUpgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(nestedBackpack);
                        if (UpgradeMethods.hasQuickDepositUpgrade(nestedUpgrades)) {
                            openAltGuiDepth = !UpgradeMethods.transferFromBackpackToInventory(player, nestedBackpack, world, pos, side, false);
                            if (!openAltGuiDepth) openAltGui = false;
                        }else if (UpgradeMethods.hasQuickDepositPreciseUpgrade(nestedUpgrades)) {
                            openAltGuiDepth = !UpgradeMethods.transferFromBackpackToInventory(player, nestedBackpack, world, pos, side, true);
                            if (!openAltGuiDepth) openAltGui = false;
                        }
                    }
                }
                return !openAltGui ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
            }
        }
        return EnumActionResult.PASS;
    }

    //to open the guis
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        return handleBackpackOpening(itemStack, world, player, hand, false);
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
                list.add(TextUtils.localizeEffect("item.ironbackpacks.upgrade."+ ItemIUpgradeRegistry.getItemUpgrade(upgradeStack).getName(upgradeStack)+".name"));
                upgradesUsed += ItemIUpgradeRegistry.getItemUpgrade(upgradeStack).getUpgradeCost(upgradeStack);
            }

            if (upgrades.size() > 0)
                list.add("");

            if (this.getSpecialty(null) != null)
                list.add(TextUtils.localizeEffect(this.getSpecialty(null)));

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

    //TODO: Implement for 1.12. Ignored here as it would involve too much messing with client/server sync for equipped packs, which is messy code.
//    //The data that is needed always for the client
//    //Removes the item data, as it is too much data and causes crashes with things like RS drives
//    @Nonnull
//    @Override
//    public NBTTagCompound getNBTShareTag(ItemStack stack) {
//        NBTTagCompound compound = stack.getTagCompound();
//        compound.removeTag(IronBackpacksConstants.NBTKeys.ITEMS);
//        return compound;
//    }


    //=============================================================================Helper Methods===================================================================================


    public ActionResult<ItemStack> handleBackpackOpening(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand, boolean knownShift){
        if (world.isRemote) {
            NBTUtils.setUUID(itemStack);
            PlayerWearingBackpackCapabilities.setCurrentBackpack(player, itemStack);
        }

        if (!world.isRemote) {
            NBTUtils.setUUID(itemStack);
            PlayerWearingBackpackCapabilities.setCurrentBackpack(player, itemStack);
            boolean sneaking = knownShift || player.isSneaking();
            if (!sneaking) {
                int guiId = ItemIBackpackRegistry.getIndexOf((IBackpack) itemStack.getItem());
                player.playSound(IronBackpacksSounds.open_backpack, 1F, 1F);
                player.openGui(IronBackpacks.instance, guiId, world, (int) player.posX, (int) player.posY, (int) player.posZ); //"Normal usage"
                return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
            } else { //if sneaking
                if (openAltGui) {
                    int guiId = ItemIBackpackRegistry.getIndexOf((IBackpack) itemStack.getItem());
                    player.playSound(IronBackpacksSounds.open_backpack, 1F, 1F);
                    player.openGui(IronBackpacks.instance, (guiId * -1) - 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
                } else
                    openAltGui = true;
                return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    private double getFullness(ItemStack stack) {
        ItemStack[] inventory;
        int total = 0;
        int full = 0;

        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if (nbtTagCompound.hasKey("Items")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Items", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
                    inventory = new ItemStack[((ItemIUpgradableITieredBackpack)stack.getItem()).getSize(stack)];
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
    public Achievement getAchievementOnCraft(ItemStack stack, EntityPlayer player, IInventory matrix) {
        if (stack.getItem() instanceof ItemBackpack) {
            ItemBackpack backpack = (ItemBackpack) stack.getItem();
            switch (backpack.getTier(null)){
                case 0: //ToDo: Make NOT magic numbers, enum somewhere
                    return IronBackpacksAchievements.basicPackCrafted;
                case 1:
                    return IronBackpacksAchievements.ironPackCrafted;
                case 2:
                    return IronBackpacksAchievements.goldPackCrafted;
                case 3:
                    return IronBackpacksAchievements.diamondPackCrafted;
            }
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        try {
            EntityPlayer player = IronBackpacks.proxy.getClientPlayer();
            if (player != null && player.getGameProfile().getId().equals(UUID.fromString("eb21559e-bb22-46f2-897b-71eee2d5c09b"))) {
                return super.getItemStackDisplayName(stack).replaceAll("Backpack", "Snail");
            } else {
                return super.getItemStackDisplayName(stack);
            }
        } catch (RuntimeException e) {
            Logger.error("Display name errors found. Report to mod author please.");
            return super.getItemStackDisplayName(stack);
        }
    }

}
