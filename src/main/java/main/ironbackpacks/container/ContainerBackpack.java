package main.ironbackpacks.container;

import main.ironbackpacks.inventory.InventoryBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerBackpack extends Container {

//    public InventoryBackpack inventoryBackpack = new InventoryBackpack(this, 9, 4);
//    private World world;
//    private int posX;
//    private int posY;
//    private int posZ;

    private EntityPlayer player;
    private final World world;

    public InventoryBackpack inventory;

    public IronBackpackType type;
    public int xSize;
    public int ySize;

//    public ContainerBackpack(InventoryPlayer inventoryPlayer, World world, int x, int y, int z) {
    public ContainerBackpack(EntityPlayer entityPlayer, InventoryBackpack backpackInventory, IronBackpackType type, int xSize, int ySize){
        this.player = entityPlayer;
        this.world = player.worldObj;
        this.inventory = backpackInventory;
        this.type = type;
        this.xSize = xSize;
        this.ySize = ySize;

        layoutContainer(entityPlayer.inventory, backpackInventory, xSize, ySize, type);
    }

    public ContainerBackpack(EntityPlayer entityPlayer, InventoryBackpack backpackInventory) {
        this.player = entityPlayer;
        this.world = player.worldObj;
        this.inventory = backpackInventory;

        layoutContainer(entityPlayer.inventory, backpackInventory, 200, 222, IronBackpackType.BASIC); //TODO - not always basic type

    }

    protected void bindBackpackInventory(InventoryBackpack backpackInventory) {
        //TODO - make dynamic with config sizes
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 9; j++){
                addSlotToContainer(new Slot(backpackInventory, j + i * 9 + 9, 8 + j * 18, 84 + i + 18));
            }
        }
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        20 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, /*8*/ 20 + i * 18, 142));
        }
    }

    protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize, IronBackpackType type){ // IronChestType type, int xSize, int ySize) {

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
            if (i < IronBackpackType.BASIC.size) //TODO- change to dynamic sizing
            {
                if (!mergeItemStack(itemstack1, IronBackpackType.BASIC.size, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!BackpackSlot.acceptsStack(itemstack1))
            {
                return null;
            }
            else if (!mergeItemStack(itemstack1, 0, IronBackpackType.BASIC.size, false))
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
    public boolean canInteractWith(EntityPlayer arg0) {
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
