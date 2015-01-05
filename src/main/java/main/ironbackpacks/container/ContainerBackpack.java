package main.ironbackpacks.container;

import main.ironbackpacks.container.slot.BackpackSlot;
import main.ironbackpacks.container.slot.NestingBackpackSlot;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksHelper;
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
        ItemStack baseBackpack = IronBackpacksHelper.getBackpack(player);
        int[] upgrades = ((ItemBaseBackpack) baseBackpack.getItem()).getUpgradesFromNBT(baseBackpack);

        for (int chestRow = 0; chestRow < type.getRowCount(); chestRow++) {
            for (int chestCol = 0; chestCol < type.getRowLength(); chestCol++) {
                if (UpgradeMethods.hasNestingUpgrade(upgrades)){
                    addSlotToContainer(new NestingBackpackSlot(chestInventory, chestCol + chestRow * type.getRowLength(), 20 + chestCol * 18, 18 + chestRow * 18, this.type));
                }else {
                    addSlotToContainer(new BackpackSlot(chestInventory, chestCol + chestRow * type.getRowLength(), 20 + chestCol * 18, 18 + chestRow * 18));
                }
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
            if (i < type.getSize()) //if clicking from backpack to player
            {
                if (!mergeItemStack(itemstack1, type.getSize(), inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!((BackpackSlot) inventorySlots.get(1)).acceptsStack(itemstack1)){ //slot 1 is a backpackSlot
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

    public void backpackToInventory(){
        for (int i = 0; i <= type.getSize(); i++) {
            transferStackInSlot(player, i);
        }
    }

    public void inventoryToBackpack(){
        int start = type.getSize();
        int end = start + player.inventory.getSizeInventory() - player.inventory.getHotbarSize() - 4; //Not sure why it is 4 too large...
        for (int i = start; i < end; i++){
            transferStackInSlot(player, i);
        }
    }

    public void hotbarToBackpack(){
        int start = type.getSize() + player.inventory.getSizeInventory() - player.inventory.getHotbarSize() - 4;
        int end = start + this.player.inventory.getHotbarSize();
        for (int i = start; i < end; i++){
            transferStackInSlot(player, i);
        }
    }

}
