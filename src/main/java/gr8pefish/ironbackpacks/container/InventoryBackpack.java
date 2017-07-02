package gr8pefish.ironbackpacks.container;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

//ToDo: Currently unused, may implement later if need additional functionality via a wrapper like this
//public class InventoryBackpack implements IItemHandlerModifiable {
//
//    private final IItemHandlerModifiable backpackInventory;
//    private final ItemStack backpackStack;
//
//    public InventoryBackpack(ItemStack backpackStack) {
//        this.backpackStack = backpackStack;
//        backpackInventory = (IItemHandlerModifiable) backpackStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
//    }
//
//    protected ItemStack getBackpackStack() {
//        return backpackStack;
//    }
//
//    @Override
//    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
//        backpackInventory.setStackInSlot(slot, stack);
//    }
//
//    @Override
//    public int getSlots() {
//        return backpackInventory.getSlots();
//    }
//
//    @Nonnull
//    @Override
//    public ItemStack getStackInSlot(int slot) {
//        return backpackInventory.getStackInSlot(slot);
//    }
//
//    @Nonnull
//    @Override
//    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
//        return backpackInventory.insertItem(slot, stack, simulate);
//    }
//
//    @Nonnull
//    @Override
//    public ItemStack extractItem(int slot, int amount, boolean simulate) {
//        return backpackInventory.extractItem(slot, amount, simulate);
//    }
//
//    @Override
//    public int getSlotLimit(int slot) {
//        return backpackInventory.getSlotLimit(slot);
//    }
//}
