package gr8pefish.ironbackpacks.inventory;

import gr8pefish.ironbackpacks.item.BaseBackpackItem;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

public class BackpackInventory extends BasicInventory {
    private final ItemStack backpackStack;

    public BackpackInventory(ItemStack backpackStack, int size) {
        super(size);
        this.backpackStack = backpackStack;
        CompoundTag tag = backpackStack.getOrCreateTag();
        if (tag.contains("Inventory")) {
            DefaultedList<ItemStack> items = DefaultedList.ofSize(getInvSize(), ItemStack.EMPTY);
            Inventories.fromTag(tag.getCompound("Inventory"), items);
            for (int slot = 0; slot < getInvSize(); slot++) {
                setInvStack(slot, items.get(slot));
            }
        }
    }

    private DefaultedList<ItemStack> getItems() {
        DefaultedList<ItemStack> items = DefaultedList.ofSize(getInvSize(), ItemStack.EMPTY);
        for (int slot = 0; slot < getInvSize(); slot++) {
            items.set(slot, getInvStack(slot));
        }
        return items;
    }

    @Override
    public boolean isValidInvStack(int slot, ItemStack stack) {
        return !(stack.getItem() instanceof BaseBackpackItem);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        CompoundTag inventory = Inventories.toTag(new CompoundTag(), getItems());
        inventory.putInt("Size", getInvSize());
        backpackStack.putSubTag("Inventory", inventory);
    }
}
