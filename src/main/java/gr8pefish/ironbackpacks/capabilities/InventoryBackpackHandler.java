package gr8pefish.ironbackpacks.capabilities;

import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.inventory.IBackpackInventoryProvider;
import gr8pefish.ironbackpacks.api.backpack.inventory.IronBackpacksInventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InventoryBackpackHandler implements IBackpackInventoryProvider, ICapabilityProvider {

    private ItemStack backpack;

    public InventoryBackpackHandler(ItemStack stack) {
        this.backpack = stack;
    }

    @Nonnull
    @Override
    public IItemHandler getInventory() {
        return BackpackInfo.fromStack(backpack).getInventory();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == IronBackpacksInventoryHelper.BACKPACK_INV_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == IronBackpacksInventoryHelper.BACKPACK_INV_CAPABILITY ? (T) this : null;
    }

    public static class Default extends InventoryBackpackHandler {
        public Default() {
            super(ItemStack.EMPTY);
        }
    }
}
