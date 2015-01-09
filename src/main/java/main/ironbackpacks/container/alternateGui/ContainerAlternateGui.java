package main.ironbackpacks.container.alternateGui;

import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.container.slot.GhostSlot;
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

//        //adds chest's slots
//        ItemStack baseBackpack = IronBackpacksHelper.getBackpack(player);
//        int[] upgrades = ((ItemBaseBackpack) baseBackpack.getItem()).getUpgradesFromNBT(baseBackpack);
//
//        for (int chestRow = 0; chestRow < type.getRowCount(); chestRow++) {
//            for (int chestCol = 0; chestCol < type.getRowLength(); chestCol++) {
//                if (UpgradeMethods.hasNestingUpgrade(upgrades)){
//                    addSlotToContainer(new NestingBackpackSlot(chestInventory, chestCol + chestRow * type.getRowLength(), 20 + chestCol * 18, 18 + chestRow * 18, this.type));
//                }else {
//                    addSlotToContainer(new BackpackSlot(chestInventory, chestCol + chestRow * type.getRowLength(), 20 + chestCol * 18, 18 + chestRow * 18));
//                }
//            }
//        }

        //adds slots depending on upgrades
        int rowCount = (int) Math.floor(customInv.getSizeInventory() / 9);
        boolean hasHopperUpgrade = customInv.getSizeInventory() % 9 == 1;  //since hopper upgrade is only one slot
        int yStart = UpgradeMethods.hasRenamingUpgrade(IronBackpacksHelper.getUpgradesFromNBT(IronBackpacksHelper.getBackpack(player))) ? 20 : 40;
        for (int row = 0; row < rowCount; row++){
            int colCount = hasHopperUpgrade ? 1 : 9; //will place the hopper upgrade slot first
            hasHopperUpgrade = false;
            yStart += 35;
            for (int col = 0; col < colCount; col++){
                int xSpot = colCount == 1 ? 70 : 20 + col * 18; //center if it hopper slot
                addSlotToContainer(new GhostSlot(customInv, col + row * colCount, xSpot, yStart));
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

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

//        if (!player.worldObj.isRemote) {
//            this.inventory.onGuiSaved(player);
//        }
    }

    public EntityPlayer getPlayer() { return player; }

    public void renameBackpack(String toName){
        ItemStack itemStack = IronBackpacksHelper.getBackpack(this.player);
        itemStack.setStackDisplayName(toName);
    }

}
