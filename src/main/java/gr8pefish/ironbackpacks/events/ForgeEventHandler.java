package gr8pefish.ironbackpacks.events;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.container.backpack.ContainerBackpack;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.entity.EntityBackpack;
import gr8pefish.ironbackpacks.entity.extendedProperties.PlayerBackpackDeathProperties;
import gr8pefish.ironbackpacks.entity.extendedProperties.PlayerBackpackProperties;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.client.ClientEquippedPackMessage;
import gr8pefish.ironbackpacks.util.IronBackpacksHelper;
import gr8pefish.ironbackpacks.util.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * All the events used that fire on the Forge Event bus
 */
public class ForgeEventHandler {

    /**
     * Called whenever an item is picked up by a player. The basis for all the filters, and the event used for the hopper/restocking and crafter/crafting upgrades too so it doesn't check too much and causes lag..
     * @param event - the event fired
     */
    @SubscribeEvent
    public void onItemPickupEvent(EntityItemPickupEvent event) {
        if (event.isCanceled())
            return; //ends the event
        else{
            ArrayList<ArrayList<ItemStack>> backpacks = getFilterCrafterAndRestockerBackpacks(event.entityPlayer);
            boolean doFilter = checkRestockerUpgradeItemPickup(event, backpacks.get(4)); //doFilter is false if the itemEntity is in the restockerUpgrade's slots and the itemEntity's stackSize < refillSize
            if (doFilter) {
                checkFilterUpgrade(event, backpacks.get(0)); //beware creative testing takes the itemstack still
            }
            for (int i = 1; i < 4; i++) {
                checkCrafterUpgrade(event, backpacks.get(i), i);//1x1, 2x2, and 3x3 crafters/crafters
            }
        }
    }

    /**
     * Called whenever the player uses an item. Used for the restocking(hopper) upgrade.
     * @param event - the event fired
     */
    @SubscribeEvent
    public void onPlayerItemUseEvent(PlayerUseItemEvent.Finish event){
        ItemStack resuppliedStack = null;
        if (!event.isCanceled()){
            ArrayList<ArrayList<ItemStack>> backpacks = getFilterCrafterAndRestockerBackpacks(event.entityPlayer);
            resuppliedStack = checkRestockerUpgradeItemUse(event, backpacks.get(2)); //reduce the stack in the backpack if you can refill and send back the refilled itemStack
            if (resuppliedStack != null) {
                event.result = resuppliedStack;
            }
        }
    }

