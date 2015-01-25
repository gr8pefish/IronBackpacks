package main.ironbackpacks.container.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GhostSlot extends BackpackSlot{

    public int index;

    public GhostSlot(IInventory iInventory, int index, int x, int y) {
        super(iInventory, index, x, y);
        this.index = index;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player){
        return true;
    }

    @Override
    public ItemStack decrStackSize(int par1){
        return null;
    }

    @Override
    public void putStack(ItemStack itemStack){
        if(itemStack != null) {
            itemStack.stackSize = 1;
        }
        inventory.setInventorySlotContents(index, itemStack);
        onSlotChanged();
    }

}
