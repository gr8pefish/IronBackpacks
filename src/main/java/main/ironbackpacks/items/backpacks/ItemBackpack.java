package main.ironbackpacks.items.backpacks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.lwjgl.input.Keyboard;

import java.util.List;

/**
 * Base class for all the backpack items to extend
 */
public class ItemBackpack extends Item implements IBackpack {

    private boolean openAltGui = true; //to track which gui to open

    private final int id; //internal id
    private final int size; //size of the backpack
    private final int rowLength; //length of each row
    private final int upgradePoints; //number of upgradePoints
    private final String fancyName; //display name

    public ItemBackpack(int id, int size, int rowLength, String texture, String fancyName, int upgradePoints) {
        setCreativeTab(IronBackpacks.creativeTab);
        setTextureName(ModInformation.ID + ":" + texture);
        setUnlocalizedName(ModInformation.ID + ":" + fancyName);
        setMaxStackSize(1);
        setNoRepair();

        this.id = id;
        this.size = size;
        this.rowLength = rowLength;
        this.upgradePoints = upgradePoints;
        this.fancyName = fancyName;
    }

    public ItemBackpack(BackpackTypes type) {
        this(type.getId(), type.getSize(), type.getRowLength(), type.getTexture(), type.getName(), type.getUpgradePoints());
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
    @Override
    public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) { //server side
            if (!player.isSneaking()) { //only do it when player is sneaking
                return false;
            }
            if (UpgradeMethods.hasQuickDepositUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(itemstack))) {
                openAltGui = !UpgradeMethods.transferFromBackpackToInventory(player, itemstack, world, x, y, z);
                return !openAltGui;
            }
        }
        return false;
    }

    //to open the guis
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote){ //client side
            IronBackpacks.proxy.updateCurrBackpack(player, itemStack); //need to update on client side so has access to backpack for GUI's backpack stack's display name
            return itemStack;
        } else {
            NBTHelper.setUUID(itemStack);
            IronBackpacks.proxy.updateCurrBackpack(player, itemStack);
            if (!player.isSneaking()){
                player.openGui(IronBackpacks.instance, getGuiId(), world, (int) player.posX, (int) player.posY, (int) player.posZ); //"Normal usage"
                return itemStack;
            }else { //if sneaking
                if (openAltGui)
                    player.openGui(IronBackpacks.instance, (getGuiId() * -1) - 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
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
                list.add(""); //blank line for spacing/aesthetics

            list.add(upgradesUsed + "/" + totalUpgradePoints + " upgrade points used."); //upgrades used
            list.add(UpgradeMethods.getAltGuiUpgradesUsed(upgrades) + "/" + IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED + " alternate gui upgrades used."); //alt gui upgrades used

            if (ConfigHandler.renamingUpgradeRequired)
                list.add("(The " + IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED + "th upgrade must include the renaming upgrade)");

            int tierUpgradeCount = ConfigHandler.additionalUpgradesLimit + getGuiId(); //hardcoded with 4 tiers

            if (tierUpgradeCount > 0)
                list.add((IronBackpacksHelper.getAdditionalUpgradesTimesApplied(stack) * ConfigHandler.additionalUpgradesIncrease) + "/" + (tierUpgradeCount * ConfigHandler.additionalUpgradesIncrease) + " additional upgrade points added."); //additional upgrade points used
        } else {
            if (totalUpgradePoints > 0)
                list.add("Hold shift for more info.");
        }
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
    public int getUpgradeSlots() {
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
    public String getFancyName() {
        return fancyName;
    }

    @Override
    public int getGuiId() {
        return getId() - 1;
    }

}
