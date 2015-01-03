package main.ironbackpacks.items.backpacks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.inventory.InventoryBackpack;
import main.ironbackpacks.items.ItemBase;
import main.ironbackpacks.items.upgrades.UpgradeTypes;
import main.ironbackpacks.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBaseBackpack extends ItemBase {

    protected int id;
    private int upgradeSlots;

    public ItemBaseBackpack(String unlocName, String textureName,int id, int upgradeSlots) {
        super(unlocName, textureName);
        setMaxStackSize(1);
        this.id = id;
        this.upgradeSlots = upgradeSlots;
    }

    public int getUpgradeSlots(){
        return upgradeSlots;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote){
            return itemStack;
        }else {
//            NBTHelper.setUUID(itemStack); //TODO - need to add this in?
            int[] upgrades;

            if (!player.isSneaking()) {
                upgrades = getUpgradesFromNBT(player, itemStack);
                int x = upgrades[0];
                int y = upgrades[1];
                int z = upgrades[2];

                player.openGui(IronBackpacks.instance, id, world, x, y, z);
                return itemStack;
            }else{
                return itemStack; //TODO - alternate GUI for renaming here
            }
        }
    }

    public int[] getUpgradesFromNBT(EntityPlayer player, ItemStack parent) {
        int[] upgrades = new int[3]; //default [0,0,0]
        ItemStack stack = findParentItemStack(player, parent);
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if(nbtTagCompound.hasKey("Upgrades")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Upgrades", Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int hasUpgrade = stackTag.getByte("Upgrade");
                        if (hasUpgrade == 1){ //true
                            upgrades[i] = hasUpgrade;
                        }
                    }
                }
            }
        }
        return upgrades;
    }

    public int[] getUpgradesFromNBT(ItemStack stack) { //TODO - Unckecked
        int[] upgrades = new int[3]; //default [0,0,0]
//        ItemStack stack = findParentItemStack(player, parent);
        if (stack != null) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null) {
                if(nbtTagCompound.hasKey("Upgrades")) {
                    NBTTagList tagList = nbtTagCompound.getTagList("Upgrades", Constants.NBT.TAG_COMPOUND); //TODO - should be TAG_LIST?
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int hasUpgrade = stackTag.getByte("Upgrade");
                        if (hasUpgrade != 0){ //true
                            upgrades[i] = hasUpgrade;
                        }
                    }
                }
            }
        }
        System.out.println("Upgrades: "+upgrades[0]+upgrades[1]+upgrades[2]);
        return upgrades;
    }

    public ItemStack findParentItemStack(EntityPlayer entityPlayer, ItemStack stack){
        if (NBTHelper.hasUUID(stack)){
            UUID parentUUID = new UUID(stack.getTagCompound().getLong(ModInformation.MOST_SIG_UUID), stack.getTagCompound().getLong(ModInformation.LEAST_SIG_UUID));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++){
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

                if (itemStack != null && itemStack.getItem() instanceof ItemBaseBackpack && NBTHelper.hasUUID(itemStack)){
                    if (itemStack.getTagCompound().getLong(ModInformation.MOST_SIG_UUID) == parentUUID.getMostSignificantBits() && itemStack.getTagCompound().getLong(ModInformation.LEAST_SIG_UUID) == parentUUID.getLeastSignificantBits()){
                        return itemStack;
                    }
                }
            }
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        int[] upgrades = this.getUpgradesFromNBT(itemStack);
        for (int i = 0; i < this.upgradeSlots; i++){
//            if (upgrades[i] == 0) {
//                list.add("Empty upgrade slot");
//            }else{
            list.add(UpgradeTypes.values()[upgrades[i]].getFancyName());
//            }
        }
    }
}
