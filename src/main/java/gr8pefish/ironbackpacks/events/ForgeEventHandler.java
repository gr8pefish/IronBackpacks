package gr8pefish.ironbackpacks.events;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerDeathBackpackCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.integration.InterModSupport;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.client.ClientEquippedPackMessage;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * All the events used that fire on the Forge Event bus
 */
public class ForgeEventHandler {


    //====================================================================== Player Capability Events =======================================================================

    /**
     * Attaches a capability to the entity. Works on both the client and server players.
     * @param event - attach capability event
     */
    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            if (!event.getObject().hasCapability(IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY, null)) {
                event.addCapability(new ResourceLocation(Constants.MODID + "." + Constants.WEARING_BACKPACK_CAPABILITY_STRING), new PlayerWearingBackpackCapabilities());
            }
            if (!event.getObject().hasCapability(IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY, null)) {
                event.addCapability(new ResourceLocation(Constants.MODID + "." + Constants.DEATH_BACKPACK_CAPABILITY_STRING), new PlayerDeathBackpackCapabilities());
            }
        }
    }


    /**
     * When a player dies and respawns we need to clone their old data over
     * @param event - the clone event
     */
    @SubscribeEvent
    public void onPlayerCloning(PlayerEvent.Clone event) {
        if (event.isWasDeath()) { //deal with dumb returning from the end code, have to only clone data when the player actually dies
            if (event.getOriginal().hasCapability(IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY, null)) {
                PlayerDeathBackpackCapabilities oldCap = event.getOriginal().getCapability(IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY, null);
//                NBTTagCompound tagCompound = oldCap.serializeNBT();
                PlayerDeathBackpackCapabilities newCap = event.getEntityPlayer().getCapability(IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY, null);
//                newCap.deserializeNBT(tagCompound); //ToDo: Test inclusion, as better than manually copying over?

                //update new data with old
                newCap.setEternityBackpacks(oldCap.getEternityBackpacks());
                newCap.setEquippedBackpack(oldCap.getEquippedBackpack());
            }
        } else { //return from end
            if (event.getOriginal().hasCapability(IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY, null)) {
                PlayerWearingBackpackCapabilities oldCap = event.getOriginal().getCapability(IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY, null);
//                NBTTagCompound tagCompound = oldCap.serializeNBT();
                PlayerWearingBackpackCapabilities newCap = event.getEntityPlayer().getCapability(IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY, null);
//                newCap.deserializeNBT(tagCompound);

                //update new data with old
                newCap.setCurrentBackpack(oldCap.getCurrentBackpack());
                newCap.setEquippedBackpack(oldCap.getEquippedBackpack());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDrops(PlayerDropsEvent event) {
        if (!event.getEntity().world.isRemote) { //server
            EntityItem drop = IronBackpacksHelper.savePlayerDeathDrops(event.getEntityPlayer()); //get equipped backpack, if it exists, and add it to drop
            if (drop != null) {
                event.getDrops().add(drop);
            }
        }
    }

    /**
     * When a player dies, check if player has any backpacks with keepOnDeathUpgrade so then they are saved for when they spawn
     * @param event - the event
     */
    @SubscribeEvent(priority = EventPriority.NORMAL) //ToDo: Change to PlayerDropsEvent
    public void onDeath(LivingDeathEvent event){
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer){ //server side
            IronBackpacksHelper.saveEternityBackpacksOnDeath((EntityPlayer) event.getEntity());
        }
    }

    /**
     * When a player respawns, check if player had any backpacks with keepOnDeathUpgrade so then they spawn with them
     * @param event - the event
     */
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event){
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer){ //server side
            IronBackpacksHelper.loadBackpackOnDeath((EntityPlayer) event.getEntity());
        }
    }


    /**
     * Used to make sure the player's equipped backpack is shown correctly
     * @param event - the player logged in event
     */
    @SubscribeEvent
    public void onPlayerLogIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event){

        ItemStack backpack = PlayerWearingBackpackCapabilities.getEquippedBackpack(event.player);

        if (backpack != null) { //ToDo: even update if null?

            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
//            PlayerBackpackProperties.setEquippedBackpack(event.player, backpack); //update server on correct pack //TODO: unnecessary?

//            if (!ConfigHandler.disableRendering) {
//                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
//            }
        }
    }


    /**
     * Used to make sure the player respawns with an equipped backpack if they should
     * @param event - the player respawn event
     */
    @SubscribeEvent
    public void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event){
        ItemStack backpack = PlayerWearingBackpackCapabilities.getEquippedBackpack(event.player);
        if (backpack != null) {

            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
//            PlayerBackpackProperties.setEquippedBackpack(event.player, backpack); //update server on correct pack

//            if (!ConfigHandler.disableRendering)
//                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
        }
    }

    /**
     * Used to make sure the equipped backpack transfers over correctly between dimensions
     * @param event - the change dimension event
     */
    @SubscribeEvent
    public void onPlayerDimChange(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event){
        ItemStack backpack = PlayerWearingBackpackCapabilities.getEquippedBackpack(event.player);
        if (backpack != null) {

            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
//            PlayerBackpackProperties.setEquippedBackpack(event.player, backpack); //TODO: test with these removed

//            if (!ConfigHandler.disableRendering)
//                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player); //spawn new pack
        } else {
            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(null), (EntityPlayerMP) event.player); //update client on correct pack
//            PlayerBackpackProperties.setEquippedBackpack(event.player, null);
        }
    }

    //====================================================================== Upgrade pseudo-tick Events =======================================================================

    /**
     * Called whenever an items is picked up by a player. The basis for all the filters, and the event used for the hopper/restocking and crafter/recipes upgrades too so it doesn't check too much and causes lag..
     * @param event - the event fired
     */
    //ToDo: Make this functional for offhand restocking
    @SubscribeEvent
    public void onItemPickupEvent(EntityItemPickupEvent event) {
        if (!event.isCanceled()) {
            ArrayList<ArrayList<ItemStack>> backpacks = IronBackpacksEventHelper.getFilterCrafterAndRestockerBackpacks(event.getEntityPlayer());

            //Any instance in which you need to restock onPickup? I don't think so, but leaving this here just in case I find a case I missed.
//            boolean doFilter = IronBackpacksEventHelper.checkRestockingUpgradeItemPickup(event, backpacks.get(4)); //doFilter is false if the itemEntity is in the restockerUpgrade's slots and the itemEntity's stackSize < refillSize

            IronBackpacksEventHelper.checkFilterUpgrade(event, backpacks.get(0)); //beware creative testing takes the itemstack still

            for (int i = 1; i < 4; i++) {
                IronBackpacksEventHelper.checkCrafterUpgrade(event, backpacks.get(i), i);//1x1, 2x2, and 3x3 crafters/crafters
            }
        }
    }

    /**
     * Called whenever the player right clicks an item. Used for the restocking(hopper) upgrade.
     * @param event - the event fired
     */
    //ToDo: Make this functional for offhand restocking
    @SubscribeEvent
    public void onPlayerItemUseEvent(PlayerInteractEvent.RightClickItem event){
        if (!event.isCanceled()) { //only do it for main hand clicks
            doRestock(event.getEntityPlayer(), event.getItemStack());
        }
    }


    /**
     * Called whenever the player right clicks a block. Used for the restocking(hopper) upgrade.
     * @param event - the event fired
     */
    //ToDo: Make this functional for offhand restocking
    @SubscribeEvent
    public void onPlayerRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event){
        if (!event.isCanceled()) { //only do it for main hand clicks

            ItemStack itemStack = null;
            ArrayList<ArrayList<ItemStack>> backpacks = null;

            //ray trace the block placed
            RayTraceResult rayTraceResult = event.getWorld().rayTraceBlocks(event.getEntityPlayer().getPositionVector(), event.getEntityPlayer().getLookVec());
            if (rayTraceResult == null) return; //didn't raytrace correctly, return
            //get the block targeted as an itemstack
            itemStack = event.getWorld().getBlockState(event.getPos()).getBlock().getPickBlock(event.getWorld().getBlockState(event.getPos()), rayTraceResult, event.getWorld(), event.getPos(), event.getEntityPlayer());
            //get backpack
            backpacks = IronBackpacksEventHelper.getFilterCrafterAndRestockerBackpacks(event.getEntityPlayer());


            //do restock if valid
            if (event.getEntityPlayer().getHeldItem(event.getHand()) != null && itemStack != null && backpacks != null && !backpacks.get(4).isEmpty()) { //null checks and has a backpack to restock from
                if (!IronBackpacksHelper.areItemsEqualForStacking(event.getEntityPlayer().getHeldItem(event.getHand()), itemStack)) { //if item in hand != item placed then interacting with something else and try to restock
                    doRestock(event.getEntityPlayer(), event.getItemStack());
                }
            }


        }
    }

    private static void doRestock(EntityPlayer player, ItemStack stack) {
        ItemStack resuppliedStack;
        ArrayList<ArrayList<ItemStack>> backpacks = IronBackpacksEventHelper.getFilterCrafterAndRestockerBackpacks(player);
        if (player != null && stack != null) {
            resuppliedStack = IronBackpacksEventHelper.checkRestockerUpgradeItemUse(player, stack, backpacks.get(4)); //reduce the stack in the backpack if you can refill and send back the refilled itemStack
            if (resuppliedStack != null) {
                stack.setCount(resuppliedStack.getCount()); //set the new stack size (as you can't/don't need to directly replace the stack)
            }
        }
    }

    /**
     * Called whenever the player places a block. Used for the restocking(hopper) upgrade.
     * @param event - the event fired
     */
    @SubscribeEvent
    public void onBlockPlacedEvent(BlockEvent.PlaceEvent event) {

        if (!event.isCanceled()) { //only do it when I should

            //ExUtils builders wands broken b/c RWTema does weird things, disabled compat
            if (event.getPlayer().getHeldItem(event.getHand()) != null && InterModSupport.isExtraUtilsLoaded && InterModSupport.isExUtilsBuildersWand(event.getPlayer().getHeldItem(event.getHand()).getItem())) return;
            //ToDo To Fix: instead of return directly, set a static class-level bool updateRestockNextTick true first, and make a main tick handler which updates if true and sets the bool back to false, so that it updates *after* the event and works

            //get all the backpacks to restock with
            ArrayList<ArrayList<ItemStack>> backpacks = IronBackpacksEventHelper.getFilterCrafterAndRestockerBackpacks(event.getPlayer());

            //ray trace the block placed
            RayTraceResult rayTraceResult = event.getWorld().rayTraceBlocks(event.getPlayer().getPositionVector(), event.getPlayer().getLookVec());
            //get the block as an itemstack
            ItemStack itemStackPlaced = event.getPlacedBlock().getBlock().getPickBlock(event.getPlacedBlock(), rayTraceResult, event.getWorld(), event.getPos(), event.getPlayer());

            if (event.getPlayer().getHeldItem(event.getHand()) != null && itemStackPlaced != null && !backpacks.get(4).isEmpty()) { //null checks and has a backpack to restock from
                if (!IronBackpacksHelper.areItemsEqualForStacking(event.getPlayer().getHeldItem(event.getHand()), itemStackPlaced)) { //if item in hand != item placed, if not the same then placed with some other method and have to scan inv
                    //pass that itemstack (along with other things) to a delegating method to deal with restocking for other methods
                    IronBackpacksEventHelper.handleIndirectRestock(event.getPlayer(), backpacks.get(4), itemStackPlaced);
                } else { //normally placed item
                    IronBackpacksEventHelper.handleDirectRestock(event.getPlayer(), backpacks.get(4), event.getPlayer().getHeldItem(event.getHand()), true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event) {
        if (!event.isCanceled()){ //only do it for main hand clicks
            ArrayList<ArrayList<ItemStack>> backpacks = IronBackpacksEventHelper.getFilterCrafterAndRestockerBackpacks(event.getEntityPlayer());
            ImmutablePair<ItemStack, Slot> pair = IronBackpacksEventHelper.checkRestockerUpgradeArrowLoose(event.getEntityPlayer(), backpacks.get(4)); //reduce the stack in the backpack if you can refill and send back the refilled itemStack
            if (pair != null) {
                event.getEntityPlayer().inventory.setInventorySlotContents(pair.getRight().getSlotIndex(), pair.getLeft());
//                event.getItemStack().stackSize = resuppliedStack.stackSize; //set the new stack size (as you can't/don't need to directly replace the stack)
            }
        }
    }

    //ToDo: Decide to include or not
    //The issue is that the client needs to send the message, but also needs to know to cancel or not. The server event also needs to know to cancel itself or not.
//    @SubscribeEvent
//    public void onBlockClicked(PlayerInteractEvent.RightClickBlock event){ //ToDo: keep code DRY (see ItemBackpack's deposit code)
//
//        if (!event.isCanceled()) { //runs on both sides
//
//            EntityPlayer player = event.getEntityPlayer();
//            ItemStack itemstack = IronBackpacksCapabilities.getWornBackpack(event.getEntityPlayer()); //check equipped pack
//            boolean openAltGui = true;
//
////            if (event.getWorld().isRemote) { //client side
////                if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) { //left control
////                    NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.DEPOSIT_FROM_EQUIPPED_BACKPACK_KEYBINDING)); //send message to server to deal with logic
////                    setCanceledOrNotSomehow
////                }
////            } else {
////                setCanceledOrNotSomehow
////            }
//
//            if (player.isSneaking() && itemstack != null) { //only do it when player is sneaking and has a backpack equipped
//
//                //deal with shift clicking while holding an item that can be placed (e.g. a hopper on a chest)
//                ItemStack stackHeld = event.getItemStack();
//                ItemStack offhandHeld = event.getEntityPlayer().getHeldItemOffhand();
//
//                if (stackHeld != null || offhandHeld != null) return; //enforce that it only works with an empty hand on both main and offhand
//                //some terrible code to deal with event calling incorrectly on server side, BUT IT WORKS ToDo: Clean this up or delete if it won't work
////                if (stackHeld != null && stackHeld.getItem() instanceof ItemBlock) {
////                    ItemBlock itemblock = (ItemBlock)stackHeld.getItem();
////                    if ((event.getSide() == Side.SERVER) || !itemblock.canPlaceBlockOnSide(event.getWorld(), event.getPos(), event.getFace(), player, stackHeld)) { //placable block, return
////                        return;
////                    }
////                }
//
//                World world = event.getWorld();
//                BlockPos pos = event.getPos();
//                EnumFacing side = event.getFace();
//
//                ArrayList<ItemStack> upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(itemstack);
//                boolean hasDepthUpgrade = UpgradeMethods.hasDepthUpgrade(upgrades);
//                if (UpgradeMethods.hasQuickDepositUpgrade(upgrades)) {
//                    openAltGui = !UpgradeMethods.transferFromBackpackToInventory(player, itemstack, world, pos, side, false);
//                    if (!hasDepthUpgrade)
//                        if (!openAltGui) event.setCanceled(true);
//                } else if (UpgradeMethods.hasQuickDepositPreciseUpgrade(upgrades)) {
//                    openAltGui = !UpgradeMethods.transferFromBackpackToInventory(player, itemstack, world, pos, side, true);
//                    if (!hasDepthUpgrade)
//                        if (!openAltGui) event.setCanceled(true);
//                }
//                boolean openAltGuiDepth;
//                if (hasDepthUpgrade) {
//                    ContainerBackpack container = new ContainerBackpack(new InventoryBackpack(player, itemstack));
//                    for (int j = 0; j < container.getInventoryBackpack().getSizeInventory(); j++) {
//                        ItemStack nestedBackpack = container.getInventoryBackpack().getStackInSlot(j);
//                        if (nestedBackpack != null && nestedBackpack.getItem() != null && nestedBackpack.getItem() instanceof IBackpack) {
//                            ArrayList<ItemStack> nestedUpgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(nestedBackpack);
//                            if (UpgradeMethods.hasQuickDepositUpgrade(nestedUpgrades)) {
//                                openAltGuiDepth = !UpgradeMethods.transferFromBackpackToInventory(player, nestedBackpack, world, pos, side, false);
//                                if (!openAltGuiDepth) openAltGui = false;
//                            } else if (UpgradeMethods.hasQuickDepositPreciseUpgrade(nestedUpgrades)) {
//                                openAltGuiDepth = !UpgradeMethods.transferFromBackpackToInventory(player, nestedBackpack, world, pos, side, true);
//                                if (!openAltGuiDepth) openAltGui = false;
//                            }
//                        }
//                    }
//                    if (!openAltGui) event.setCanceled(true);
//                }
//            }
//        }
//    }

    //====================================================================== Misc. Events =======================================================================

    /**
     * Fires whenever an item is placed in both slots of an anvil. Used to disable processing if one of the items is a backpack.
     * @param event - anvil update event
     */
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if ((event.getLeft().getItem() instanceof ItemBackpack) || (event.getRight().getItem() instanceof ItemBackpack)) { //if a backpack in an anvil slot
            //probably overkill, but making sure it can't process
            event.setOutput(null);
            event.setResult(Event.Result.DENY);
            event.setCanceled(true);
        }
    }

    /**
     *  When the config is changed this will reload the changes to ensure it is correctly updated
     * @param event - the event
     */
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Constants.MODID)) {
            ConfigHandler.syncConfig(false);
        }
    }

    /**
     * Update the player to make rendering work for others.
     * @param event
     */
    @SubscribeEvent
    public void onTrack(net.minecraftforge.event.entity.player.PlayerEvent.StartTracking event) {
        EntityPlayer tracker = event.getEntityPlayer(); //the tracker
        Entity targetEntity = event.getTarget(); //the target that is being tracked
        if (targetEntity instanceof EntityPlayerMP) { //only entityPlayerMP ( MP part is very important :/ )
            EntityPlayer targetPlayer = (EntityPlayer) targetEntity; //typecast to entityPlayer
            if (targetPlayer.hasCapability(IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY, null)) { //if have the capability
                ItemStack backpack = IronBackpacksCapabilities.getWornBackpack(targetPlayer);
                if (backpack != null) { //if the target is wearing a backpack need to update
                    NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) tracker); //send a packet to the tracker's client to update their target
                } else {
                    NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) tracker);
                }
            }
        }
    }

}
