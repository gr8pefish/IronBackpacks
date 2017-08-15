package gr8pefish.ironbackpacks.api.upgrade;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Used to mark an item as an upgrade so it can be picked up by internal handling.
 */
public interface IUpgrade {

    /**
     * Gets the container object for all upgrade data from the stack.
     *
     * @param stack The stack to get the upgrade from
     * @return the container object for all upgrade data
     */
    @Nonnull
    default BackpackUpgrade getUpgrade(@Nonnull ItemStack stack) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("upgrade"))
            return IronBackpacksAPI.getUpgrade(new ResourceLocation(stack.getTagCompound().getString("upgrade")));

        return IronBackpacksAPI.getUpgrade(IronBackpacksAPI.NULL);
    }
}
