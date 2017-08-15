package gr8pefish.ironbackpacks.api.backpack.inventory;

import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

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
    public static IItemHandler getBackpackInventory(ItemStack stack) {
        if (stack.hasCapability(IronBackpacksInventoryHelper.BACKPACK_INV_CAPABILITY, null)) {
            IItemHandler inventory = stack.getCapability(IronBackpacksInventoryHelper.BACKPACK_INV_CAPABILITY, null).getInventory(BackpackInfo.fromStack(stack).getVariant());
            return inventory;
        }
        return null;
    }

    //TODO: More here, ideally don't allow "full access" to backpack's inventory, but rather wrapped safer methods to access it

}
