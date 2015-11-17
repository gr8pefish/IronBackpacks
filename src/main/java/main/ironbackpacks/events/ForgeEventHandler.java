package main.ironbackpacks.events;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.items.backpacks.BackpackTypes;
import main.ironbackpacks.items.backpacks.IBackpack;
import main.ironbackpacks.items.backpacks.ItemBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksHelper;
import main.ironbackpacks.util.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * All the events used that fire on the Forge Event bus
 */
public class ForgeEventHandler {

    /**
     * Called whenever an item is picked up by a player. The basis for all the filters, and the event used for the hopper/restocking and condenser/crafting upgrades too so it doesn't check too much and causes lag..
     * @param event - the event
     */
    @SubscribeEvent
    public void onItemPickupEvent(EntityItemPickupEvent event) {
        if (event.isCanceled())
            return; //ends the event
        else{
            ArrayList<ArrayList<ItemStack>> backpacks = getFilterCondenserAndHopperBackpacks(event.entityPlayer);
            boolean doFilter = checkHopperUpgrade(event, backpacks.get(2)); //doFilter is false if the itemEntity is in the hopperUpgrade's slots and the itemEntity's stackSize < refillSize
            if (doFilter) {
                checkFilterUpgrade(event, backpacks.get(0));
            }
            checkCondenserUpgrade(event, backpacks.get(1));
        }

    }

