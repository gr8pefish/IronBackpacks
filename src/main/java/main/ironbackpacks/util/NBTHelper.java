package main.ironbackpacks.util;

import main.ironbackpacks.ModInformation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class NBTHelper {

    public static boolean hasUUID(ItemStack itemStack){
        return hasTag(itemStack, ModInformation.MOST_SIG_UUID) && hasTag(itemStack, ModInformation.LEAST_SIG_UUID);
    }

    public static void setUUID(ItemStack itemStack){
        initNBTCompound(itemStack);

        if (!hasUUID(itemStack)){
            UUID itemUUID = UUID.randomUUID();

            setLong(itemStack, ModInformation.MOST_SIG_UUID, itemUUID.getMostSignificantBits());
            setLong(itemStack, ModInformation.LEAST_SIG_UUID, itemUUID.getLeastSignificantBits());
        }
    }

    public static UUID getUUID(ItemStack itemStack){
        initNBTCompound(itemStack);

        if (hasUUID(itemStack)){
            return new UUID(itemStack.getTagCompound().getLong(ModInformation.MOST_SIG_UUID), itemStack.getTagCompound().getLong(ModInformation.LEAST_SIG_UUID));
        }
        return null;
    }

    private static void setLong(ItemStack itemStack, String tag, Long value){
        initNBTCompound(itemStack);
        itemStack.stackTagCompound.setLong(tag, value);
    }

    private static boolean hasTag(ItemStack itemStack, String tag){
        return itemStack != null && itemStack.hasTagCompound() && itemStack.stackTagCompound.hasKey(tag);
    }

    private static void initNBTCompound(ItemStack itemStack){
        if (itemStack.stackTagCompound == null){
            itemStack.setTagCompound(new NBTTagCompound());
        }
    }
}
