package main.ironbackpacks.items.backpacks;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.util.NBTHelper;
import main.ironbackpacks.util.TextUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.lwjgl.input.Keyboard;
import vazkii.botania.api.item.IBlockProvider;

import java.util.List;

/**
 * Base class for all the backpack items to extend
 */
@Optional.Interface(iface="vazkii.botania.api.item.IBlockProvider", modid="Botania")
public class ItemBackpack extends Item implements IBackpack, IBlockProvider {

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
    //returning true will stop the alt. gui from opening
    //returning false will let it continue as normal (i.e. it can open)
    @Override
    public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) { //server side
            if (!player.isSneaking()) { //only do it when player is sneaking
                return false;
            }
            int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(itemstack);
            boolean hasDepthUpgrade = UpgradeMethods.hasDepthUpgrade(upgrades);
            if (UpgradeMethods.hasQuickDepositUpgrade(upgrades)) {
                openAltGui = !UpgradeMethods.transferFromBackpackToInventory(player, itemstack, world, x, y, z, false);
                if (!hasDepthUpgrade)
                    return !openAltGui;
            }else if (UpgradeMethods.hasQuickDepositPreciseUpgrade(upgrades)) {
                openAltGui = !UpgradeMethods.transferFromBackpackToInventory(player, itemstack, world, x, y, z, true);
                if (!hasDepthUpgrade)
                    return !openAltGui;
            }
            boolean openAltGuiDepth;
            if (hasDepthUpgrade) {
                ItemBackpack itemBackpack = (ItemBackpack)itemstack.getItem();
                ContainerBackpack container = new ContainerBackpack(player, new InventoryBackpack(player, itemstack, BackpackTypes.values()[itemBackpack.getId()]), BackpackTypes.values()[itemBackpack.getId()]);
                for (int j = 0; j < container.getInventoryBackpack().getSizeInventory(); j++) {
                    ItemStack nestedBackpack = container.getInventoryBackpack().getStackInSlot(j);
                    if (nestedBackpack != null && nestedBackpack.getItem() != null && nestedBackpack.getItem() instanceof IBackpack) {
                        int[] nestedUpgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(nestedBackpack);
                        if (UpgradeMethods.hasQuickDepositUpgrade(nestedUpgrades)) {
                            openAltGuiDepth = !UpgradeMethods.transferFromBackpackToInventory(player, nestedBackpack, world, x, y, z, false);
                            if (!openAltGuiDepth) openAltGui = false;
                        }else if (UpgradeMethods.hasQuickDepositPreciseUpgrade(nestedUpgrades)) {
                            openAltGuiDepth = !UpgradeMethods.transferFromBackpackToInventory(player, nestedBackpack, world, x, y, z, true);
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

    //=======================================================IBackpack====================================================

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


    //==============================================IBlockProvider from Botania's API============================================

    /**
     * Uses Botania's API to make the backpack able to provide blocks to items that need it.
     * @param player - the player
     * @param requestor - itemStack requesting items
     * @param stack - the stack to request items from (i.e. my backpack)
     * @param block - the block requested
     * @param meta - metadata of the block
     * @param doIt - if a test or real thing (currently opposite of what it should be...)
     * @return - true if successful, false otherwise
     */
    @Optional.Method(modid="Botania")
    @Override
    public boolean provideBlock(EntityPlayer player, ItemStack requestor, ItemStack stack, Block block, int meta, boolean doIt) {

        //simulate inventory to see if it has items
        InventoryBackpack invBackpack = makeInv(IronBackpacks.proxy.getCurrBackpack(player), player);
        int amount = invBackpack.hasStackInInv(block, meta);

        if (amount > 0){
            if (doIt) { //if doIt actually remove items
                invBackpack.removeOneItem(block, meta); //returns true if it was removed
            }
            return true; //have to return true even in !doIt
        }

        return false; //if amount < 0 return false because you can't provide anything
    }

    /**
     * Uses Botania's API to get the available items
     * @param player - the player using the item
     * @param requestor - the item requestign the IBlockProvider
     * @param stack - the stack to request items from (my backpack)
     * @param block - the block to compare against
     * @param meta - the metadata of the block
     * @return integer of the amount of items the backpack has
     */
    @Optional.Method(modid="Botania")
    @Override
    public int getBlockCount(EntityPlayer player, ItemStack requestor, ItemStack stack, Block block, int meta) {
        InventoryBackpack invBackpack = makeInv(IronBackpacks.proxy.getCurrBackpack(player), player);
        int amount = invBackpack.hasStackInInv(block, meta);
        return amount;
    }

    /**
     * Simulates a backpack inventory
     * @param stack - the backpack itemstack
     * @param player - the player to simulate opening it
     * @return - the InventoryBackpack
     */
    private InventoryBackpack makeInv(ItemStack stack, EntityPlayer player){
        BackpackTypes type = BackpackTypes.values()[this.getGuiId()];
        return new InventoryBackpack(player, stack, type);
    }
}
