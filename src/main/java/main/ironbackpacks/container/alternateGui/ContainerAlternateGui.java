package main.ironbackpacks.container.alternateGui;

import invtweaks.api.container.ChestContainer;
import invtweaks.api.container.InventoryContainer;
import main.ironbackpacks.client.gui.buttons.ButtonTypes;
import main.ironbackpacks.container.slot.GhostSlot;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

/**
 * The container used when the backpack is opened to the alternate gui.
 */
@InventoryContainer
public class ContainerAlternateGui extends Container {

    private EntityPlayer player; //the player
    private InventoryAlternateGui inventory; //the inventory
    private int xSize = 0; //the x-size
    private int ySize = 0; //the y-size
    private ItemStack stack; //the itemstack backpack

    private int filterAdvSlotIdStart; //the advanced filter's start
    private int[] upgrades; //the upgrades applied

    public ContainerAlternateGui(EntityPlayer entityPlayer, InventoryAlternateGui inventoryAlternateGui, int xSize, int ySize) {
        this.player = entityPlayer;
        this.inventory = inventoryAlternateGui;
        this.xSize = xSize;
        this.ySize = ySize;
        this.stack = IronBackpacksHelper.getBackpack(player);
        this.upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(stack);

        layoutContainer(entityPlayer.inventory, inventoryAlternateGui, xSize, ySize);
        if (UpgradeMethods.hasFilterAdvancedUpgrade(upgrades))
            initFilterSlots();
    }

