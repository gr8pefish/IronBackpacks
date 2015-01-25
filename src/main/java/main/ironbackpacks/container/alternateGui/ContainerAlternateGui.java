package main.ironbackpacks.container.alternateGui;

import invtweaks.api.container.ChestContainer;
import main.ironbackpacks.container.slot.GhostSlot;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@ChestContainer
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
        int yStart = UpgradeMethods.hasRenamingUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(IronBackpacksHelper.getBackpack(player))) ? 18 : 0;
        for (int row = 0; row < rowCount; row++){
            yStart += 36;
            for (int col = 0; col < colCount; col++){
                addSlotToContainer(new GhostSlot(customInv, col + row * colCount, 20 + col * 18, yStart)); //old - customInv, row + col * rowCount, 20 + (col * 18), yStart)
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
    public ItemStack transferStackInSlot(EntityPlayer p, int i)
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
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getHasStack() && player.getHeldItem() != null && getSlot(slot).getStack().isItemEqual(player.getHeldItem())) {
            return null;
        // otherwise they may be clicking on a ghostSlot
        }else if (slot >= 0 && slot < inventory.getSizeInventory()) {
            if (player.inventory.getItemStack() != null) { //clicking on slot with an itemStack
                ItemStack usedStack = player.inventory.getItemStack();
                inventory.setInventorySlotContents(slot, new ItemStack(usedStack.getItem(), 1, usedStack.getItemDamage()));
                return null;
            }else{ //clicking with an empty hand
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

    //=====================HELPER METHODS============================

    public EntityPlayer getPlayer() { return player; }

    public void renameBackpack(String toName){
        ItemStack itemStack = IronBackpacksHelper.getBackpack(this.player);
        itemStack.setStackDisplayName(toName);
    }

    @ChestContainer.RowSizeCallback //Inventory tweaks
    public int getNumColumns(){
        return (int) Math.floor(this.inventory.getSizeInventory() / 9);
    }

    public void removeSlotsInRow(int row){ //for the button upgrade
        for (int i = (row-1)*9; i < row*9; i++){
            inventory.setInventorySlotContents(i, null);
        }
    }


}
