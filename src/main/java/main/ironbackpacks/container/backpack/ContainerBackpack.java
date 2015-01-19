package main.ironbackpacks.container.backpack;

import invtweaks.api.container.ChestContainer;
import main.ironbackpacks.container.slot.BackpackSlot;
import main.ironbackpacks.container.slot.NestingBackpackSlot;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@ChestContainer //Inventory tweaks
public class ContainerBackpack extends Container {

    private EntityPlayer player;
    public InventoryBackpack inventory;
    public IronBackpackType type;
    public int xSize = 0;
    public int ySize = 0;

    public ContainerBackpack(EntityPlayer entityPlayer, InventoryBackpack backpackInventory, IronBackpackType type){
        //overloaded constructor for when size is irrelevant

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

    //credit to cpw for basic layout of adding backpack's slots
    protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize, IronBackpackType type){

        //adds chest's slots
        ItemStack baseBackpack = IronBackpacksHelper.getBackpack(player);
        int[] upgrades = IronBackpacksHelper.getUpgradesFromNBT(baseBackpack);

        for (int backpackRow = 0; backpackRow < type.getRowCount(); backpackRow++) {
            for (int backpackCol = 0; backpackCol < type.getRowLength(); backpackCol++) {
                if (UpgradeMethods.hasNestingUpgrade(upgrades)){
                    addSlotToContainer(new NestingBackpackSlot(chestInventory, backpackCol + backpackRow * type.getRowLength(), 20 + backpackCol * 18, 18 + backpackRow * 18, this.type));
                }else {
                    addSlotToContainer(new BackpackSlot(chestInventory, backpackCol + backpackRow * type.getRowLength(), 20 + backpackCol * 18, 18 + backpackRow * 18));
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
            else if (!((BackpackSlot) inventorySlots.get(1)).acceptsStack(itemstack1)){ //slot 1 represents either the NestingBackpackSLot or the BackpackSlot
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

    public ItemStack transferStackInSlot(ItemStack itemToPutInBackpack)
    {
        ItemStack itemstack1 = itemToPutInBackpack;
        if (!mergeItemStack(itemstack1, 0, type.getSize(), false)) //stack, startIndex, endIndex,
        {
            System.out.println("Failing here"); //TODO - something is occasionally wrong here (w/ mergeItemStack - solution is custom implementation)
            return null;
        }
        else if (!((BackpackSlot) inventorySlots.get(1)).acceptsStack(itemstack1)){ //slot 1 is a backpackSlot
            return null;
        }
        return itemToPutInBackpack;
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

    @Override
    public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
    // this will prevent the player from interacting with the item that opened the inventory:
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
            return null;
        }
        return super.slotClick(slot, button, flag, player);
    }

    //=====================HELPER METHODS============================

    public void save(EntityPlayer player) {
        if (!player.worldObj.isRemote) {
            this.inventory.onGuiSaved(player);
        }
    }

    public EntityPlayer getPlayer() { return player; }

    public void backpackToInventory(){
        for (int i = 0; i <= type.getSize()+1; i++) {
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


    //===========================Sorting Algorithm=============================

    public void sort(){
        if (!inventorySlots.isEmpty() && !inventoryItemStacks.isEmpty()){
            mergeStacks(); //merge all the items together into the least possible number of stacks
            swapStacks();  //then swap out the null stacks/blank spaces so that the backpack's stacks with items are condensed into the smallest area possible
            reorderStacks(); //finally reorder the stacks (I am using alphabetization by the stack's display names).
        }
    }

    private void mergeStacks(){
        for (int i = 0; i < type.getSize(); i++){
            Slot tempSlot = (Slot) inventorySlots.get(i);
            if (tempSlot!= null && tempSlot.getHasStack()){
                ItemStack tempStack = tempSlot.getStack();
                if (tempStack!= null && tempStack.stackSize < tempStack.getMaxStackSize()){
                    fillSlot(tempSlot, i+1);
                }
            }
        }
    }

    private void fillSlot(Slot slotToFill, int startIndex){
        ItemStack stackToFill = slotToFill.getStack();
        int fillAmt = stackToFill.getMaxStackSize() - stackToFill.stackSize;
        if (fillAmt > 0){
            for (int i = startIndex; i < type.getSize(); i++){
                stackToFill = slotToFill.getStack();
                fillAmt = stackToFill.getMaxStackSize() - stackToFill.stackSize;
                Slot tempSlot = (Slot) inventorySlots.get(i);
                if (tempSlot != null && tempSlot.getHasStack()){
                    ItemStack tempStack = tempSlot.getStack();
                    if (tempStack.stackSize > 0 && tempStack.isItemEqual(stackToFill)){
                        if (tempStack.stackSize > fillAmt){
                            tempSlot.decrStackSize(fillAmt);
                            slotToFill.putStack(new ItemStack(stackToFill.getItem(), stackToFill.getMaxStackSize(), stackToFill.getItemDamage()));
                            slotToFill.onSlotChanged();
                            break;
                        }else{
                            tempSlot.putStack(null);
                            slotToFill.putStack(new ItemStack(stackToFill.getItem(), stackToFill.stackSize + tempStack.stackSize, stackToFill.getItemDamage()));
                            slotToFill.onSlotChanged();
                        }
                    }
                }
            }
        }
    }

    private void swapStacks(){
        ArrayList<Integer> indicesOfSlotsWithItems = new ArrayList<Integer>();
        for (int i = 0; i < type.getSize(); i++){
            Slot tempSlot = (Slot) inventorySlots.get(i);
            if (tempSlot!= null && tempSlot.getHasStack()){
                ItemStack tempStack = tempSlot.getStack();
                if (tempStack != null && tempStack.stackSize > 0){
                    indicesOfSlotsWithItems.add(i);
                }
            }
        }
        if (!indicesOfSlotsWithItems.isEmpty()) {
            if (indicesOfSlotsWithItems.get(indicesOfSlotsWithItems.size() - 1) != (indicesOfSlotsWithItems.size() - 1)) { //if not already swapped so no null slots,
                for (int i = 0; i < indicesOfSlotsWithItems.size(); i++) {
                    Slot tempSlot = (Slot) inventorySlots.get(i);
                    if (tempSlot != null) {
                        if (!tempSlot.getHasStack()) {
                            swapNull(tempSlot, (Slot) inventorySlots.get(indicesOfSlotsWithItems.get(i)));
                        }
                    }
                }
            }
        }
    }

    private void swapNull(Slot nullSlot, Slot stackSlot){
        if (stackSlot != null && stackSlot.getHasStack()) {
            nullSlot.putStack(stackSlot.getStack());
            stackSlot.putStack(null);
            stackSlot.onSlotChanged();
        }
    }

    private void reorderStacks(){
        ArrayList<ItemStack> itemStacks = new ArrayList<ItemStack>(countLengthOfStacks());
        for (int i = 0; i < type.getSize(); i++){
            Slot tempSlot = (Slot) inventorySlots.get(i);
            if (tempSlot != null && tempSlot.getHasStack()) {
                itemStacks.add(tempSlot.getStack());
            } else {
                break;
            }
        }
        if (!itemStacks.isEmpty()) {
            Collections.sort(itemStacks, new ItemStackNameComparator());
            for (int i = 0; i < itemStacks.size(); i++) {
                Slot tempSlot = (Slot) inventorySlots.get(i);
                tempSlot.putStack(itemStacks.get(i));
            }
        }
    }

    private class ItemStackNameComparator implements Comparator<ItemStack> {
        @Override
        public int compare(ItemStack stack1, ItemStack stack2) {
            return stack1.getDisplayName().compareToIgnoreCase(stack2.getDisplayName());
        }
    }

    private int countLengthOfStacks(){
        int total = 0;
        for (int i = 0; i < type.getSize(); i++){
            Slot tempSlot = (Slot) inventorySlots.get(i);
            if (tempSlot != null && tempSlot.getHasStack()) {
                total++;
            } else {
                return total;
            }
        }
        return total;
    }

    @ChestContainer.RowSizeCallback //Inventory tweaks
    public int getNumColumns(){
        return type.getRowLength();
    }

    @ChestContainer.IsLargeCallback //Inventory tweaks
    public boolean getVerticalButtons(){
        return false;
    }


}
