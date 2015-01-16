package main.ironbackpacks.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

import java.util.ArrayList;

public class ForgeEventHandler {

    //All the events that fire on the Forge Event bus

    @SubscribeEvent
    public void onItemPickupEvent(EntityItemPickupEvent event) {
        if (event.isCanceled())
            return;
        else{
            ArrayList<ArrayList<ItemStack>> backpacks = IronBackpacksHelper.getFilterCondenserAndHopperBackpacks(event.entityPlayer);
            boolean doFilter = checkHopperUpgrade(event, backpacks.get(0)); //doFilter is false if the itemEntity is in the hopperUpgrade's slots and the itemEntity's stackSize < refillSize
            if (doFilter) {
                checkFilterUpgrade(event, backpacks.get(1));
            }
            checkCondenserUpgrade(event, backpacks.get(2));
        }

    }

//    @SubscribeEvent
//    public void onItemPlacedEvent(){
//
//    }

    @SubscribeEvent
    public void playerDiesEvent(PlayerDropsEvent event){
        //if save backpack on death upgrade, don't drop the item
    }

    public boolean checkHopperUpgrade(EntityItemPickupEvent event, ArrayList<ItemStack> backpackStacks){
        boolean doFilter = true;
        if (!backpackStacks.isEmpty()){
            for (ItemStack backpack : backpackStacks) {
                IronBackpackType type = IronBackpackType.values()[((ItemBaseBackpack) backpack.getItem()).getGuiId()];
                ContainerBackpack container = new ContainerBackpack(event.entityPlayer, new InventoryBackpack(event.entityPlayer, backpack, type), type);
                if (!(event.entityPlayer.openContainer instanceof ContainerBackpack)) { //can't have the backpack open
                    ArrayList<ItemStack> hopperItems = UpgradeMethods.getHopperItems(backpack);
                    for (ItemStack hopperItem : hopperItems) {
                        if (hopperItem != null) {

                            boolean foundSlot = false;
                            ItemStack stackToResupply = null;
                            Slot slotToResupply = null;

                            for (int i = type.getSize(); i < type.getSize() + 36; i++){ //check player's inv for item
                                Slot tempSlot = (Slot) container.getSlot(i);
                                if (tempSlot!= null && tempSlot.getHasStack()){
                                    ItemStack tempItem = tempSlot.getStack();
                                    if (tempItem.isItemEqual(hopperItem) && tempItem.stackSize < tempItem.getMaxStackSize()){ //found and less than max stack size
                                        foundSlot = true;
                                        slotToResupply = tempSlot;
                                        stackToResupply = tempItem;
                                        break;
                                    }
                                }
                            }

                            if (foundSlot){ //try to resupply with the itemEntity first
                                boolean done = false;
                                if (event.item.getEntityItem().isItemEqual(stackToResupply)){
                                    int amountToResupply = stackToResupply.getMaxStackSize() - stackToResupply.stackSize;
                                    if (event.item.getEntityItem().stackSize >= amountToResupply) {
                                        event.item.setEntityItemStack(new ItemStack(event.item.getEntityItem().getItem(), event.item.getEntityItem().stackSize - amountToResupply, event.item.getEntityItem().getItemDamage()));
                                        slotToResupply.putStack(new ItemStack(stackToResupply.getItem(), stackToResupply.getMaxStackSize(), stackToResupply.getItemDamage()));
                                        done = true;
                                    }else {
                                        doFilter = false;
                                    }
                                }
                                if (!done) { //then resupply from the backpack (if necessary)
                                    for (int i = 0; i < type.getSize(); i++) {
                                        Slot tempSlot = (Slot) container.getSlot(i);
                                        if (tempSlot != null && tempSlot.getHasStack()) {
                                            ItemStack tempItem = tempSlot.getStack();
                                            if (tempItem.isItemEqual(stackToResupply)) {
                                                int amountToResupply = stackToResupply.getMaxStackSize() - stackToResupply.stackSize - event.item.getEntityItem().stackSize;
                                                System.out.println("got here");
                                                if (tempItem.stackSize >= amountToResupply) {
                                                    tempSlot.decrStackSize(amountToResupply);
                                                    slotToResupply.putStack(new ItemStack(stackToResupply.getItem(), stackToResupply.getMaxStackSize(), stackToResupply.getItemDamage()));
                                                } else {
                                                    tempSlot.decrStackSize(tempItem.stackSize);
                                                    slotToResupply.putStack(new ItemStack(stackToResupply.getItem(), stackToResupply.stackSize + tempItem.stackSize, stackToResupply.getItemDamage()));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                container.onContainerClosed(event.entityPlayer);
            }
        }
        return doFilter;
    }

    public void checkFilterUpgrade(EntityItemPickupEvent event, ArrayList<ItemStack> backpackStacks){
        if (!backpackStacks.isEmpty()){
            for (ItemStack backpack : backpackStacks) {
                IronBackpackType type = IronBackpackType.values()[((ItemBaseBackpack) backpack.getItem()).getGuiId()];
                ContainerBackpack container = new ContainerBackpack(event.entityPlayer, new InventoryBackpack(event.entityPlayer, backpack, type), type);
                if (!(event.entityPlayer.openContainer instanceof ContainerBackpack)) { //can't have the backpack open
                    ArrayList<ItemStack> filterItems = UpgradeMethods.getFilterItems(backpack);
                    for (ItemStack filterItem : filterItems) {
                        if (filterItem != null) {
                            if (event.item.getEntityItem().isItemEqual(filterItem)) {
                                container.transferStackInSlot(event.item.getEntityItem()); //custom method to put itemEntity's itemStack into the backpack
                            }
                        }
                    }
                }
                container.onContainerClosed(event.entityPlayer);
            }
        }
    }

    public void checkCondenserUpgrade(EntityItemPickupEvent event, ArrayList<ItemStack> backpackStacks){
        CraftingManager craftingManager = CraftingManager.getInstance();
        if (!backpackStacks.isEmpty()){
            for (ItemStack backpack : backpackStacks) {
                if (!(event.entityPlayer.openContainer instanceof ContainerBackpack)) { //can't have the backpack open

                    IronBackpackType type = IronBackpackType.values()[((ItemBaseBackpack) backpack.getItem()).getGuiId()];
                    ContainerBackpack container = new ContainerBackpack(event.entityPlayer, new InventoryBackpack(event.entityPlayer, backpack, type), type);

                    ContainerWorkbench containerWorkbench = new ContainerWorkbench(event.entityPlayer.inventory, event.item.worldObj, 0, 0, 0);
                    InventoryCrafting inventoryCrafting = new InventoryCrafting(containerWorkbench, 3, 3); //fake workbench/inventory for checking matching recipe

                    ArrayList<ItemStack> condenserItems = UpgradeMethods.getCondenserItems(backpack);
                    for (ItemStack condenserItem : condenserItems) {
                        if (condenserItem != null) {
                            for (int index = 0; index < type.getSize(); index++) {
                                Slot theSlot = (Slot) container.getSlot(index);
                                if (theSlot!=null && theSlot.getHasStack()) {
                                    ItemStack theStack = theSlot.getStack();
                                    if (theStack != null && theStack.stackSize >= 9 && theStack.isItemEqual(condenserItem)) {
                                        ItemStack myStack = new ItemStack(theStack.getItem(), 1, theStack.getItemDamage()); //stackSize of 1
                                        for (int i = 0; i < 9; i++) {
                                            inventoryCrafting.setInventorySlotContents(i, myStack); //3x3 crafting grid full of the item
                                        }
                                        ItemStack recipeOutput = craftingManager.findMatchingRecipe(inventoryCrafting, event.item.worldObj);
                                        if (recipeOutput != null) {
                                            int numberOfIterations = (int) Math.floor(theStack.stackSize / 9);
                                            ItemStack myRecipeOutput = new ItemStack(recipeOutput.getItem(), numberOfIterations, recipeOutput.getItemDamage());
                                            if (container.transferStackInSlot(myRecipeOutput) != null) {
                                                theSlot.decrStackSize(theStack.stackSize - (theStack.stackSize % 9));
                                            }
                                            container.save(event.entityPlayer);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    container.sort(); //sort items
                    container.onContainerClosed(event.entityPlayer);
                }
            }
        }
    }

}
