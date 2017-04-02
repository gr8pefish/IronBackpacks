package gr8pefish.ironbackpacks.api;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IUpgrade {

    @Nonnull
    BackpackUpgrade getUpgrade(ItemStack stack);
}
