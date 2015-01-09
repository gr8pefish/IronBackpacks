package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.client.gui.inventory.GUIBackpack;
import main.ironbackpacks.container.alternateGui.InventoryAlternateGui;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public enum UpgradeMethods {

    NONE{
        @Override
        public void doGuiAlteration(GUIBackpack gui, FontRenderer fontRenderer, int xSize, int ySize) {

        }
        @Override
        public void doContainerAlteration(){

        }
    },
    BUTTON{
        @Override
        public void doGuiAlteration(GUIBackpack gui, FontRenderer fontRenderer, int xSize, int ySize) {
////            fontRenderer.drawString("Button", xSize - 20, ySize - 96 + 2, 4210752);
//            fontRenderer.drawString(StatCollector.translateToLocal("player.inventor"), 20, ySize - 96 + 2, 4210752);
        }
        @Override
        public void doContainerAlteration(){

        }
    };

    public abstract void doGuiAlteration(GUIBackpack gui, FontRenderer fontRenderer, int xSize, int ySize);
    public abstract void doContainerAlteration();

    public static boolean hasButtonUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int i = 0; i < upgrades.length; i++){
            if (upgrades[i] == IronBackpacksConstants.Upgrades.BUTTON_UPGRADE_ID){
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasNestingUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int i = 0; i < upgrades.length; i++){
            if (upgrades[i] == IronBackpacksConstants.Upgrades.NESTING_UPGRADE_ID){
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasRenamingUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        if (!ConfigHandler.renamingUpgradeRequired){
            return true;
        }else {
            for (int i = 0; i < upgrades.length; i++) {
                if (upgrades[i] == IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID) {
                    hasUpgrade = true;
                    break;
                }
            }
        }
        return hasUpgrade;
    }

    public static boolean hasDamageBarUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int i = 0; i < upgrades.length; i++) {
            if (upgrades[i] == IronBackpacksConstants.Upgrades.DAMAGE_BAR_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasFilterUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int i = 0; i < upgrades.length; i++) {
            if (upgrades[i] == IronBackpacksConstants.Upgrades.FILTER_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasHopperUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int i = 0; i < upgrades.length; i++) {
            if (upgrades[i] == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasCondenserUpgrade(int[] upgrades) {
        boolean hasUpgrade = false;
        for (int i = 0; i < upgrades.length; i++) {
            if (upgrades[i] == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static int getAlternateGuiUpgradesCount(int[] upgrades){ //TODO - make these more dynamic
        int numberOfUpgrades = 0;
        if (!ConfigHandler.renamingUpgradeRequired) {
            numberOfUpgrades = 1;
            for (int i = 0; i < upgrades.length; i++) {
                if (upgrades[i] == IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID || upgrades[i] == IronBackpacksConstants.Upgrades.FILTER_UPGRADE_ID ||
                        upgrades[i] == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrades[i] == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                    numberOfUpgrades++;
                }
            }
        }else{
            for (int i = 0; i < upgrades.length; i++) {
                if (upgrades[i] == IronBackpacksConstants.Upgrades.RENAMING_UPGRADE_ID || upgrades[i] == IronBackpacksConstants.Upgrades.FILTER_UPGRADE_ID ||
                        upgrades[i] == IronBackpacksConstants.Upgrades.HOPPER_UPGRADE_ID || upgrades[i] == IronBackpacksConstants.Upgrades.CONDENSER_UPGRADE_ID) {
                    numberOfUpgrades++;
                }
            }
        }
        return numberOfUpgrades;
    }

    public static Item[] getFilterItems(ItemStack stack){
        Item[] returnArray = new Item[9]; //TODO - hardcoded

        ItemBaseBackpack backpack = ((ItemBaseBackpack) stack.getItem());

        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null){
            if (nbtTagCompound.hasKey("Filter")) {
                NBTTagList tagList = nbtTagCompound.getTagList("Filter", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    int j = stackTag.getByte("Slot");
                    if (i >= 0 && i <= 9) { //TODO - hardcoded
                        returnArray[j] = ItemStack.loadItemStackFromNBT(stackTag).getItem();
                    }
                }
            }
        }
        return returnArray;
    }

    public static int getAlternateGuiUpgradeSlots(int[] upgrades) {
        int slots = 0;
        for (int i = 0; i < upgrades.length; i++) {
            if (upgrades[i] == IronBackpacksConstants.Upgrades.FILTER_UPGRADE_ID) {
                slots += 9; //TODO - hardcoded
            }
        }
        return slots;
    }



}
