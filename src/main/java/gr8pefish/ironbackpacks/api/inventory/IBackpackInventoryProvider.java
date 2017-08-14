package gr8pefish.ironbackpacks.api.inventory;

import gr8pefish.ironbackpacks.api.variant.BackpackVariant;
import gr8pefish.ironbackpacks.api.variant.BackpackVariantEnum;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

/**
 * Provides an interface for the capability which exposes the internal inventory of a backpack.
 * Acquire an instance of this using {@link net.minecraft.item.ItemStack#getCapability(Capability, EnumFacing)}.
 */
public interface IBackpackInventoryProvider extends INBTSerializable<NBTTagCompound> {

    /**
     * Note: modifying this clientside is not advised
     * @param variant The backpack variant, can use helpers to access this
     * @return The inventory representing this backpack
     */
    @Nonnull
//    IItemHandler getInventory(@Nonnull BackpackVariant variant);
    IItemHandler getInventory(@Nonnull BackpackVariantEnum variant);

}