    /**
     * When a player dies, check if player has any backpacks with keepOnDeathUpgrade so then they are saved for when they spawn
     * @param event - the event
     */
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event){
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer){ //server side
            IronBackpacks.proxy.saveBackpackOnDeath((EntityPlayer) event.entity);
        }
    }

    /**
     * When a player respawns, check if player had any backpacks with keepOnDeathUpgrade so then they spawn with them
     * @param event - the event
     */
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event){
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer){ //server side
            IronBackpacks.proxy.loadBackpackOnDeath((EntityPlayer) event.entity);
        }
    }

    /**
     *  When the config is changed this will reload the changes to ensure it is correctly updated
     * @param event - the event
     */
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(ModInformation.ID)) {
            ConfigHandler.syncConfig();
            Logger.info("Refreshing configuration file.");
        }
    }

    //============================================================================Helper Methods===============================================================================

    /**
     * Gets all the backpacks that have filter, condenser, or hopper upgrades in them for the EntityItemPickupEvent event.
     * @param player - the player to check
     * @return - a nested array list of the array lists of each type of backpack that has each filter type
     */
    private ArrayList<ArrayList<ItemStack>> getFilterCondenserAndHopperBackpacks(EntityPlayer player){
        ArrayList<ItemStack> filterBackpacks = new ArrayList<ItemStack>();
        ArrayList<ItemStack> condenserBackpacks = new ArrayList<ItemStack>();
        ArrayList<ItemStack> hopperBackpacks = new ArrayList<ItemStack>();
        ArrayList<ArrayList<ItemStack>> returnArray = new ArrayList<ArrayList<ItemStack>>();

        //get the equipped pack
        getEventBackpacks(IronBackpacks.proxy.getEquippedBackpack(player), filterBackpacks, condenserBackpacks, hopperBackpacks, player);

        //get the packs in the inventory
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            getEventBackpacks(stack, filterBackpacks, condenserBackpacks, hopperBackpacks, player);
        }

        returnArray.add(filterBackpacks);
        returnArray.add(condenserBackpacks);
        returnArray.add(hopperBackpacks);
        return returnArray;
    }

    private void getEventBackpacks(ItemStack backpack, ArrayList<ItemStack> filterBackpacks, ArrayList<ItemStack> condenserBackpacks, ArrayList<ItemStack> hopperBackpacks, EntityPlayer player){
        if (backpack != null && backpack.getItem() != null && backpack.getItem() instanceof IBackpack) {

            int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack);
            addToLists(backpack, filterBackpacks, condenserBackpacks, hopperBackpacks, upgrades);

            if (UpgradeMethods.hasDepthUpgrade(upgrades)) {
                ItemBackpack itemBackpack = (ItemBackpack)backpack.getItem();
                ContainerBackpack container = new ContainerBackpack(player, new InventoryBackpack(player, backpack, BackpackTypes.values()[itemBackpack.getId()]), BackpackTypes.values()[itemBackpack.getId()]);
                for (int j = 0; j < container.getInventoryBackpack().getSizeInventory(); j++) {
                    ItemStack nestedBackpack = container.getInventoryBackpack().getStackInSlot(j);
                    if (nestedBackpack != null && nestedBackpack.getItem() != null && nestedBackpack.getItem() instanceof IBackpack) {
                        addToLists(nestedBackpack, filterBackpacks, condenserBackpacks, hopperBackpacks, IronBackpacksHelper.getUpgradesAppliedFromNBT(nestedBackpack));
                    }
                }
            }
        }
    }

    private void addToLists(ItemStack stack, ArrayList<ItemStack> filterBackpacks, ArrayList<ItemStack> condenserBackpacks, ArrayList<ItemStack> hopperBackpacks, int[] upgrades){
        if (UpgradeMethods.hasFilterBasicUpgrade(upgrades) || UpgradeMethods.hasFilterModSpecificUpgrade(upgrades) ||
                UpgradeMethods.hasFilterFuzzyUpgrade(upgrades) || UpgradeMethods.hasFilterOreDictUpgrade(upgrades) ||
                UpgradeMethods.hasFilterAdvancedUpgrade(upgrades) || UpgradeMethods.hasFilterMiningUpgrade(upgrades)) {
            filterBackpacks.add(stack);
        }
        if (UpgradeMethods.hasCondenserUpgrade(upgrades)) {
            condenserBackpacks.add(stack);
        }
        if (UpgradeMethods.hasHopperUpgrade(upgrades)) {
            hopperBackpacks.add(stack);
        }
    }

    /**
     * Checks the hopper/restocking upgrade to try and refill items.
     * @param event - EntityItemPickupEvent
     * @param backpackStacks - the backpacks with this upgrade
     * @return - boolean successful
     */
    private boolean checkHopperUpgrade(EntityItemPickupEvent event, ArrayList<ItemStack> backpackStacks){
        boolean doFilter = true;
        if (!backpackStacks.isEmpty()){
            for (ItemStack backpack : backpackStacks) {
                BackpackTypes type = BackpackTypes.values()[((ItemBackpack) backpack.getItem()).getGuiId()];
                ContainerBackpack container = new ContainerBackpack(event.entityPlayer, new InventoryBackpack(event.entityPlayer, backpack, type), type);
                if (!(event.entityPlayer.openContainer instanceof ContainerBackpack)) { //can't have the backpack open
                    container.sort(); //TODO: test with this added in
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
                                                int amountToResupply;
                                                if (event.item.getEntityItem().isItemEqual(stackToResupply)) {
                                                    amountToResupply = stackToResupply.getMaxStackSize() - stackToResupply.stackSize - event.item.getEntityItem().stackSize;
                                                    if (tempItem.stackSize >= amountToResupply) {
                                                        tempSlot.decrStackSize(amountToResupply);
                                                        slotToResupply.putStack(new ItemStack(stackToResupply.getItem(), stackToResupply.getMaxStackSize() - event.item.getEntityItem().stackSize, stackToResupply.getItemDamage()));
                                                        break;
                                                    } else {
                                                        tempSlot.decrStackSize(tempItem.stackSize);
                                                        slotToResupply.putStack(new ItemStack(stackToResupply.getItem(), stackToResupply.stackSize + tempItem.stackSize, stackToResupply.getItemDamage()));
                                                    }
                                                }else{
                                                    amountToResupply = stackToResupply.getMaxStackSize() - stackToResupply.stackSize;
                                                    if (tempItem.stackSize >= amountToResupply) {
                                                        tempSlot.decrStackSize(amountToResupply);
                                                        slotToResupply.putStack(new ItemStack(stackToResupply.getItem(), stackToResupply.getMaxStackSize(), stackToResupply.getItemDamage()));
                                                        break;
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
                }
                container.sort();
                container.onContainerClosed(event.entityPlayer);
            }
        }
        return doFilter;
    }

    /**
     * Checks the backpacks with the condenser/crafting upgrade to craft the specified items
     * @param event - EntityItemPickupEvent
     * @param backpackStacks - the backpacks with the condenser upgrade
     */
    private void checkCondenserUpgrade(EntityItemPickupEvent event, ArrayList<ItemStack> backpackStacks){
        if (!backpackStacks.isEmpty()){
            CraftingManager craftingManager = CraftingManager.getInstance();
            for (ItemStack backpack : backpackStacks) {
                if (!(event.entityPlayer.openContainer instanceof ContainerBackpack)) { //can't have the backpack open

                    BackpackTypes type = BackpackTypes.values()[((ItemBackpack) backpack.getItem()).getGuiId()];
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

    //===================================================================Filter Upgrade======================================================================

    /**
     * Checks the filters to see what items should be picked up and put in the backpack(s).
     * @param event - EntityItemPickupEvent
     * @param backpackStacks - the backpacks with a filter
     */
    private void checkFilterUpgrade(EntityItemPickupEvent event, ArrayList<ItemStack> backpackStacks){
        if (!backpackStacks.isEmpty()){
            for (ItemStack backpack : backpackStacks) {
                BackpackTypes type = BackpackTypes.values()[((ItemBackpack) backpack.getItem()).getGuiId()];
                ContainerBackpack container = new ContainerBackpack(event.entityPlayer, new InventoryBackpack(event.entityPlayer, backpack, type), type);
                if (!(event.entityPlayer.openContainer instanceof ContainerBackpack)) { //can't have the backpack open
                    int[] upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack);

                    if (UpgradeMethods.hasFilterBasicUpgrade(upgrades))
                        transferWithBasicFilter(UpgradeMethods.getBasicFilterItems(backpack), event, container);

                    if (UpgradeMethods.hasFilterModSpecificUpgrade(upgrades))
                        transferWithModSpecificFilter(UpgradeMethods.getModSpecificFilterItems(backpack), event, container);

                    if (UpgradeMethods.hasFilterFuzzyUpgrade(upgrades))
                        transferWithFuzzyFilter(UpgradeMethods.getFuzzyFilterItems(backpack), event, container);

                    if (UpgradeMethods.hasFilterOreDictUpgrade(upgrades))
                        transferWithOreDictFilter(UpgradeMethods.getOreDictFilterItems(backpack), getOreDict(event.item.getEntityItem()), event, container);

                    if (UpgradeMethods.hasFilterMiningUpgrade(upgrades))
                        transferWithMiningFilter(UpgradeMethods.getMiningFilterItems(backpack), getOreDict(event.item.getEntityItem()), event, container);

                    if (UpgradeMethods.hasFilterAdvancedUpgrade(upgrades)) {
                        ItemStack[] advFilterItems = UpgradeMethods.getAdvFilterAllItems(backpack);
                        byte[] advFilterButtonStates = UpgradeMethods.getAdvFilterButtonStates(backpack);

                        transferWithBasicFilter(UpgradeMethods.getAdvFilterBasicItems(advFilterItems, advFilterButtonStates), event, container);
                        transferWithModSpecificFilter(UpgradeMethods.getAdvFilterModSpecificItems(advFilterItems, advFilterButtonStates), event, container);
                        transferWithFuzzyFilter(UpgradeMethods.getAdvFilterFuzzyItems(advFilterItems, advFilterButtonStates), event, container);
                        transferWithOreDictFilter(UpgradeMethods.getAdvFilterOreDictItems(advFilterItems, advFilterButtonStates), getOreDict(event.item.getEntityItem()), event, container);
                    }

                }
                container.onContainerClosed(event.entityPlayer);
            }
        }
    }

    /**
     * Transfers items with respect to exact matching.
     * @param filterItems - the itemstacks to check
     * @param event - EntityItemPickupEvent
     * @param container - the backpack to transfer items into
     */
    private void transferWithBasicFilter(ArrayList<ItemStack> filterItems, EntityItemPickupEvent event, ContainerBackpack container){
        for (ItemStack filterItem : filterItems) {
            if (filterItem != null) {
                if (event.item.getEntityItem().isItemEqual(filterItem)) {
                    container.transferStackInSlot(event.item.getEntityItem()); //custom method to put itemEntity's itemStack into the backpack
                }
            }
        }
    }

    /**
     * Transfers items ignoring damage values.
     * @param filterItems - the items to check
     * @param event - EntityItemPickupEvent
     * @param container - the backpack to transfer items into
     */
    private void transferWithFuzzyFilter(ArrayList<ItemStack> filterItems, EntityItemPickupEvent event, ContainerBackpack container){
        for (ItemStack filterItem : filterItems) {
            if (filterItem != null) {
                if (event.item.getEntityItem().getItem() == filterItem.getItem()) {
                    container.transferStackInSlot(event.item.getEntityItem()); //custom method to put itemEntity's itemStack into the backpack
                }
            }
        }
    }

    /**
     * Transfers items with respect to the ore dictionary
     * @param filterItems - the items to check
     * @param itemEntityOre - the ore dictionary entry of the item
     * @param event - EntityItemPickupEvent
     * @param container - the backpack to move items into
     */
    private void transferWithOreDictFilter(ArrayList<ItemStack> filterItems, ArrayList<String> itemEntityOre, EntityItemPickupEvent event, ContainerBackpack container){
        for (ItemStack filterItem : filterItems) {
            if (filterItem != null) {
                ArrayList<String> filterItemOre = getOreDict(filterItem);
                if (itemEntityOre != null && filterItemOre != null) {
                    for (String oreName : itemEntityOre) {
                        if (oreName != null && filterItemOre.contains(oreName)) {
                            container.transferStackInSlot(event.item.getEntityItem()); //custom method to put itemEntity's itemStack into the backpack
                        }
                    }
                }
            }
        }
    }

    /**
     * Transfers items with respect to the category of the same mod
     * @param filterItems - the items to check
     * @param event -EntityItemPickupEvent
     * @param container - the backpack to move the items into
     */
    private void transferWithModSpecificFilter(ArrayList<ItemStack> filterItems, EntityItemPickupEvent event, ContainerBackpack container){
        for (ItemStack filterItem : filterItems) {
            if (filterItem != null) {
                //if modId1 == modId2 same mod so transfer
                if (GameRegistry.findUniqueIdentifierFor(event.item.getEntityItem().getItem()).modId.equals(GameRegistry.findUniqueIdentifierFor(filterItem.getItem()).modId)){
                    container.transferStackInSlot(event.item.getEntityItem());
                }
            }
        }
    }

    /**
     * Transfers items with ore in the name
     * @param filterItems - the items to check
     * @param event -EntityItemPickupEvent
     * @param container - the backpack to move the items into
     */
    private void transferWithMiningFilter(ArrayList<ItemStack> filterItems, ArrayList<String> itemEntityOre, EntityItemPickupEvent event, ContainerBackpack container){
        transferWithBasicFilter(filterItems, event, container);
        if (itemEntityOre != null) {
            for (String oreName : itemEntityOre) {
                if (oreName != null && (oreName.contains("ore") || oreName.contains("gem") || oreName.contains("dust"))) {
                    container.transferStackInSlot(event.item.getEntityItem()); //custom method to put itemEntity's itemStack into the backpack
                }
            }
        }
    }

    /**
     * Gets the ore dictionary entries from an item
     * @param itemStack - the item to check
     * @return - OreDict entries in string form, null if no entries
     */
    private ArrayList<String> getOreDict(ItemStack itemStack){
        int[] ids = OreDictionary.getOreIDs(itemStack);
        ArrayList<String> retList = new ArrayList<String>();
        if (ids.length > 0){
            for (int i = 0; i < ids.length; i++) {
                if (i > 0 && !retList.contains(OreDictionary.getOreName(ids[i]))) { //no duplicates
                    retList.add(OreDictionary.getOreName(ids[i]));
                }else{
                    retList.add(OreDictionary.getOreName(ids[i]));
                }
            }
        }
        return retList.isEmpty() ? null : retList;
    }


}
