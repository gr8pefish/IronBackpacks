package gr8pefish.ironbackpacks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Class to layout the container for backpacks
 */
public class ContainerBackpack extends Container {

    //The backpack's inventory (ToDo: Need IBackpack for BackpackInfo too maybe?)
    private final InventoryBackpack inventoryBackpack;

    public ContainerBackpack(InventoryPlayer playerInv, InventoryBackpack inventoryBackpack) {
        int i;
        int j;

        this.inventoryBackpack = inventoryBackpack;

        //ToDo: Split slot creation into methods?

        //Backpack slots
        for (i = 0; i < 2; ++i) {
            for (j = 0; j < 9; ++j) {
                int k = j + i * 9;
                addSlotToContainer(new SlotItemHandler(inventoryBackpack, k, 8 + j * 18, 18 + i * 18));
            }
        }

        //Player Inventory slots
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 68 + i * 18));
            }
        }

        //Hotbar slots
        for (i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 126));
        }

    }

    //ToDo: Copied from old code, need to review/test/change
    //Mostly copied from IronChests
    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();
            if (slotIndex < inventoryBackpack.getSlots()) { //if clicking from backpack to player
                if (!mergeItemStack(slotStack, inventoryBackpack.getSlots(), inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            }
//            else if (!((BackpackSlot) inventorySlots.get(1)).acceptsStack(slotStack)) //ToDo: Fix
//                return null;
            else if (!mergeItemStack(slotStack, 0, inventoryBackpack.getSlots(), false))
                return ItemStack.EMPTY;
            if (slotStack.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }
        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true; //ToDo: Refine
    }

}