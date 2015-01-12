package main.ironbackpacks.container.slot;

import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GhostSlot extends BackpackSlot{ //TODO - currently just filler code

    public int index;

    public GhostSlot(IInventory iInventory, int index, int x, int y) {
        super(iInventory, index, x, y);
        this.index = index;
    }

    public void onPickupFromSlot(EntityPlayer player, ItemStack stack)
    {
        stack = null;
        this.onSlotChanged();
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }

    @Override
    public ItemStack decrStackSize(int amt)
    {
        return null;
    }

    @Override
    public void putStack(ItemStack stack)
    {
        this.inventory.setInventorySlotContents(this.index, stack);
        this.onSlotChanged();
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return acceptsStack(itemStack);
    }

    public boolean acceptsStack(ItemStack itemStack){
        return !(itemStack.getItem() instanceof ItemBaseBackpack);
    }
}
