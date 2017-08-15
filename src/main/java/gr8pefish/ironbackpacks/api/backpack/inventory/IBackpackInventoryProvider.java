package gr8pefish.ironbackpacks.api.backpack.inventory;

import gr8pefish.ironbackpacks.api.backpack.variant.BackpackVariant;
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
     * Gets the inventory of the backpack given the variant.
     * Note: Modifying this Client Side is not advised
     *
     * @param variant The backpack variant, can use helpers to access this
     * @return The inventory representing this backpack, as an {@link IItemHandler}
     */
    @Nonnull
    IItemHandler getInventory(@Nonnull BackpackVariant variant);

}
