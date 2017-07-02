package gr8pefish.ironbackpacks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class BackpackContainer extends Container {

    private final int blocked;
    private final int size;

    public BackpackContainer(InventoryPlayer invPlayer, EnumHand hand, IItemHandlerModifiable invBag) {

        this.size = invBag.getSlots();

        System.out.println("Inventory size: "+ size);

        int cols = 9;
        int rows = size/9;
        int i;
        int j;

        //Backpack slots
        for (i = 0; i < rows; ++i) {
            for (j = 0; j < cols; ++j) {
                int k = j + i * 9;
                addSlotToContainer(new SlotItemHandler(invBag, k, 8 + j * 18, 18 + i * 18));
            }
        }

        //Player Inventory slots
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 68 + i * 18));
            }
        }

        //Hotbar slots
        for (i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 126));
        }

        blocked = hand == EnumHand.MAIN_HAND ? (inventorySlots.size() - 1) - (8 - invPlayer.currentItem) : -1;
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player)
    {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        Slot slot = this.getSlot(slotIndex);

        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();
        ItemStack newStack = stack.copy();

        if (slotIndex < size) {
            if (!this.mergeItemStack(stack, size, this.inventorySlots.size(), true))
                return ItemStack.EMPTY;
            slot.onSlotChanged();
        }
        else if (!this.mergeItemStack(stack, 0, size, false)) {
            return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        }
        else {
            slot.onSlotChanged();
        }

        return slot.onTake(player, newStack);
    }

    @Nonnull
    @Override
    public ItemStack slotClick(int slot, int button, ClickType flag, EntityPlayer player) {
        if (slot == blocked) {
            return ItemStack.EMPTY;
        }

        return super.slotClick(slot, button, flag, player);
    }
}
