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

    private final EntityPlayer player;
    private final World world;

//    public ContainerBackpack(InventoryPlayer inventoryPlayer, World world, int x, int y, int z) {
    public ContainerBackpack(EntityPlayer player, InventoryBackpack backpackInventory) {
        this.player = player;
        this.world = player.worldObj;

        bindBackpackInventory(backpackInventory);

        bindPlayerInventory(player.inventory);

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

    @Override //copied from vanilla
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack var2 = null;
        Slot var3 = (Slot) this.inventorySlots.get(slotIndex);

        if (var3 != null && var3.getHasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (slotIndex == 0) {
                if (!this.mergeItemStack(var4, 10, 46, true)) {
                    return null;
                }

                var3.onSlotChange(var4, var2);
            } else if (slotIndex >= 10 && slotIndex < 37) {
                if (!this.mergeItemStack(var4, 1, 10, false)) {
                    return null;
                }
            } else if (slotIndex >= 37 && slotIndex < 46) {
                if (!this.mergeItemStack(var4, 1, 10, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(var4, 10, 46, false)) {
                return null;
            }

            if (var4.stackSize == 0) {
                var3.putStack((ItemStack) null);
            } else {
                var3.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize) {
                return null;
            }

            var3.onPickupFromSlot(player, var4);
        }

        return var2;
    }

    @Override
    public boolean canInteractWith(EntityPlayer arg0) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if (!player.worldObj.isRemote) {
//            this.craftingMatrix.onGuiSaved(player);
            //TODO - save items with NBT
        }
    }

    public EntityPlayer getPlayer() { return player; }
}
