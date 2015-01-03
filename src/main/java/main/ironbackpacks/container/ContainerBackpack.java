package main.ironbackpacks.container;

import main.ironbackpacks.container.slot.BackpackSlot;
import main.ironbackpacks.inventory.InventoryBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class ContainerBackpack extends Container {

    private EntityPlayer player;
    public InventoryBackpack inventory;
    public IronBackpackType type;
    public int xSize = 0;
    public int ySize = 0;

    public ContainerBackpack(EntityPlayer entityPlayer, InventoryBackpack backpackInventory, IronBackpackType type){
        this.player = entityPlayer;
        this.inventory = backpackInventory;
        this.type = type;

        layoutContainer(entityPlayer.inventory, backpackInventory, xSize, ySize, type);
    }

    public ContainerBackpack(EntityPlayer entityPlayer, InventoryBackpack backpackInventory, IronBackpackType type, int xSize, int ySize){
        this.player = entityPlayer;
        this.inventory = backpackInventory;
        this.type = type;
        this.xSize = xSize;
        this.ySize = ySize;

        layoutContainer(entityPlayer.inventory, backpackInventory, xSize, ySize, type);
    }

    //credit to cpw
    protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize, IronBackpackType type){

        //adds chest's slots
        for (int chestRow = 0; chestRow < type.getRowCount(); chestRow++) {
            for (int chestCol = 0; chestCol < type.getRowLength(); chestCol++) {
                addSlotToContainer(new BackpackSlot(chestInventory, chestCol + chestRow * type.getRowLength(), 20 + chestCol * 18, 18 + chestRow * 18));
            }
        }

        //adds player's inventory
        int leftCol = (xSize - 162) / 2 + 1;
        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++)
        {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++)
            {
                addSlotToContainer(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, ySize - (4 - playerInvRow) * 18 - 10));
            }

        }

        //adds player's hotbar
        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++)
        {
            addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
        }
    }

    @Override //copied from IronChests
    public ItemStack transferStackInSlot(EntityPlayer p, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(i);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < type.getSize())
            {
                if (!mergeItemStack(itemstack1, type.getSize(), inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!BackpackSlot.acceptsStack(itemstack1))
            {
                return null;
            }
            else if (!mergeItemStack(itemstack1, 0, type.getSize(), false))
            {
                return null;
            }
            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }


    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if (!player.worldObj.isRemote) {
            this.inventory.onGuiSaved(player);
        }
    }

    public EntityPlayer getPlayer() { return player; }
}
