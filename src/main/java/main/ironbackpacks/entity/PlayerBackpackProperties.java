package main.ironbackpacks.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerBackpackProperties implements IExtendedEntityProperties {

    public static final String PROP_ID = "ironBackpacks";
    private ItemStack equippedBackpack;

    @Override
    public void saveNBTData(NBTTagCompound tag) {
        NBTTagCompound properties = new NBTTagCompound();
        if (equippedBackpack != null)
            equippedBackpack.writeToNBT(properties);
        tag.setTag(PROP_ID, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound tag) {
        NBTTagCompound properties = (NBTTagCompound) tag.getTag(PROP_ID);
        try {
            equippedBackpack = ItemStack.loadItemStackFromNBT(properties);
        } catch (NullPointerException e) {
            equippedBackpack = null;
        }
    }

    @Override
    public void init(Entity entity, World world) {

    }

    public ItemStack getEquippedBackpack() {
        return equippedBackpack;
    }

    public PlayerBackpackProperties setEquippedBackpack(ItemStack stack) {
        this.equippedBackpack = stack;
        return this;
    }

    public static void create(EntityLivingBase livingBase) {
        livingBase.registerExtendedProperties(PROP_ID, new PlayerBackpackProperties());
    }

    public static PlayerBackpackProperties get(EntityLivingBase livingBase) {
        return (PlayerBackpackProperties) livingBase.getExtendedProperties(PROP_ID);
    }

    public static ItemStack getEquippedBackpack(EntityLivingBase livingBase) {
        return get(livingBase).getEquippedBackpack();
    }

    public static void setEquippedBackpack(EntityLivingBase livingBase, ItemStack stack) {
        get(livingBase).setEquippedBackpack(stack);
    }

    public static void reset(EntityLivingBase livingBase) {
        get(livingBase).setEquippedBackpack(null);
    }
}
