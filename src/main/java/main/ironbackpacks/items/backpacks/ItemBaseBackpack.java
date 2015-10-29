package main.ironbackpacks.items.backpacks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.items.ItemBase;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
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
public class ItemBaseBackpack extends ItemBase {

    private int guiId; //the id for the gui used
    private int typeID; //the id for the type of backpack (IronBackpackTypes)
    private int upgradeSlots; //number of upgrade slots
    private boolean openAltGui; //which gui to open

    public ItemBaseBackpack(String unlocName, String textureName, int upgradeSlots, int ID) {
        super(unlocName, textureName);
        setMaxStackSize(1);
        setNoRepair();
        this.upgradeSlots = upgradeSlots;
        guiId = ID-1; //0,1,2,3
        typeID = ID; //1,2,3,4
        openAltGui = true;

    }

    public int getTypeId() { return typeID;}

    public int getGuiId() { return guiId;}

    public int getUpgradeSlots(){
        return upgradeSlots;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(stack);
        return UpgradeMethods.hasDamageBarUpgrade(upgrades);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getFullness(stack);
    }

    /**
     * Gets the fullness of the backpack for the durability bar.
     * Note: Checks total fullness used, so if all slots hold 1 stackable item (itemX) it will show the fullness of only (type.size * itemX) and not as full.
     * @param stack - the backpack
     * @return - double representing the fullness
     */
    public double getFullness(ItemStack stack){
        ItemStack[] inventory;
        int total = 0;
        int full = 0;
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null){
                if (nbtTagCompound.hasKey("Items")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
                    inventory = new ItemStack[IronBackpackType.values()[this.guiId].getSize()];
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = stackTag.getByte("Slot");
                        if (i >= 0 && i <= inventory.length) {
                            inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                    }
                    for (ItemStack tempStack: inventory) {
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

    //to open the guis
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote){ //client side
            System.out.println("Client side gogogo");
            IronBackpacks.proxy.updateCurrBackpack(player, itemStack); //need to update on client side so has access to backpack for GUI's backpack stack's display name //TODO: alter?
            return itemStack;
        }else {
            NBTHelper.setUUID(itemStack);
            IronBackpacks.proxy.updateCurrBackpack(player, itemStack);
            if (!player.isSneaking()){
//                System.out.println("not sneaking");
//                openServerGui((EntityPlayerMP)player, guiId); //TODO: custom implementation of opening guis
                player.openGui(IronBackpacks.instance, guiId, world, (int) player.posX, (int) player.posY, (int) player.posZ);
                if (IronBackpacksHelper.getEquippedBackpack(player) != null){
                    System.out.println("equipped pack on side x");
                }
//                NetworkingHandler.network.sendTo(new OpenGuiClientElement(guiId, itemStack), (EntityPlayerMP)player); //TODO ? commented in before
                return itemStack;
            }else { //if sneaking
                System.out.println("sneaking");
                if (openAltGui) player.openGui(IronBackpacks.instance, (guiId * -1) - 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
                else openAltGui = true;
                return itemStack;
            }
        }
    }

    private void openServerGui(EntityPlayerMP entityPlayerMP, int guiId){
        IronBackpackType type = IronBackpackType.values()[guiId];
        Container remoteGuiContainer = new ContainerBackpack(entityPlayerMP, new InventoryBackpack(entityPlayerMP, IronBackpacksHelper.getBackpack(entityPlayerMP), type), type);
        entityPlayerMP.getNextWindowId();
        entityPlayerMP.closeContainer();
        int windowId = entityPlayerMP.currentWindowId;
        entityPlayerMP.openContainer = remoteGuiContainer;
        entityPlayerMP.openContainer.windowId = windowId;
        entityPlayerMP.openContainer.addCraftingToCrafters(entityPlayerMP);
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

    public void openAltGui(EntityPlayer player, World world){
        player.openGui(IronBackpacks.instance, (guiId * -1) - 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
    }

    //Adds a fancy tooltip
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(itemStack);
        int totalUpgradePoints = IronBackpacksHelper.getTotalUpgradePointsFromNBT(itemStack);
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            int upgradesUsed = 0;
            for (int upgrade : upgrades){
                list.add(IronBackpacksConstants.Upgrades.LOCALIZED_NAMES[upgrade]);
                upgradesUsed += IronBackpacksConstants.Upgrades.UPGRADE_POINTS[upgrade];
            }
            if (upgrades.length > 0) list.add(""); //blank line for spacing/aesthetics
            list.add(upgradesUsed + "/" +totalUpgradePoints + " upgrade points used."); //upgrades used
            list.add(UpgradeMethods.getAltGuiUpgradesUsed(upgrades) + "/" + IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED + " alternate gui upgrades used."); //alt gui used
            if (ConfigHandler.renamingUpgradeRequired) list.add("(The "+IronBackpacksConstants.Upgrades.ALT_GUI_UPGRADES_ALLOWED+"th upgrade must include the renaming upgrade)");
            int tierUpgradeCount = ConfigHandler.additionalUpgradesLimit + this.getGuiId(); //hardcoded with 4 tiers
            if (tierUpgradeCount > 0) list.add((IronBackpacksHelper.getAdditionalUpgradesTimesApplied(itemStack) * ConfigHandler.additionalUpgradesIncrease) +
                    "/" + (tierUpgradeCount * ConfigHandler.additionalUpgradesIncrease) + " additional upgrade points added."); //additional upgrade times used
        }else{
            if (totalUpgradePoints > 0) {
                list.add("Hold shift for more info.");
            }
        }
    }

}
