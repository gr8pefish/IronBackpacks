package gr8pefish.ironbackpacks.api.backpack.inventory;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackVariant;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IronBackpacksInventoryHelper {

    /**
     * The capability object for IBackpackInventoryProvider.
     *
     * Query this to get the internal inventory. Alternatively use {@link IronBackpacksInventoryHelper#getBackpackInventory(ItemStack)}
     */
    @CapabilityInject(IBackpackInventoryProvider.class)
    public static final Capability<IBackpackInventoryProvider> BACKPACK_INV_CAPABILITY = null;

    /**
     * Gets the inventory from a backpack's ItemStack.
     *
     * @param stack - A backpack as an {@link ItemStack}
     * @return - The inventory if it has one (as an {@link IItemHandler}, null otherwise
     */
    @Nullable
    private static IItemHandler getBackpackInventory(@Nonnull ItemStack stack) { //TODO: Use this or remove it
        Preconditions.checkNotNull(stack, "ItemStack cannot be null.");
        //Check has capability
        if (stack.hasCapability(IronBackpacksInventoryHelper.BACKPACK_INV_CAPABILITY, null)) {
            //Get backpackInfo
            BackpackInfo backpackInfo = BackpackInfo.fromStack(stack);
            //Get backpackVariant
            BackpackVariant backpackVariant = backpackInfo.getVariant();
            //Get IItemHandler via capability, passing in variant
            return stack.getCapability(IronBackpacksInventoryHelper.BACKPACK_INV_CAPABILITY, null).getInventory(backpackVariant);
        }
        //Doesn't have the correct capability, return null
        return null;
    }

    //TODO: More here, ideally don't allow "full access" to backpack's inventory, but rather wrapped safer methods to access it

}
