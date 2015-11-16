package main.ironbackpacks.container.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Used in the alternate gui to create a 'ghost item' that disappears when it is picked up and doesn't decrement the item stack when it is placed in.
 */
public class GhostSlot extends BackpackSlot {

    public int index; //the slot index in the inventory

    public GhostSlot(IInventory iInventory, int index, int x, int y) {
        super(iInventory, index, x, y);
        this.index = index;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return true;
    }

    @Override
    public ItemStack decrStackSize(int par1) {
        return null;
    }

    //Always sets the stack size to 1
    @Override
    public void putStack(ItemStack itemStack) {
        if (itemStack != null) {
            itemStack.stackSize = 1;
        }
        inventory.setInventorySlotContents(index, itemStack);
        onSlotChanged();
    }

}
