package gr8pefish.ironbackpacks.container;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSize;
import gr8pefish.ironbackpacks.util.InventoryBlacklist;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The backpack's container class, holding all the slots of the backpack.
 */
@ChestContainer
public class ContainerBackpack extends Container {

    // Fields

    @Nonnull
    private final BackpackInfo backpackInfo;
    @Nonnull
    private final BackpackSize backpackSize;
    @Nonnull
    private final ItemStack backpackStack;
    /**
     * Offhand used or not.
     */
    private int blocked = -1;
    private ItemStack blockedStack = ItemStack.EMPTY;
    // In which slot of the player's inventory is the backpack.
    private Slot backpackSlot;

    // Constructor

    public ContainerBackpack(@Nonnull ItemStack backpackStack, @Nonnull InventoryPlayer inventoryPlayer, @Nullable EnumHand hand) {
        Preconditions.checkNotNull(backpackStack, "backpackStack cannot be null");
        Preconditions.checkNotNull(inventoryPlayer, "inventoryPlayer cannot be null");

        BackpackInfo backpackInfo = BackpackInfo.fromStack(backpackStack);
        IItemHandler itemHandler = backpackInfo.getInventory();

        Preconditions.checkNotNull(backpackInfo, "backpackInfo cannot be null");
        Preconditions.checkNotNull(itemHandler, "itemHandler cannot be null");

        this.backpackInfo = backpackInfo;
        this.backpackStack = backpackStack;
        this.backpackSize = backpackInfo.getVariant().getBackpackSize();

        setupSlots(inventoryPlayer, itemHandler, hand);
    }

    // Override

    // This method is used by minecraft to prevent a player from interacting with a container
    // that may have been broken while its window was opened, and therefore prevent item duping
    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        // If the backpack stack is no longer a backpack the player shouldn't be able to interact with this container
        if (!(backpackStack.getItem() instanceof IBackpack))
            return false;
        // If the backpack slot no longer has the backpack the player shouldn't be able to interact with this container
        if (backpackSlot != null && backpackSlot.getStack() != backpackStack)
            return false;
        return true;
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        super.putStackInSlot(slotID, stack);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        Slot slot = this.getSlot(slotIndex);

        if (!slot.canTakeStack(player))
            return slot.getStack();

        if (slotIndex == blocked)
            return ItemStack.EMPTY;

        if (!slot.getHasStack())
            return ItemStack.EMPTY;

        ItemStack stack = slot.getStack();
        if (!stack.isEmpty() && ItemStack.areItemStacksEqual(stack, blockedStack))
            return ItemStack.EMPTY;

        ItemStack newStack = stack.copy();

        if (InventoryBlacklist.INSTANCE.isBlacklisted(slot.getStack()))
            return ItemStack.EMPTY;
        else if (slotIndex < backpackSize.getTotalSize()) {
            if (!this.mergeItemStack(stack, backpackSize.getTotalSize(), this.inventorySlots.size(), true))
                return ItemStack.EMPTY;
            slot.onSlotChanged();
        } else if (!this.mergeItemStack(stack, 0, backpackSize.getTotalSize(), false))
            return ItemStack.EMPTY;

        if (stack.isEmpty())
            slot.putStack(ItemStack.EMPTY);
        else
            slot.onSlotChanged();

