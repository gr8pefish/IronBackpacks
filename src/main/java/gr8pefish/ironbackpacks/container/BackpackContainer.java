package gr8pefish.ironbackpacks.container;

import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSize;
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
    private final BackpackSize backpackSize;

    public BackpackContainer(InventoryPlayer invPlayer, EnumHand hand, IItemHandlerModifiable invBag, BackpackSize backpackSize) {

        this.size = invBag.getSlots();
        this.backpackSize = backpackSize;

        System.out.println("Inventory size: "+ backpackSize);

        setupSlots(invPlayer, invBag);

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

    public BackpackSize getBackpackSize() {
        return backpackSize;
    }

    public int getBorderTop() { return 17; }
    public int getBorderSide() { return 7; }
    public int getBorderBottom() { return 7; }

    /** Returns the space between container and player inventory in pixels. */
    public int getBufferInventory() { return 13; }
    /** Returns the space between player inventory and hotbar in pixels. */
    public int getBufferHotbar() { return 4; }

    public int getMaxColumns() { return BackpackSize.MAX.getColumns(); }
    public int getMaxRows() { return BackpackSize.MAX.getRows(); }


    public int getWidth() { return Math.max(backpackSize.getColumns(), 9) * 18 + getBorderSide() * 2; }
    public int getHeight() { return getBorderTop() + (backpackSize.getRows() * 18) +
            getBufferInventory() + (4 * 18) +
            getBufferHotbar() + getBorderBottom(); }

    public int getContainerInvWidth() { return backpackSize.getColumns() * 18; }
    public int getContainerInvHeight() { return backpackSize.getRows() * 18; }
    public int getContainerInvXOffset() { return getBorderSide() +
            Math.max(0, (getPlayerInvWidth() - getContainerInvWidth()) / 2); }

    public int getPlayerInvWidth() { return 9 * 18; }
    public int getPlayerInvHeight() { return 4 * 18 + getBufferHotbar(); }
    public int getPlayerInvXOffset() { return getBorderSide() +
            Math.max(0, (getContainerInvWidth() - getPlayerInvWidth()) / 2); }

    protected void setupSlots(InventoryPlayer inventoryPlayer, IItemHandlerModifiable itemHandler) {
        setupBackpackSlots(itemHandler);
        setupPlayerSlots(inventoryPlayer);
    }

    protected void setupBackpackSlots(IItemHandlerModifiable itemHandler) {
        int xOffset = 1 + getContainerInvXOffset();
        int yOffset = 1 + getBorderTop();
        for (int y = 0; y < backpackSize.getRows(); y++, yOffset += 18)
            for (int x = 0; x < backpackSize.getColumns(); x++)
                addSlotToContainer(new SlotItemHandler(itemHandler, x + y * backpackSize.getColumns(),
                        xOffset + x * 18, yOffset));
    }

    protected void setupPlayerSlots(InventoryPlayer inventoryPlayer) {
        int xOffset = 1 + getPlayerInvXOffset();
        int yOffset = 1 + getBorderTop() + getContainerInvHeight() + getBufferInventory();

        // Inventory
        for (int y = 0; y < 3; y++, yOffset += 18)
            for (int x = 0; x < 9; x++)
                addSlotToContainer(new Slot(
                        inventoryPlayer, x + y * 9 + 9,
                        xOffset + x * 18, yOffset));

        // Hotbar
        yOffset += getBufferHotbar();
        for (int x = 0; x < 9; x++)
            addSlotToContainer(new Slot(
                    inventoryPlayer, x,
                    xOffset + x * 18, yOffset));
    }



}
