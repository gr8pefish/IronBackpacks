package gr8pefish.ironbackpacks.api;

import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IBackpack {

    @Nonnull
    default BackpackInfo getBackpackInfo(@Nonnull ItemStack stack) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");

        return BackpackInfo.fromStack(stack);
    }

    default void updateBackpack(@Nonnull ItemStack stack, @Nonnull BackpackInfo backpackInfo) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");
        Preconditions.checkNotNull(backpackInfo, "BackpackInfo cannot be null");

        IronBackpacksAPI.applyPackInfo(stack, backpackInfo);
    }
}