    //Overloaded constructor for when size doesn't matter
    public ContainerAlternateGui(EntityPlayer entityPlayer, InventoryAlternateGui inventoryAlternateGui) {
        this.player = entityPlayer;
        this.inventory = inventoryAlternateGui;
        this.stack = IronBackpacksHelper.getBackpack(player);
        this.upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(stack);

        layoutContainer(entityPlayer.inventory, inventoryAlternateGui, xSize, ySize);
        if (UpgradeMethods.hasFilterAdvancedUpgrade(upgrades))
            initFilterSlots();
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public InventoryAlternateGui getInventoryAlternateGui() {
        return inventory;
    }

    public ItemStack getStack() {
        return stack;
    }

    public int getFilterAdvSlotIdStart() {
        return filterAdvSlotIdStart;
    }

    /**
     * Places each slot where it should be.
     *
     * @param playerInventory - the player's inventory
     * @param customInv       - the backpack's inventory
     * @param xSize           - the width
     * @param ySize           - the height
     */
    private void layoutContainer(IInventory playerInventory, IInventory customInv, int xSize, int ySize) {

        //Need to calculate which row the advanced filter will be on to place the buttons correctly
        int advFilterRow = UpgradeMethods.hasFilterAdvancedUpgrade(upgrades) ? 0 : -1;
        if (UpgradeMethods.hasFilterBasicUpgrade(upgrades)) advFilterRow++;
        if (UpgradeMethods.hasFilterFuzzyUpgrade(upgrades)) advFilterRow++;
        if (UpgradeMethods.hasFilterOreDictUpgrade(upgrades)) advFilterRow++;
        if (UpgradeMethods.hasFilterModSpecificUpgrade(upgrades)) advFilterRow++;
        filterAdvSlotIdStart = advFilterRow * 9;

        //adds slots depending on upgrades
        int rowCount = (int) Math.floor(customInv.getSizeInventory() / 9);
        int colCount = 9;
        int yStart = UpgradeMethods.hasRenamingUpgrade(upgrades) ? 18 : 0;
        for (int row = 0; row < rowCount; row++) {
            yStart += 36;
            for (int col = 0; col < colCount; col++) {
                addSlotToContainer(new GhostSlot(customInv, col + row * colCount, 20 + col * 18, yStart));
            }
        }

        //adds player's inventory
        int leftCol = (xSize - 162) / 2 + 1;
        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                addSlotToContainer(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, ySize - (4 - playerInvRow) * 18 - 10));
            }

        }

        //adds player's hotbar
        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
        }
    }

    @Override //disables shift-clicking (because ghost slots)
    public ItemStack transferStackInSlot(EntityPlayer p, int i) {
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        if (UpgradeMethods.hasFilterAdvancedUpgrade(upgrades))
            saveSlots();
        if (!player.worldObj.isRemote)
            this.inventory.onGuiSaved(player); //only save on server side

    }

    //Where ghost slots' functionality is really handled
    @Override
    public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
        // this will prevent the player from interacting with the item that opened the inventory:
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getHasStack() && player.getHeldItem() != null && getSlot(slot).getStack().isItemEqual(player.getHeldItem())) {
            return null;
            // otherwise they may be clicking on a ghostSlot
        } else if (slot >= 0 && slot < inventory.getSizeInventory()) {
            if (player.inventory.getItemStack() != null) { //clicking on slot with an itemStack
                ItemStack usedStack = player.inventory.getItemStack();
                inventory.setInventorySlotContents(slot, new ItemStack(usedStack.getItem(), 1, usedStack.getItemDamage()));
                return null;
            } else { //clicking with an empty hand
                inventory.setInventorySlotContents(slot, null);
                return null;
            }
        }
        //otherwise it is a normal slot
        try {
            return super.slotClick(slot, button, flag, player);
        } catch (Exception e) {
            //Horrible work around for a bug when double clicking on a stack in inventory which matches a filter item
            //This does stop double clicking to fill a stack from working with this GUI open.
            return null;
        }
    }

    //===========================================================HELPER METHODS========================================================

    /**
     * Renames the backpack to the string parameter
     *
     * @param toName - the new name
     */
    public void renameBackpack(String toName) {
        ItemStack itemStack = IronBackpacksHelper.getBackpackFromPlayersInventory(this.player);
        stack.setStackDisplayName("\u00A7r" + toName); //client
        itemStack.setStackDisplayName("\u00A7r" + toName); //server (not really, but this way works...)
    }

    /**
     * Remove the items in the specified row (part of the button upgrade).
     *
     * @param row - the row to clear
     */
    public void removeSlotsInRow(int row) {
        if (row == (filterAdvSlotIdStart / 9) + 1) {
            Arrays.fill(inventory.advFilterStacks, null);
            Arrays.fill(inventory.advFilterButtonStates, (byte) ButtonTypes.EXACT.getID());
            inventory.advFilterButtonStartPoint = 0;
            initFilterSlots();
        } else {
            for (int i = (row - 1) * 9; i < row * 9; i++) {
                inventory.setInventorySlotContents(i, null);
            }
        }
    }

    @ChestContainer.RowSizeCallback //Inventory tweaks compatibility
    public int getNumColumns() {
        return (int) Math.floor(this.inventory.getSizeInventory() / 9);
    }

    //========================================================Advanced Filter Methods===============================================================

    /**
     * Initializes the advanced filter slots.
     */
    public void initFilterSlots() {
        for (int i = 0; i < 9; i++) {
            getSlot(filterAdvSlotIdStart + i).putStack(inventory.advFilterStacks[getWraparoundIndex(i)]);
        }
    }

    /**
     * Sets the advanced filter start point to the correct one and then re-initializes the correct slots.
     *
     * @param side - the way to move the advanced filter (left or right)
     */
    public void changeAdvFilterSlots(String side) {

        saveSlots();
        int startPoint = inventory.advFilterButtonStartPoint;

        if (side.equals(IronBackpacksConstants.Miscellaneous.MOVE_LEFT)) {
            if (startPoint == 0)
                inventory.advFilterButtonStartPoint = 17;
            else
                inventory.advFilterButtonStartPoint--;
        } else if (side.equals(IronBackpacksConstants.Miscellaneous.MOVE_RIGHT)) {
            if (startPoint == 17)
                inventory.advFilterButtonStartPoint = 0;
            else
                inventory.advFilterButtonStartPoint++;
        }

        initFilterSlots();
    }

    /**
     * Saves the slot to the correct items.
     */
    private void saveSlots() {
        for (int i = 0; i < 9; i++) {
            inventory.advFilterStacks[getWraparoundIndex(i)] = getSlot(filterAdvSlotIdStart + i).getStack();
        }
    }

    /**
     * Gets the index to change the start point to if the advanced filter needs to 'wrap around' (slot 17 -&lt; slot 0)
     *
     * @param orderNumber - the spot to check
     * @return the index it should be
     */
    public int getWraparoundIndex(int orderNumber) {
        int startPoint = inventory.advFilterButtonStartPoint;
        if (startPoint + orderNumber > 17) {
            return (orderNumber + startPoint) - 18;
        } else
            return (startPoint + orderNumber);
    }

    /**
     * Sets the advanced filter button to the type parameter.
     *
     * @param index       - the index of the button to alter
     * @param typeToSetTo - the new type of the button
     */
    public void setAdvFilterButtonType(int index, int typeToSetTo) {
        inventory.advFilterButtonStates[index] = (byte) typeToSetTo;
    }

}
