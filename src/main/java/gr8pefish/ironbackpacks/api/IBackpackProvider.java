package gr8pefish.ironbackpacks.api;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public interface IBackpackProvider extends INBTSerializable<NBTTagCompound> {

    /**
     * Note: modifying this clientside is not advised
     * @param variant The backpack variant, can use helpers to access
     * @return The inventory representing this backpack
     */
    @Nonnull
    IItemHandler getInventory(@Nonnull BackpackVariant variant);

//    /**
//     * Syncs the bag inventory associated with this color to the player provided (usually the owner of this capability instance)
//     * @param color The bag color to sync. If null, syncs every color.
//     * @param player The player to sync the bags to.
//     */
//    void sync(EnumDyeColor color, @Nonnull EntityPlayerMP player);

}