        return slot.onTake(player, newStack);
    }

    @Nonnull
    @Override
    public ItemStack slotClick(int slotId, int button, ClickType flag, EntityPlayer player) {
        if (slotId < 0 || slotId > inventorySlots.size())
            return super.slotClick(slotId, button, flag, player);

        Slot slot = inventorySlots.get(slotId);
        if (!canTake(slotId, slot, button, player, flag))
            return slot.getStack();

        return super.slotClick(slotId, button, flag, player);
    }

    // Helper

    public boolean canTake(int slotId, Slot slot, int button, EntityPlayer player, ClickType clickType) {
        // Block interaction with open backpack slot
        if (slotId == blocked)
            return false;

        ItemStack slotStack = slot.getStack();
        if (!slotStack.isEmpty() && ItemStack.areItemStacksEqual(slotStack, blockedStack))
            return false;

        // Block placing of backpacks and blacklisted stacks into backpack inventory
        if (slotId <= backpackSize.getTotalSize() - 1) {
            if (InventoryBlacklist.INSTANCE.isBlacklisted(player.inventory.getItemStack()))
                return false;

            if (player.inventory.getItemStack().getItem() instanceof IBackpack) // TODO - Check for nesting upgrades and properly handle
                return false;
        }

        // Hotbar swapping via number keys
        if (clickType == ClickType.SWAP) {
            int hotbarId = backpackSize.getTotalSize() + 27 + button; // Backpack slots + main inventory + hotbar id
            // Block swapping with open backpack slot
            if (blocked == hotbarId)
                return false;

            Slot hotbarSlot = getSlot(hotbarId);
            ItemStack hotbarStack = hotbarSlot.getStack();
            if (!hotbarStack.isEmpty() && ItemStack.areItemStacksEqual(hotbarStack, blockedStack))
                return false;

            // Block swapping of backpacks and blacklisted stacks into backpack inventory
            if (slotId <= backpackSize.getTotalSize() - 1) {
                if (InventoryBlacklist.INSTANCE.isBlacklisted(slotStack) || InventoryBlacklist.INSTANCE.isBlacklisted(hotbarStack))
                    return false;

                if (slotStack.getItem() instanceof IBackpack || hotbarStack.getItem() instanceof IBackpack) // TODO - Check for nesting upgrades and properly handle
                    return false;
            }
        }

        return true;
    }

    /**
     * The localized display name of the backpack.
     *
     * @return - The name as a String
     */
    @Nonnull
    public String getName() {
        return backpackStack.getDisplayName();
    }

    /**
     * Gets the {@link BackpackSize} of the backpack.
     *
     * @return - The BackpackSize
     */
    public BackpackSize getBackpackSize() {
        return backpackSize;
    }

    // Setup slots
    // All credit for code below here goes to copygirl (comments/javadocs/precondition checks mine though)

    /**
     * Sets up the backpack's slots, delegating where necessary.
     *
     * @param inventoryPlayer - The player's inventory
     * @param itemHandler     - The IItemHandler of the backpack
     */
    private void setupSlots(@Nonnull InventoryPlayer inventoryPlayer, @Nonnull IItemHandler itemHandler, @Nullable EnumHand hand) {
        Preconditions.checkNotNull(inventoryPlayer, "inventoryPlayer cannot be null");
        Preconditions.checkNotNull(itemHandler, "itemHandler cannot be null");

        setupBackpackSlots(itemHandler);
        setupPlayerSlots(inventoryPlayer, hand);

        // Store the slot where the backpack is to detect if it's moved in canInteractWith()
        for (Slot slot : inventorySlots) {
            if (slot.getStack() == backpackStack) {
                backpackSlot = slot;
                break;
            }
        }
    }

    /**
     * Sets up the slots for the backpack specifically.
     *
     * @param itemHandler - The {@link IItemHandler} for the backpack.
     */
    private void setupBackpackSlots(@Nonnull IItemHandler itemHandler) {
        Preconditions.checkNotNull(itemHandler, "itemHandler cannot be null");

        int xOffset = 1 + getContainerInvXOffset();
        int yOffset = 1 + getBorderTop();
        for (int y = 0; y < backpackSize.getRows(); y++, yOffset += 18)
            for (int x = 0; x < backpackSize.getColumns(); x++)
                addSlotToContainer(new SlotItemHandler(itemHandler, x + y * backpackSize.getColumns(), xOffset + x * 18, yOffset) {
                    @Override
                    public void onSlotChanged() {
                        super.onSlotChanged();
                        // Update the backpack as soon as its inventory is changed or items will be duped
                        if (backpackStack.getItem() instanceof IBackpack)
                            ((IBackpack) backpackStack.getItem()).updateBackpack(backpackStack, backpackInfo);
                        else
                            IronBackpacks.LOGGER.debug("Attempted to update backpack on non-IBackpack item {}. Changes will not persist.");
                    }
                });
    }

    /**
     * Sets up the slots for the player specifically.
     *
     * @param inventoryPlayer - the {@link InventoryPlayer} for the player.
     */
    private void setupPlayerSlots(@Nonnull InventoryPlayer inventoryPlayer, @Nullable EnumHand hand) {
        Preconditions.checkNotNull(inventoryPlayer, "inventoryPlayer cannot be null");

        int xOffset = 1 + getPlayerInvXOffset();
        int yOffset = 1 + getBorderTop() + getContainerInvHeight() + getBufferInventory();

        //Inventory
        for (int y = 0; y < 3; y++, yOffset += 18)
            for (int x = 0; x < 9; x++)
                addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, xOffset + x * 18, yOffset));

        //Hotbar
        yOffset += getBufferHotbar();
        for (int x = 0; x < 9; x++) {
            Slot slot = addSlotToContainer(new Slot(inventoryPlayer, x, xOffset + x * 18, yOffset) {
                @Override
                public boolean canTakeStack(final EntityPlayer playerIn) {
                    ItemStack slotStack = getStack();
                    return slotNumber != blocked && (!slotStack.isEmpty() && !ItemStack.areItemStacksEqual(slotStack, blockedStack));
                }
            });
            if (x == inventoryPlayer.currentItem && hand == EnumHand.MAIN_HAND)
                blocked = slot.slotNumber;
        }
    }

    public ContainerBackpack setBlockedStack(ItemStack blockedStack) {
        this.blockedStack = blockedStack;
        return this;
    }

    // GUI/slot setup helpers

    /**
     * Returns the size of the top border in pixels.
     */
    public int getBorderTop() {
        return 17;
    }

    /**
     * Returns the size of the side border in pixels.
     */
    public int getBorderSide() {
        return 7;
    }

    /**
     * Returns the size of the bottom border in pixels.
     */
    public int getBorderBottom() {
        return 7;
    }

    /**
     * Returns the space between container and player inventory in pixels.
     */
    public int getBufferInventory() {
        return 13;
    }

    /**
     * Returns the space between player inventory and hotbar in pixels.
     */
    public int getBufferHotbar() {
        return 4;
    }

    /**
     * Returns the size of the maximum number of columns possible.
     */
    public int getMaxColumns() {
        return BackpackSize.MAX.getColumns();
    }

    /**
     * Returns the size of the maximum number of rows possible.
     */
    public int getMaxRows() {
        return BackpackSize.MAX.getRows();
    }

    /**
     * Returns the total width of the container in pixels.
     */
    public int getWidth() {
        return Math.max(backpackSize.getColumns(), 9) * 18 + getBorderSide() * 2;
    }

    /**
     * Returns the total height of the container in pixels.
     */
    public int getHeight() {
        return getBorderTop() + (backpackSize.getRows() * 18) +
                getBufferInventory() + (4 * 18) +
                getBufferHotbar() + getBorderBottom();
    }

    /**
     * Returns the size of the container's width, only the inventory/slots, not the border, in pixels.
     */
    public int getContainerInvWidth() {
        return backpackSize.getColumns() * 18;
    }

    /**
     * Returns the size of the container's height, only the inventory/slots, not the border, in pixels.
     */
    public int getContainerInvHeight() {
        return backpackSize.getRows() * 18;
    }

    /**
     * Returns the size of the x offset for the backpack container in pixels.
     */
    public int getContainerInvXOffset() {
        return getBorderSide() + Math.max(0, (getPlayerInvWidth() - getContainerInvWidth()) / 2);
    }

    /**
     * Returns the size of the x offset for the player's inventory in pixels.
     */
    public int getPlayerInvXOffset() {
        return getBorderSide() + Math.max(0, (getContainerInvWidth() - getPlayerInvWidth()) / 2);
    }

    /**
     * Returns the size of the player's inventory width, not including the borders, in pixels.
     */
    public int getPlayerInvWidth() {
        return 9 * 18;
    }

    /**
     * Returns the size of the player's inventory height, including the hotbar, in pixels.
     */
    public int getPlayerInvHeight() {
        return 4 * 18 + getBufferHotbar();
    }

    // Inventory Tweaks

    @ChestContainer.RowSizeCallback
    public int getRowSize() {
        return Math.max(getBackpackSize().getRows(), 9);
    }
}
