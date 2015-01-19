package main.ironbackpacks.container.alternateGui;

import main.ironbackpacks.container.slot.MaxStackSizeOneSlot;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAlternateGui extends Container {

    private EntityPlayer player;
    public InventoryAlternateGui inventory;
    public int xSize = 0;
    public int ySize = 0;

    public ContainerAlternateGui(EntityPlayer entityPlayer, InventoryAlternateGui inventoryAlternateGui, int xSize, int ySize){
        this.player = entityPlayer;
        this.inventory = inventoryAlternateGui;
        this.xSize = xSize;
        this.ySize = ySize;

        layoutContainer(entityPlayer.inventory, inventoryAlternateGui, xSize, ySize);
    }

    public ContainerAlternateGui(EntityPlayer entityPlayer, InventoryAlternateGui inventoryAlternateGui){
        this.player = entityPlayer;
        this.inventory = inventoryAlternateGui;

        layoutContainer(entityPlayer.inventory, inventoryAlternateGui, xSize, ySize);
    }

    protected void layoutContainer(IInventory playerInventory, IInventory customInv, int xSize, int ySize){

        //adds slots depending on upgrades
        int rowCount = (int) Math.floor(customInv.getSizeInventory() / 9);
        int colCount = 9;
        int yStart = UpgradeMethods.hasRenamingUpgrade(IronBackpacksHelper.getUpgradesFromNBT(IronBackpacksHelper.getBackpack(player))) ? 18 : 0;
        for (int row = 0; row < rowCount; row++){
            yStart += 36;
            for (int col = 0; col < colCount; col++){
                addSlotToContainer(new MaxStackSizeOneSlot(customInv, row + col * rowCount, 20 + (col * 18), yStart)); //TODO - ghostSlot
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

    @Override //disables shift-clicking (because ghost slots)
    public ItemStack transferStackInSlot(EntityPlayer p, int i) //TODO? - depends upon implementation of ghostSlot
    {
        return null;
    }


    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if (!player.worldObj.isRemote) {
            this.inventory.onGuiSaved(player); //only save on server side
        }
    }

    @Override
    public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
        // this will prevent the player from interacting with the item that opened the inventory:
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getHasStack() && getSlot(slot).getStack().isItemEqual(player.getHeldItem())) {
            return null;
        }
        return super.slotClick(slot, button, flag, player);
    }

    //=====================HELPER METHODS============================

    public EntityPlayer getPlayer() { return player; }

    public void renameBackpack(String toName){
        ItemStack itemStack = IronBackpacksHelper.getBackpack(this.player);
        itemStack.setStackDisplayName(toName);
    }


}
