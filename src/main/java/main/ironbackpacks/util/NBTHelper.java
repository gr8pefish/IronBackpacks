package main.ironbackpacks.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class NBTHelper {

    //Code for making sure the itemStack is unique, credit goes to sapient for almost all of this code

    /**
     * Checks if the item has a UUID
     * @param itemStack - the itemstack to check
     * @return - boolean value
     */
    public static boolean hasUUID(ItemStack itemStack){
        return hasTag(itemStack, IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID) && hasTag(itemStack, IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID);
    }

    /**
     * Sets the UUID
     * @param itemStack - the itemstack to set
     */
    public static void setUUID(ItemStack itemStack){
        initNBTCompound(itemStack);

        if (!hasUUID(itemStack)){
            UUID itemUUID = UUID.randomUUID();

            setLong(itemStack, IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID, itemUUID.getMostSignificantBits());
            setLong(itemStack, IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID, itemUUID.getLeastSignificantBits());
        }
    }

    /**
     * Gets the UUID
     * @param itemStack - the itemstack to check
     * @return - UUID of the itemstack
     */
    public static UUID getUUID(ItemStack itemStack){
        initNBTCompound(itemStack);

        if (hasUUID(itemStack)){
            return new UUID(itemStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID), itemStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID));
        }
        return null;
    }

    /**
     * Sets a value of data type long to the itemstack
     * @param itemStack - the stack to set to
     * @param tag - the tag referring to the value
     * @param value - the long value to set it to
     */
    private static void setLong(ItemStack itemStack, String tag, Long value){
        initNBTCompound(itemStack);
        itemStack.stackTagCompound.setLong(tag, value);
    }

    /**
     * Checks if the stack has a tag
     * @param itemStack - the itemstack to check
     * @param tag - the tag that references the stored value
     * @return - boolean
     */
    private static boolean hasTag(ItemStack itemStack, String tag){
        return itemStack != null && itemStack.hasTagCompound() && itemStack.stackTagCompound.hasKey(tag);
    }

    /**
     * Only sets a new compound if the stack doesn't already have one.
     * @param itemStack - the stack to set it to
     */
    private static void initNBTCompound(ItemStack itemStack){
        if (itemStack.stackTagCompound == null){
            Logger.info("Resetting NBT tag");
            itemStack.setTagCompound(new NBTTagCompound());
        }
    }
}
