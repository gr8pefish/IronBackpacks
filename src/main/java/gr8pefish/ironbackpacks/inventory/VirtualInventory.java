package gr8pefish.ironbackpacks.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

import java.util.List;

public class VirtualInventory implements Inventory {
    private final DefaultedList<ItemStack> items;

    public VirtualInventory(int size) {
        items = DefaultedList.ofSize(size, ItemStack.EMPTY);
    }

    public VirtualInventory(int size, List<ItemStack> items) {
        this(size);
        for (int i = 0; i < items.size(); i++) {
            setInvStack(i, items.get(i) == null ? ItemStack.EMPTY : items.get(i));
        }
    }

    public static VirtualInventory fromTag(CompoundTag tag) {
        int size = tag.getInt("Size");
        DefaultedList<ItemStack> items = DefaultedList.ofSize(size, ItemStack.EMPTY);
        Inventories.fromTag(tag, items);
        return new VirtualInventory(size, items);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static CompoundTag toTag(CompoundTag tag, Inventory inventory) {
        DefaultedList<ItemStack> items = DefaultedList.ofSize(inventory.getInvSize(), ItemStack.EMPTY);
        for (int i = 0; i < inventory.getInvSize(); i++) {
            items.set(i, inventory.getInvStack(i));
        }
        CompoundTag invTag = tag.getCompound("Inventory");
        invTag.putInt("Size", inventory.getInvSize());
        return Inventories.toTag(invTag, items);
    }

    @Override
    public int getInvSize() {
        return items.size();
    }

    @Override
    public boolean isInvEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getInvStack(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack takeInvStack(int slot, int amount) {
        markDirty();
        return Inventories.splitStack(items, slot, amount);
    }

    @Override
    public ItemStack removeInvStack(int slot) {
        markDirty();
        return Inventories.removeStack(items, slot);
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getInvMaxStackAmount()) {
            stack.setCount(getInvMaxStackAmount());
        }
        markDirty();
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {
        items.clear();
        markDirty();
    }
}
