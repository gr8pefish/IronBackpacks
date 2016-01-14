package main.ironbackpacks.entity.extendedProperties;

import main.ironbackpacks.ModInformation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public class PlayerBackpackDeathProperties implements IExtendedEntityProperties {

    public static final String PROP_DEATH_ID = ModInformation.ID + "_death";
    private ArrayList<ItemStack> eternityPacks;
    private ItemStack equippedBackpack;

    @Override
    public void saveNBTData(NBTTagCompound tag) {
        //make new list
        NBTTagList tagList = new NBTTagList();

        //save the equipped pack
        NBTTagCompound equipped = new NBTTagCompound();
        if (equippedBackpack != null)
            equippedBackpack.writeToNBT(equipped);
        else
            equipped.setBoolean("noEquipped", false);
        tagList.appendTag(equipped);

        //save all the eternity packs
        if (eternityPacks != null && eternityPacks.size() > 0) {
            for (ItemStack pack : eternityPacks) {
                NBTTagCompound saved = new NBTTagCompound();
                pack.writeToNBT(saved);
                tagList.appendTag(saved);
            }
        }

        //save all to the tag
        tag.setTag(PROP_DEATH_ID, tagList);
    }

    @Override
    public void loadNBTData(NBTTagCompound tag) {
        NBTTagList tagList = tag.getTagList(PROP_DEATH_ID, Constants.NBT.TAG_COMPOUND);

        //get the equipped pack without crashing
        if (!tagList.getCompoundTagAt(0).hasKey("noEquipped")) { //if the key doesn't exist
            try {
                equippedBackpack = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(0));
            } catch (NullPointerException e) { //might as well keep this catch statement
                equippedBackpack = null;
            }
        } else {
            equippedBackpack = null;
        }

        //get all the eternity packs
        if (tagList.tagCount() >= 1) {
            for (int i = 1; i < tagList.tagCount(); i++) {
                if (tagList.getCompoundTagAt(i) != null)
                    eternityPacks.add(ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i)));
            }
        }

    }

    @Override
    public void init(Entity entity, World world) {

    }

    //Getters and setters

    public ItemStack getEquippedBackpack() {
        return equippedBackpack;
    }

    public PlayerBackpackDeathProperties setEquippedBackpack(ItemStack stack) {
        this.equippedBackpack = stack;
        return this;
    }

    public ArrayList<ItemStack> getEternityBackpacks() {
        return eternityPacks;
    }

    public PlayerBackpackDeathProperties setEternityBackpacks(ArrayList<ItemStack> packs) {
        this.eternityPacks = packs;
        return this;
    }

    //Other helper methods

    public static void create(EntityLivingBase livingBase) {
        livingBase.registerExtendedProperties(PROP_DEATH_ID, new PlayerBackpackDeathProperties());
    }

    public static PlayerBackpackDeathProperties get(EntityLivingBase livingBase) {
        return (PlayerBackpackDeathProperties) livingBase.getExtendedProperties(PROP_DEATH_ID);
    }

    //Static methods

    public static ItemStack getEquippedBackpack(EntityLivingBase livingBase) {
        return get(livingBase).getEquippedBackpack();
    }

    public static void setEquippedBackpack(EntityLivingBase livingBase, ItemStack stack) {
        get(livingBase).setEquippedBackpack(stack);
    }

    public static ArrayList<ItemStack> getEternityBackpacks(EntityLivingBase livingBase) {
        return get(livingBase).getEternityBackpacks();
    }

    public static void setEternityBackpacks(EntityLivingBase livingBase, ArrayList<ItemStack> packs) {
        get(livingBase).setEternityBackpacks(packs);
    }

    public static void reset(EntityLivingBase livingBase) {
        get(livingBase).setEquippedBackpack(null);
        get(livingBase).setEternityBackpacks(new ArrayList<>()); //empty list
    }

}