    /**
     * When a player dies, check if player has any backpacks with keepOnDeathUpgrade so then they are saved for when they spawn
     * @param event - the event
     */
    @SubscribeEvent//(priority = EventPriority.HIGHEST)
    public void onDeath(LivingDeathEvent event){
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer){ //server side
            IronBackpacksHelper.saveBackpackOnDeath((EntityPlayer) event.entity);
        }
    }

    /**
     * When a player respawns, check if player had any backpacks with keepOnDeathUpgrade so then they spawn with them
     * @param event - the event
     */
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event){
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer){ //server side
            IronBackpacksHelper.loadBackpackOnDeath((EntityPlayer) event.entity);
        }
    }

    /**
     * When a player is "constructed" this is called, we need to give them our extended properties
     * @param event - the construction event
     */
    @SubscribeEvent
    public void onEntityConstruction(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            if(PlayerBackpackProperties.get((EntityPlayer) event.entity) == null)
                PlayerBackpackProperties.create((EntityPlayer) event.entity);
            if (PlayerBackpackDeathProperties.get((EntityPlayer) event.entity) == null)
                PlayerBackpackDeathProperties.create((EntityPlayer) event.entity);
        }
    }

    /**
     * Used to make sure the player's equipped backpack is shown correctly
     * @param event - the player logged in event
     */
    @SubscribeEvent
    public void onPlayerLogIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event){
        ItemStack backpack = PlayerBackpackProperties.getEquippedBackpack(event.player);
        if (!EntityBackpack.backpacksSpawnedMap.containsKey(event.player) && backpack != null) {

            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
            PlayerBackpackProperties.setEquippedBackpack(event.player, backpack); //update server on correct pack

            if (!ConfigHandler.disableRendering)
                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
        }
    }

    /**
     * When a player dies and respawns we need to clone their old data over.
     * @param event - the clone event
     */
    @SubscribeEvent
    public void onPlayerCloning(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        PlayerBackpackDeathProperties epNew = PlayerBackpackDeathProperties.get(event.entityPlayer);
        PlayerBackpackDeathProperties epOld = PlayerBackpackDeathProperties.get(event.original);

        //update new dat with old
        epNew.setEternityBackpacks(epOld.getEternityBackpacks());
        epNew.setEquippedBackpack(epOld.getEquippedBackpack());
    }

    /**
     * Used to make sure the player respawns with an equipped backpack if they should
     * @param event - the player respawn event
     */
    @SubscribeEvent
    public void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event){
        ItemStack backpack = PlayerBackpackProperties.getEquippedBackpack(event.player);
        if (!EntityBackpack.backpacksSpawnedMap.containsKey(event.player) && backpack != null) {

            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
            PlayerBackpackProperties.setEquippedBackpack(event.player, backpack); //update server on correct pack

            if (!ConfigHandler.disableRendering)
                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
        }
    }

    /**
     * Used to make sure the equipped backpack transfers over correctly between dimensions
     * @param event - the change dimension event
     */
    @SubscribeEvent
    public void onPlayerDimChange(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event){
        ItemStack backpack = PlayerBackpackProperties.getEquippedBackpack(event.player);
        if (backpack != null) {
            if (EntityBackpack.backpacksSpawnedMap.containsKey(event.player)) //if has old dimension backpack
                IronBackpacksHelper.killEntityBackpack(event.player); //kill old backpack

            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
            PlayerBackpackProperties.setEquippedBackpack(event.player, backpack); //TODO: test with these removed

            if (!ConfigHandler.disableRendering)
                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player); //spawn new pack
        } else {
            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(null), (EntityPlayerMP) event.player); //update client on correct pack
            PlayerBackpackProperties.setEquippedBackpack(event.player, null);
        }
    }

    /**
     *  When the config is changed this will reload the changes to ensure it is correctly updated
     * @param event - the event
     */
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(Constants.MODID)) {
            ConfigHandler.syncConfig(false);
        }
    }

    //============================================================================Helper Methods===============================================================================

    /**
     * Gets all the backpacks that have filter, condenser, or restocker upgrades in them for the EntityItemPickupEvent event.
     * @param player - the player to check
     * @return - a nested array list of the array lists of each type of backpack that has each filter type
     */
    private ArrayList<ArrayList<ItemStack>> getFilterCrafterAndRestockerBackpacks(EntityPlayer player){
        ArrayList<ItemStack> filterBackpacks = new ArrayList<ItemStack>();
        ArrayList<ItemStack> crafterTinyBackpacks = new ArrayList<ItemStack>();
        ArrayList<ItemStack> crafterSmallBackpacks = new ArrayList<ItemStack>();
        ArrayList<ItemStack> crafterBackpacks = new ArrayList<ItemStack>();
        ArrayList<ItemStack> restockerBackpacks = new ArrayList<ItemStack>();
        ArrayList<ArrayList<ItemStack>> returnArray = new ArrayList<ArrayList<ItemStack>>();

        //get the equipped pack
        getEventBackpacks(PlayerBackpackProperties.getEquippedBackpack(player), filterBackpacks, crafterTinyBackpacks, crafterSmallBackpacks, crafterBackpacks, restockerBackpacks, player);


        //get the packs in the inventory
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            getEventBackpacks(stack, filterBackpacks, crafterTinyBackpacks, crafterSmallBackpacks, crafterBackpacks, restockerBackpacks, player);
        }

        returnArray.add(filterBackpacks);
        returnArray.add(crafterTinyBackpacks);
        returnArray.add(crafterSmallBackpacks);
        returnArray.add(crafterBackpacks);
        returnArray.add(restockerBackpacks);
        return returnArray;
    }

    private void getEventBackpacks(ItemStack backpack, ArrayList<ItemStack> filterBackpacks, ArrayList<ItemStack> crafterTinyBackpacks, ArrayList<ItemStack> crafterSmallBackpacks, ArrayList<ItemStack> crafterBackpacks, ArrayList<ItemStack> restockerBackpacks, EntityPlayer player){
        if (backpack != null && backpack.getItem() != null && backpack.getItem() instanceof IBackpack) {

            ArrayList<ItemStack> upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack);
            addToLists(backpack, filterBackpacks, crafterTinyBackpacks, crafterSmallBackpacks, crafterBackpacks, restockerBackpacks, upgrades);

            if (UpgradeMethods.hasDepthUpgrade(upgrades)) {
                ItemBackpack itemBackpack = (ItemBackpack)backpack.getItem();
                ContainerBackpack container = new ContainerBackpack(new InventoryBackpack(player, backpack));
                for (int j = 0; j < container.getInventoryBackpack().getSizeInventory(); j++) {
                    ItemStack nestedBackpack = container.getInventoryBackpack().getStackInSlot(j);
                    if (nestedBackpack != null && nestedBackpack.getItem() != null && nestedBackpack.getItem() instanceof IBackpack) {
                        addToLists(nestedBackpack, filterBackpacks, crafterTinyBackpacks, crafterSmallBackpacks, crafterBackpacks, restockerBackpacks, IronBackpacksHelper.getUpgradesAppliedFromNBT(nestedBackpack));
                    }
                }
            }
        }
    }

    private void addToLists(ItemStack stack, ArrayList<ItemStack> filterBackpacks, ArrayList<ItemStack> crafterTinyBackpacks, ArrayList<ItemStack> crafterSmallBackpacks, ArrayList<ItemStack> crafterBackpacks, ArrayList<ItemStack> restockerBackpacks, ArrayList<ItemStack> upgrades){
        if (UpgradeMethods.hasFilterBasicUpgrade(upgrades) || UpgradeMethods.hasFilterModSpecificUpgrade(upgrades) ||
                UpgradeMethods.hasFilterFuzzyUpgrade(upgrades) || UpgradeMethods.hasFilterOreDictUpgrade(upgrades) ||
                UpgradeMethods.hasFilterAdvancedUpgrade(upgrades) || UpgradeMethods.hasFilterMiningUpgrade(upgrades)) {
            filterBackpacks.add(stack);
        }
        if (UpgradeMethods.hasCraftingTinyUpgrade(upgrades)) {
            crafterTinyBackpacks.add(stack);
        }
        if (UpgradeMethods.hasCraftingSmallUpgrade(upgrades)) {
            crafterSmallBackpacks.add(stack);
        }
        if (UpgradeMethods.hasCraftingUpgrade(upgrades)) {
            crafterBackpacks.add(stack);
        }
        if (UpgradeMethods.hasRestockingUpgrade(upgrades)) {
            restockerBackpacks.add(stack);
        }
    }


    //TODO: cleanup the following two methods

    /**
     * Checks the hopper/restocking upgrade to try and refill items.
     * @param event - EntityItemPickupEvent
     * @param backpackStacks - the backpacks with this upgrade
     * @return - boolean successful
     */
    private boolean checkRestockerUpgradeItemPickup(EntityItemPickupEvent event, ArrayList<ItemStack> backpackStacks){
        boolean doFilter = true;
        boolean shouldSave = false;
        if (!backpackStacks.isEmpty()){
            for (ItemStack backpack : backpackStacks) {
                shouldSave = false;
//                BackpackTypes type = BackpackTypes.values()[((ItemBackpackSubItems) backpack.getItem()).getGuiId()];
                ItemBackpack itemBackpack = ((ItemBackpack)backpack.getItem()); //TODO: hardcoded
                ContainerBackpack container = new ContainerBackpack(new InventoryBackpack(event.entityPlayer, backpack)); //TODO: remove additional itemstack parameter
                if (!(event.entityPlayer.openContainer instanceof ContainerBackpack)) { //can't have the backpack open
                    container.sort(); //TODO: test with this removed
                    ArrayList<ItemStack> restockerItems = UpgradeMethods.getRestockingItems(backpack);
                    for (ItemStack restockerItem : restockerItems) {
                        if (restockerItem != null) {

                            boolean foundSlot = false;
                            ItemStack stackToResupply = null;
                            Slot slotToResupply = null;

                            for (int i = itemBackpack.getSize(backpack); i < itemBackpack.getSize(backpack) + 36; i++){ //check player's inv for item
                                Slot tempSlot = (Slot) container.getSlot(i);
                                if (tempSlot!= null && tempSlot.getHasStack()){
                                    ItemStack tempItem = tempSlot.getStack();
                                    if (IronBackpacksHelper.areItemsEqualAndStackable(tempItem, restockerItem)){ //found and less than max stack size
                                        foundSlot = true;
                                        slotToResupply = tempSlot;
                                        stackToResupply = tempItem;
                                        break;
                                    }
                                }
                            }

                            if (foundSlot){ //try to resupply with the itemEntity first
                                boolean done = false;
                                if (IronBackpacksHelper.areItemsEqualForStacking(event.item.getEntityItem(), stackToResupply)){
                                    int amountToResupply = stackToResupply.getMaxStackSize() - stackToResupply.stackSize;
                                    if (event.item.getEntityItem().stackSize >= amountToResupply) { //if larger size of stack on the ground than needed to resupply
                                        event.item.setEntityItemStack(new ItemStack(event.item.getEntityItem().getItem(), event.item.getEntityItem().stackSize - amountToResupply, event.item.getEntityItem().getItemDamage()));
                                        slotToResupply.putStack(new ItemStack(stackToResupply.getItem(), stackToResupply.getMaxStackSize(), stackToResupply.getItemDamage()));
                                        done = true;
                                        shouldSave = true;
                                    }else { //just resupply what you can, it will automatically go into the player's slot needed
                                        doFilter = false;
                                    }
                                }
                                if (!done) { //then resupply from the backpack (if necessary)
                                    for (int i = 0; i < itemBackpack.getSize(backpack); i++) {
                                        Slot tempSlot = (Slot) container.getSlot(i);
                                        if (tempSlot != null && tempSlot.getHasStack()) {
                                            ItemStack tempItem = tempSlot.getStack();
                                            if (IronBackpacksHelper.areItemsEqualForStacking(tempItem, stackToResupply)) {
                                                int amountToResupply;
                                                if (IronBackpacksHelper.areItemsEqualForStacking(event.item.getEntityItem(), stackToResupply)) {
                                                    amountToResupply = stackToResupply.getMaxStackSize() - stackToResupply.stackSize - event.item.getEntityItem().stackSize;
                                                    if (tempItem.stackSize >= amountToResupply) {
                                                        tempSlot.decrStackSize(amountToResupply);
                                                        slotToResupply.putStack(new ItemStack(stackToResupply.getItem(), stackToResupply.getMaxStackSize() - event.item.getEntityItem().stackSize, stackToResupply.getItemDamage()));
                                                        container.sort();
                                                        container.onContainerClosed(event.entityPlayer);
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
                                                        container.sort();
                                                        container.onContainerClosed(event.entityPlayer);
                                                        break;
                                                    } else {
                                                        tempSlot.decrStackSize(tempItem.stackSize);
                                                        slotToResupply.putStack(new ItemStack(stackToResupply.getItem(), stackToResupply.stackSize + tempItem.stackSize, stackToResupply.getItemDamage()));
                                                    }
                                                }
                                                shouldSave = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (shouldSave) {
                    container.sort();
                    container.onContainerClosed(event.entityPlayer);
                }
            }
        }
        return doFilter;
    }

    /**
     * Checks the hopper/restocking upgrade to try and refill items. Decrements from the backpack's stacks and updates the appropriate slot/stack in the player's inventory.
     * for each backpack
     *  if backpack has itemUsed in filter
     *      if backpack has itemUsed in inv
     *          resupply itemUsed
     *              get rid of backpackStack
     *              return new size of itemUsed stack
     * @param event - PlayerUseItemEvent.Finish
     * @param backpackStacks - the backpacks with this upgrade
     */
    private ItemStack checkRestockerUpgradeItemUse(PlayerUseItemEvent.Finish event, ArrayList<ItemStack> backpackStacks){
        if (!backpackStacks.isEmpty()){
            for (ItemStack backpack : backpackStacks) {
//                BackpackTypes type = BackpackTypes.values()[((ItemBackpackSubItems) backpack.getItem()).getGuiId()];
                ItemBackpack itemBackpack = (ItemBackpack)backpack.getItem(); //TODO: hardcoded
                ContainerBackpack container = new ContainerBackpack(new InventoryBackpack(event.entityPlayer, backpack));
                if (!(event.entityPlayer.openContainer instanceof ContainerBackpack)) { //can't have the backpack open
                    container.sort(); //TODO: test with this removed
                    ArrayList<ItemStack> restockerItems = UpgradeMethods.getRestockingItems(backpack);
                    for (ItemStack restockerItem : restockerItems) {
                        if (restockerItem != null) {

                            boolean foundSlot = false;
                            ItemStack stackToResupply = null;
                            Slot slotToResupply = null;

                            for (int i = itemBackpack.getSize(backpack); i < itemBackpack.getSize(backpack) + 36; i++){ //check player's inv for item (backpack size + 36 for player inv)
                                Slot tempSlot = (Slot) container.getSlot(i);
                                if (tempSlot!= null && tempSlot.getHasStack()){
                                    ItemStack tempItem = tempSlot.getStack();
                                    if (IronBackpacksHelper.areItemsEqualForStacking(event.item, restockerItem) //has to be same item as what was used in the event
                                            && IronBackpacksHelper.areItemsEqualAndStackable(tempItem, restockerItem)){ //found and less than max stack size
                                        foundSlot = true;
                                        slotToResupply = tempSlot;
                                        stackToResupply = tempItem;
                                        break;
                                    }
                                }
                            }

                            if (foundSlot){ // resupply from the backpack
                                for (int i = 0; i < itemBackpack.getSize(backpack); i++) {
                                    Slot backpackSlot = (Slot) container.getSlot(i);
                                    if (backpackSlot != null && backpackSlot.getHasStack()) {
                                        ItemStack backpackItemStack = backpackSlot.getStack();

                                        if (IronBackpacksHelper.areItemsEqualForStacking(stackToResupply, backpackItemStack)) {
                                            int amountToResupply = stackToResupply.getMaxStackSize() - stackToResupply.stackSize;

                                            if (backpackItemStack.stackSize >= amountToResupply) {
                                                backpackSlot.decrStackSize(amountToResupply);
                                                container.sort();
                                                container.onContainerClosed(event.entityPlayer);
                                                return (new ItemStack(stackToResupply.getItem(), stackToResupply.getMaxStackSize(), stackToResupply.getItemDamage()));

                                            } else {
                                                backpackSlot.decrStackSize(backpackItemStack.stackSize);
                                                container.sort();
                                                container.onContainerClosed(event.entityPlayer);
                                                return (new ItemStack(stackToResupply.getItem(), stackToResupply.stackSize + backpackItemStack.stackSize, stackToResupply.getItemDamage()));
                                                //don't have to iterate
                                                //b/c once sorted you have as big of a stack as you will ever have so it can only refill that much
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } //no save b/c returns and saves if it does anything
            }
        }
        return null;
    }

    /**
     * Checks the backpacks with the crafter/crafting upgrade to craft the specified items
     * @param event - EntityItemPickupEvent
     * @param backpackStacks - the backpacks with the crafter upgrade
     * @param craftingGridDiameterToFill - The size of the crafting grid to try filling with (1x1 or 2x2 or 3x3)
     */
    private void checkCrafterUpgrade(EntityItemPickupEvent event, ArrayList<ItemStack> backpackStacks, int craftingGridDiameterToFill){
        boolean shouldSave = false;
        if (!backpackStacks.isEmpty()){
            CraftingManager craftingManager = CraftingManager.getInstance();
            for (ItemStack backpack : backpackStacks) {
                shouldSave = false;
                if (!(event.entityPlayer.openContainer instanceof ContainerBackpack)) { //can't have the backpack open

//                    BackpackTypes type = BackpackTypes.values()[((ItemBackpackSubItems) backpack.getItem()).getGuiId()];
                    ItemBackpack itemBackpack = (ItemBackpack)backpack.getItem();
                    ContainerBackpack container = new ContainerBackpack(new InventoryBackpack(event.entityPlayer, backpack));

                    container.sort(); //sort to make sure all items are in their smallest slot numbers possible
                    if (container.getInventoryBackpack().getStackInSlot( //if the last slot has an item
                            container.getInventoryBackpack().getSizeInventory()) != null){ //assume the backpack is full and stop trying to craft
                        break; //TODO: test
                    }

                    ContainerWorkbench containerWorkbench = new ContainerWorkbench(event.entityPlayer.inventory, event.item.worldObj, new BlockPos(0, 0, 0)); //TODO: test 1.8
                    InventoryCrafting inventoryCrafting = new InventoryCrafting(containerWorkbench, 3, 3); //fake workbench/inventory for checking matching recipe

                    ArrayList<ItemStack> crafterItems;
                    switch (craftingGridDiameterToFill){
                        case 1:
                            crafterItems = UpgradeMethods.getCrafterTinyItems(backpack);
                            break;
                        case 2:
                            crafterItems = UpgradeMethods.getCrafterSmallItems(backpack);
                            break;
                        case 3:
                            crafterItems = UpgradeMethods.getCrafterItems(backpack);
                            break;
                        default: //should be unreachable
                            crafterItems = UpgradeMethods.getCrafterItems(backpack);
                            Logger.error("IronBackpacks CraftingUpgrade Error, will probably give the wrong output");
                    }

                    for (ItemStack crafterItem : crafterItems) {
                        if (crafterItem != null) {
                            for (int index = 0; index < itemBackpack.getSize(backpack); index++) {
                                Slot theSlot = (Slot) container.getSlot(index);
                                if (theSlot!=null && theSlot.getHasStack()) {
                                    ItemStack theStack = theSlot.getStack();
                                    if (theStack != null && theStack.stackSize >= (craftingGridDiameterToFill*craftingGridDiameterToFill) && IronBackpacksHelper.areItemsEqualForStacking(theStack, crafterItem)) {
                                        ItemStack myStack = new ItemStack(theStack.getItem(), 1, theStack.getItemDamage()); //stackSize of 1
                                        if (craftingGridDiameterToFill == 2){//special handling needed to make it a square
                                            inventoryCrafting.setInventorySlotContents(0, myStack);
                                            inventoryCrafting.setInventorySlotContents(1, myStack);
                                            inventoryCrafting.setInventorySlotContents(3, myStack);
                                            inventoryCrafting.setInventorySlotContents(4, myStack);
                                        }else {
                                            for (int i = 0; i < (craftingGridDiameterToFill*craftingGridDiameterToFill); i++) {
                                                inventoryCrafting.setInventorySlotContents(i, myStack); //crafting grid with a 1x1 (single item) or 3x3 square of the item
                                            }
                                        }
                                        ItemStack recipeOutput = craftingManager.findMatchingRecipe(inventoryCrafting, event.item.worldObj);
                                        if (recipeOutput != null) { //TODO: test math is correct here

                                            shouldSave = true;

                                            int numberOfIterations = (int) Math.floor(theStack.stackSize / (craftingGridDiameterToFill * craftingGridDiameterToFill));
                                            int numberOfItems = recipeOutput.stackSize * numberOfIterations;

                                            if (numberOfItems > 64){ //multiple stacks, need to make sure there is room

                                                //More efficient code [that doesn't work]
//                                                int tempNumberOfItems = numberOfItems;
//                                                int totalStacks = ((int)Math.ceil(numberOfItems / 64d));
//                                                for (int numOfStacks = 0; numOfStacks < totalStacks; numOfStacks++) {
//                                                    Logger.info("temp number of items: "+tempNumberOfItems);
//                                                    ItemStack myRecipeOutput = new ItemStack(recipeOutput.getItem(), tempNumberOfItems, recipeOutput.getItemDamage());
//                                                    if (container.transferStackInSlot(myRecipeOutput) != null) { //check if there is room to put them
//                                                        int decrementAmount = tempNumberOfItems >= 64 ? 64 : tempNumberOfItems;
//                                                        theSlot.decrStackSize(theStack.stackSize - ((int) Math.ceil(decrementAmount / recipeOutput.stackSize)));
//                                                    }
//                                                    tempNumberOfItems -= 64;
//                                                }

                                                //TODO: iterates an excessive amount, make it more efficient by using the basis of the code above
                                                for (int i = 0; i < numberOfIterations; i++){ //for every possible crafting operation
                                                    ItemStack myRecipeOutput = new ItemStack(recipeOutput.getItem(), recipeOutput.stackSize, recipeOutput.getItemDamage()); //get the output
                                                    ItemStack stack = container.transferStackInSlot(myRecipeOutput); //try to put that output into the backpack
                                                    if (stack == null){ //can't put it anywhere
                                                        break;
                                                    }else if (stack.stackSize != 0){ //remainder present, stack couldn't be fully transferred, undo the last operation
                                                        Slot slot = container.getSlot(itemBackpack.getSize(backpack)-1); //last slot in pack
                                                        slot.putStack(new ItemStack(recipeOutput.getItem(), recipeOutput.getMaxStackSize()-(recipeOutput.stackSize - stack.stackSize), recipeOutput.getItemDamage()));
                                                        break;
                                                    } else { //normal condition, stack was fully transferred
                                                        theSlot.decrStackSize(1);
                                                    }
                                                }
                                            }else {
                                                ItemStack myRecipeOutput = new ItemStack(recipeOutput.getItem(), numberOfItems, recipeOutput.getItemDamage());
                                                if (container.transferStackInSlot(myRecipeOutput) != null) {
                                                    theSlot.decrStackSize(theStack.stackSize - (theStack.stackSize % (craftingGridDiameterToFill * craftingGridDiameterToFill)));
                                                }
                                                container.save(event.entityPlayer);
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (shouldSave) {
                        container.sort(); //sort items
                        container.onContainerClosed(event.entityPlayer);
                    }
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
//                BackpackTypes type = BackpackTypes.values()[((ItemBackpackSubItems) backpack.getItem()).getGuiId()];
                ContainerBackpack container = new ContainerBackpack(new InventoryBackpack(event.entityPlayer, backpack));
                if (!(event.entityPlayer.openContainer instanceof ContainerBackpack)) { //can't have the backpack open
                    ArrayList<ItemStack> upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(backpack);

                    if (UpgradeMethods.hasFilterBasicUpgrade(upgrades))
                        transferWithBasicFilter(UpgradeMethods.getBasicFilterItems(backpack), event, container);

                    if (UpgradeMethods.hasFilterModSpecificUpgrade(upgrades))
                        transferWithModSpecificFilter(UpgradeMethods.getModSpecificFilterItems(backpack), event, container);

                    if (UpgradeMethods.hasFilterFuzzyUpgrade(upgrades))
                        transferWithFuzzyFilter(UpgradeMethods.getFuzzyFilterItems(backpack), event, container);

                    if (UpgradeMethods.hasFilterOreDictUpgrade(upgrades))
                        transferWithOreDictFilter(UpgradeMethods.getOreDictFilterItems(backpack), getOreDict(event.item.getEntityItem()), event, container);

                    if (UpgradeMethods.hasFilterAdvancedUpgrade(upgrades)) {
                        ItemStack[] advFilterItems = UpgradeMethods.getAdvFilterAllItems(backpack);
                        byte[] advFilterButtonStates = UpgradeMethods.getAdvFilterButtonStates(backpack);

                        transferWithBasicFilter(UpgradeMethods.getAdvFilterBasicItems(advFilterItems, advFilterButtonStates), event, container);
                        transferWithModSpecificFilter(UpgradeMethods.getAdvFilterModSpecificItems(advFilterItems, advFilterButtonStates), event, container);
                        transferWithFuzzyFilter(UpgradeMethods.getAdvFilterFuzzyItems(advFilterItems, advFilterButtonStates), event, container);
                        transferWithOreDictFilter(UpgradeMethods.getAdvFilterOreDictItems(advFilterItems, advFilterButtonStates), getOreDict(event.item.getEntityItem()), event, container);
                    }

                    if (UpgradeMethods.hasFilterMiningUpgrade(upgrades))
                        transferWithMiningFilter(UpgradeMethods.getMiningFilterItems(backpack), getOreDict(event.item.getEntityItem()), event, container);

                    if (UpgradeMethods.hasFilterVoidUpgrade(upgrades))
                        deleteWithVoidFilter(UpgradeMethods.getVoidFilterItems(backpack), event);

                }
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
        boolean shouldSave = false;
        for (ItemStack filterItem : filterItems) {
            if (filterItem != null) {
                if (IronBackpacksHelper.areItemsEqualForStacking(event.item.getEntityItem(), filterItem)) {
                    container.transferStackInSlot(event.item.getEntityItem());
                    shouldSave = true;
                }
            }
        }
        if (shouldSave) container.onContainerClosed(event.entityPlayer);
    }

    /**
     * Transfers items ignoring damage values.
     * @param filterItems - the items to check
     * @param event - EntityItemPickupEvent
     * @param container - the backpack to transfer items into
     */
    private void transferWithFuzzyFilter(ArrayList<ItemStack> filterItems, EntityItemPickupEvent event, ContainerBackpack container){
        boolean shouldSave = false;
        for (ItemStack filterItem : filterItems) {
            if (filterItem != null) {
                if (event.item.getEntityItem().getItem() == filterItem.getItem()) {
                    container.transferStackInSlot(event.item.getEntityItem()); //custom method to put itemEntity's itemStack into the backpack
                    shouldSave = true;
                }
            }
        }
        if (shouldSave) container.onContainerClosed(event.entityPlayer);
    }

    /**
     * Transfers items with respect to the ore dictionary
     * @param filterItems - the items to check
     * @param itemEntityOre - the ore dictionary entry of the item
     * @param event - EntityItemPickupEvent
     * @param container - the backpack to move items into
     */
    private void transferWithOreDictFilter(ArrayList<ItemStack> filterItems, ArrayList<String> itemEntityOre, EntityItemPickupEvent event, ContainerBackpack container){
        boolean shouldSave = false;
        for (ItemStack filterItem : filterItems) {
            if (filterItem != null) {
                ArrayList<String> filterItemOre = getOreDict(filterItem);
                if (itemEntityOre != null && filterItemOre != null) {
                    for (String oreName : itemEntityOre) {
                        if (oreName != null && filterItemOre.contains(oreName)) {
                            container.transferStackInSlot(event.item.getEntityItem()); //custom method to put itemEntity's itemStack into the backpack
                            shouldSave = true;
                        }
                    }
                }
            }
        }
        if (shouldSave) container.onContainerClosed(event.entityPlayer);
    }

    /**
     * Transfers items with respect to the category of the same mod
     * @param filterItems - the items to check
     * @param event - EntityItemPickupEvent
     * @param container - the backpack to move the items into
     */
    private void transferWithModSpecificFilter(ArrayList<ItemStack> filterItems, EntityItemPickupEvent event, ContainerBackpack container){
        boolean shouldSave = false;
        for (ItemStack filterItem : filterItems) {
            if (filterItem != null) {
                //if modId1 == modId2 same mod so transfer
                if (GameRegistry.findUniqueIdentifierFor(event.item.getEntityItem().getItem()).modId.equals(GameRegistry.findUniqueIdentifierFor(filterItem.getItem()).modId)){
                    container.transferStackInSlot(event.item.getEntityItem());
                    shouldSave = true;
                }
            }
        }
        if (shouldSave) container.onContainerClosed(event.entityPlayer);
    }

    /**
     * Transfers items with ore in the name
     * @param filterItems - the items to check
     * @param event - EntityItemPickupEvent
     * @param container - the backpack to move the items into
     */
    private void transferWithMiningFilter(ArrayList<ItemStack> filterItems, ArrayList<String> itemEntityOre, EntityItemPickupEvent event, ContainerBackpack container){
        boolean shouldSave = false;
        transferWithBasicFilter(filterItems, event, container);
        if (itemEntityOre != null) {
            for (String oreName : itemEntityOre) {
                //TODO: fancier checking method, this is a 'contains' so it will get extra items ex: 'mining c*ore*'
                if (oreName != null && (oreName.contains("ore") || oreName.contains("gem") || oreName.contains("dust"))) {
                    container.transferStackInSlot(event.item.getEntityItem()); //custom method to put itemEntity's itemStack into the backpack
                    shouldSave = true;
                }
            }
        }
        if (shouldSave) container.onContainerClosed(event.entityPlayer);
    }

    /**
     * Deletes items in the void filter by destroying the entityItem picked up intead of moving it into the backpack or elsewhere
     * @param filterItems - the items to delete
     * @param event - EntityItemPickupEvent
     */
    private void deleteWithVoidFilter(ArrayList<ItemStack> filterItems, EntityItemPickupEvent event){
        for (ItemStack stack : filterItems) {
            if (stack != null) {
                if (event.item.getEntityItem().getItem() == stack.getItem()){ //if same item (but different damage value)
                    event.item.setDead(); //delete it
                    event.item.onUpdate(); //update to make sure it's gone
                    event.setCanceled(true); //make sure it can't be picked up by other mods/vanilla
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
