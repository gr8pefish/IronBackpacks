package gr8pefish.ironbackpacks.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface IUpgrade {

    @Nonnull
    default BackpackUpgrade getUpgrade(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("upgrade"))
            return IronBackpacksAPI.getUpgrade(new ResourceLocation(stack.getTagCompound().getString("upgrade")));

        return IronBackpacksAPI.getUpgrade(IronBackpacksAPI.NULL);
    }
}
