package gr8pefish.ironbackpacks.gui;

import gr8pefish.ironbackpacks.inventory.BackpackInventory;
import gr8pefish.ironbackpacks.item.BaseBackpackItem;
import gr8pefish.ironbackpacks.util.ModTranslatableText;
import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class BackpackController extends CottonCraftingController {
    private ItemStack backpackStack;

    public BackpackController(int syncId, PlayerInventory playerInventory, int backpackSlot) {
        super(null, syncId, playerInventory);
        this.backpackStack = playerInventory.getInvStack(backpackSlot);
        BaseBackpackItem item = (BaseBackpackItem) backpackStack.getItem();
        this.playerInventory = playerInventory;
        this.blockInventory = new BackpackInventory(backpackStack, item.getCapacity(backpackStack));

        WGridPanel rootPanel = (WGridPanel) getRootPanel();
        Text label = backpackStack.hasCustomName() ? backpackStack.getName() : new ModTranslatableText("gui", "backpack");
        rootPanel.add(new WLabel(label, WLabel.DEFAULT_TEXT_COLOR), 0, 0);
        int rows = item.getContainerRows(backpackStack);
        int columns = item.getContainerColumns(backpackStack);
        for (int row = 1; row <= rows; row++) {
            for (int col = 0; col < columns; col++) {
                WItemSlot slot = WItemSlot.of(blockInventory, ((row - 1) * columns) + col);
                rootPanel.add(slot, col, row);
            }
        }

        rootPanel.add(new WLabel(new TranslatableText("container.inventory"), WLabel.DEFAULT_TEXT_COLOR), 0, rows+2);
        rootPanel.add(this.createPlayerInventoryPanel(), 0, rows+3);
        rootPanel.validate(this);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return -1;
    }

    @Override
    public ItemStack onSlotClick(int slotNumber, int button, SlotActionType action, PlayerEntity player) {
        if (slotNumber >= 0 && slotNumber < slotList.size()) {
            Slot slot = slotList.get(slotNumber);
            ItemStack target = slot.getStack();
            ItemStack cursor = playerInventory.getCursorStack();
            if (slot.inventory == playerInventory) {
                if (action == SlotActionType.QUICK_MOVE && target == backpackStack) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (action == SlotActionType.PICKUP && cursor == backpackStack) {
                    return ItemStack.EMPTY;
                }
            }
            return super.onSlotClick(slotNumber, button, action, player);
        }
        return ItemStack.EMPTY;
    }
}
