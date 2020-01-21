package gr8pefish.ironbackpacks.gui;

import gr8pefish.ironbackpacks.inventory.UpgradeInventory;
import gr8pefish.ironbackpacks.item.UpgradeItem;
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

public class UpgradeController extends CottonCraftingController {
    private ItemStack backpackStack;

    public UpgradeController(int syncId, PlayerInventory playerInventory, int backpackSlot) {
        super(null, syncId, playerInventory);
        this.backpackStack = playerInventory.getInvStack(backpackSlot);
        UpgradeInventory inv = new UpgradeInventory(backpackStack);
        this.playerInventory = playerInventory;
        this.blockInventory = inv;

        WGridPanel rootPanel = (WGridPanel) getRootPanel();
        Text label = new ModTranslatableText("gui", "upgrade");
        rootPanel.add(new WLabel(label, WLabel.DEFAULT_TEXT_COLOR), 0, 0);
        int slots = inv.getInvSize();
        int rows = 1 + (slots / 9);
        int columns = Math.min(9, slots);
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
            ItemStack cursor = playerInventory.getCursorStack();
            if (slot.inventory != playerInventory) {
                if (action == SlotActionType.PICKUP && !cursor.isEmpty()) {
                    if (cursor == backpackStack || !(cursor.getItem() instanceof UpgradeItem)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            return super.onSlotClick(slotNumber, button, action, player);
        }
        return ItemStack.EMPTY;
    }
}
