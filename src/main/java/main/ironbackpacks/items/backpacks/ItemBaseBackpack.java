package main.ironbackpacks.items.backpacks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.items.ItemBase;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.network.UpdateBackpackMessage;
import main.ironbackpacks.proxies.CommonProxy;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemBaseBackpack extends ItemBase {

    private int guiId;
    private int typeID;
    private int upgradeSlots;
    private int upgradeIndex;

    private boolean openAltGui;

    public ItemBaseBackpack(String unlocName, String textureName, int id, int upgradeSlots, int typeID) {
        super(unlocName, textureName);
        setMaxStackSize(1);
        this.guiId = id; //0,1,2,3
        this.typeID = typeID; //1,2,3,4
        this.upgradeSlots = upgradeSlots;
        this.upgradeIndex = upgradeSlots-1;

        openAltGui = true;
    }

    public int getTypeId() { return typeID;}

    public int getGuiId() { return guiId;}

    public int getUpgradeSlots(){
        return upgradeSlots;
    }

    public int getUpgradeIndex(){
        return upgradeIndex;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(stack);
        return UpgradeMethods.hasDamageBarUpgrade(upgrades);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getFullness(stack);
    }

    //gets the fullness of the backpack for the durability bar
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


    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote){ //client side
//            NetworkingHandler.network.sendToServer(new UpdateBackpackMessage(itemStack)); //TODO: try with this line?
            //TODO: check that the client side is getting the right player (EntityPlayer, not EntityPlayerMP) so it retrieves the correct backpack
            CommonProxy.updateCurrBackpack(player, itemStack);
            return itemStack;
        }else {
            NBTHelper.setUUID(itemStack);
//            NetworkingHandler.network.sendToServer(new UpdateBackpackMessage(stack));
            CommonProxy.updateCurrBackpack(player, itemStack);
            if (!player.isSneaking()) {
                player.openGui(IronBackpacks.instance, guiId, world, (int) player.posX, (int) player.posY, (int) player.posZ);
                return itemStack;
            }else {
                if (openAltGui) player.openGui(IronBackpacks.instance, (guiId * -1) - 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
                else openAltGui = true;
                return itemStack;
            }
        }
    }

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
