package gr8pefish.ironbackpacks.api;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IBackpack {

    @Nonnull
    default BackpackInfo getBackpackInfo(ItemStack stack) {
        return BackpackInfo.fromStack(stack);
    }

    default void updateBackpack(ItemStack stack, BackpackInfo backpackInfo) {
        IronBackpacksHelper.applyPackInfo(stack, backpackInfo);
    }
}
